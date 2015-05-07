package com.o2o.maile.entity;

public class User {

	public enum UserType {
		seller, buyer;
	}

	private String id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 真实姓名
	 */
	private String realName;

	private String question;

	private String answer;

	private String sex;

	private String birthday;

	/**
	 * 头像图片
	 */
	private String img;

	/**
	 * 用户账户余额
	 */
	private String userMoney;

	/**
	 * 积分
	 */
	private String payPoint;

	/**
	 * 经验值
	 */
	private String rankPoint;

	private String regTime;

	/**
	 * 最后登录时间
	 */
	private String lastLoginTime;

	/**
	 * 最后登录ip
	 */
	private String lastLoginIp;

	/**
	 * 访问次数
	 */
	private int visitCount;

	/**
	 * 用户等级
	 */
	private String userRank;

	/**
	 * 注册来源
	 */
	private String fromType;
	
	/**
	 * 联系电话 
	 */
	private String telephone;

	private String qq;

	private String sinaweibo;

	private String province;

	private String city;

	private String isValidated;

	private String mobilePhoneIsBind;

	private String emailIsBind;

	private String brand;

	private String model;

	private String signature;

	private String companyname;

	/**
	 * 职业
	 */
	private String career;

	/**
	 * 职位
	 */
	private String jobPosition;

	/**
	 * 爱好
	 */
	private String hobby;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(String userMoney) {
		this.userMoney = userMoney;
	}

	public String getPayPoint() {
		return payPoint;
	}

	public void setPayPoint(String payPoint) {
		this.payPoint = payPoint;
	}

	public String getRankPoint() {
		return rankPoint;
	}

	public void setRankPoint(String rankPoint) {
		this.rankPoint = rankPoint;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public String getUserRank() {
		return userRank;
	}

	public void setUserRank(String userRank) {
		this.userRank = userRank;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSinaweibo() {
		return sinaweibo;
	}

	public void setSinaweibo(String sinaweibo) {
		this.sinaweibo = sinaweibo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(String isValidated) {
		this.isValidated = isValidated;
	}

	public String getMobilePhoneIsBind() {
		return mobilePhoneIsBind;
	}

	public void setMobilePhoneIsBind(String mobilePhoneIsBind) {
		this.mobilePhoneIsBind = mobilePhoneIsBind;
	}

	public String getEmailIsBind() {
		return emailIsBind;
	}

	public void setEmailIsBind(String emailIsBind) {
		this.emailIsBind = emailIsBind;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

}
