package com.demo.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Activate(group = {Constants.PROVIDER}, order = -999)
public class DataProviderFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DataProviderFilter.class);

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        try {
            long start = System.currentTimeMillis();
            //从隐藏参数中获取USERID
            String userId = RpcContext.getContext().getAttachment(CommonConstants.USERID);
            //存放REDIS
            //将USERID放置到dubbo上下文中，以便业务代码使用
            RpcContext.getContext().set(CommonConstants.USERID, userId);

            String ispt = "";


            Result result = invoker.invoke(invocation);
            //大于3000ms或者异常时打印相关信息
            if (System.currentTimeMillis() - start > 1) {
                if (invoker.getUrl() != null) {
                    logger.info("线程:[{}],获取客户端:[{}],是否平台:[{}],用户ID:[{}],接口名称:[{}],方法名称:[{}],参数:[{}],结果:[{}],耗时:[{}]ms,被调用结束.",
                            Thread.currentThread().getName(), RpcContext.getContext().getRemoteAddressString(), ispt, userId, invoker.getInterface(),
                            invocation.getMethodName(), JSON.toJSONString(invocation.getArguments()), JSON.toJSONString(result)
                            , (System.currentTimeMillis() - start));
                } else {
                    logger.info("线程:[{}],获取客户端:[{}],是否平台:[{}],用户ID:[{}],结果:[{}],耗时:[{}]ms,调用结束.", Thread.currentThread().getName(),
                            RpcContext.getContext().getRemoteAddressString(), ispt, userId, JSON.toJSONString(result), (System.currentTimeMillis() - start));
                }
            }
            return result;
        } catch (RuntimeException e) {
            logger.error("线程:[{}],dubbo未知异常:[{}],service:[{}],method:[{}],exception:[{}]，异常结束.",
                    Thread.currentThread().getName(), RpcContext.getContext().getRemoteHost(), invoker.getInterface().getName(),
                    invocation.getMethodName(), (e.getClass().getName() + ":" + e.getMessage()), e);
            throw e;
        }
    }
}
