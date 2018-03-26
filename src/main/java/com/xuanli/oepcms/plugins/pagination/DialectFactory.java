package com.xuanli.oepcms.plugins.pagination;

import com.xuanli.oepcms.plugins.pagination.dialects.MySqlDialect;
import com.xuanli.oepcms.plugins.pagination.dialects.OracleDialect;

/**
 * 方言工厂
 * 
 * @FileName DialectFactory.java
 * @Author WangMengmeng E-mail:wangmeng6447@163.com
 * @CreatTime 2016年10月23日 下午6:48:41
 * @Version 1.0
 */
public class DialectFactory {

	/**
	 * @Description 方言对象创建
	 * @param dbtype
	 * @return
	 * @throws Exception IDialect
	 */
	public static IDialect getDialectByDbtype( String dbtype ) throws Exception {
		if ( "mysql".equalsIgnoreCase(dbtype) ) {
			return new MySqlDialect();
		} else if ( "oracle".equalsIgnoreCase(dbtype) ) {
			return new OracleDialect();
		} else {
			return null;
		}
	}

}
