package com.xuanli.oepcms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.UserAdmin;
import com.xuanli.oepcms.mapper.UserAdminMapper;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.RanNumUtil;
import com.xuanli.oepcms.util.SessionAdminUtil;

@Service
@Transactional
public class AdminUserService extends BaseService{
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserAdminMapper useradmin;
	@Autowired
	SessionAdminUtil sessionUtil;
	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午2:13:52
	 */
	public String login(String username, String password, HttpServletRequest request) {
		UserAdmin userAdmin = new UserAdmin();
		userAdmin.setUname(username);
		List<UserAdmin> list = useradmin.select(userAdmin);
		if (null != list && list.size() > 0) {
			UserAdmin result = list.get(0);
			if (PasswordUtil.verify(password, result.getUpwd())) {
				// 登陆成功
				String tokenId = RanNumUtil.getRandom();
				sessionUtil.setSessionUser(tokenId, result);
				return tokenId;
			} else {
				// 用户名或者密码错误
				return "2";
			}
		} else {
			// 用户名或者密码错误
			return "2";
		}
	}
	
	public String updatepwd(UserAdmin currentUser,String oldpwd, String newpwd, HttpServletRequest request) {
		UserAdmin userAdmin = new UserAdmin();
		userAdmin.setUname(currentUser.getUname());
		userAdmin.setId(currentUser.getId());
		List<UserAdmin> list = useradmin.select(userAdmin);
		if (null != list && list.size() > 0) {
			UserAdmin result = list.get(0);
			if (PasswordUtil.verify(oldpwd, result.getUpwd())) {
				// 登陆成功
				String generate = PasswordUtil.generate(newpwd);
				userAdmin.setUpwd(generate);
				useradmin.updateByPrimaryKeySelective(userAdmin);
				//String tokenId = RanNumUtil.getRandom();
				//essionUtil.setSessionUser(tokenId, result);
				return "1";
			} else {
				// 用户名或者密码错误
				return "2";
			}
		} else {
			// 用户名或者密码错误
			return "2";
		}
	}
	
	
}
