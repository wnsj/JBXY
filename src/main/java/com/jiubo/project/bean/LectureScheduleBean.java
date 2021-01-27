package com.jiubo.project.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author swd
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lecture_schedule")
@ApiModel(value="LectureScheduleBean对象", description="")
public class LectureScheduleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String user;

    @ApiModelProperty(value = "课程ID")
    private String className;

    @ApiModelProperty(value = "首次进入课程的时间")
    private LocalDateTime time;

    @ApiModelProperty(value = "0是未读，1是已读")
    private Integer isread;

    private String deparment;

    private String deparsmall;

    @ApiModelProperty(value = "听完课的时间")
    private LocalTime listenTime;

    @ApiModelProperty(value = "听完课的日期")
    private LocalDate listenDate;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}
