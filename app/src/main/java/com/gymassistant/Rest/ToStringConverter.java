package com.gymassistant.Rest;


import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Converter;

public final class ToStringConverter implements Converter<String> {

    @Override
    public String fromBody(ResponseBody body) throws IOException {
        return body.string();
    }

    @Override
    public RequestBody toBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}
