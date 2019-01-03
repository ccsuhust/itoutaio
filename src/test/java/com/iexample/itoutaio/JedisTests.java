package com.iexample.itoutaio;

import com.iexample.itoutaio.model.User;
import com.iexample.itoutaio.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ItoutaioApplication.class)
public class JedisTests {
    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void testObject()
    {
        User user = new User();
        user.setPassword("123");
        user.setName("123");
        user.setSalt("123");
        user.setHeadUrl("123");

        jedisAdapter.setObject("user1",user);

        User u = jedisAdapter.getObject("user1",User.class);
        if(u==null)
            System.out.println("User is null");
      //  System.out.println(u.toString());
        System.out.println(ToStringBuilder.reflectionToString(u));

        //"{\"headUrl\":\"123\",\"id\":0,\"name\":\"123\",\"password\":\"123\",\"salt\":\"123\"}"
    }
}
