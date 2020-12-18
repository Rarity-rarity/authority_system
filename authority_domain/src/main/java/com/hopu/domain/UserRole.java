package com.hopu.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色
 */
@Data
@TableName("t_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;  // 用户id
    private String roleId; // 角色id
}
