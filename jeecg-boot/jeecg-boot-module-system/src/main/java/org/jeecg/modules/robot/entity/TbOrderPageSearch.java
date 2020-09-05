package org.jeecg.modules.robot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 搜索订单
 */
@ToString
@Setter
@Getter
public class TbOrderPageSearch {
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int queryType = 2; // 1:创建时间;2:付款时间;3:结算时间
    private String startTime; // 设置默认最小值
    private String endTime;
    private String tkStatus = ""; // 12:已付款;14:已收货;3: 已结算;13:已失效; 空:全部
    private String memberType = ""; // 2:第二方;3:第三方;空:全部

    private int pageNo = 1; // 第几页
    private int pageSize = 40; // 一页显示几条

    public static String defaultStartTime() {
        LocalDate stime = LocalDate.now().plus(-3, ChronoUnit.MONTHS)
                .plus(2,ChronoUnit.DAYS);
        return df.format(stime);    //格式化前3月的时间
    }

    public static String defaultEndTime() {
        return df.format(LocalDate.now());
    }

    public String getParams() {
        startTime = null != startTime ? startTime : defaultStartTime();
        endTime = null != endTime ? endTime : defaultEndTime();
        return "&pageNo=" + pageNo
                + "&startTime=" + startTime
                + "&endTime=" + endTime
                + "&queryType=" + queryType
                + "&tkStatus=" + tkStatus
                + "&memberType=" + memberType
                + "&pageSize=" + pageSize;
    }
}
