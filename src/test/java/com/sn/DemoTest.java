package com.sn;

import com.sn.mapper.CustomerMapper;
import com.sn.po.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import javax.jms.Session;

@Slf4j
public class DemoTest {

    @Test
    public void dbTest() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            String method = CustomerMapper.class.getName() + ".queryById";
            Customer customer = (Customer) session.selectOne(method, 3);
            System.out.println(customer);
        } catch (IOException e) {
//            log.error("dbTest",e);
        }      
      
    }
    
    @Test
    public void dbTest2() {
    	String resource = "mybatis-config.xml";
    	InputStream inputStream = null;
    	
    	try {
			inputStream = Resources.getResourceAsStream(resource);
			
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession openSession = sqlSessionFactory.openSession();
			String method = CustomerMapper.class.getName()+".queryById";
			Customer customer = openSession.selectOne(method,4);
//			Long id = (long) 4;
//			int x = openSession.delete(method, id);
//			System.out.println(x);
			System.out.println(customer);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
     
}
