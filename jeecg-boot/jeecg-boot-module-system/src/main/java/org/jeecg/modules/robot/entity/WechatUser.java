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
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
@Data
@TableName("wechat_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="wechat_user对象", description="微信用户")
public class WechatUser {
    
	/**用户ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "用户ID")
	private java.lang.String id;
	/**用户微信ID*/
	@Excel(name = "用户微信ID", width = 15)
    @ApiModelProperty(value = "用户微信ID")
	private java.lang.String wxid;
	/**所属机器人微信ID*/
	@Excel(name = "所属机器人微信ID", width = 15)
    @ApiModelProperty(value = "所属机器人微信ID")
	private java.lang.String robotWxid;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
	private java.lang.Integer state;
	/**来源*/
	@Excel(name = "来源", width = 15)
    @ApiModelProperty(value = "来源")
	private java.lang.Integer source;
	/**用户昵称*/
	@Excel(name = "用户昵称", width = 15)
    @ApiModelProperty(value = "用户昵称")
	private java.lang.String nickname;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private java.lang.String note;
	/**性别*/
	@Excel(name = "性别", width = 15)
    @ApiModelProperty(value = "性别")
	private java.lang.Integer sex;
	/**头像*/
	@Excel(name = "头像", width = 15)
    @ApiModelProperty(value = "头像")
	private java.lang.String headUrl;
	/**邀请者微信ID*/
	@Excel(name = "邀请者微信ID", width = 15)
    @ApiModelProperty(value = "邀请者微信ID")
	private java.lang.String inviteWxid;
	/**申请添加好友时发送的消息*/
	@Excel(name = "申请添加好友时发送的消息", width = 15)
    @ApiModelProperty(value = "申请添加好友时发送的消息")
	private java.lang.Object regMessage;
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
