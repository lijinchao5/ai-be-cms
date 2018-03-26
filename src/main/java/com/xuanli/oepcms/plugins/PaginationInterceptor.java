package com.xuanli.oepcms.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.xuanli.oepcms.plugins.pagination.DialectFactory;
import com.xuanli.oepcms.plugins.pagination.IDialect;
import com.xuanli.oepcms.plugins.pagination.Pagination;


/**
 * 分页拦截器
 *  
 * @FileName PaginationInterceptor.java
 * @Author WangMengmeng E-mail:wangmeng6447@163.com
 * @CreatTime 2016年10月23日 下午7:29:59
 * @Version 1.0
 */
@Component
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PaginationInterceptor implements Interceptor {
	
	/* 方言类型 */
	private String dialectType = "mysql";
	
	/* 方言实现类 */
	private String dialectClazz;

	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		if (target instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) target;
			MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
			RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
			/* 不需要分页的场合 */
			if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
				return invocation.proceed();
			}
			
			/* 定义数据库方言 */
			IDialect dialect = null;
			if (dialectType != null && !"".equals(dialectType)) {
				dialect = DialectFactory.getDialectByDbtype(dialectType);
			} else {
				if (dialectClazz != null && !"".equals(dialectClazz)) {
					try {
						Class<?> clazz = Class.forName(dialectClazz);
						if (IDialect.class.isAssignableFrom(clazz)) {
							dialect = (IDialect) clazz.newInstance();
						}
					} catch (ClassNotFoundException e) {
						throw new Exception("Class :" + dialectClazz + " is not found");
					}
				}
			}
			
			/* 未配置方言则抛出异常 */
			if (dialect == null) {
				throw new Exception("The value of the dialect property in mybatis configuration.xml is not defined.");
			}

			/*
			 * 禁用内存分页
			 */
			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			String originalSql = (String) boundSql.getSql();
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

			/*
			 * 查询总记录数 count
			 */
			if (rowBounds instanceof Pagination) {
				Pagination page = (Pagination) rowBounds;
				System.out.println(page);
				if(!page.isSearchAll()) {
					MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
					Connection connection = (Connection) invocation.getArgs()[0];
					
					if (page.isSearchCount()) {
						page = this.count(originalSql, connection, mappedStatement, boundSql, page);
					}
					originalSql = dialect.buildPaginationSql(originalSql, page.getOffsetCurrent(), page.getSize());
				}
			}

			/*
			 * 查询 SQL 设置
			 */
			metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
		}

		return invocation.proceed();
	}

	/**
	 * 查询总记录条数
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 */
	public Pagination count(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
			Pagination page) {
		String sqlUse = sql;
		int order_by = sql.toUpperCase().lastIndexOf("ORDER BY");
		if ( order_by > -1 ) {
			sqlUse = sql.substring(0, order_by);
		}
		StringBuffer countSql = new StringBuffer("SELECT COUNT(1) AS TOTAL FROM (");
		countSql.append(sqlUse).append(") A");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql.toString());
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql.toString(),
					boundSql.getParameterMappings(), boundSql.getParameterObject());
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
					boundSql.getParameterObject(), countBS);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int total = 0;
			if (rs.next()) {
				total = rs.getInt(1);
			}
			page.setTotal(total);
			/**
			 * 当前页大于总页数，当前页设置为第一页
			 */
			if(page.getCurrent() > page.getPages()){
				page = new Pagination(1, page.getSize());
				page.setTotal(total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return page;
	}

	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties prop) {
		String dialectType = prop.getProperty("dialectType");
		String dialectClazz = prop.getProperty("dialectClazz");
		if (dialectType != null && !"".equals(dialectType)) {
			this.dialectType = dialectType;
		}
		if (dialectClazz != null && !"".equals(dialectClazz)) {
			this.dialectClazz = dialectClazz;
		}
	}

	public void setDialectType(String dialectType) {
		this.dialectType = dialectType;
	}

	public void setDialectClazz(String dialectClazz) {
		this.dialectClazz = dialectClazz;
	}

}