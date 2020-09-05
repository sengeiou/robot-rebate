package org.jeecg.modules.robot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 搜索订单
 */
@ToString
@Setter
@Getter
public class TbOrderPageSearch {
    private int queryType = 2; // 1:创建时间;2:付款时间;3:结算时间
    private String startTime; // 设置默认最小值
    private String endTime;
    private String tkStatus = ""; // 12:已付款;14:已收货;3: 已结算;13:已失效; 空:全部
    private String memberType = ""; // 2:第二方;3:第三方;空:全部

    private int pageNo = 1; // 第几页
    private int pageSize = 40; // 一页显示几条

    public static String defaultStartTime() {
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -3);  //设置为前3月
        calendar.add(Calendar.DATE, 2); // 加2天
        Date dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        return sdf.format(dBefore);    //格式化前3月的时间
    }

    public String getParams() {
        return "&pageNo=" + pageNo
                + "&startTime=" + startTime
                + "&endTime=" + endTime
                + "&queryType=" + queryType
                + "&tkStatus=" + tkStatus
                + "&memberType=" + memberType
                + "&pageSize=" + pageSize;
    }
}
