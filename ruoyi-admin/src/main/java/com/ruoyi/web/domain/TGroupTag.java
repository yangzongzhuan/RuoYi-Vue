package com.ruoyi.web.domain;



import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @description t_group_tag
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_group_tag")
public class TGroupTag implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * grpembrs
     */
    @ApiModelProperty("grpembrs")
    private Integer grpembrs;

    /**
     * grpregcaps
     */
    @ApiModelProperty("grpregcaps")
    private BigDecimal grpregcaps;

    /**
     * fdcmbrs
     */
    @ApiModelProperty("fdcmbrs")
    private Long fdcmbrs;

    /**
     * listedmbrs
     */
    @ApiModelProperty("listedmbrs")
    private Integer listedmbrs;

    /**
     * gxjsmbrs
     */
    @ApiModelProperty("gxjsmbrs")
    private Long gxjsmbrs;

    /**
     * statembrs
     */
    @ApiModelProperty("statembrs")
    private Integer statembrs;

    /**
     * ratio
     */
    @ApiModelProperty("ratio")
    private Double ratio;

    /**
     * hysl
     */
    @ApiModelProperty("hysl")
    private Integer hysl;

    /**
     * hyjzd
     */
    @ApiModelProperty("hyjzd")
    private Double hyjzd;

    /**
     * zcsfsl
     */
    @ApiModelProperty("zcsfsl")
    private Integer zcsfsl;

    /**
     * qyjzd
     */
    @ApiModelProperty("qyjzd")
    private Double qyjzd;

    /**
     * licnums
     */
    @ApiModelProperty("licnums")
    private Integer licnums;

    public TGroupTag() {}
}

