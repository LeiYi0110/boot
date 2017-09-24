package com.bjxc.utils;

import com.bjxc.utils.encide.Md5PasswordEncoder;

public class PasswordCoder {
	public static final String salt = "cl2015";	//√‹Œƒ
	private static final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
	
	public static String codePassword(String word){
		String pwd=md5PasswordEncoder.encodePassword(word, salt);
		return pwd;
	}
}
