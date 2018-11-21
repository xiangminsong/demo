package cn.itcast.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import cn.itcast.controller.JsoupController.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class JsoupController {
	
//	@Test
//	public void test_jsoup() {
//		try {
//			Document document = Jsoup.connect("http://item.jd.com/4093819.html").get();
////			Elements ele = document.select("div .sku-name");
//			Elements ele = document.select("div .tab-con");
//			ele.forEach(e -> {
//				System.err.println(e.html());
//			});
//			Elements price = document.select("div .summary-price-wrap");
//			price.forEach(p -> {
//				System.out.println(price);
//			});
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

	//http://item.jd.com/4093819.html
	@Test
	public void test_jsoup() throws IOException {
		
			
			Document doc = Jsoup.connect("http://item.jd.com/4093819.html").get();
//			 Elements eles = doc.getElementsByClass("detail");
			String select = doc.select("#detail").first().text();
			System.err.println(select);
			String text = doc.body().text();
			System.out.println(text);
	}
	
	
	@Test
	public void test_sctatch() {
		try {
			URL url = new URL("http://www.163.com");
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			InputStream input = connection.getInputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = input.read(b)) != -1) {
				String line = new String(b, 0, len, "utf-8");
				System.out.println(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test
	public void cloneTest() {
		User user  = new User("aa", "zhangsan", 18);
		System.out.println(user);
		User user2 = user.clone(user);
		System.out.println(user2);
		String strJson = JSONObject.toJSONString(user);
		System.out.println(strJson);
		Gson gson = new Gson();
		User user3 = gson.fromJson(strJson, User.class);
		System.out.println(user3);
	}
	
	class User{
		private String name;
		private String desc;
		private Integer age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("User [name=");
			builder.append(name);
			builder.append(", desc=");
			builder.append(desc);
			builder.append(", age=");
			builder.append(age);
			builder.append("]");
			return builder.toString();
		}
		public User(String name, String desc, Integer age) {
			super();
			this.name = name;
			this.desc = desc;
			this.age = age;
		}
		public User clone(User user) {
			User user2 = new User(user.getName(), user.getDesc(), user.getAge());
			return user2;
		}
	}
}
