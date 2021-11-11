package com.cos.reflect.filter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.reflect.anno.RequestMapping;
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
		
//		//user만 파싱하기
		String endPoint = req.getRequestURI().replaceAll(req.getContextPath(), "");
		//System.out.println("엔드포인트 : " + endPoint);
		
		UserController userController = new UserController();
		
		Method[] methods = userController.getClass().getDeclaredMethods();
		//선언된 메서드를 저장해줌
		//getDeclaredMethods() : 그 파일의 메소드만(선언된 메소드)
		//getMethods() : 상속된 것까지 모두
		
		
//		//리플렉션->메소드를 런타임 시점에서 (실행시) 찾아내서 실행
//		for (Method method : methods) {
//			//System.out.println(method.getName());
//			
//			if(endPoint.equals("/"+method.getName())) {
//				try {
//					method.invoke(userController);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		
		for (Method method : methods) {	//method 수 만큼 돌아감
			Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
			
			RequestMapping requestMapping = (RequestMapping)annotation;
			System.out.println(requestMapping.value());
			
			if(requestMapping.value().equals(endPoint)) {
				try {
					String path = (String)method.invoke(userController);
					
					RequestDispatcher dis = request.getRequestDispatcher(path); //requestDispatcher는 필터 안탐(내부에서 실행됨)
					//response.sendRedirect하면 필터 다시탐 -> 필터를 타면서 톰켓이 req, res객체를 만들어줌
					//requestDispatcher는 req, res를 다시만드는게 아니라 있던걸 덮어씌움-> 다시 톰캣을 안타고 내부적으로 동작
					//내부적으로 req, res객체 들고 파일 찾아서 응답
					dis.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
}
