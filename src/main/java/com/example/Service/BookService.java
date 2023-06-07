package com.example.Service;

import com.example.domain.Book;

import java.util.List;

public interface BookService {

    //get all
    List<Book> getAllBook();

    //get all by page
    List<Book> getAllBook(long current, long size);

    //书名模糊搜索
    List<Book> getBooksByTitle(String keyword);

    //作者模糊搜索
    List<Book> getBooksByAuthor(String keyword);

    Book getByBookId(Integer bookId);

    //新增book
    Boolean addBook(Book book);

    //删除book
    Boolean deleteBook(Integer bookId);

    //修改book
    Boolean updateBook(Book book);

    //获取书名
    String getBookName(Integer bookId);
}
