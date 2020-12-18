package com.hopu.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类，定义通用属性，可以不继承
 */
@Data
@ToString
public class BaseEntity implements Serializable {
    private String id;
    private Date createTime;
    private Date updateTime;

}
