package com.o2o.maile.network.config;

/**
 * remote request url
 */
public class RequestUrl {

	public static final String HOST_URL = "http://121.42.207.45:8080/server";

	// public static final String HOST_URL =
	// "http://192.168.1.101:8080/com.igou.server";

	public interface connect {
		/**
		 * 连接 获取推送
		 */
		public String connect = "/user/connect";

	}

	public interface account {
		/**
		 * 登陆
		 */
		public String login = "/user/login";

		/**
		 * 注册
		 */
		public String register = "/user/regist";

		/**
		 * 修改密码
		 */
		public String modifypwd = "/user/updatePassword";

		/**
		 * 忘记密码
		 */
		public String forgetpwd = "";
	}

	public interface storeSet {

		/**
		 * 设置位置
		 */
		public String setloc = "/store/setPosition";

		/**
		 * 设置范围
		 */
		public String setServiceRange = "/store/setServiceRange";

		/**
		 * 设置种类
		 */
		public String setGoodsCategory = "/store/setGoodsCategory";

	}

	public interface goods {

		/**
		 * 查询商品种类
		 */
		public String queryGoodsCategory = "/goods/queryGoodsCategory";

		/**
		 * 查询商品（关键字）
		 */
		public String queryGoodsByKey = "/goods/queryGoodsByKeyword";

		/**
		 * 查询商品（关键字）
		 */
		public String queryGoodsByCategory = "/goods/queryGoodsByCategory";

	}

	public interface order {

		/**
		 * 买家下单
		 */
		public String buyCreateOrder = "/order/create";

		/**
		 * 非标商品的创建订单请求
		 */
		public String buyCreateOrder2 = "/order/create2";

		/**
		 * 买家订单
		 */
		public String queryBuyerOrder = "/order/queryBuyerOrder";
		/**
		 * 卖家订单
		 */
		public String querySellerOrder = "/order/querySellerOrder";

	}

}
