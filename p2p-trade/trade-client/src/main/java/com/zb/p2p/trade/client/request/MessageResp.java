package com.zb.p2p.trade.client.request;

import lombok.Data;

/**
 * Created by limingxin on 2018/1/15.
 */
@Data
public class MessageResp {
    /**
     * 消息编码
     * 消息内容
     * 是否成功
     */
    private String errorCode,
            errorMessage,
            success;
}
