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
 * @description t_judicial_freezes
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_judicial_freezes")
public class TJudicialFreezes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
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
     * be_executed_person
     */
    @ApiModelProperty("be_executed_person")
    private String beExecutedPerson;

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
     * executive_court
     */
    @ApiModelProperty("executive_court")
    private String executiveCourt;

    /**
     * number
     */
    @ApiModelProperty("number")
    private String number;

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
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * type_code
     */
    @ApiModelProperty("type_code")
    private String typeCode;

    /**
     * fre_eid
     */
    @ApiModelProperty("fre_eid")
    private String freEid;

    /**
     * source_eid
     */
    @ApiModelProperty("source_eid")
    private String sourceEid;

    /**
     * lose_efficacy_reason
     */
    @ApiModelProperty("lose_efficacy_reason")
    private String loseEfficacyReason;

    /**
     * lose_efficacy_date
     */
    @ApiModelProperty("lose_efficacy_date")
    private String loseEfficacyDate;

    /**
     * detail
     */
    @ApiModelProperty("detail")
    private String detail;

    /**
     * row_update_time
     */
    @ApiModelProperty("row_update_time")
    private String rowUpdateTime;

    public TJudicialFreezes() {}
}

