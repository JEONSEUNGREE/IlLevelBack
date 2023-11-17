package com.trip.penguin.constant;

public enum CommonConstant {

    SUCCESS("success"), FAIL("fail"), Y("y"), N("n");

    private String name;

    CommonConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
