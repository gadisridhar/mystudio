
package com.tinylight.common.web.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.tinylight.common.dao.base.IBaseDao;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Scott.cgi
 * @since  2009-3-13
 * 测试基础类
 * 用@Test注解的方法为Junit启动方法
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springConfig/applicationContext.xml"})
public abstract class BaseTest {
	
	//让子类拥有日志记录的能力
	protected  Logger log=LoggerFactory.getLogger(this.getClass());
	
	
	
	/**
	 * spring bean的后处理器,如果子类是spring初始化的bean,则在初始化后设置注解dao的实体类类型
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@PostConstruct
	@SuppressWarnings("unchecked")
	protected void preparedDao() throws IllegalArgumentException, IllegalAccessException{
		Class<? extends BaseTest> cl = this.getClass();
		for(Field f : cl.getDeclaredFields()){
			log.info("测试类注解属性名称  = {},类型 = {}",f.getName(),f.getGenericType());
			if(f.isAnnotationPresent(Resource.class) && f.getAnnotation(Resource.class).name().equals("baseDao")){
				f.setAccessible(true);//修改private修饰权限
				Type[] params = ((ParameterizedType)f.getGenericType()).getActualTypeArguments();
				log.info("实体类的参数类型  = {}",params[0]);
			    ((IBaseDao<?,?>)f.get(this)).changeEntityClass((Class)params[0]);//设置实体类类型
			    f.setAccessible(false);//恢复private修饰权限
			}
		}
	}
}

