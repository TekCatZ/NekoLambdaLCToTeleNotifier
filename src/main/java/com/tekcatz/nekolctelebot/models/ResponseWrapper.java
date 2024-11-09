package com.tekcatz.nekolctelebot.models;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    T data;
}
