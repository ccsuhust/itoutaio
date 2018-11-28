package com.iexample.itoutaio.service;

import com.iexample.itoutaio.dao.NewsDao;
import com.iexample.itoutaio.model.News;
import com.iexample.itoutaio.util.ToutiaoUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    NewsDao newsDao;

    public List<News> getLastNews(int userId, int offset, int limit) {
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int i = file.getOriginalFilename().lastIndexOf(".");
        if (i < 0)
            return null;
        String fileExt = file.getOriginalFilename().substring(i + 1).toLowerCase();
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        //Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
        //        StandardCopyOption.REPLACE_EXISTING);
        Path path = new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath();
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }
    public int addNews(News news)
    {
        newsDao.addNews(news);
        return  news.getUserId();
    }

}
