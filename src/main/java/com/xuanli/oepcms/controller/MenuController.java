/**
 * @fileName:  MenuController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月16日 下午1:47:48
 */ 
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.MenuService;
import com.xuanli.oepcms.util.TreeObject;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/** 
 * @author  QiaoYu 
 */
//@RestController
//@RequestMapping(value = "/menu/")
public class MenuController extends BaseController{
	@Autowired
	MenuService menuService;
	
	/**
	 * @Description:  TODO 用户获取menu菜单信息
	 * @CreateName:  QiaoYu
	 * @CreateDate:  2018年1月16日 下午1:38:34
	 */
	@ApiOperation(value="获取菜单", notes="获取菜单方法")
	@RequestMapping(value = "getUserMenu.do", method = RequestMethod.GET)
	public RestResult<List<TreeObject>> getMenu() {
//		UserEntity userEntity = getCurrentUser();
//		List<TreeObject> trees = menuService.getUserMenu(userEntity);
//		return ok(trees);
		return null;
	}
}
