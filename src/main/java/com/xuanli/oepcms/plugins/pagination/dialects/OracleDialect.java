package com.xuanli.oepcms.plugins.pagination.dialects;

import com.xuanli.oepcms.plugins.pagination.IDialect;

/**
 * ORACLE数据库分页语句
 * 
 * @FileName OracleDialect.java
 * @Author WangMengmeng E-mail:wangmeng6447@163.com
 * @CreatTime 2016年10月23日 下午7:25:48
 * @Version 1.0
 */
public class OracleDialect implements IDialect {

	public String buildPaginationSql( String originalSql, int offset, int limit ) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ( SELECT TEMP.*, ROWNUM ROW_ID FROM ( ");
		sql.append(originalSql).append(" ) TEMP WHERE ROWNUM <=").append((offset >= 1) ? (offset + limit) : limit);
		sql.append(") WHERE ROW_ID > ").append(offset);
		return sql.toString();
	}

}
