package com.ruoyi.web.domain;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * @description t_enterprise_basic
 * @author www.itgongju.com
 * @date 2023-12-02
 */
@Data
@ApiModel("t_enterprise_basic")
@TableName("t_enterprise_basic")
public class TEnterpriseBasicDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * entname
     */
    @ApiModelProperty("entname")
    private String entname;

    /**
     * uniscid
     */
    @TableId(value = "uniscid", type = IdType.INPUT)
    @ApiModelProperty("uniscid")
    private String uniscid;

    /**
     * regno
     */
    @ApiModelProperty("regno")
    private String regno;

    /**
     * enttype_cn
     */
    @ApiModelProperty("enttype_cn")
    private String enttypeCn;

    /**
     * regcap
     */
    @ApiModelProperty("regcap")
    private String regcap;

    /**
     * paidcap
     */
    @ApiModelProperty("paidcap")
    private String paidcap;

    /**
     * opscope
     */
    @ApiModelProperty("opscope")
    private String opscope;

    /**
     * domdistrict
     */
    @ApiModelProperty("domdistrict")
    private String domdistrict;

    /**
     * domdistrict_cn
     */
    @ApiModelProperty("domdistrict_cn")
    private String domdistrictCn;

    /**
     * entstatus_cn
     */
    @ApiModelProperty("entstatus_cn")
    private String entstatusCn;

    /**
     * esdate
     */
    @ApiModelProperty("esdate")
    private String esdate;

    /**
     * opfrom
     */
    @ApiModelProperty("opfrom")
    private String opfrom;

    /**
     * opto
     */
    @ApiModelProperty("opto")
    private String opto;

    /**
     * apprdate
     */
    @ApiModelProperty("apprdate")
    private String apprdate;

    /**
     * regorg_cn
     */
    @ApiModelProperty("regorg_cn")
    private String regorgCn;

    /**
     * lerepname
     */
    @ApiModelProperty("lerepname")
    private String lerepname;

    /**
     * regcapcur_cn
     */
    @ApiModelProperty("regcapcur_cn")
    private String regcapcurCn;

    /**
     * empnum
     */
    @ApiModelProperty("empnum")
    private String empnum;

    /**
     * industrycode
     */
    @ApiModelProperty("industrycode")
    private String industrycode;

    /**
     * industryname
     */
    @ApiModelProperty("industryname")
    private String industryname;

    /**
     * reg_addr
     */
    @ApiModelProperty("reg_addr")
    private String regAddr;

    /**
     * orgtype
     */
    @ApiModelProperty("orgtype")
    private String orgtype;

    /**
     * orgtype_cn
     */
    @ApiModelProperty("orgtype_cn")
    private String orgtypeCn;

    /**
     * perid
     */
    @ApiModelProperty("perid")
    private String perid;

    /**
     * grpid
     */
    @ApiModelProperty("grpid")
    private String grpid;

    /**
     * grpname
     */
    @ApiModelProperty("grpname")
    private String grpname;

    @TableField(exist = false)
    private boolean checked;

    @TableField(exist = false)
    private List<TPatentsRelations> tPatentsRelationsList;

    @TableField(exist = false)
    private List<TTrademarks> tTrademarksList;

    @TableField(exist = false)
    private List<TNewJobs> tNewJobsList;

    // 是否为上市公司
    @TableField(exist = false)
    private boolean isListed;

    // 关联企业数量
    @TableField(exist = false)
    private int relaNums;

    // 上市企业披露的关联企业数量
    @TableField(exist = false)
    private int listedRelaNums;


//    private TEnterpriseStockholder tEnterpriseStockholder;

    public TEnterpriseBasicDto() {}
}
