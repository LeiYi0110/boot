package com.bjxc.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BjxcRestfulInterceptor implements HandlerInterceptor {

	 /**
     * 在DispatcherServlet完全处理完请求后被调用(可以在该方法进行一些资源的清理操作)
     */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object vlaue, Exception exception) throws Exception {
	}

	/**
     * 在业务处理完成请求后，在DispatcherServlet向客户端返回响应前被调用
     */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object value, ModelAndView model) throws Exception {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		
	}

	/**
     * 业务处理器处理之前被调用，被拦截返回false，反之能正常到Controller层
     */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object value) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		return true;
	}

}
