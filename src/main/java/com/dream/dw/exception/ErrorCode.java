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
    public static final Integer ErrorCode_0001 = 1;
    public static final Integer ErrorCode_0002 = 2;
    public static final Integer ErrorCode_0003 = 3;
    public static final Integer ErrorCode_0004 = 4;
    public static final Integer ErrorCode_0010 = 10;
    public static final Integer ErrorCode_0020 = 20;
    public static final Integer ErrorCode_0030 = 30;
    public static final Integer ErrorCode_0100 = 100;
    public static final Integer ErrorCode_0200 = 200;
    public static final Integer ErrorCode_0300 = 300;
    public static final Integer ErrorCode_0400 = 400;
    public static final Integer ErrorCode_0500 = 500;
    public static final Integer ErrorCode_0600 = 600;
    public static final Integer ErrorCode_0700 = 700;
    public static final Integer ErrorCode_0800 = 800;
    public static final Integer ErrorCode_0900 = 900;

    private static HashMap<Integer, String> error = new HashMap<>();

    static {
        error.put(ErrorCode_0000, "response ok");
        error.put(ErrorCode_0001, "username/email or password error");
        error.put(ErrorCode_0002, "user has login");
        error.put(ErrorCode_0003, "register message error");
        error.put(ErrorCode_0004, "active error");
        error.put(ErrorCode_0010, "user not found by id");
        error.put(ErrorCode_0020, "delete user error");
        error.put(ErrorCode_0030, "update user info error");
        error.put(ErrorCode_0100, "travelnode not found by id");
        error.put(ErrorCode_0200, "add travelnode error");
        error.put(ErrorCode_0300, "delete travelnode error");
        error.put(ErrorCode_0400, "update travelnode error");
        error.put(ErrorCode_0500, "increase browser count error");
        error.put(ErrorCode_0600, "increase collect count error");
        error.put(ErrorCode_0700, "increase like count error");
        error.put(ErrorCode_0800, "upload img error");
        error.put(ErrorCode_0900, "delete img error");
    }

    public static String getMessage(Integer code) {
        return error.get(code);
    }

}
