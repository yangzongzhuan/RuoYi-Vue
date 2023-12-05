package com.ruoyi.web.domain;



import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @description t_action_person
 * @author www.itgongju.com
 * @date 2023-12-02
 */
@Data
@ApiModel("t_action_person")
public class TActionPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * entname
     */
    @ApiModelProperty("entname")
    @TableField("entname")
    private String entname;

    /**
     * uniscid
     */
    @ApiModelProperty("uniscid")
    @TableField("uniscid")
    private String uniscid;

    /**
     * actgid
     */
    @ApiModelProperty("actgid")
    @TableField("actgid")
    private String actgid;

    /**
     * actrelid
     */
    @ApiModelProperty("actrelid")
    @TableField("actrelid")
    private String actrelid;

    /**
     * actreltype1
     */
    @ApiModelProperty("actreltype1")
    @TableField("actreltype1")
    private String actreltype1;

    /**
     * actreltype
     */
    @ApiModelProperty("actreltype")
    @TableField("actreltype")
    private String actreltype;

    /**
     * relrelid
     */
    @ApiModelProperty("relrelid")
    @TableField("relrelid")
    private String relrelid;

    /**
     * fromid
     */
    @ApiModelProperty("fromid")
    @TableField("fromid")
    private String fromid;

    /**
     * fromname
     */
    @ApiModelProperty("fromname")
    @TableField("fromname")
    private String fromname;

    /**
     * fromtype
     */
    @ApiModelProperty("fromtype")
    @TableField("fromtype")
    private String fromtype;

    /**
     * toid
     */
    @ApiModelProperty("toid")
    private String toid;

    /**
     * toname
     */
    @ApiModelProperty("toname")
    private String toname;

    /**
     * totype
     */
    @ApiModelProperty("totype")
    private String totype;

    /**
     * reltype
     */
    @ApiModelProperty("reltype")
    private String reltype;

    /**
     * property
     */
    @ApiModelProperty("property")
    private String property;

    /**
     * acttype1
     */
    @ApiModelProperty("acttype1")
    private String acttype1;

    /**
     * acttype
     */
    @ApiModelProperty("acttype")
    private String acttype;

    public TActionPerson() {}
}

