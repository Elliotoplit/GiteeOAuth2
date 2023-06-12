package com.example.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Service.BookService;
import com.example.dao.BookDao;
import com.example.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    //查询所有
    @Override
    public List<Book> getAllBook() {
        return bookDao.selectList(null);
    }

    //分页查询
    @Override
    public List<Book> getAllBook(long current, long size) {
        Page<Book> page = new Page<>(current, size);
        Page<Book> p = bookDao.selectPage(page, null);
        return p.getRecords();
    }

    //书名模糊搜索
    @Override
    public List<Book> getBooksByTitle(String keyword) {
        System.out.println("keyword = " + keyword);
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Book::getTitle, keyword);
        return bookDao.selectList(wrapper);
    }

    //作者模糊搜索
    @Override
    public List<Book> getBooksByAuthor(String keyword) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Book::getAuthor, keyword);
        return bookDao.selectList(wrapper);
    }


    //根据id获取book
    @Override
    public Book getByBookId(Integer bookId) {
        return bookDao.selectById(bookId);
    }

    //新增book
    @Override
    public Boolean addBook(Book book) {
        int insert = bookDao.insert(book);
        System.out.println(insert);
        return insert > 0;
    }

    //删除book
    @Override
    public Boolean deleteBook(Integer bookId) {
        return bookDao.deleteById(bookId) > 0;
    }

    //修改book
    @Override
    public Boolean updateBook(Book book) {
        System.out.println("book = " + book);
        return bookDao.updateById(book) > 0;
    }

    @Override
    public String getBookName(Integer bookId) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getId, bookId);
        List<Book> books = bookDao.selectList(wrapper);
        return books.get(0).getTitle();
    }


}
