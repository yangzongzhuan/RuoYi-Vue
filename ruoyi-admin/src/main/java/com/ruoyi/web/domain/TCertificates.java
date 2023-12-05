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
 * @description t_certificates
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_certificates")
public class TCertificates implements Serializable {

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
     * id_id
     */
    @ApiModelProperty("id_id")
    private String idId;

    /**
     * info_type
     */
    @ApiModelProperty("info_type")
    private String infoType;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * register_no
     */
    @ApiModelProperty("register_no")
    private String registerNo;

    /**
     * valid_end
     */
    @ApiModelProperty("valid_end")
    private String validEnd;

    /**
     * valid_start
     */
    @ApiModelProperty("valid_start")
    private String validStart;

    /**
     * validity_start
     */
    @ApiModelProperty("validity_start")
    private String validityStart;

    /**
     * public_date
     */
    @ApiModelProperty("public_date")
    private String publicDate;

    /**
     * state
     */
    @ApiModelProperty("state")
    private String state;

    /**
     * province
     */
    @ApiModelProperty("province")
    private String province;

    /**
     * issue_unit
     */
    @ApiModelProperty("issue_unit")
    private String issueUnit;

    public TCertificates() {}
}

