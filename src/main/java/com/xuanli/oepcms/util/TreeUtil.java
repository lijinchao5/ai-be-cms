package com.xuanli.oepcms.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 把一个list集合,里面的bean含有 parentId 转为树形式
 * 
 */
public class TreeUtil {

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param typeId
	 *            传入的父节点ID
	 * @return String
	 */
	public List<TreeObject> getChildTreeObjects(List<TreeObject> list, int praentId) {
		List<TreeObject> returnList = new ArrayList<TreeObject>();
		for (Iterator<TreeObject> iterator = list.iterator(); iterator.hasNext();) {
			TreeObject t = iterator.next();
			if (t.getParentId().intValue() == praentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 * 
	 * @author lanyuan Email: mmm333zzz520@163.com
	 * @date 2013-12-4 下午7:27:30
	 * @param list
	 * @param TreeObject
	 */
	private void recursionFn(List<TreeObject> list, TreeObject t) {
		List<TreeObject> childList = getChildList(list, t);// 得到子节点列表
		t.setChildren(childList);
		for (TreeObject tChild : childList) {
			if (hasChild(list, tChild)) {// 判断是否有子节点
				// returnList.add(TreeObject);
				Iterator<TreeObject> it = childList.iterator();
				while (it.hasNext()) {
					TreeObject n = it.next();
					recursionFn(list, n);
				}
			}
		}
	}

	// 得到子节点列表
	private List<TreeObject> getChildList(List<TreeObject> list, TreeObject t) {

		List<TreeObject> tlist = new ArrayList<TreeObject>();
		Iterator<TreeObject> it = list.iterator();
		while (it.hasNext()) {
			TreeObject n = it.next();
			if (n.getParentId().intValue() == t.getId()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	List<TreeObject> returnList = new ArrayList<TreeObject>();

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param typeId
	 *            传入的父节点ID
	 * @param prefix
	 *            子节点前缀
	 */
	public List<TreeObject> getChildTreeObjects(List<TreeObject> list, int typeId, String prefix) {
		if (list == null)
			return null;
		for (Iterator<TreeObject> iterator = list.iterator(); iterator.hasNext();) {
			TreeObject node = iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (node.getParentId().intValue() == typeId) {
				recursionFn(list, node, prefix);
			}
			// 二、遍历所有的父节点下的所有子节点
			/*
			 * if (node.getParentId()==0) { recursionFn(list, node); }
			 */
		}
		return returnList;
	}

	private void recursionFn(List<TreeObject> list, TreeObject node, String p) {
		List<TreeObject> childList = getChildList(list, node);// 得到子节点列表
		if (hasChild(list, node)) {// 判断是否有子节点
			returnList.add(node);
			Iterator<TreeObject> it = childList.iterator();
			while (it.hasNext()) {
				TreeObject n = it.next();
				n.setName(p + n.getName());
				n.setText(p + n.getText());
				n.setState(p + n.getState());
				n.setUrl(p+n.getUrl());
				recursionFn(list, n, p + p);
			}
		} else {
			returnList.add(node);
		}
	}

	// 判断是否有子节点
	private boolean hasChild(List<TreeObject> list, TreeObject t) {
		//List<TreeObject> treeObjects = getChildList(list, t);
		if (getChildList(list, t).size()>0) {
			//t.setIconCls("nianji-easyuitree");
			return true;
		}else{
			//t.setIconCls("banji-easyuitree");
			return false;
		}
	}
}
