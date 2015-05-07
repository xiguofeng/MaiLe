package com.o2o.maile.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.o2o.maile.R;
import com.o2o.maile.entity.User;
import com.o2o.maile.network.logic.UserLogic;
import com.o2o.maile.ui.view.CustomProgressDialog;
import com.o2o.maile.util.AppManager;
import com.o2o.maile.util.UserInfoManager;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	public static final String ORIGIN_FROM_REG_KEY = "com.reg";

	public static final String ORIGIN_FROM_ORDER_KEY = "com.order";

	private EditText mUserNameEt;
	private EditText mPassWordEt;
	private CheckBox mRemberpswCb;
	private TextView mShortcutBuyTv;
	// private LinearLayout layoutProcess;
	private Button mLoginBtn;
	private Button mRegNewUserBtn;

	private CustomProgressDialog mCustomProgressDialog;
	private String mUserName;
	private String mPassWord;

	private User mUser = new User();

	private Context mContext = LoginActivity.this;

	private String mComeFlag;

	// 登陆装填提示handler更新主线程，提示登陆状态情况
	Handler mLoginHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case UserLogic.LOGIN_SUC: {
				// TODO
				// if (!TextUtils.isEmpty(mComeFlag)
				// || ORIGIN_FROM_ORDER_KEY.endsWith(mComeFlag)) {
				// AppManager.getInstance().killActivity(MainActivity.class);
				// }
				UserInfoManager.saveUserInfo(LoginActivity.this,
						mUser.getUsername(), mUser.getPassword(), mUser);
				UserInfoManager.setUserInfo(LoginActivity.this);
				UserInfoManager.setLoginIn(LoginActivity.this, true);

				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);

				break;
			}
			case UserLogic.LOGIN_FAIL: {
				Toast.makeText(mContext, R.string.login_fail,
						Toast.LENGTH_SHORT).show();
				break;
			}
			case UserLogic.LOGIN_EXCEPTION: {
				break;
			}
			case UserLogic.NET_ERROR: {
				break;
			}

			default:
				break;
			}

			if (null != mCustomProgressDialog) {
				mCustomProgressDialog.dismiss();
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		AppManager.getInstance().addActivity(LoginActivity.this);
		initView();
		initData();

		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	private void initView() {
		/* 初始化控件 */
		mUserNameEt = (EditText) findViewById(R.id.login_username);
		mPassWordEt = (EditText) findViewById(R.id.login_password);

		mShortcutBuyTv = (TextView) findViewById(R.id.login_shortcut_buy_tv);
		mShortcutBuyTv.setOnClickListener(this);
		// layoutProcess = (LinearLayout) findViewById(R.id.login_status);

		mRemberpswCb = (CheckBox) findViewById(R.id.checkBox_remberpsw);

		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mLoginBtn.setOnClickListener(this);

		mRegNewUserBtn = (Button) findViewById(R.id.register_btn);
		mRegNewUserBtn.setOnClickListener(this);

	}

	private void initData() {
		if (!TextUtils.isEmpty(getIntent().getAction())
				&& getIntent().getAction().equals(ORIGIN_FROM_REG_KEY)) {
			mUserNameEt.setText(UserInfoManager.userInfo.getUsername());
			mPassWordEt.setText(UserInfoManager.userInfo.getPassword());
			login();
			mComeFlag = ORIGIN_FROM_REG_KEY;
		} else if (!TextUtils.isEmpty(getIntent().getAction())
				&& getIntent().getAction().equals(ORIGIN_FROM_ORDER_KEY)) {

			if (UserInfoManager.getRememberPwd(mContext)) {
				mUserNameEt.setText(UserInfoManager.userInfo.getUsername());
				mPassWordEt.setText(UserInfoManager.userInfo.getPassword());
			}
			// mShortcutBuyTv.setVisibility(View.GONE);
			mComeFlag = ORIGIN_FROM_ORDER_KEY;
			// // 检测是否存在SD卡，存在SD卡的情况下进行判断文件是否存在
			// if (AndroidTools.isHasSD()) {
			// // 检测是否存在文件，不存在，则创建xml文件
			// if (AndroidTools.isFileExists(Fileconfig.xmlinfopath)) {
			// // 存在xml,读取内容，放置到表单中国
			// String xmlpath = Fileconfig.sdrootpath
			// + Fileconfig.xmlinfopath;
			// mUserNameEt.setText(XMLHelper.readXMLByNodeName(
			// UserXmlParseConst.USERNAME, xmlpath));
			// mPassWordEt.setText(XMLHelper.readXMLByNodeName(
			// UserXmlParseConst.PASSWORD, xmlpath));
			// mRemberpswCb.setChecked(true);
			//
			// }
			// } else {
			// // 没有内存卡，不需要执行操作
			// }
		} else {
			if (UserInfoManager.getRememberPwd(mContext)) {
				UserInfoManager.setUserInfo(LoginActivity.this);
				mUserNameEt.setText(UserInfoManager.userInfo.getUsername());
				mPassWordEt.setText(UserInfoManager.userInfo.getPassword());
				mRemberpswCb.setChecked(true);
			}
		}
		// 设置checkbox监听事件，如果选中，则将信息写入xml中，如果未选中，则删除xml文件
		mRemberpswCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// 如果点选记住密码,检测是否存在SD卡，如果不存在，提示，则不能进行写文件操作
				if (isChecked) {
					UserInfoManager.setRememberPwd(mContext, true);
					// if (!AndroidTools.isHasSD()) {
					// Toast.makeText(LoginActivity.this, "没有SD卡",
					// Toast.LENGTH_SHORT).show();
					// }
				} else {
					UserInfoManager.setRememberPwd(mContext, false);
				}

			}
		});

	}

	private void login() {
		// 设置点击背景
		// layoutProcess.setVisibility(View.VISIBLE);
		// 获取用户的登录信息，连接服务器，获取登录状态
		mUserName = mUserNameEt.getText().toString().trim();
		mPassWord = mPassWordEt.getText().toString().trim();

		if ("".equals(mUserName) || "".equals(mPassWord)) {
			// layoutProcess.setVisibility(View.GONE);
			Toast.makeText(LoginActivity.this,
					mContext.getString(R.string.login_emptyname_or_emptypwd),
					Toast.LENGTH_SHORT).show();
		} else {
			mUser.setUsername(mUserName);
			mUser.setPassword(mPassWord);
			mCustomProgressDialog = new CustomProgressDialog(mContext);
			mCustomProgressDialog.show();
			UserLogic.login(mContext, mLoginHandler, mUser);
			// 启动登陆线程
		}
	}

	private void Reg() {
		Intent intent = new Intent(LoginActivity.this, RegActivity.class);
		startActivity(intent);
		LoginActivity.this.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_shortcut_buy_tv: {
			UserInfoManager.setLoginIn(LoginActivity.this, false);
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		}
		case R.id.login_btn: {
			login();
			break;
		}
		case R.id.register_btn: {
			Reg();
			break;
		}

		default:
			break;
		}

	}

}
