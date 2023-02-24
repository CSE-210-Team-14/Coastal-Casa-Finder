package com0.coastalcasa.Utils;

import lombok.Data;

@Data
public class ResponseInfo<T> {

    protected int status;

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
        this.status = ResponseCodeEnums.SUCCESS.getCode();
        this.message = ResponseCodeEnums.SUCCESS.getMessage();
    }

    public ResponseInfo(ResponseCodeEnums statusEnums) {
        this.status = statusEnums.getCode();
        this.message = statusEnums.getMessage();
    }

    public ResponseInfo(int status, String msg) {
        this.status = status;
        this.message = msg;
    }

    public ResponseInfo(T data) {
        this.data = data;
        this.status = ResponseCodeEnums.SUCCESS.getCode();
        this.message = ResponseCodeEnums.SUCCESS.getMessage();
    }


    public ResponseInfo(T data, String msg) {
        this.data = data;
        this.status = ResponseCodeEnums.SUCCESS.getCode();
        this.message = msg;
    }

}