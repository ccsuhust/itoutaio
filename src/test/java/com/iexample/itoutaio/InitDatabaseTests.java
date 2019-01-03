package com.iexample.itoutaio;

import com.iexample.itoutaio.dao.CommentDao;
import com.iexample.itoutaio.dao.LoginTicketDao;
import com.iexample.itoutaio.dao.NewsDao;
import com.iexample.itoutaio.dao.UserDao;
import com.iexample.itoutaio.model.*;
import com.iexample.itoutaio.service.CommentService;
import com.iexample.itoutaio.util.ToutiaoUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.html.parser.Entity;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ItoutaioApplication.class)
//@WebAppConfiguration  这行会修改默认的启动路径需要注释掉
//@Sql({"/init-schema.sql"})//只需run once
public class InitDatabaseTests {

	@Autowired
	UserDao userDao;

	@Autowired
	NewsDao newsDao;
	@Autowired
	LoginTicketDao ticketDao;
	@Autowired
	CommentService commentService;
	@Autowired
	CommentDao commentDao;

	Random random = new Random();
	/*@Test
	public void fun() {
		StringBuilder msg = new StringBuilder();

		msg.append("str");

	}*/
	///*测试 userDao newsDao 往数据库填写原始数据*/
	@Test
	public void contextLoads() {

		/*for(int i=1;i<=10;i++)
		{
			User user = new User();
			user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("User %d",i));
			user.setPassword("");
			user.setSalt("");
			userDao.addUser(user);

			News news = new News();
			news.setCommentCount(i+1);
			Date d= new Date();
			//d.setTime(d.getTime()+1000*3600*i);
			news.setCreatedDate(new Date(d.getTime()+1000*3600*i));
			news.setId(i);
			news.setImage(String.format("https://images.nowcoder.com/head/%dm.png",i));
			news.setLikeCount(i);
			news.setLink(String.format("https://www.nowcoder.com/profile/%d",i));
			news.setTitle(String.format("Title %d",i));
			news.setUserId(i);
			newsDao.addNews(news);

			for(int j=1;j<=3;j++)
			{
				Comment comment = new Comment();
				comment.setContent("comment "+String.valueOf(j));
				comment.setCreated_date(new Date());
				comment.setEntity_id(news.getId());
				comment.setEntity_type(EntityType.ENTITY_NEWS);
				comment.setUser_id(i);
				comment.setStatus(0);
				commentService.addComment(comment);

			}

			LoginTicket ticket = new LoginTicket();
			ticket.setExpired(d);
			ticket.setId(i);
			ticket.setUserId(i);
			ticket.setStatus(0);
			ticket.setTicket(String.format("ticket %d",i));
			ticketDao.addTicket(ticket);
			//ticketDao.updateStatus(ticket.getTicket(),2);
		}*/
		//Assert.assertNotNull(commentService.selectbyEntity(1,EntityType.ENTITY_NEWS).get(0));
		//System.out.println(commentDao.selectbyEntity(10, EntityType.ENTITY_NEWS).get(0));;
		//System.out.println(commentDao.getCommentCount(10, EntityType.ENTITY_NEWS));;
		//commentService.addComment(comment);
		//commentService.addComment(comment);
		//Assert.assertEquals(ticketDao.selectByTicket("ticket 1").getTicket(),"ticket 1");

	}

}
