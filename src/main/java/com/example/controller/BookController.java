package com.example.controller;

import com.example.Service.BookService;
import com.example.domain.Book;
import com.example.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public Result getBooks() {
        System.out.println("获取所有book请求：" + new Date());
        return new Result(200, bookService.getAllBook());
    }

    @GetMapping("/page")
    public Result getBooks(long current, long size) {
        System.out.println("获取分页book请求：" + current + ":" + size + " " + new Date());
        List<Book> allBook = bookService.getAllBook(current, size);
        return new Result(200, allBook);
    }

    //书名模糊搜索
    @GetMapping("/title")
    public Result getBookByTitle(String keyword) {
        System.out.println("获取书名模糊搜素请求：" + keyword + new Date());
        List<Book> books = bookService.getBooksByTitle(keyword);
        boolean flag = books.size() != 0;
        return flag ? new Result(200, books) : new Result(201, "无”" + keyword + "”相关图书数据");
    }

    //作者模糊搜索
    @GetMapping("/author")
    public Result getBookByAuthor(String keyword) {
        System.out.println("获取作者模糊搜素请求：" + keyword + new Date());
        List<Book> books = bookService.getBooksByAuthor(keyword);
        boolean flag = books.size() != 0;
        return flag ? new Result(200, books) : new Result(201, "无”" + keyword + "”相关图书数据");
    }

    @GetMapping("/id")
    public Result getBookById(Integer bookId) {
        System.out.println("根据id获取图书信息：" + bookId + new Date());
        Book book = bookService.getByBookId(bookId);

        return book == null ?
                new Result(201, "此图书id不存在") :
                new Result(200, book);
    }

    //删除图书
    @DeleteMapping
    public Result deleteById(Integer bookId) {
        System.out.println("根据id删除图书：" + new Date());
        return bookService.deleteBook(bookId) ?
                new Result(200, "删除成功") :
                new Result(201, "删除失败 请检查是否有该id的图书");
    }

    //新增图书
    @PostMapping
    public Result addBook(@RequestBody Book book) {
        System.out.println("新增图书：" + new Date());
        book.setLend(0);
        book.setRemaining(book.getTotal());
        return bookService.addBook(book) ?
                new Result(200, "添加成功") :
                new Result(201, "添加失败 请检查输入内容");
    }

    //修改图书
    @PutMapping
    public Result modifyBook(@RequestBody Book book) {
        System.out.println("根据id修改图书：" + new Date());
        return bookService.updateBook(book) ?
                new Result(200, "修改成功") :
                new Result(201, "修改失败 请检查输入内容和id");
    }

    //获取书名
    @GetMapping("/name")
    public Result getBookNameByID(Integer bookId) {
        System.out.println("根据id获取图书名称：" + " " + new Date());
        String title = bookService.getBookName(bookId);
        return title != null ? new Result(200, title) : new Result(201, "该id无对应图书");
    }


}
