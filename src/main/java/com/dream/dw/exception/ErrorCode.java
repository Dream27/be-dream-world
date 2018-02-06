package com.dream.dw.exception;

import lombok.Data;

import java.util.HashMap;

@Data
public class ErrorCode {

    /*login module error code 000X
    * user module error code 00X0
    * travelnote module error code 0X00
    * */
    /*response ok*/
    public static final Integer ErrorCode_0000 = 0;
    /*username/email or password error*/
    public static final Integer ErrorCode_1000 = 1000;
    public static final Integer ErrorCode_1001 = 1001;
    public static final Integer ErrorCode_1002 = 1002;
    public static final Integer ErrorCode_1003 = 1003;
    public static final Integer ErrorCode_1004 = 1004;
    public static final Integer ErrorCode_1005 = 1005;
    public static final Integer ErrorCode_1006 = 1006;
    public static final Integer ErrorCode_1100 = 1100;
    public static final Integer ErrorCode_1101 = 1101;
    public static final Integer ErrorCode_1102 = 1102;
    public static final Integer ErrorCode_1103 = 1103;
    public static final Integer ErrorCode_1104 = 1104;
    public static final Integer ErrorCode_1105 = 1105;
    public static final Integer ErrorCode_1106 = 1106;
    public static final Integer ErrorCode_1107 = 1107;
    public static final Integer ErrorCode_1108 = 1108;

    private static HashMap<Integer, String> error = new HashMap<>();

    static {
        error.put(ErrorCode_0000, "response ok");
        error.put(ErrorCode_1000, "username/email or password error");
        error.put(ErrorCode_1001, "user has login");
        error.put(ErrorCode_1002, "register message error");
        error.put(ErrorCode_1003, "active error");
        error.put(ErrorCode_1004, "user not found by id");
        error.put(ErrorCode_1005, "delete user error");
        error.put(ErrorCode_1006, "update user info error");
        error.put(ErrorCode_1100, "travelnode not found by id");
        error.put(ErrorCode_1101, "add travelnode error");
        error.put(ErrorCode_1102, "delete travelnode error");
        error.put(ErrorCode_1103, "update travelnode error");
        error.put(ErrorCode_1104, "increase browser count error");
        error.put(ErrorCode_1105, "increase collect count error");
        error.put(ErrorCode_1106, "increase like count error");
        error.put(ErrorCode_1107, "upload img error");
        error.put(ErrorCode_1108, "delete img error");
    }

    public static String getMessage(Integer code) {
        return error.get(code);
    }

}
