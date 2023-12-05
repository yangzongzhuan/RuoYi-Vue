package com.ruoyi.web.domain;



import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * @description t_enterprise_stockholder
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_enterprise_stockholder")
@TableName("t_enterprise_stockholder")
public class TEnterpriseStockholder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("entname")
    private String entname;

    /**
     * uniscid
     */
    @ApiModelProperty("uniscid")
    private String uniscid;

    /**
     * invname
     */
    @ApiModelProperty("invname")
    private String invname;

    /**
     * invid
     */
    @ApiModelProperty("invid")
    private String invid;

    /**
     * invtype_cn
     */
    @ApiModelProperty("invtype_cn")
    private String invtypeCn;

    /**
     * subconprop
     */
    @ApiModelProperty("subconprop")
    private String subconprop;

    /**
     * subconam
     */
    @ApiModelProperty("subconam")
    private String subconam;

    public TEnterpriseStockholder() {}
}
