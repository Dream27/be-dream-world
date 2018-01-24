package com.dream.dw.response;


import com.alibaba.fastjson.JSONObject;
import com.dream.dw.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Response wrapper.
 */
public class Responses {

    private static final Logger log = LoggerFactory.getLogger(Responses.class);

    /**
     * OK response. (200)
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<ResponsesBody> ok(T result) {
        return new ResponseEntity<>(new ResponsesBody<>(ErrorCode.ErrorCode_0000, ErrorCode.getMessage(ErrorCode.ErrorCode_0000), result), HttpStatus.OK);
    }

    /**
     * Error response. (500)
     * @param errorCode
     * @return
     */
    public static ResponseEntity<ResponsesBody> error(Integer errorCode) {
        return error(errorCode, ErrorCode.getMessage(errorCode));
    }

    /**
     * Error response.
     * @param errorCode
     * @return
     */
    public static <T> ResponseEntity<ResponsesBody> error(Integer errorCode, T result) {
        log.error("returning error with errorCode:{}, error message:{}, result:{}", errorCode, ErrorCode.getMessage(errorCode), JSONObject.toJSONString(result));
        return new ResponseEntity<>(new ResponsesBody<>(errorCode, ErrorCode.getMessage(errorCode), result), HttpStatus.BAD_REQUEST);
    }

    /**
     * Error response.
     * @param errorCode
     * @return
     */
    public static <T> ResponseEntity<ResponsesBody> error(Integer errorCode, String message, T result) {
        log.error("returning error with errorCode:{}, error message:{}, body:{}", errorCode, message, JSONObject.toJSONString(result));
        return new ResponseEntity<>(new ResponsesBody<>(errorCode, message, result), HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ResponsesBody> unauth(Integer errorCode, T result) {
        log.error("returning unauth error with errorCode:{}, error message:{}, body:{}", errorCode, ErrorCode.getMessage(errorCode), JSONObject.toJSONString(result));
        return new ResponseEntity<>(new ResponsesBody<>(errorCode, ErrorCode.getMessage(errorCode), result), HttpStatus.UNAUTHORIZED);
    }

}
