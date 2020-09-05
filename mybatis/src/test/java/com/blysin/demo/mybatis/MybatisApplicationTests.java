package com.blysin.demo.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blysin.demo.web.dao.PlatformUserDao;
import com.blysin.demo.web.domian.PlatformUser;
import com.blysin.demo.web.service.NewPlatformService;
import com.blysin.demo.web.service.PlatformUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.plugin.services.PlatformService;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {

    @Autowired
    private NewPlatformService newPlatformService;
    @Autowired
    private PlatformUserDao platformUserDao;
    @Autowired
    private PlatformUserService platformUserService;

    @Test
    public void platformService() {
        System.out.println(platformUserService.getById(2));
        System.out.println(platformUserService.list());
    }

    @Test
    public void contextLoads() {
        System.out.println(newPlatformService.findAll());
        System.out.println(newPlatformService.getById(1));
        System.out.println(newPlatformService.getByParkName("hello"));

    }

    @Test
    public void platformUserDao() {
        //PlatformUser user = platformUserDao.selectById(1);
        //
        //user.setEmail("blysin@163.com");
        //
        //platformUserDao.updateById(user);
        //
        //platformUserDao.deleteById(1);
        //
        //PlatformUser user = new PlatformUser();
        //user.setEmail("test2@baomidou.com");
        //
        //System.out.println(platformUserDao.selectList(Wrappers.<PlatformUser>lambdaQuery().eq(PlatformUser::getAge, 18)));

        //Map condition = new HashMap();
        //condition.put("email", "blysin@163.com");
        //condition.put("age", "18");
        //platformUserDao.deleteByMap(condition);

        Page page = new Page(0, 10);

        IPage<PlatformUser> userIPage = platformUserDao.findUsers(page, "com");


        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getPages());
        System.out.println(userIPage.getSize());
        System.out.println(userIPage.getTotal());
        System.out.println(userIPage.getRecords());

        System.out.println("-----------------------------------------");

        page = new Page(0, 2);
        userIPage = platformUserDao.findUsers(page, "com");
        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getPages());
        System.out.println(userIPage.getSize());
        System.out.println(userIPage.getTotal());
        System.out.println(userIPage.getRecords());

        System.out.println("-----------------------------------------");

        page = new Page(1, 2);
        userIPage = platformUserDao.findUsers(page, "com");
        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getPages());
        System.out.println(userIPage.getSize());
        System.out.println(userIPage.getTotal());
        System.out.println(userIPage.getRecords());

        System.out.println("-----------------------------------------");

        page = new Page(2, 2);
        userIPage = platformUserDao.findUsers(page, "com");
        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getPages());
        System.out.println(userIPage.getSize());
        System.out.println(userIPage.getTotal());
        System.out.println(userIPage.getRecords());

        System.out.println("-----------------------------------------");

        page = new Page(3, 2);
        userIPage = platformUserDao.findUsers(page, "com");
        System.out.println(userIPage.getCurrent());
        System.out.println(userIPage.getPages());
        System.out.println(userIPage.getSize());
        System.out.println(userIPage.getTotal());
        System.out.println(userIPage.getRecords());

        //System.out.println(platformUserDao.findById(1));
    }

}
