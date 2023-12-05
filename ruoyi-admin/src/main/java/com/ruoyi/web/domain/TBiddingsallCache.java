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
 * @description t_biddingsall_cache
 * @author www.itgongju.com
 * @date 2023-12-02
 */
@Data
@ApiModel("t_biddingsall_cache")
public class TBiddingsallCache implements Serializable {

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
     * role
     */
    @ApiModelProperty("role")
    private String role;

    /**
     * proj_num
     */
    @ApiModelProperty("proj_num")
    private String projNum;

    /**
     * province
     */
    @ApiModelProperty("province")
    private String province;

    /**
     * notice_type
     */
    @ApiModelProperty("notice_type")
    private String noticeType;

    /**
     * date
     */
    @ApiModelProperty("date")
    private String date;

    /**
     * tender_agent
     */
    @ApiModelProperty("tender_agent")
    private String tenderAgent;

    /**
     * area_name
     */
    @ApiModelProperty("area_name")
    private String areaName;

    /**
     * area_code
     */
    @ApiModelProperty("area_code")
    private String areaCode;

    /**
     * source
     */
    @ApiModelProperty("source")
    private String source;

    /**
     * tender_corpnames
     */
    @ApiModelProperty("tender_corpnames")
    private String tenderCorpnames;

    /**
     * content
     */
    @ApiModelProperty("content")
    private String content;

    public TBiddingsallCache() {}
}

