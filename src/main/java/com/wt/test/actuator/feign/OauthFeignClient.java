package com.wt.test.actuator.feign;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;
import java.util.Map;

/**
 * @author 一贫
 * @date 2021/1/4
 */
@FeignClient(name = "his", configuration = FeignConfig.class)
public interface OauthFeignClient {

    @RequestLine("POST")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Map<String,String> post(URI uri);

    @RequestLine("GET")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Map<String,String> get(URI uri);
}
