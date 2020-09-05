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
 * @Description: 淘宝客订单
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
@Data
@TableName("tbk_trade")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="tbk_trade对象", description="淘宝客订单")
public class TbkTrade {
    
	/**订单ID*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "订单ID")
	private java.lang.Integer id;
	/**淘宝子订单编号*/
	@Excel(name = "淘宝子订单编号", width = 15)
    @ApiModelProperty(value = "淘宝子订单编号")
	private java.lang.Integer tradeId;
	/**淘宝订单编号*/
	@Excel(name = "淘宝订单编号", width = 15)
    @ApiModelProperty(value = "淘宝订单编号")
	private java.lang.Integer tradeParentId;
	/**点击时间*/
	@Excel(name = "点击时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "点击时间")
	private java.util.Date clickTime;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date tkCreateTime;
	/**淘宝付款时间*/
	@Excel(name = "淘宝付款时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "淘宝付款时间")
	private java.util.Date tbPaidTime;
	/**付款时间*/
	@Excel(name = "付款时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "付款时间")
	private java.util.Date tkPaidTime;
	/**订单状态*/
	@Excel(name = "订单状态", width = 15)
    @ApiModelProperty(value = "订单状态")
	private java.lang.String tkStatusText;
	/**订单状态值*/
	@Excel(name = "订单状态值", width = 15)
    @ApiModelProperty(value = "订单状态值")
	private java.lang.Integer tkStatus;
	/**商品ID*/
	@Excel(name = "商品ID", width = 15)
    @ApiModelProperty(value = "商品ID")
	private java.lang.Integer itemId;
	/**商品图片*/
	@Excel(name = "商品图片", width = 15)
    @ApiModelProperty(value = "商品图片")
	private java.lang.String itemImg;
	/**商品链接*/
	@Excel(name = "商品链接", width = 15)
    @ApiModelProperty(value = "商品链接")
	private java.lang.String itemLink;
	/**商品标题*/
	@Excel(name = "商品标题", width = 15)
    @ApiModelProperty(value = "商品标题")
	private java.lang.String itemTitle;
	/**商品数量*/
	@Excel(name = "商品数量", width = 15)
    @ApiModelProperty(value = "商品数量")
	private java.lang.Integer itemNum;
	/**商品单价*/
	@Excel(name = "商品单价", width = 15)
    @ApiModelProperty(value = "商品单价")
	private java.math.BigDecimal itemPrice;
	/**类目名称*/
	@Excel(name = "类目名称", width = 15)
    @ApiModelProperty(value = "类目名称")
	private java.lang.String itemCategoryName;
	/**掌柜旺旺*/
	@Excel(name = "掌柜旺旺", width = 15)
    @ApiModelProperty(value = "掌柜旺旺")
	private java.lang.String sellerNick;
	/**店铺名称*/
	@Excel(name = "店铺名称", width = 15)
    @ApiModelProperty(value = "店铺名称")
	private java.lang.String sellerShopTitle;
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
	/**媒体名称*/
	@Excel(name = "媒体名称", width = 15)
    @ApiModelProperty(value = "媒体名称")
	private java.lang.String siteName;
	/**推广位ID*/
	@Excel(name = "推广位ID", width = 15)
    @ApiModelProperty(value = "推广位ID")
	private java.lang.Integer adzoneId;
	/**推广位名称*/
	@Excel(name = "推广位名称", width = 15)
    @ApiModelProperty(value = "推广位名称")
	private java.lang.String adzoneName;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15)
    @ApiModelProperty(value = "订单类型")
	private java.lang.String orderType;
	/**付款金额*/
	@Excel(name = "付款金额", width = 15)
    @ApiModelProperty(value = "付款金额")
	private java.math.BigDecimal alipayTotalPrice;
	/**佣金比率*/
	@Excel(name = "佣金比率", width = 15)
    @ApiModelProperty(value = "佣金比率")
	private java.math.BigDecimal totalCommissionRate;
	/**佣金金额*/
	@Excel(name = "佣金金额", width = 15)
    @ApiModelProperty(value = "佣金金额")
	private java.math.BigDecimal totalCommissionFee;
	/**补贴金额*/
	@Excel(name = "补贴金额", width = 15)
    @ApiModelProperty(value = "补贴金额")
	private java.math.BigDecimal subsidyFee;
	/**补贴比率*/
	@Excel(name = "补贴比率", width = 15)
    @ApiModelProperty(value = "补贴比率")
	private java.math.BigDecimal subsidyRate;
	/**补贴类型*/
	@Excel(name = "补贴类型", width = 15)
    @ApiModelProperty(value = "补贴类型")
	private java.lang.String subsidyType;
	/**收入比率*/
	@Excel(name = "收入比率", width = 15)
    @ApiModelProperty(value = "收入比率")
	private java.math.BigDecimal incomeRate;
	/**分成比率*/
	@Excel(name = "分成比率", width = 15)
    @ApiModelProperty(value = "分成比率")
	private java.math.BigDecimal pubShareRate;
	/**提成百分比*/
	@Excel(name = "提成百分比", width = 15)
    @ApiModelProperty(value = "提成百分比")
	private java.math.BigDecimal tkTotalRate;
	/**技术服务费率*/
	@Excel(name = "技术服务费率", width = 15)
    @ApiModelProperty(value = "技术服务费率")
	private java.math.BigDecimal tkCommissionRateForMediaPlatform;
	/**技术服务费*/
	@Excel(name = "技术服务费", width = 15)
    @ApiModelProperty(value = "技术服务费")
	private java.math.BigDecimal tkCommissionFeeForMediaPlatform;
	/**付款预估收入*/
	@Excel(name = "付款预估收入", width = 15)
    @ApiModelProperty(value = "付款预估收入")
	private java.math.BigDecimal pubSharePreFee;
	/**结算预估收入*/
	@Excel(name = "结算预估收入", width = 15)
    @ApiModelProperty(value = "结算预估收入")
	private java.math.BigDecimal tkCommissionPreFeeForMediaPlatform;
	/**推广者身份*/
	@Excel(name = "推广者身份", width = 15)
    @ApiModelProperty(value = "推广者身份")
	private java.lang.String tkOrderRoleText;
	/**推广者身份值*/
	@Excel(name = "推广者身份值", width = 15)
    @ApiModelProperty(value = "推广者身份值")
	private java.lang.Integer tkOrderRole;
	/**成交平台*/
	@Excel(name = "成交平台", width = 15)
    @ApiModelProperty(value = "成交平台")
	private java.lang.String terminalType;
	/**分享费率*/
	@Excel(name = "分享费率", width = 15)
    @ApiModelProperty(value = "分享费率")
	private java.math.BigDecimal pubShareFee;
	/**保证时间*/
	@Excel(name = "保证时间", width = 15)
    @ApiModelProperty(value = "保证时间")
	private java.lang.String tkDepositTime;
	/**啊里妈妈费率*/
	@Excel(name = "啊里妈妈费率", width = 15)
    @ApiModelProperty(value = "啊里妈妈费率")
	private java.math.BigDecimal alimamaRate;
	/**啊里妈妈费用*/
	@Excel(name = "啊里妈妈费用", width = 15)
    @ApiModelProperty(value = "啊里妈妈费用")
	private java.math.BigDecimal alimamaShareFee;
	/**保证价格*/
	@Excel(name = "保证价格", width = 15)
    @ApiModelProperty(value = "保证价格")
	private java.math.BigDecimal depositPrice;
	/**保证时间*/
	@Excel(name = "保证时间", width = 15)
    @ApiModelProperty(value = "保证时间")
	private java.lang.String tbDepositTime;
	/**平台类型*/
	@Excel(name = "平台类型", width = 15)
    @ApiModelProperty(value = "平台类型")
	private java.lang.String itemPlatformTypeText;
	/**退款标识*/
	@Excel(name = "退款标识", width = 15)
    @ApiModelProperty(value = "退款标识")
	private java.lang.Integer refundTag;
	/** 预售*/
	@Excel(name = " 预售", width = 15)
    @ApiModelProperty(value = " 预售")
	private java.lang.Integer preSell;
	/**业务标识*/
	@Excel(name = "业务标识", width = 15)
    @ApiModelProperty(value = "业务标识")
	private java.lang.Integer tkBizTag;
	/**来源*/
	@Excel(name = "来源", width = 15)
    @ApiModelProperty(value = "来源")
	private java.lang.String flowSource;
	/**商品是否点击*/
	@Excel(name = "商品是否点击", width = 15)
    @ApiModelProperty(value = "商品是否点击")
	private java.lang.Integer supportItemClick;
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
