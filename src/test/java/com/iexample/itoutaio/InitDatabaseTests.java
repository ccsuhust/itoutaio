package com.iexample.itoutaio;

import com.iexample.itoutaio.dao.LoginTicketDao;
import com.iexample.itoutaio.dao.NewsDao;
import com.iexample.itoutaio.dao.UserDao;
import com.iexample.itoutaio.model.LoginTicket;
import com.iexample.itoutaio.model.News;
import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.util.ToutiaoUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

	Random random = new Random();
	/*@Test
	public void fun() {
		StringBuilder msg = new StringBuilder();

		msg.append("str");

	}*/
	///*测试 userDao newsDao 往数据库填写原始数据*/
	@Test
	public void contextLoads() {
        System.out.println(new Date());
        System.out.println(new Date(new Date().getTime()));
		/*for(int i=0;i<10;i++)
		{
			User user = new User();
			user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("User %d",i));
			user.setPassword("");
			user.setSalt("");
			userDao.addUser(user);

			News news = new News();
			news.setCommentCount(i);
			Date d= new Date();
			//d.setTime(d.getTime()+1000*3600*i);
			news.setCreatedDate(new Date(d.getTime()+1000*3600*i));
			news.setId(i);
			news.setImage(String.format("https://images.nowcoder.com/head/%dm.png",i));
			news.setLikeCount(i);
			news.setLink(String.format("https://www.nowcoder.com/profile/%d",i));
			news.setTitle(String.format("Title %d",i));
			news.setUserId(i+1);
			newsDao.addNews(news);

			LoginTicket ticket = new LoginTicket();
			ticket.setExpired(d);
			ticket.setId(i);
			ticket.setUserId(i);
			ticket.setStatus(i);
			ticket.setTicket(String.format("ticket %d",i));
			ticketDao.addTicket(ticket);
			ticketDao.updateStatus(ticket.getTicket(),2);
		}*/

		//Assert.assertEquals(ticketDao.selectByTicket("ticket 1").getTicket(),"ticket 1");
	}

}
