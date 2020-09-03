package org.jeecg.modules.robot.entity;

import lombok.Data;

/**
 *  推广位
 */
@Data
public class TbAdzone {
    private Long recordId; // 如：1962050151
    private Boolean result; // 如：true
    private String pid; // 如：mm_1106390200_1962050151_110820500222
    private Long adzoneId; // 如：110820500222
    private String adzoneName; // 如：测试2
}
