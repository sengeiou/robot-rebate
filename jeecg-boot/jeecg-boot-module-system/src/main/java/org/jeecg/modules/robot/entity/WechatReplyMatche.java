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
 * @Description: 回复匹配
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
@Data
@TableName("wechat_reply_matche")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="wechat_reply_matche对象", description="回复匹配")
public class WechatReplyMatche {
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**匹配内容*/
	@Excel(name = "匹配内容", width = 15)
    @ApiModelProperty(value = "匹配内容")
	private java.lang.String content;
	/**匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；*/
	@Excel(name = "匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；", width = 15)
    @ApiModelProperty(value = "匹配事件==>100:私聊消息；200:群聊消息；300:暂无；400:群成员增加；410:群成员减少；500:收到好友请求；600:二维码收款；700:收到转账；800:软件开始启动；900:新的账号登录完成；910:账号下线；")
	private java.lang.Integer type;
	/**回复配置ID*/
	@Excel(name = "回复配置ID", width = 15)
    @ApiModelProperty(value = "回复配置ID")
	private java.lang.String configId;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
}
