package com.o2o.maile.network.config;

/**
 * remote result
 */
public class MsgResult {

	/**
	 * 网络请求返回标识
	 */
	public static final String RESULT_TAG = "result";

	/**
	 * 请求成功标识
	 */
	public static final String RESULT_SUCCESS = "true";

	/**
	 * 请求失败标识
	 */
	public static final String RESULT_FAIL = "false";

	/**
	 * msg
	 */
	public static final String RESULT_MSG_TAG = "message";

	/**
	 * address
	 */
	public static final String RESULT_PUSH_ADDRESS_TAG = "pushAddress";

	/**
	 * errorMessage
	 */
	public static final String RESULT_ERROR_MSG_TAG = "errorMessage";

	/**
	 * 网络请求返回数据标识
	 */
	public static final String RESULT_DATA_TAG = "rows";

	public static final String B2A_RESULT_SUCCESS = "0";

	public static final String B2A_RESULT_NOT_BIND = "1";

	public static final String B2A_RESULT_SUCCESS_TAG = "STATE";

	public static final String B2A_RESULT_DATA_TAG = "DATAS";

}
