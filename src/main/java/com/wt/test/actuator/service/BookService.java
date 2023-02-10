package com.wt.test.actuator.service;

import com.wt.test.actuator.entity.Book;
import com.wt.test.actuator.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qiyu
 * @date 2023/2/10
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final TestMapper testMapper;

    @Transactional(rollbackFor = Exception.class)
    public void testInsertNew() {
        Book newaBook = new Book();
        testMapper.insert(newaBook);
        log.info("TestService insert new book {}",newaBook.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void testInsertOld() {
        Book book = new Book(1L);
        log.info("TestService insert book 1");
        testMapper.insert(book);
    }
}
