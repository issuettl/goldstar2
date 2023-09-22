/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.manager.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author issuettl
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ManagerLogExecution {
	public String menu();
	public String button();
	public String process();
}
