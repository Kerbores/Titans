package club.zhcsjxls.test;

import java.util.Date;

import org.nutz.lang.Times;

import club.zhcs.jxls.anno.Title;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project jxls
 *
 * @file User.java
 *
 * @description
 *
 * @copyright 内部代码,禁止转发
 *
 * @time 2016年5月20日 下午3:31:44
 *
 */
public class User {

	@Title("编号")
	private int id;

	@Title("姓名")
	private String name;

	@Title("生日")
	private Date birthDay = Times.now();

	@Title("财富")
	private double wealth;

}
