package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book")
public class Book {
    @TableId(type = IdType.AUTO)
    private Integer id;//图书id
    private String title;//书名
    private String author;//作者
    private String publisher;//出版社
    private String pubDate;//发布日期
    private String isbn;//isbn编号
    private Double price;//价格
    private Integer total;//总量
    private Integer lend;//已借出
    private Integer remaining;//余量
}
