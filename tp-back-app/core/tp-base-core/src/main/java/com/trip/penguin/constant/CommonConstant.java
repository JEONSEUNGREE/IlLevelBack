package com.trip.penguin.constant;

public enum CommonConstant {

    SUCCESS("success"), FAIL("fail"), Y("Y"), N("N"), ACCOUNT_TOKEN("account_token");

    private String name;

    CommonConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
