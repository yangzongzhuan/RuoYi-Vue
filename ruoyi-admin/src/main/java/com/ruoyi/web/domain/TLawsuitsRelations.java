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
 * @description t_lawsuits_relations
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_lawsuits_relations")
public class TLawsuitsRelations implements Serializable {

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
     * title
     */
    @ApiModelProperty("title")
    private String title;

    /**
     * case_no
     */
    @ApiModelProperty("case_no")
    private String caseNo;

    /**
     * cause_action
     */
    @ApiModelProperty("cause_action")
    private String causeAction;

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
     * court_code
     */
    @ApiModelProperty("court_code")
    private String courtCode;

    /**
     * role
     */
    @ApiModelProperty("role")
    private String role;

    /**
     * trail_result
     */
    @ApiModelProperty("trail_result")
    private String trailResult;

    /**
     * judgeresult
     */
    @ApiModelProperty("judgeresult")
    private String judgeresult;

    /**
     * sub_amount
     */
    @ApiModelProperty("sub_amount")
    private String subAmount;

    /**
     * related_companies
     */
    @ApiModelProperty("related_companies")
    private String relatedCompanies;

    /**
     * freezing_info
     */
    @ApiModelProperty("freezing_info")
    private String freezingInfo;

    /**
     * date
     */
    @ApiModelProperty("date")
    private String date;

    /**
     * related_relation
     */
    @ApiModelProperty("related_relation")
    private String relatedRelation;

    /**
     * pub_date
     */
    @ApiModelProperty("pub_date")
    private String pubDate;

    /**
     * last_updated_time
     */
    @ApiModelProperty("last_updated_time")
    private String lastUpdatedTime;

    public TLawsuitsRelations() {}
}

