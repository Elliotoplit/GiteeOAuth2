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

    @Override
    public List<BookOrder> getAllOrder() {
        List<BookOrder> bookOrders = bookOrderDao.selectList(null);
        return bookOrderDao.selectList(null);
    }

    @Override
    public List<BookOrder> getOrderById(Integer userId) {
        QueryWrapper<BookOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BookOrder::getUserId, userId);
        return bookOrderDao.selectList(wrapper);
    }

    //获取借阅中条目
    @Override
    public List<BookOrder> getUnReturnBookOrder() {
        QueryWrapper<BookOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BookOrder::getStatus, 1);
        return bookOrderDao.selectList(wrapper);
    }

    //获取已归还条目
    @Override
    public List<BookOrder> getReturnedBookOrder() {
        QueryWrapper<BookOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BookOrder::getStatus, 0);
        return bookOrderDao.selectList(wrapper);
    }

    //新增借阅记录
    @Override
    public Boolean addBookOrder(BookOrder bookOrder) {
        return bookOrderDao.insert(bookOrder) > 0;
    }

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
