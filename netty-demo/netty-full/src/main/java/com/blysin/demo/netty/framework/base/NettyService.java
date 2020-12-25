package com.blysin.demo.netty.framework.base;

import com.blysin.demo.netty.business.domain.StpApiBaseReq;
import com.blysin.demo.netty.business.domain.StpApiBaseResp;

/**
 * netty消息处理
 *
 * @author daishaokun
 * @date 2020/3/12
 */
public interface NettyService {
    /**
     * 首次发起链接请求时鉴权
     *
     * @param req
     * @return
     */
    StpApiBaseResp auth(StpApiBaseReq req);

    /**
     * 处理车场离线
     */
    void offline(String lotCode);

    /**
     * 处理普通消息
     *
     * @param req
     * @return
     */
    StpApiBaseResp handleMessage(StpApiBaseReq req);
}
