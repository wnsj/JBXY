package com.jiubo.project.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("password")
@ApiModel(value="PasswordBean对象", description="")
public class PasswordBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课程ID")
    private String classid;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码使用次数上限")
    private Integer upperlimit;

    @ApiModelProperty(value = "判断密码上限,默认使用0次")
    private Integer judge;

    @ApiModelProperty(value = "备注")
    private String remarks;


}
