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
 * @description t_copyrights_relations
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_copyrights_relations")
public class TCopyrightsRelations implements Serializable {

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
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * number
     */
    @ApiModelProperty("number")
    private String number;

    /**
     * name
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * short_name
     */
    @ApiModelProperty("short_name")
    private String shortName;

    /**
     * first_date
     */
    @ApiModelProperty("first_date")
    private String firstDate;

    /**
     * success_date
     */
    @ApiModelProperty("success_date")
    private String successDate;

    /**
     * approval_date
     */
    @ApiModelProperty("approval_date")
    private String approvalDate;

    /**
     * type_name
     */
    @ApiModelProperty("type_name")
    private String typeName;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * type_num
     */
    @ApiModelProperty("type_num")
    private String typeNum;

    /**
     * version
     */
    @ApiModelProperty("version")
    private String version;

    /**
     * company
     */
    @ApiModelProperty("company")
    private String company;

    /**
     * created_time
     */
    @ApiModelProperty("created_time")
    private String createdTime;

    /**
     * last_updated_time
     */
    @ApiModelProperty("last_updated_time")
    private String lastUpdatedTime;

    public TCopyrightsRelations() {}
}
