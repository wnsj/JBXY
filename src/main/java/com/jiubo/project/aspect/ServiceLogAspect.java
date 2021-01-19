package com.jiubo.project.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jiubo.project.bean.LogInfo;
import com.jiubo.project.util.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


@Aspect
@Component
public class ServiceLogAspect {
    @Value("${logDir}")
    private String logDir;

    final static Logger log = LoggerFactory.getLogger(com.jiubo.project.aspect.ServiceLogAspect.class);

    /**
     * AOP通知：
     * 1. 前置通知：在方法调用之前执行
     * 2. 后置通知：在方法正常调用之后执行
     * 3. 环绕通知：在方法调用之前和之后，都分别可以执行的通知
     * 4. 异常通知：如果在方法调用过程中发生异常，则通知
     * 5. 最终通知：在方法调用之后执行
     */

    /**
     * 切面表达式：
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 *代表所有类型
     * 第二处 包名代表aop监控的类所在的包
     * 第三处 .. 代表该包以及其子包下的所有类方法
     * 第四处 * 代表类名，*代表所有类
     * 第五处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */


    @Around(value = "(execution(* com.jiubo.project.action..*.*(..)))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        LogInfo logInfo = new LogInfo();
        log.info("====== 开始执行 {}.{} ======",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());
        logInfo.setMethodName(joinPoint.getTarget().getClass() + "." + joinPoint.getSignature().getName());


        // 记录开始时间
        long begin = System.currentTimeMillis();

        logInfo.setStartTime(DateUtils.formatDateTime(new Date()));

        try {
            addLog(joinPoint, logInfo);
        } catch (Exception e) {
            logInfo.setException(e.toString());
        }


        // 执行目标 service
        Object result = joinPoint.proceed();

        // 记录结束时间
        long end = System.currentTimeMillis();
        logInfo.setEndTime(DateUtils.formatDateTime(new Date()));
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else if (takeTime > 2000) {
            log.warn("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else {
            log.info("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        }
        logInfo.setRunTime(takeTime);


        String s = JSONObject.toJSONString(logInfo);

        // 获取文件根目录
        File dir = new File("");
        String courseFile = dir.getCanonicalPath();
        String[] split = courseFile.split("\\\\");

        if (split.length > 0) {
            String root = split[0];
            String name = DateUtils.formatDate(new Date());

            String path = root + logDir + "/" + name + ".txt";
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

            // write
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s + "\r\n");
            bw.flush();
            bw.close();
            fw.close();
        }
        return result;
    }

    @Pointcut(value = "(execution(* com.jiubo.samy.action..*.*(..)))")
    public void serviceAspect() {
    }

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "exception")
    public void exceptionListen(JoinPoint point, Throwable exception) throws IOException {
        LogInfo logInfo = new LogInfo();

        try {
            addLog(point, logInfo);
        } catch (Exception e) {
            logInfo.setException(e.toString());
        }

        logInfo.setEndTime(DateUtils.formatDateTime(new Date()));

        logInfo.setMethodName(point.getTarget().getClass() + "." + point.getSignature().getName());

        logInfo.setException(exception.toString());

        String s = JSONObject.toJSONString(logInfo);
        // 获取文件根目录
        File dir = new File("");
        String courseFile = dir.getCanonicalPath();
        String[] split = courseFile.split("\\\\");

        if (split.length > 0) {
            String root = split[0];
            String name = DateUtils.formatDate(new Date());

            String path = root + logDir + "/" + name + ".txt";
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

            // write
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s + "\r\n");
            bw.flush();
            bw.close();
            fw.close();
        }
    }

    private void addLog(JoinPoint point, LogInfo logInfo) {
        Object[] args = point.getArgs();

        if (args.length > 0) {
            String json = new Gson().toJson(args);
            JSONArray myJsonArray = JSONArray.parseArray(json);

            for (Object object : myJsonArray) {
                if (null == object || "null" == object || "" == object) continue;

                if (object.toString().contains("[")) continue;

                JSONObject jsonObject = JSONObject.parseObject(object.toString());
                if (!jsonObject.containsKey("ip") && !jsonObject.containsKey("user")) continue;
                Object ip = jsonObject.get("ip");
                Object user = jsonObject.get("user");
                if (null != ip) {
                    logInfo.setIp(ip.toString());
                    log.info("ip地址{}", ip.toString());
                }

                if (null != user) {
                    logInfo.setUser(user.toString());
                    log.info("user===={}", user.toString());
                }
            }
        }
    }
}
