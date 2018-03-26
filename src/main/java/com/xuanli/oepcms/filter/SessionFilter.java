/**
 * @fileName:  SessionFilter.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月17日 上午10:06:08
 */
package com.xuanli.oepcms.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.xuanli.oepcms.entity.UserAdmin;
import com.xuanli.oepcms.util.SessionAdminUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Order(1)
@WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月17日 上午10:06:44
	 */

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月17日 上午10:06:44
	 */

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 不过滤的uri
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String[] notFilter = new String[] { "login.do", "logout.do", "/picture", "/resourceSync", "/file" };
		String uri = request.getRequestURI();
		boolean doFilter = true;
		if (uri.indexOf(".do") != -1) {
			logger.info("SessionFilter拦截器[URI=" + uri + "]");
			for (String s : notFilter) {
				if (uri.indexOf(s) != -1) {
					doFilter = false;
					break;
				}
			}
			if (doFilter) {
				ServletContext context = request.getServletContext();
				ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
				SessionAdminUtil sessionUtil = ctx.getBean(SessionAdminUtil.class);
				Enumeration<String> enumeration = request.getHeaders("X-AUTH-TOKEN");
				if (enumeration.hasMoreElements()) {
					String tokenId = (String) enumeration.nextElement();
					UserAdmin user = sessionUtil.getSessionUser(tokenId);
					if (null != user) {
						logger.info("开始进入Controller[Session=" + user + "]");
						chain.doFilter(request, response);
					} else {
						logger.info("进入SessionFilter拦截器[Session是空的请求!]");
						response.setContentType("text/json; charset=utf-8");
						PrintWriter printWriter = response.getWriter();
						RestResult<String> restResult = new RestResult<String>();
						restResult.setCode(99998);
						restResult.setResult("登陆超时");
						restResult.setMessage("登陆超时");
						printWriter.print(JSON.toJSONString(restResult));
						printWriter.flush();
						printWriter.close();
					}
				} else {
					logger.info("进入SessionFilter拦截器[Session是空的请求!]");
					PrintWriter printWriter = response.getWriter();
					RestResult<String> restResult = new RestResult<String>();
					restResult.setCode(99998);
					restResult.setResult("用户未登陆");
					restResult.setMessage("用户未登陆");
					printWriter.print(JSON.toJSONString(restResult));
					printWriter.flush();
					printWriter.close();
				}
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
//		chain.doFilter(servletRequest, response);
	}

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月17日 上午10:06:44
	 */

	@Override
	public void destroy() {
	}

}
