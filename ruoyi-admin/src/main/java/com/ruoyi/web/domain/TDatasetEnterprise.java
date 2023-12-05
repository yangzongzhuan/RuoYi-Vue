package com.ruoyi.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("t_rela_dataset_enterprise")
@TableName("t_rela_dataset_enterprise")
public class TDatasetEnterprise {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("dataset_id")
    private String datasetId;

    @ApiModelProperty("uniscid")
    private String uniscid;
}
