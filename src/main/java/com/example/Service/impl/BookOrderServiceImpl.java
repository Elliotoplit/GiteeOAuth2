package com.example.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Service.BookOrderService;
import com.example.dao.BookOrderDao;
import com.example.domain.BookOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookOrderServiceImpl implements BookOrderService {

    @Autowired
    private BookOrderDao bookOrderDao;

    //获取所有借阅记录
    @Override
    public List<BookOrder> getAllOrder() {
        List<BookOrder> bookOrders = bookOrderDao.selectList(null);
        return bookOrderDao.selectList(null);
    }

    //获取图书借阅记录 By username
    @Override
    public List<BookOrder> getOrderByUsername(String username) {
        QueryWrapper<BookOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BookOrder::getUsername, username);
        return bookOrderDao.selectList(wrapper);
    }

    //新增借阅记录
    @Override
    public Boolean addBookOrder(BookOrder bookOrder) {
        return bookOrderDao.insert(bookOrder) > 0;
    }

    //修改借阅记录
    @Override
    public Boolean updateOrder(BookOrder bookOrder) {
        return bookOrderDao.updateById(bookOrder) > 0;
    }

    //删除借阅记录 -by id
    @Override
    public Boolean delOrder(Integer orderId) {
        return bookOrderDao.deleteById(orderId) > 0;
    }
}
