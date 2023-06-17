package com.example.Service;

import com.example.domain.BookOrder;

import java.util.List;

public interface BookOrderService {

    //获取所有图书借阅条目
    List<BookOrder> getAllOrder();

    //获取某用户所有图书借阅条目
    List<BookOrder> getOrderByUsername(String username);

    //新增借阅记录
    Boolean addBookOrder(BookOrder bookOrder);

    //修改借阅记录
    Boolean updateOrder(BookOrder bookOrder);

    //删除借阅记录
    Boolean delOrder(Integer orderId);
}
