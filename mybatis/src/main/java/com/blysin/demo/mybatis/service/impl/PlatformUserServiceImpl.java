package com.blysin.demo.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blysin.demo.web.dao.PlatformUserDao;
import com.blysin.demo.web.domian.PlatformUser;
import com.blysin.demo.web.service.PlatformUserService;
import org.springframework.stereotype.Service;

/**
 * @author daishaokun
 * @date 2019-09-24
 */
@Service
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserDao, PlatformUser> implements PlatformUserService {

}