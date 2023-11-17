package com.trip.penguin.response;


import com.trip.penguin.constant.CommonConstant;
import lombok.*;

@Builder
public class JsonResponse {

    public String message;

    public CommonConstant result;

    public Object data;
}
