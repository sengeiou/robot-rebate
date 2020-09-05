package org.jeecg.modules.robot.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 推广位
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
@Data
@TableName("tbk_spread")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="tbk_spread对象", description="推广位")
public class TbkSpread {
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**Pid*/
	@Excel(name = "Pid", width = 15)
    @ApiModelProperty(value = "Pid")
	private java.lang.String pid;
	/**产品ID*/
	@Excel(name = "产品ID", width = 15)
    @ApiModelProperty(value = "产品ID")
	private java.lang.Integer pubId;
	/**媒体ID*/
	@Excel(name = "媒体ID", width = 15)
    @ApiModelProperty(value = "媒体ID")
	private java.lang.Integer siteId;
	/**推广位ID*/
	@Excel(name = "推广位ID", width = 15)
    @ApiModelProperty(value = "推广位ID")
	private java.lang.Integer adzoneId;
	/**推广位名称*/
	@Excel(name = "推广位名称", width = 15)
    @ApiModelProperty(value = "推广位名称")
	private java.lang.String adzoneName;
	/**注册时间*/
	@Excel(name = "注册时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册时间")
	private java.util.Date createTime;
	/**创建者*/
	@Excel(name = "创建者", width = 15)
    @ApiModelProperty(value = "创建者")
	private java.lang.String createBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private java.util.Date updateTime;
	/**修改者*/
	@Excel(name = "修改者", width = 15)
    @ApiModelProperty(value = "修改者")
	private java.lang.String updateBy;
}
