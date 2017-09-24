package com.bjxc.school.security;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SchoolController {
	 protected static Logger logger = Logger.getLogger(SchoolController.class);  

	 /**
	  * 跳转到comminpage页面
	  * @return
	  */
	 @RequestMapping("/school")
	 public  String forwordIndexPage(){
		 logger.debug("Received request to show common page");  
		
		 return "school";
	 }
	 
	/* @RequestMapping("loginok")
	 public @ResponseBody String loginOk(){
		 System.out.println("login success"); 
		 UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
		 userDetails.getUsername();
		 return "success";
	 }
	
	 @RequestMapping("mypermission")
	 public @ResponseBody List<String> loadPermission(){
		 List<String> permission=new ArrayList<String>();
		 UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
		String username= userDetails.getUsername();
		if("admin".equals(username)){
			permission.add("index");
			permission.add("admin");
		}else{
			permission.add("index");
		}
		System.out.println(permission);
		 return permission;
	 } */
	 
}
