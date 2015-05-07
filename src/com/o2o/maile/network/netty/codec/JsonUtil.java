package com.o2o.maile.network.netty.codec;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj
	 *            准备转换的对象
	 * @return json字符串
	 * @throws Exception
	 */
	public static String beanToJson(Object obj) throws JsonException {
		try {
			ObjectMapper objectMapper = getMapperInstance();
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new JsonException(e.toString());
		}
	}

	/**
	 * 将json字符串转换成java对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToBean(String json, Class<T> cls) throws JsonException {
		try {
			ObjectMapper objectMapper = getMapperInstance();
			T vo = objectMapper.readValue(json, cls);
			return vo;
		} catch (Exception e) {
			throw new JsonException(e.toString());
		}
	}

	
	private static ObjectMapper getMapperInstance() {
		return new ObjectMapper();

	}
	
	public static class JsonException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3549384463303674720L;

		JsonException(String msg){
			super(msg);
		}
	}
}
