package com.wt.test.actuator.controller;

import com.wt.test.actuator.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiyu
 * @date 2023/2/10
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final TestService testService;

    @GetMapping("/retry")
    public void retry(){
        try {
            testService.testRetry();
        }catch (Exception e){
            log.error("TestController retry error",e);
        }
    }

    @GetMapping("/transaction")
    public void transaction(){
        try {
            testService.testTransaction();
        }catch (Exception e){
            log.error("TestController transaction error",e);
        }
    }

    @GetMapping("/transaction/manual")
    public void transactionManual(){
        try {
            testService.testTransactionManual();
        }catch (Exception e){
            log.error("TestController transactionManual error",e);
        }
    }
}
