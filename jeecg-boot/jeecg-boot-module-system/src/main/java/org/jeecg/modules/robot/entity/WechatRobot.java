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
 * @Description: 机器人
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
@Data
@TableName("wechat_robot")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="wechat_robot对象", description="机器人")
public class WechatRobot {
    
	/**机器人ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "机器人ID")
	private java.lang.String id;
	/**微信机器人ID*/
	@Excel(name = "微信机器人ID", width = 15)
    @ApiModelProperty(value = "微信机器人ID")
	private java.lang.String wxid;
	/**机器人昵称*/
	@Excel(name = "机器人昵称", width = 15)
    @ApiModelProperty(value = "机器人昵称")
	private java.lang.String nickname;
	/**头像*/
	@Excel(name = "头像", width = 15)
    @ApiModelProperty(value = "头像")
	private java.lang.String headUrl;
	/**背景*/
	@Excel(name = "背景", width = 15)
    @ApiModelProperty(value = "背景")
	private java.lang.String backgroundUrl;
	/**个性说明*/
	@Excel(name = "个性说明", width = 15)
    @ApiModelProperty(value = "个性说明")
	private java.lang.String signature;
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
	/**同步时间(和系统时间一致)*/
	@Excel(name = "同步时间(和系统时间一致)", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "同步时间(和系统时间一致)")
	private java.util.Date syncTime;
}
