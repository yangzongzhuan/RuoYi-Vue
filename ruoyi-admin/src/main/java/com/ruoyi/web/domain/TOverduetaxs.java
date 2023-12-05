package com.ruoyi.web.domain;



import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @description t_overduetaxs
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_overduetaxs")
public class TOverduetaxs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * eid
     */
    @ApiModelProperty("eid")
    private String eid;

    /**
     * ename
     */
    @ApiModelProperty("ename")
    private String ename;

    /**
     * uniscid
     */
    @ApiModelProperty("uniscid")
    private String uniscid;

    /**
     * pub_department
     */
    @ApiModelProperty("pub_department")
    private String pubDepartment;

    /**
     * overdue_type
     */
    @ApiModelProperty("overdue_type")
    private String overdueType;

    /**
     * history_overdue_amount
     */
    @ApiModelProperty("history_overdue_amount")
    private String historyOverdueAmount;

    /**
     * area
     */
    @ApiModelProperty("area")
    private String area;

    /**
     * overdue_amount
     */
    @ApiModelProperty("overdue_amount")
    private String overdueAmount;

    /**
     * curr_overdue_amount
     */
    @ApiModelProperty("curr_overdue_amount")
    private String currOverdueAmount;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * overdue_time
     */
    @ApiModelProperty("overdue_time")
    private String overdueTime;

    /**
     * url
     */
    @ApiModelProperty("url")
    private String url;

    /**
     * tax_bureau
     */
    @ApiModelProperty("tax_bureau")
    private String taxBureau;

    public TOverduetaxs() {}
}

