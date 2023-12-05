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
 * @description t_change_records
 * @author zhangshuo
 * @date 2023-12-02
 */
@Data
@ApiModel("t_change_records")
public class TChangeRecords implements Serializable {

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
     * after_content
     */
    @ApiModelProperty("after_content")
    private String afterContent;

    /**
     * before_content
     */
    @ApiModelProperty("before_content")
    private String beforeContent;

    /**
     * type
     */
    @ApiModelProperty("type")
    private String type;

    /**
     * change_item
     */
    @ApiModelProperty("change_item")
    private String changeItem;

    /**
     * change_date
     */
    @ApiModelProperty("change_date")
    private String changeDate;

    public TChangeRecords() {}
}

