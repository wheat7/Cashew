package com.wheat7.cashew.http;

/**
 * Created by wheat7 on 2017/08/10
 */

public class ApiException extends RuntimeException {

    private static String msg;

    public ApiException(String detailMessage) {
        super(detailMessage);
        this.msg = detailMessage;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
