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
 * @description t_group_relation
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_group_relation")
public class TGroupRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * groupid
     */
    @ApiModelProperty("groupid")
    private String groupid;

    /**
     * grpname
     */
    @ApiModelProperty("grpname")
    private String grpname;

    /**
     * fromid
     */
    @ApiModelProperty("fromid")
    private String fromid;

    /**
     * fromname
     */
    @ApiModelProperty("fromname")
    private String fromname;

    /**
     * fromtype
     */
    @ApiModelProperty("fromtype")
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
     * property
     */
    @ApiModelProperty("property")
    private String property;

    /**
     * property_cn
     */
    @ApiModelProperty("property_cn")
    private String propertyCn;

    /**
     * reltype
     */
    @ApiModelProperty("reltype")
    private String reltype;

    /**
     * relpath
     */
    @ApiModelProperty("relpath")
    private String relpath;

    public TGroupRelation() {}
}

