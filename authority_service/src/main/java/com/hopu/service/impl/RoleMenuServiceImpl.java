package com.hopu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hopu.domain.RoleMenu;
import com.hopu.mapper.RoleMenuMapper;
import com.hopu.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleMenuServiceImpl  extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
}
