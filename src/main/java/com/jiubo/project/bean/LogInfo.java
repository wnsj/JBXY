package com.jiubo.project.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LogInfo {
    private String ip;
    private String methodName;
    private String startTime;
    private String endTime;
    private Long runTime;
    private String exception;
    private String user;
}
