package com.cos.reflect.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//동작 시점을 결정할 수 있음
//컴파일 시 -> .class로 바뀔때
//런타임 시 -> 실행중(요청 받았을 때 해야하므로 이걸로함)
@Target({ElementType.METHOD})//클래스(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	String value();
}
