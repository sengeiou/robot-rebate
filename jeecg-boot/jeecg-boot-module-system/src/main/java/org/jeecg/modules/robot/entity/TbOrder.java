package org.jeecg.modules.robot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息
 */
@ToString
@Setter
@Getter
public class TbOrder {
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date clickTime; // 如："2020-08-08 16:40:43", (点击时间)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tkCreateTime; // 如："2020-08-08 16:41:13",  (创建时间)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tkPaidTime; // 如："2020-08-08 16:41:34", (付款时间)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tbPaidTime; // 如："2020-08-08 16:41:24", (淘宝付款时间)
    private Long itemId; // 如：575179534092,     (商品ID)
    private String itemImg; // 如："//img.alicdn.com/tfscom/i4/3050367059/O1CN018rS87f2214P3ARMaU_!!3050367059.jpg", (商品图片)
    private String itemLink; // 如："http://item.taobao.com/item.htm?id=575179534092", (商品链接)
    private String itemTitle; // 如："天猫精灵语音远程控制定时开关220V智能无线家用wifi手机遥控插座", (商品标题)
    private String sellerNick; // 如："德国精工造",  (掌柜旺旺)
    private String sellerShopTitle; // 如："国际电工官方金牌店",  (店铺名称)
    private Integer itemNum; // 如：1,      (商品数量)
    private BigDecimal itemPrice; // 如：96.00,  (商品单价)
    private String itemCategoryName; // 如："电子/电工",	(类目名称)
    private Long tradeId; // 如："1168757697830645329", (淘宝订单编号)
    private Long tradeParentId; // 如："1168757697830645329", (淘宝子订单编号)
    private String tkStatusText; // 如："已失效", (订单状态)
    private Integer tkStatus; // 如：13,
    private String orderType; // 如："淘宝", (订单类型)
    private BigDecimal alipayTotalPrice; // 如：46.56, (付款金额)
    private BigDecimal totalCommissionRate; // 如：0.45,  (佣金比率)
    private BigDecimal totalCommissionFee; // 如：0.00  (佣金金额)
    private BigDecimal subsidyFee; // 如：0.00, (补贴金额)
    private BigDecimal subsidyRate; // 如：0.00, (补贴比率)
    private String subsidyType; // 如：--, (补贴类型)
    private BigDecimal incomeRate; // 如：0.45, (收入比率)
    private BigDecimal pubShareRate; // 如：100.00,  (分成比率)
    private BigDecimal tkTotalRate; // 如：0.45,  (提成百分比)
    private BigDecimal tkCommissionRateForMediaPlatform; // 如：0.00, (技术服务费率)
    private BigDecimal tkCommissionFeeForMediaPlatform; // 如：0.00,  (技术服务费)
    private BigDecimal pubSharePreFee; // 如：0.21,  (付款预估收入)
    private BigDecimal tkCommissionPreFeeForMediaPlatform; // 如：0.00, (结算预估收入)
    private String tkOrderRoleText; // 如："二方",  (推广者身份)
    private Integer tkOrderRole; // 如：2,
    private Long siteId; // 如：1962050151, (媒体ID)
    private String siteName; // 如："Simple.Chen",  (媒体名称)
    private Long adzoneId; // 如：110732600234,  (推广位ID)
    private String adzoneName; // 如："薇家雅适",  (推广位名称)
    private String terminalType; // 如："无线",  (成交平台)
    private BigDecimal pubShareFee; // 如：0.00,
    private String tkDepositTime; // 如：--
    private BigDecimal alimamaRate; // 如：0.00,
    private BigDecimal alimamaShareFee; // 如：0.00,
    private BigDecimal depositPrice; // 如：0.00,
    private String tbDepositTime; // 如：--
    private String itemPlatformTypeText; // 如："淘宝",
    private Integer refundTag; // 如：0,
    private Integer preSell; // 如：0,
    private Long pubId; // 如：1106390200,
    private Integer tkBizTag; // 如：1,
    private String flowSource; // 如：--
    private Boolean supportItemClick; // 如：true,
    private String pid;

    public String getPid(){
        return String.format("mm_%d_%d_%d", pubId, siteId, adzoneId);
    }
}
