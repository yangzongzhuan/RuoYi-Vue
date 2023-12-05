package com.ruoyi.web.domain;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * @description t_actual_controller
 * @author www.itgongju.com
 * @date 2023-12-02
 */
@Data
@ApiModel("t_actual_controller")
public class TActualController implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * entname
     */
    @ApiModelProperty("entname")
    private String entname;

    /**
     * uniscid
     */
    @ApiModelProperty("uniscid")
    private String uniscid;

    /**
     * ctrlname
     */
    @ApiModelProperty("ctrlname")
    private String ctrlname;

    /**
     * ctrlid
     */
    @ApiModelProperty("ctrlid")
    private String ctrlid;

    /**
     * isidrctrl
     */
    @ApiModelProperty("isidrctrl")
    private String isidrctrl;

    /**
     * ctrltype
     */
    @ApiModelProperty("ctrltype")
    private String ctrltype;

    /**
     * shratio
     */
    @ApiModelProperty("shratio")
    private BigDecimal shratio;

    /**
     * ctrlpath
     */
    @ApiModelProperty("ctrlpath")
    private String ctrlpath;

    public TActualController() {}
}

