package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book_order")
public class BookOrder {
    @TableId(type = IdType.AUTO)
    private Integer id;//借阅记录id
    private Integer bookId;//图书 id
    private String bookName;//图书名
    private String username;//唯一用户名
    private Integer status;//1借阅中 0已归还
    private String borrowDate;//借阅日期
    private String returnDate;//应还日期 = 借阅时间 + 3个月
    private String actualReturnDate;//实际归还日期 status为 0 时为 null（未归还）
}
