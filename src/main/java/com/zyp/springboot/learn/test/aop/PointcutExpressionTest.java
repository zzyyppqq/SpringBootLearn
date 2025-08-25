package com.zyp.springboot.learn.test.aop;

import com.zyp.springboot.learn.test.aware.HelloService;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

/**
 * Joinpoint，织入点，指需要执行代理操作的某个类的某个方法(仅支持方法级别的JoinPoint)；
 * Pointcut是JoinPoint的表述方式，能捕获JoinPoint。
 *
 * 最常用的切点表达式是AspectJ的切点表达式。需要匹配类，定义ClassFilter接口；匹配方法，定义MethodMatcher接口。
 * PointCut需要同时匹配类和方法，包含ClassFilter和MethodMatcher，
 * AspectJExpressionPointcut是支持AspectJ切点表达式的PointCut实现，简单实现仅支持execution函数。
 */
public class PointcutExpressionTest {

	public static void main(String[] args) throws Exception {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* com.zyp.springboot.learn.test.aware.HelloService.*(..))");
		Class<HelloService> clazz = HelloService.class;
		Method method = clazz.getDeclaredMethod("getBeanFactory");

		System.out.println("pointcut matches: " + pointcut.matches(clazz));
		System.out.println("pointcut.matches" + pointcut.matches(method, clazz));
	}
}