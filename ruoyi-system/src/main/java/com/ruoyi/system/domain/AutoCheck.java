package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("auto_check")
public class AutoCheck {
    private String status;

    @TableField(value = "extranet_ip")
    private String extranetIp;

    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtranetIp() {
        return extranetIp;
    }

    public void setExtranetIp(String extranetIp) {
        this.extranetIp = extranetIp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
