package service2;
/**
 * Bean2的静态工厂
 */
public class Bean2Factory {
	public static Bean2 getBean2(){
		System.out.println("静态工厂的获得Bean2的方法...");
		return new Bean2();
	}
}
