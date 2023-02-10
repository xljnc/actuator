package com.wt.test.actuator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qiyu
 * @date 2023/2/10
 */
@TableName("book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @TableId("id")
    private Long id;
}
