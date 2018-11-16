package com.iexample.itoutaio.service;

import com.iexample.itoutaio.dao.NewsDao;
import com.iexample.itoutaio.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    NewsDao newsDao;
    public  List<News> getLastNews(int userId, int offset, int limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }
}
