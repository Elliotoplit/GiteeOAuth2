package com.example.controller;

import com.example.Service.BookOrderService;
import com.example.Service.BookService;
import com.example.Service.UserService;
import com.example.domain.BookOrder;
import com.example.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/bookOrder")
@CrossOrigin
public class BookOrderController {

    @Autowired
    private BookOrderService bookOrderService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Result getAllOrder() {
        System.out.println("获取所有Order请求：" + new Date());
        return new Result(200, bookOrderService.getAllOrder());
    }

    @GetMapping("/id")
    public Result getOrderById(Integer userId) {
        System.out.println("获取用户" + userId + "所有Order请求：" + new Date());
        return new Result(200, bookOrderService.getOrderById(userId));
    }

    @GetMapping("/unreturn")
    public Result getUnReturnOrder() {
        System.out.println("获取借阅中的条目..." + new Date());
        return new Result(200, bookOrderService.getUnReturnBookOrder());
    }

    @GetMapping("returned")
    public Result getReturnedOrder() {
        System.out.println("获取已归还的条目..." + new Date());
        return new Result(200, bookOrderService.getReturnedBookOrder());
    }

    //新增记录
    @PostMapping
    public Result addBookOrder(@RequestBody BookOrder bookOrder) {
        System.out.println("新增条目..." + new Date());
        Integer bookId = bookOrder.getBookId();
        String bookName = bookService.getBookName(bookId);
        String userName = userService.selectNameById(bookOrder.getUserId());
        System.out.println("userName = " + userName);
        bookOrder.setBookName(bookName);
        bookOrder.setUserName(userName);
        System.out.println("bookOrder = " + bookOrder);
        return bookOrderService.addBookOrder(bookOrder) ?
                new Result(200, "添加成功") :
                new Result(201, "添加失败 请检查输入数据");
    }

    //修改记录
    @PutMapping
    public Result updateBookOrder(@RequestBody BookOrder bookOrder) {
        System.out.println("修改条目..." + new Date());
        bookOrder.setBookName(bookService.getBookName(bookOrder.getBookId()));
        bookOrder.setUserName(userService.selectNameById(bookOrder.getBookId()));
        return bookOrderService.updateOrder(bookOrder) ?
                new Result(200, "修改成功") :
                new Result(201, "修改失败 请检查输入数据");
    }

    @DeleteMapping
    //删除借阅记录
    public Result delOrder(Integer orderId) {
        System.out.println("删除条目..." + new Date());
        return bookOrderService.delOrder(orderId) ?
                new Result(200, "删除成功") :
                new Result(201, "删除失败 请检查id是否正确");
    }

}
