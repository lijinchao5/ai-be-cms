package com.xuanli.oepcms.plugins.pagination;

/**
 * 分页语句接口
 * 
 * @FileName IDialect.java
 * @Author WangMengmeng E-mail:wangmeng6447@163.com
 * @CreatTime 2016年10月23日 下午7:27:09
 * @Version 1.0
 */
public interface IDialect {

	/**
	 * 组装分页语句
	 * @param originalSql 原始语句
	 * @param offset 偏移量
	 * @param limit 界限
	 * @return 分页语句
	 */
	String buildPaginationSql(String originalSql, int offset, int limit);
}
