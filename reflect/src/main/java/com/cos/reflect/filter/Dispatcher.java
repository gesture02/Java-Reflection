package com.cos.reflect.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.reflect.controller.UserController;

public class Dispatcher implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		System.out.println("디스패처 진입");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
//		System.out.println("컨텍스트패스 : " + req.getContextPath());
//		System.out.println("식별자패스 : " + req.getRequestURI());
//		System.out.println("전체주소 : " + req.getRequestURL());
		
		//user만 파싱하기
		String endPoint = req.getRequestURI().replaceAll(req.getContextPath(), "");
		System.out.println("엔드포인트 : " + endPoint);
		
		UserController userController = new UserController();
	}
	
}
