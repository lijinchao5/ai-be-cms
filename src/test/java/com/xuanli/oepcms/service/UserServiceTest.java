package com.xuanli.oepcms.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.converters.CalendarConverter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.util.PasswordUtil;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;
//    @Test
//    public void shouldAddUser() {
//        User user = new User();
//        user.setUsername("test" + UUID.randomUUID().toString());
//        user.setDesc("test desc");
//        user.setPassword("password");
//        user.setMobile("18011111111");
//        userService.add(user);
//
//        User addedUser = userService.findByName(user.getUsername());
//        assertThat(addedUser, notNullValue());
//        assertThat(addedUser.getId(), notNullValue());
//        assertThat(addedUser.getId().longValue(), greaterThan(0L));
//        assertThat(addedUser.getUsername(), is(user.getUsername()));
//        assertThat(addedUser.getDesc(), is(user.getDesc()));
//        assertThat(addedUser.getPassword(), is(not(user.getPassword())));
//        assertThat(PasswordUtil.verify(user.getPassword(), addedUser.getPassword()), is(true));
//    }
    
    @Test
    public void saveUser() {
//    	UserEntity user = new UserEntity();
//    	user.setBirthDate(new Date());
//    	user.setName("1234");
//    	user.setMobile("1234565");
//    	user.setNameNum("1233333");
//    	user.setCreateId("1");
//    	user.setCreateDate(new Date());
//    	user.setEnableFlag("T");
//    	user.setRoleId(new Integer(2));
//    	user.setPassword(PasswordUtil.generate("admin"));
//    	int i = userService.saveUser(user);
//    	System.out.println(i);
    }
    
    @Test
    public void testPerfectUserInfo() {
//    	UserEntity userEntity = new UserEntity();
//    	userEntity.setId(107L);
//		userEntity.setName("zhangsan");
//		userEntity.setBirthDate(new Date());
//		userEntity.setSex("M");
//		userEntity.setStudySectionId(1111);
//		userEntity.setGradeLevelId(2);
//		userEntity.setBookVersionId(1212);
//		userService.perfectUserInfo(userEntity);
//		
//	    UserEntity user = userService.selectById(107L);
//	    assertThat(user, notNullValue());
//	    assertThat(user.getId(), notNullValue());
//	    assertThat(user.getId().longValue(), greaterThan(0L));
//	    assertThat(user.getName(), is(userEntity.getName()));
//	    assertThat(user.getSex(), is(userEntity.getSex()));
//	    assertThat(user.getStudySectionId(), is(userEntity.getStudySectionId()));
//	    assertThat(user.getGradeLevelId(), is(userEntity.getGradeLevelId()));
//	    assertThat(user.getGradeLevelId(), is(userEntity.getGradeLevelId()));
    }

}
