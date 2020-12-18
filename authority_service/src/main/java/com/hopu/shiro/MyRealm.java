package com.hopu.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hopu.domain.*;
import com.hopu.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IRoleMenuService roleMenuService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;

	// 用户认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// 先获取token中携带的用户名
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = token.getUsername();
		// 用户查询
		User user = userService.getOne(new QueryWrapper<User>().eq("user_name", username));
		if (user==null) {
			throw new UnknownAccountException("用户名或密码有误！");
		}
		// 返回认证后信息
		ByteSource credentialsSalt = ByteSource.Util.bytes(username + user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
		return info;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 应该先查询用户
		User user = (User) principalCollection.getPrimaryPrincipal();
		// 根据对应用户，才可以查询他有的角色和权限
		// 先查询角色
		List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
		ArrayList<String> roles = new ArrayList<>();
		userRoleList.forEach(userRole -> {
			Role role = roleService.getById(userRole.getRoleId());
			roles.add(role.getRole());
		});
		// 接着，查询权限
		ArrayList<String> permissions = new ArrayList<>();
		userRoleList.forEach(userRole -> {
			List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", userRole.getRoleId()));
			roleMenuList.forEach(roleMenu -> {
				Menu menu = menuService.getById(roleMenu.getMenuId());
				if(menu!=null){
					permissions.add(menu.getPermiss());
				}
			});
		});

		// 核心是：返回的simpleAuthorizationInfo对象必须封装对应的角色和权限信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(roles);
		simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}
}
