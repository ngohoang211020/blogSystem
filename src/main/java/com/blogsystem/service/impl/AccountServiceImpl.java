package com.blogsystem.service.impl;

import com.blogsystem.cache.CacheUtil;
import com.blogsystem.cloudinary.CloudinarySubPath;
import com.blogsystem.cloudinary.CloudinaryUtil;
import com.blogsystem.cloudinary.dto.AccountPictureUtil;
import com.blogsystem.common.constant.BlogSystemConstant;
import com.blogsystem.dto.auth.*;
import com.blogsystem.entity.UserEntity;
import com.blogsystem.enums.ServiceErrorDesc;
import com.blogsystem.enums.StatusType;
import com.blogsystem.exception.ObjectNotFoundException;
import com.blogsystem.pubsub.event.OTPRegistrationEmailEvent;
import com.blogsystem.pubsub.publisher.EmailPublisher;
import com.blogsystem.repository.UserRepository;
import com.blogsystem.security.util.CurrentUserUtils;
import com.blogsystem.service.AccountService;
import com.blogsystem.util.FileUtil;
import com.blogsystem.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailPublisher emailPublisher;
    private final CacheUtil cacheUtil;
    private final AccountPictureUtil accountPictureUtil;

    @Transactional
    @Override
    public RegisterAccountResponse register(RegisterAccountRequest request) throws IOException {
        var user = buildUser(request);
        user = userRepository.save(user);

        var otp = OTPUtil.generateOTP();
        cacheUtil.saveOTPRegistration(user.getEmail(), otp);

        var otpEvent = buildRegistrationEvent(user,otp);

        var profilePictureUrl = accountPictureUtil.presign(user.getProfilePicture());
        otpEvent.setProfilePicture(profilePictureUrl);
        emailPublisher.publishSendEmailEvent(otpEvent);

        return new RegisterAccountResponse(user.getEmail(), user.getUserId(),profilePictureUrl);
    }

    @Transactional
    @Override
    public VerifyOTPResponse verifyOTP(VerifyOTPRequest verifyOTPRequest) {
        var email = verifyOTPRequest.getEmail();
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new ObjectNotFoundException("Account is not found by this email.")
        );
        var providedOTP = verifyOTPRequest.getOtp();
        var cachedOTP = cacheUtil.getOTPRegistration(user.getEmail());
        var verifyOTPResp = new VerifyOTPResponse();
        if(cachedOTP.isBlank()||cachedOTP.isEmpty()||!cachedOTP.equals(providedOTP)){
            verifyOTPResp.setVerified(false);
        } else {
            verifyOTPResp.setVerified(true);
            user.setStatus(StatusType.ACTIVE.getValue());
            userRepository.save(user);

            cacheUtil.deleteOTPRegistration(email);
        }
        return verifyOTPResp;
    }

    @Override
    public CurrentUserResponse getCurrentUser() {
        var currentUserResp = new CurrentUserResponse();
        var user = CurrentUserUtils.getCurrentUser();
        if (user == null) {
            currentUserResp.setCode(ServiceErrorDesc.USER_NOT_FOUND.getVal());
            return currentUserResp;
        }
        var userEntity = userRepository.findByUsername(user.getName()).orElseThrow(
                () -> new UsernameNotFoundException("The user name or password is not correct.")
        );

        currentUserResp.setUserId(userEntity.getUserId());
        currentUserResp.setEmail(userEntity.getEmail());
        currentUserResp.setPhoneNumber(userEntity.getPhoneNumber());
        currentUserResp.setFullName(userEntity.getFullName());
        currentUserResp.setUsername(userEntity.getUsername());
        currentUserResp.setProfilePicture(accountPictureUtil.presign(userEntity.getProfilePicture()));
        return currentUserResp;
    }

    private UserEntity buildUser(RegisterAccountRequest request) {
        var user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setStatus(StatusType.INACTIVE.getValue());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setProfilePicture(request.getProfilePicture());
        return user;
    }
    private OTPRegistrationEmailEvent buildRegistrationEvent(UserEntity user, String otp){
        var otpEvent = new OTPRegistrationEmailEvent(this);
        otpEvent.setOtp(otp);
        otpEvent.setDuration(BlogSystemConstant.REGISTRATION_OTP_DURATION_MINUTES);
        otpEvent.setUsername(user.getUsername());
        otpEvent.setEmail(user.getEmail());
        return otpEvent;
    }
}
