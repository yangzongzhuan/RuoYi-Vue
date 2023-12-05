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
 * @description t_mortgages
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_mortgages")
public class TMortgages implements Serializable {

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
     * seq_no
     */
    @ApiModelProperty("seq_no")
    private String seqNo;

    /**
     * number
     */
    @ApiModelProperty("number")
    private String number;

    /**
     * date
     */
    @ApiModelProperty("date")
    private String date;

    /**
     * status
     */
    @ApiModelProperty("status")
    private String status;

    /**
     * status_code
     */
    @ApiModelProperty("status_code")
    private String statusCode;

    /**
     * department
     */
    @ApiModelProperty("department")
    private String department;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * amount
     */
    @ApiModelProperty("amount")
    private String amount;

    /**
     * amount_new
     */
    @ApiModelProperty("amount_new")
    private String amountNew;

    /**
     * currency
     */
    @ApiModelProperty("currency")
    private String currency;

    /**
     * period
     */
    @ApiModelProperty("period")
    private String period;

    /**
     * period_start
     */
    @ApiModelProperty("period_start")
    private String periodStart;

    /**
     * period_end
     */
    @ApiModelProperty("period_end")
    private String periodEnd;

    /**
     * scope
     */
    @ApiModelProperty("scope")
    private String scope;

    /**
     * remarks
     */
    @ApiModelProperty("remarks")
    private String remarks;

    /**
     * close_date
     */
    @ApiModelProperty("close_date")
    private String closeDate;

    /**
     * close_reason
     */
    @ApiModelProperty("close_reason")
    private String closeReason;

    /**
     * public_date
     */
    @ApiModelProperty("public_date")
    private String publicDate;

    /**
     * mortgagees
     */
    @ApiModelProperty("mortgagees")
    private String mortgagees;

    /**
     * guarantees
     */
    @ApiModelProperty("guarantees")
    private String guarantees;

    /**
     * changeinfo
     */
    @ApiModelProperty("changeinfo")
    private String changeinfo;

    /**
     * row_update_time
     */
    @ApiModelProperty("row_update_time")
    private String rowUpdateTime;

    public TMortgages() {}
}
