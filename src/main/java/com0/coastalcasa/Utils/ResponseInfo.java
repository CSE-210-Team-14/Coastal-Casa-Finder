package com0.coastalcasa.Utils;

import lombok.Data;

@Data
public class ResponseInfo<T> {

    protected int code;

    protected String message;

    private T data;

    public static <T> ResponseInfo<T> success() {
        return new ResponseInfo<>();
    }

    public static <T> ResponseInfo<T> success(T data) {
        return new ResponseInfo<>(data);
    }

    public static <T> ResponseInfo<T> fail(String message) {
        return new ResponseInfo<>(ResponseCodeEnums.FAIL.getCode(), message);
    }

    public static <T> ResponseInfo<T> fail() {
        return new ResponseInfo<>(ResponseCodeEnums.FAIL.getCode(), ResponseCodeEnums.FAIL.getMessage());
    }

    public ResponseInfo() {
        this.code = ResponseCodeEnums.SUCCESS.getCode();
        this.message = ResponseCodeEnums.SUCCESS.getMessage();
    }

    public ResponseInfo(ResponseCodeEnums statusEnums) {
        this.code = statusEnums.getCode();
        this.message = statusEnums.getMessage();
    }

    public ResponseInfo(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResponseInfo(T data) {
        this.data = data;
        this.code = ResponseCodeEnums.SUCCESS.getCode();
        this.message = ResponseCodeEnums.SUCCESS.getMessage();
    }


    public ResponseInfo(T data, String msg) {
        this.data = data;
        this.code = ResponseCodeEnums.SUCCESS.getCode();
        this.message = msg;
    }

}