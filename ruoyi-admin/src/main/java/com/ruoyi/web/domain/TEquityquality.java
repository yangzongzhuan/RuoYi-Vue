package com.ruoyi.web.domain;



import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @description t_equityquality
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_equityquality")
@TableName("t_equityquality")
public class TEquityquality implements Serializable {

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
     * u_id
     */
    @ApiModelProperty("u_id")
    private String uId;

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
     * pledgor
     */
    @ApiModelProperty("pledgor")
    private String pledgor;

    /**
     * pledgor_identify_type
     */
    @ApiModelProperty("pledgor_identify_type")
    private String pledgorIdentifyType;

    /**
     * pledgor_identify_no
     */
    @ApiModelProperty("pledgor_identify_no")
    private String pledgorIdentifyNo;

    /**
     * pledgor_amount
     */
    @ApiModelProperty("pledgor_amount")
    private String pledgorAmount;

    /**
     * pledgor_unit
     */
    @ApiModelProperty("pledgor_unit")
    private String pledgorUnit;

    /**
     * pledgor_currency
     */
    @ApiModelProperty("pledgor_currency")
    private String pledgorCurrency;

    /**
     * pawnee
     */
    @ApiModelProperty("pawnee")
    private String pawnee;

    /**
     * pawnee_identify_type
     */
    @ApiModelProperty("pawnee_identify_type")
    private String pawneeIdentifyType;

    /**
     * pawnee_identify_no
     */
    @ApiModelProperty("pawnee_identify_no")
    private String pawneeIdentifyNo;

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
     * remark
     */
    @ApiModelProperty("remark")
    private String remark;

    /**
     * public_date
     */
    @ApiModelProperty("public_date")
    private String publicDate;

    /**
     * pawnee_eid
     */
    @ApiModelProperty("pawnee_eid")
    private String pawneeEid;

    /**
     * object_company
     */
    @ApiModelProperty("object_company")
    private String objectCompany;

    /**
     * pledgor_eid
     */
    @ApiModelProperty("pledgor_eid")
    private String pledgorEid;

    /**
     * object_company_eid
     */
    @ApiModelProperty("object_company_eid")
    private String objectCompanyEid;

    /**
     * row_update_time
     */
    @ApiModelProperty("row_update_time")
    private String rowUpdateTime;

    /**
     * cancel_items
     */
    @ApiModelProperty("cancel_items")
    private String cancelItems;

    /**
     * cancel_date
     */
    @ApiModelProperty("cancel_date")
    private String cancelDate;

    /**
     * cancel_content
     */
    @ApiModelProperty("cancel_content")
    private String cancelContent;

    public TEquityquality() {}
}

