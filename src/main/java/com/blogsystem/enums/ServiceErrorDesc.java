package com.blogsystem.enums;

import java.util.Arrays;

public enum ServiceErrorDesc {
    SUCCESS(0, "Successful", ""),
    BAD_FORMAT(1, "Malformed request syntax, invalid request message", ""),
    SYSTEM_ERROR(2, "System Error", ""),
    AUTH_EXPIRED(3, "Token is expired", ""),
    AUTH_BAD_TOKEN(4, "Hệ thống đang tạm gián đoạn, vui lòng thử lại sau ít phút", ""),
    ERROR_SYSTEM_OVERLOADED(5, "Số người truy cập đang đông.", ""),
    INVALID_PARAMS(6, "Invalid Param", ""),
    IN_TIMEOUT(7, "Timeout", "In Timeout"),
    UNAUTHORIZED_CODE(8, "Account is still unauthenticated", ""),
    FORBIDDEN_CODE(9, "Don't have permission to access this resource", ""),
    USER_NOT_FOUND(10, "Không tìm thấy người dùng", ""),
    RESOURCE_NOT_FOUND(10, "Not found this resource", "")
    ;
    private final String desc;
    private final int val;
    private final String en;

    private ServiceErrorDesc(int val, String desc, String en) {
        this.val = val;
        this.desc = desc;
        this.en = en;
    }

    public String getDesc() {
        return desc;
    }

    public int getVal() {
        return val;
    }

    public String getEn() {
        return en;
    }

    public static ServiceErrorDesc getServiceErrorByVal(int val) {
        return Arrays.stream(ServiceErrorDesc.values()).filter(serviceErrorDesc -> serviceErrorDesc.val == val)
                .findFirst().orElse(SYSTEM_ERROR);
    }

    public static String getDescByVal(int val) {
        var err = Arrays.stream(ServiceErrorDesc.values()).filter(serviceErrorDesc -> serviceErrorDesc.val == val)
                .findFirst().orElse(SYSTEM_ERROR);
        return err.desc;
    }
}
