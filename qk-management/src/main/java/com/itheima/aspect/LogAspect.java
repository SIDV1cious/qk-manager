package com.itheima.aspect;

import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONUtil;
import com.itheima.anno.Logger;
import com.itheima.entity.OperateLog;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@RequiredArgsConstructor
public class LogAspect {

    private final OperateLogMapper operateLogMapper;

    @Around("@annotation(logger)")
    public Object recordLog(ProceedingJoinPoint joinPoint, Logger logger) throws Throwable {
        // 记录开始时间
        long start = System.currentTimeMillis();
        // 执行目标方法
        Object result = joinPoint.proceed();
        // 记录结束时间
        long end = System.currentTimeMillis();
        // 计算耗时
        Long costTime = end - start;
        // 将目标方法执行结果转变化为字符串形式(JSON)
        String returnValue = JSONUtil.toJsonStr(result);
        // 当前登录用户
        Integer userId = UserHolder.getUserId();
        // 操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        // 目标类名
        String className = joinPoint.getTarget().getClass().getName();
        // 目标方法名称
        String methodName = joinPoint.getSignature().getName();
        // 调用方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = JSONUtil.toJsonStr(args);

        // 封装数据保存到数据库表
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateUserId(userId);
        operateLog.setOperateTime(operateTime);
        operateLog.setClassName(className);
        operateLog.setMethodName(methodName);
        operateLog.setMethodParams(methodParams);
        operateLog.setReturnValue(returnValue);
        operateLog.setCostTime(costTime);
        // 将数据写到数据库
        operateLogMapper.insert(operateLog);

        return result;
    }

}