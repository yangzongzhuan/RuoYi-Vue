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
 * @description t_executions_relations
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_executions_relations")
@TableName("t_executions_relations")
public class TExecutionsRelations implements Serializable {

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
     * province
     */
    @ApiModelProperty("province")
    private String province;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * name
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * case_number
     */
    @ApiModelProperty("case_number")
    private String caseNumber;

    /**
     * court
     */
    @ApiModelProperty("court")
    private String court;

    /**
     * publish_date
     */
    @ApiModelProperty("publish_date")
    private String publishDate;

    /**
     * relatives
     */
    @ApiModelProperty("relatives")
    private String relatives;

    /**
     * date
     */
    @ApiModelProperty("date")
    private String date;

    /**
     * execution_desc
     */
    @ApiModelProperty("execution_desc")
    private String executionDesc;

    /**
     * execution_status
     */
    @ApiModelProperty("execution_status")
    private String executionStatus;

    /**
     * amount
     */
    @ApiModelProperty("amount")
    private String amount;

    /**
     * last_updated_time
     */
    @ApiModelProperty("last_updated_time")
    private String lastUpdatedTime;

    public TExecutionsRelations() {}
}

