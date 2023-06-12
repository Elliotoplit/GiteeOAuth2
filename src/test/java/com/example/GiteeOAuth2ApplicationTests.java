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
//            补充书名
//            Integer bookId = order.getBookId();
//            String bookName = bookService.getByBookId(bookId).getTitle();
//            System.out.println(bookName);
//            order.setBookName(bookName);
            //补充借阅者
//            Integer userId = order.getUserId();
//            String name = userService.selectByGiteeId(userId).getName();
//            order.setUserName(name);
            bookOrderService.updateOrder(order);
        }
    }


    @Test
    void testGetUserName() {
        String s = userService.selectNameById(10503315);
        System.out.println(s);
    }

    @Test
    void getBookName() {
        String bookName = bookService.getBookName(20045);
        System.out.println("bookName = " + bookName);
    }

}
