package com.wt.test.actuator.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.wt.test.actuator.feign.JacksonUtil;
import com.wt.test.actuator.feign.OauthFeignClient;
import com.wt.test.actuator.feign.UriUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 一贫
 * @date 2021/8/17
 */
@RequestMapping("/oauth2")
@Controller
public class OAuth2Controller {

    @Autowired
    private OauthFeignClient oauthFeignClient;

    @Autowired
    private JacksonUtil jacksonUtil;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(@RequestParam("code") String code, Model model) {
        System.out.println(code);
        String tokenUrl = new StringBuilder("https://gitee.com/oauth/token?grant_type=authorization_code&").append("client_id=").append("")
                .append("&client_secret=").append("")
                .append("&code=").append(code).append("&redirect_uri=").append("http://localhost:8888/oauth2/welcome").toString();
        JsonNode response = oauthFeignClient.post(UriUtil.convert(tokenUrl));
        System.out.println("access-token:" + jacksonUtil.writeValueAsString(response));
        String token = response.get("access_token").asText();
        String infoUrl = new StringBuilder("https://gitee.com/api/v5/user?access_token=").append(token).toString();
        response = oauthFeignClient.get(UriUtil.convert(infoUrl));
        System.out.println("info:" + jacksonUtil.writeValueAsString(response));
        model.addAttribute("name", response.get("name").asText());
        return "welcome";
    }
}
