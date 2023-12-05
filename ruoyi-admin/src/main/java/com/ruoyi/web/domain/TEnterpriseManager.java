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
 * @description t_enterprise_manager
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_enterprise_manager")
public class TEnterpriseManager implements Serializable {

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
     * position_cn
     */
    @ApiModelProperty("position_cn")
    private String positionCn;

    /**
     * pername
     */
    @ApiModelProperty("pername")
    private String pername;

    /**
     * perid
     */
    @ApiModelProperty("perid")
    private String perid;

    public TEnterpriseManager() {}
}

