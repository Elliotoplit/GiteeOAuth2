package com.example;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Service.BookOrderService;
import com.example.Service.BookService;
import com.example.Service.UserService;
import com.example.controller.BookOrderController;
import com.example.dao.BookDao;
import com.example.domain.Book;
import com.example.domain.BookOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GiteeOAuth2ApplicationTests {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookOrderService bookOrderService;
    @Autowired
    private BookOrderController bookOrderController;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Book::getTitle, "庆余年");
        System.out.println(bookDao.selectList(wrapper));
    }

    @Test
    void addOrderBookName() {
        List<BookOrder> allOrder = bookOrderService.getAllOrder();
        for (BookOrder order : allOrder) {
            bookOrderService.updateOrder(order);
        }
    }


    @Test
    void getBookName() {
        String bookName = bookService.getBookName(20045);
        System.out.println("bookName = " + bookName);
    }

    @Test
    void testCheckExistence() {
        int account = 123451;
        int i = userService.checkAccountExistence(account);
        System.out.println("i = " + i);
    }

}
