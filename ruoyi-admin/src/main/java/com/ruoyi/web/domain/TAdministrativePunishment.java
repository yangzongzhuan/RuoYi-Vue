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
 * @description t_administrative_punishment
 * @author www.itgongju.com
 * @date 2023-12-02
 */
@Data
@ApiModel("t_administrative_punishment")
public class TAdministrativePunishment implements Serializable {

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
     * number
     */
    @ApiModelProperty("number")
    private String number;

    /**
     * illegal_type
     */
    @ApiModelProperty("illegal_type")
    private String illegalType;

    /**
     * department
     */
    @ApiModelProperty("department")
    private String department;

    /**
     * description
     */
    @ApiModelProperty("description")
    private String description;

    /**
     * date
     */
    @ApiModelProperty("date")
    private String date;

    /**
     * content
     */
    @ApiModelProperty("content")
    private String content;

    /**
     * based_on
     */
    @ApiModelProperty("based_on")
    private String basedOn;

    /**
     * public_date
     */
    @ApiModelProperty("public_date")
    private String publicDate;

    /**
     * based_on_1
     */
    @ApiModelProperty("based_on_1")
    private String basedOn1;

    public TAdministrativePunishment() {}
}

