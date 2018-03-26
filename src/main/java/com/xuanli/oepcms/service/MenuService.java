package com.xuanli.oepcms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.MenuEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.MenuEntityMapper;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.util.TreeObject;
import com.xuanli.oepcms.util.TreeUtil;

@Service
public class MenuService {
	@Autowired
	MenuEntityMapper menuDao;

	/**
	 * @Description: TODO 获取当前用的菜单
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 下午1:50:32
	 */
	public List<TreeObject> getUserMenu(UserEntity userEntity) {
		MenuEntity menuEntity = new MenuEntity();
		menuEntity.setType(userEntity.getRoleId());
		// 获取菜单信息
		List<MenuEntity> menuEntities = menuDao.getUserMenu(menuEntity);
		List<TreeObject> treeObjects = new ArrayList<TreeObject>();
		for (MenuEntity me : menuEntities) {
			TreeObject treeObject = new TreeObject();
			treeObject.setId(me.getId());
			treeObject.setIcon(me.getIcon());
			treeObject.setParentId(me.getParentId());
			treeObject.setName(me.getName());
			String url = me.getUrl();
			if (StringUtil.isNotEmpty(url)) {
				url = url.split(",")[0];
			}
			treeObject.setUrl(url);
			treeObjects.add(treeObject);
		}
		// 转换成tree格式
		TreeUtil treeUtil = new TreeUtil();
		// 根据查询到的信息转换为树
		List<TreeObject> trees = treeUtil.getChildTreeObjects(treeObjects, 0);
		return trees;
	}

}
