package com.southeast.aop.pointcut;

/**
 * Advisor :
 * 代表一般切面，Advice本身就是一个切面，对目标类所有方法进行拦截
 * TODO:(不带有切点的切面.针对所有方法进行拦截)
 * PointcutAdvisor :
 * 代表具有切点的切面，可以指定拦截目标类哪些方法
 * TODO:(带有切点的切面,针对某个方法进行拦截)
 */
public interface PointcutAdvisor extends Advisor{
    /**
     * 获取切点
     */
    Pointcut getPointcut();
}
