package com.blogsystem.enums;

import java.util.Arrays;

public enum ServiceErrorDesc {
    SUCCESS(0, "Giao dịch thành công", ""),
    BAD_FORMAT(1, "Tham số không đúng", ""),
    SYSTEM_ERROR(2, "Lỗi hệ thống", ""),
    AUTH_EXPIRED(3, "Xác thực hết hạn", ""),
    AUTH_BAD_TOKEN(4, "Hệ thống đang tạm gián đoạn, vui lòng thử lại sau ít phút", ""),
    ERROR_SYSTEM_OVERLOADED(5, "Số người truy cập đang đông.", ""),
    INVALID_PARAMS(6, "Tham số đầu vào không hợp lệ", ""),
    IN_TIMEOUT(7, "Quá thời gian kết nối", "In Timeout"),
    UNAUTHORIZED_CODE(8, "Tài khoản chưa được xác thực", ""),
    FORBIDDEN_CODE(9, "Không có quyền truy cập", "In Timeout"),
    USER_NOT_FOUND(10, "Không tìm thấy người dùng", ""),
    RESOURCE_NOT_FOUND(10, "Không tìm thấy tài nguyên", "")
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
