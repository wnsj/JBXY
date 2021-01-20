package com.jiubo.project.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("class_infor")
@ApiModel(value="ClassInforBean对象", description="")
public class ClassInforBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "讲师姓名")
    @TableField(value = "`name`")
    private String name;

    private String department;

    @ApiModelProperty(value = "培训时间")
    private LocalDate classTime;

    @ApiModelProperty(value = "课程密码")
    private String classPwd;

    @ApiModelProperty(value = "课程名称")
    private String className;

    @ApiModelProperty(value = "课件链接")
    private String classUrl;

    @ApiModelProperty(value = "课程随机验证码")
    private String classRand;

    private String leavetwo;

    @TableField(value = "`leave`")
    private String leave;

    @ApiModelProperty(value = "课程时间")
    private Integer classLong;


}
