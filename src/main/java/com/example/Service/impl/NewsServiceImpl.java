package com.example.Service.impl;

import com.example.Service.NewsService;
import com.example.dao.NewsDao;
import com.example.domain.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;

    @Override
    public List<News> getAllNews() {
        return newsDao.selectList(null);
    }
}
