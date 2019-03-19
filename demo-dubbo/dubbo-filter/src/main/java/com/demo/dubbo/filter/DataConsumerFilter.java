package com.demo.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = {Constants.CONSUMER}, order = -999)
public class DataConsumerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DataConsumerFilter.class);

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            //RPCContext放置用户ID信息
            long start = System.currentTimeMillis();
            String userId = String.valueOf(RpcContext.getContext().get(CommonConstants.USERID));
            RpcContext.getContext().setAttachment(CommonConstants.USERID, userId);

            //懒得去了.
            String ispt = "";

            Result result = invoker.invoke(invocation);
            //大于3000ms或者异常时打印相关信息
            if (System.currentTimeMillis() - start > 1) {
                if (invoker.getUrl() != null) {
                    logger.info("线程:[{}],是否平台:[{}],用户ID:[{}],接口名称:[{}],方法名称:[{}],参数:[{}],结果:[{}],耗时:[{}]ms,调用结束.",
                            Thread.currentThread().getName(),
                            ispt, userId, invoker.getInterface(), invocation.getMethodName(),
                            JSON.toJSONString(invocation.getArguments())
                            , JSON.toJSONString(result), (System.currentTimeMillis() - start));
                }
                else {
                    logger.info("线程:[{}],是否平台:[{}],用户ID:[{}],结果:[{}],耗时:[{}]ms,调用结束.", Thread.currentThread().getName()
                            , ispt, userId, JSON.toJSONString(result), (System.currentTimeMillis() - start));
                }
            }
            return result;
        }
        catch (RuntimeException e) {
            logger.error("线程:[{}],dubbo未知异常:[{}],service:[{}],method:[{}],exception:[{}]，异常结束.",
                    Thread.currentThread().getName(), RpcContext.getContext().getRemoteHost(),
                    invoker.getInterface().getName(),
                    invocation.getMethodName(), (e.getClass().getName() + ":" + e.getMessage()), e);
            throw e;
        }
    }
}
