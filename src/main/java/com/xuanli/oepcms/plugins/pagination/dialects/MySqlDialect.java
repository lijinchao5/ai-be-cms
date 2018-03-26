package com.xuanli.oepcms.plugins.pagination.dialects;

import com.xuanli.oepcms.plugins.pagination.IDialect;

/**
 * MYSQL数据库分页语句
 * 
 * @FileName MySqlDialect.java
 * @Author WangMengmeng E-mail:wangmeng6447@163.com
 * @CreatTime 2016年10月23日 下午7:25:01
 * @Version 1.0
 */
public class MySqlDialect implements IDialect {

	public String buildPaginationSql(String originalSql, int offset, int limit) {
		StringBuilder sql = new StringBuilder(originalSql);
		sql.append(" LIMIT ").append(offset).append(",").append(limit);
		return sql.toString();
	}

}
