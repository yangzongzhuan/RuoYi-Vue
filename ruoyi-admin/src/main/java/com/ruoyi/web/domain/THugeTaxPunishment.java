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
 * @description t_huge_tax_punishment
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_huge_tax_punishment")
public class THugeTaxPunishment implements Serializable {

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
     * tax_num
     */
    @ApiModelProperty("tax_num")
    private String taxNum;

    /**
     * financial_people
     */
    @ApiModelProperty("financial_people")
    private String financialPeople;

    /**
     * agency_people
     */
    @ApiModelProperty("agency_people")
    private String agencyPeople;

    /**
     * case_type
     */
    @ApiModelProperty("case_type")
    private String caseType;

    /**
     * truth
     */
    @ApiModelProperty("truth")
    private String truth;

    /**
     * law_punishment
     */
    @ApiModelProperty("law_punishment")
    private String lawPunishment;

    /**
     * check_department
     */
    @ApiModelProperty("check_department")
    private String checkDepartment;

    /**
     * police
     */
    @ApiModelProperty("police")
    private String police;

    /**
     * url
     */
    @ApiModelProperty("url")
    private String url;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * time
     */
    @ApiModelProperty("time")
    private String time;

    /**
     * area
     */
    @ApiModelProperty("area")
    private String area;

    /**
     * start_date
     */
    @ApiModelProperty("start_date")
    private String startDate;

    /**
     * end_date
     */
    @ApiModelProperty("end_date")
    private String endDate;

    public THugeTaxPunishment() {}
}

