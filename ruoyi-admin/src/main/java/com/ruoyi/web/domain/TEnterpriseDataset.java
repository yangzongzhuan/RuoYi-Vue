package com.ruoyi.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

//create table if not exists cidigroup.t_enterprise_dataset
//        (
//        id              int auto_increment
//        primary key,
//        create_time     datetime default CURRENT_TIMESTAMP not null,
//        create_user_id  int                                not null,
//        visible_dataset int                                null,
//        dataset_name    varchar(500)                       null,
//        update_time     datetime default CURRENT_TIMESTAMP null
//        );
@Data
@ApiModel("t_enterprise_dataset")
public class TEnterpriseDataset {

    @ApiModelProperty("id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty("create_time")
    private Date createTime;

    @ApiModelProperty("create_user_id")
    private int createUserId;

    @ApiModelProperty("visible_dataset")
    private int visibleDataset;

    @ApiModelProperty("dataset_name")
    private String datasetName;

    @ApiModelProperty("update_time")
    private Date updateTime;
}
