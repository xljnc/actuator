package com.wt.test.actuator.service;

import com.wt.test.actuator.entity.Book;
import com.wt.test.actuator.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qiyu
 * @date 2023/2/9
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final BookService bookService;

//    @Retryable
    @Retryable(stateful = true)
    @Transactional(rollbackFor = Exception.class)
    public void testRetry() {
        log.info("TestService testRetry");
//        throw new RuntimeException("manual throw exception");
        throw new DuplicateKeyException("manual throw exception");
    }

    @Retryable(stateful = true)
//    @Retryable
    public void testTransaction() {
        bookService.testInsertNew(); //有事务
        bookService.testInsertOld();//有事务,throw DuplicateKeyException
    }

    @Retryable(stateful = true)
    @Transactional(rollbackFor = Exception.class)
    public void testTransactionManual() {
//        throw new RuntimeException("manual throw exception");
        bookService.testInsertOld();
    }

    @Recover
    public void recover(Exception e) {
        log.error("TestService recover", e);
    }
}
