package com.dream.dw.response;

/**
 * Response result.
 */
public class ResponsesBody<T> {
    public static final String SUCCESS = "success";

    public static final String ERROR = "error";

    public static final String NO_RELATED_DATA = "no related data";

    private Integer errorCode;

    private String message;

    private T result;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void updateResponseResult(Integer errorCode, String message, T result)
    {
        this.errorCode = errorCode;
        this.result = result;
        this.message = message;
    }

    public ResponsesBody() {
    }

    public ResponsesBody(Integer errorCode, String message, T result)
    {
        this.errorCode = errorCode;
        this.message = message;
        this.result = result;
    }

    public static ResponsesBody newResponsesBody()
    {
        return new ResponsesBody();
    }

}
