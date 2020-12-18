package com.hopu.utils;

import java.util.UUID;

@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class UUIDUtils {

	public static String getID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
