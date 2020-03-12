package com.blysin.demo.netty.business.service;

import com.blysin.demo.netty.business.domain.StpApiBaseReq;
import com.blysin.demo.netty.business.domain.StpApiBaseResp;
import com.blysin.demo.netty.framework.base.NettyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 消息处理实现
 *
 * @author daishaokun
 * @date 2020/3/12
 */
@Service
@Slf4j
public class NettyServiceImpl implements NettyService {

    @Override
    public StpApiBaseResp auth(StpApiBaseReq req) {
        log.info("鉴权：{}", req);
        return StpApiBaseResp.success(req.getReqId());
    }

    @Override
    public void offline(String lotCode) {
        log.info("车场离线：{}", lotCode);
    }

    @Override
    public StpApiBaseResp handleMessage(StpApiBaseReq req) {
        log.info("处理消息：{}", req);
        return StpApiBaseResp.success(req.getReqId());
    }
}