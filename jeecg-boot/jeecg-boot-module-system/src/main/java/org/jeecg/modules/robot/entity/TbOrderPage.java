package org.jeecg.modules.robot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 订单分页
 */
@ToString
@Setter
@Getter
public class TbOrderPage {
    private boolean hasPre = false; // 如：false
    private String positionIndex = null; // 如："1598628432_1qL2v5MLDrg2|1598628432_1qL2v5MLDrg2"
    private int prePage; // 如：1
    private int nextPage; // 如：1
    private int totalCount; // 如：-1
    private boolean hasNext = false; // 如：false
    private int pageNo; // 如：1
    private int pageSize; // 如：40
    private int totalPages; // 如：-1
    private List<TbOrder> result;
}
