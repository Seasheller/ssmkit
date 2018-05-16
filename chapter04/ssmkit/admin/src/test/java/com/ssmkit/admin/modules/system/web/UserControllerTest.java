package com.ssmkit.admin.modules.system.web;

import com.ssmkit.admin.AdminApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class UserControllerTest {

	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before 
	public void setUp() throws Exception { 
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void getUsersTest() throws Exception {
		RequestBuilder request = get("/api/user/getUsers")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("Cache-Control", "no-cache"); 
		
		String result = mvc.perform(request)
				.andExpect(status().isOk()) 
				.andExpect(jsonPath("$.length()").value(1))
				.andReturn().getResponse().getContentAsString();
		
		System.out.println("result: " + result);
	}
	
	@Test
	public void getUserTest() throws Exception {
		RequestBuilder request = get("/api/user/getUser")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("Cache-Control", "no-cache"); 
		
		String result = mvc.perform(request)
				.andExpect(status().isOk()) 
				.andExpect(jsonPath("$.username").value("admin"))
				.andReturn().getResponse().getContentAsString();
		
		System.out.println("result: " + result);
	}
	
	@Test
	public void findUserByPageTest() throws Exception {
		RequestBuilder request = get("/api/user/findUsers")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("Cache-Control", "no-cache"); 
		
		String result = mvc.perform(request)
				.andExpect(status().isOk()) 
//				.andExpect(jsonPath("$.username").value("admin"))
				.andReturn().getResponse().getContentAsString();
		
		System.err.println("result: " + result);
	}
	
	@Test
	public void deleteUserTest() throws Exception {
		RequestBuilder request = get("/api/user/deleteByExample")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("Cache-Control", "no-cache"); 
		
		String result = mvc.perform(request)
				.andExpect(status().isOk()) 
//				.andExpect(jsonPath("$.username").value("admin"))
				.andReturn().getResponse().getContentAsString();
		
		System.err.println("result: " + result);
	}


}
