package com.ruoyi.web.domain;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * @description t_enterprise_investment
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_enterprise_investment")
public class TEnterpriseInvestment implements Serializable {

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
     * invest_eid
     */
    @ApiModelProperty("invest_eid")
    private String investEid;

    /**
     * create_time
     */
    @ApiModelProperty("create_time")
    private String createTime;

    /**
     * row_update_time
     */
    @ApiModelProperty("row_update_time")
    private String rowUpdateTime;

    /**
     * stock_percent
     */
    @ApiModelProperty("stock_percent")
    private String stockPercent;

    /**
     * should_capi
     */
    @ApiModelProperty("should_capi")
    private String shouldCapi;

    /**
     * invest_name
     */
    @ApiModelProperty("invest_name")
    private String investName;

    /**
     * invest_credit_no
     */
    @ApiModelProperty("invest_credit_no")
    private String investCreditNo;

    /**
     * invest_reg_no
     */
    @ApiModelProperty("invest_reg_no")
    private String investRegNo;

    /**
     * invest_status
     */
    @ApiModelProperty("invest_status")
    private String investStatus;

    /**
     * invest_oper_name
     */
    @ApiModelProperty("invest_oper_name")
    private String investOperName;

    /**
     * invest_regist_capi
     */
    @ApiModelProperty("invest_regist_capi")
    private String investRegistCapi;

    /**
     * invest_start_date
     */
    @ApiModelProperty("invest_start_date")
    private String investStartDate;

    /**
     * belong_org
     */
    @ApiModelProperty("belong_org")
    private String belongOrg;

    /**
     * belong_org_code
     */
    @ApiModelProperty("belong_org_code")
    private String belongOrgCode;

    /**
     * invest_status_code
     */
    @ApiModelProperty("invest_status_code")
    private String investStatusCode;

    public TEnterpriseInvestment() {}
}

