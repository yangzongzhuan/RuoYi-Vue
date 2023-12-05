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
 * @description t_serious_illegal
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_serious_illegal")
public class TSeriousIllegal implements Serializable {

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
     * execution
     */
    @ApiModelProperty("execution")
    private String execution;

    /**
     * executed_person
     */
    @ApiModelProperty("executed_person")
    private String executedPerson;

    /**
     * major_tax_violatio
     */
    @ApiModelProperty("major_tax_violatio")
    private String majorTaxViolatio;

    /**
     * row_update_time
     */
    @ApiModelProperty("row_update_time")
    private String rowUpdateTime;

    public TSeriousIllegal() {}
}

