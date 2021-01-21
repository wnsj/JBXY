package com.jiubo.project.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @author 
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("courseask")
@ApiModel(value="CourseaskBean对象", description="")
public class CourseaskBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String coursetitle;

    private String coursetime;

    private String courseteacher;

    private String namea;

    private String deparment;

    private String deparmentsmall;

    private String biaodafen;

    private String kejianzb;

    private String zhendui;

    private String jiaoxueff;

    private String xiaoguo;

    private String qita;

    private String zongfen;

    @ApiModelProperty(value = "培训内容")
    private String neirong;

    @ApiModelProperty(value = "培训形式")
    private String xingshi;

    @ApiModelProperty(value = "讲师表达清晰吗")
    private String biaoda;

    @ApiModelProperty(value = "时间安排")
    private String timeanpai;

    @ApiModelProperty(value = "讲师准备")
    private String zhunbei;

    @ApiModelProperty(value = "培训氛围")
    private String fenwei;

    @ApiModelProperty(value = "达到期望吗")
    private String qiwang;

    @ApiModelProperty(value = "本次培训的满意度")
    private String manyidu;

    @ApiModelProperty(value = "此次培训还应增加哪些方面的课程")
    private String zengjia;

    @ApiModelProperty(value = "此次课程学到了哪些知识点")
    private String zhishidian;

    @ApiModelProperty(value = "实际运用")
    private String yunyong;

    private LocalDateTime time;


}
