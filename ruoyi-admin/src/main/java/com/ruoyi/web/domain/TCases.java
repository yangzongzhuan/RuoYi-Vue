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
 * @description t_cases
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_cases")
public class TCases implements Serializable {

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
     * role
     */
    @ApiModelProperty("role")
    private String role;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * court
     */
    @ApiModelProperty("court")
    private String court;

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

    /**
     * assistant
     */
    @ApiModelProperty("assistant")
    private String assistant;

    /**
     * region
     */
    @ApiModelProperty("region")
    private String region;

    /**
     * case_no
     */
    @ApiModelProperty("case_no")
    private String caseNo;

    /**
     * hearing_date
     */
    @ApiModelProperty("hearing_date")
    private String hearingDate;

    /**
     * case_status
     */
    @ApiModelProperty("case_status")
    private String caseStatus;

    /**
     * related_companies
     */
    @ApiModelProperty("related_companies")
    private String relatedCompanies;

    /**
     * last_updated_time
     */
    @ApiModelProperty("last_updated_time")
    private String lastUpdatedTime;

    public TCases() {}
}

