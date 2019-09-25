package com.blysin.demo.mybatis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blysin.demo.mybatis.domian.PlatformUser;
import com.blysin.demo.mybatis.dto.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Blysin
 * @since 2019-09-24
 */
@Repository
public interface PlatformUserDao extends BaseMapper<PlatformUser> {

    IPage<UserDTO> findUsers(Page<PlatformUser> page,@Param("state") String email);

    UserDTO findById(@Param("id") Integer id);

}
