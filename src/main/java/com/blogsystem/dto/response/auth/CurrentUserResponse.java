package com.blogsystem.dto.response.auth;

import com.blogsystem.dto.response.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CurrentUserResponse extends BaseResponse {
    private UUID userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private String fullName;
    private List<String> roles;
    private List<String> permissions;

}
