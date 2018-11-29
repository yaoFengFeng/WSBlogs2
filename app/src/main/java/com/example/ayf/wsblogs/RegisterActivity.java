package com.example.ayf.wsblogs;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayf.wsblogs.unify.Unify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by AYF on 2018/11/25.
 */
public class RegisterActivity extends AppCompatActivity {
    TextView tv, to_login,justLook;
    ImageView del, del2, del3;
    EditText pwd_edt, user_edt, phone_edt, code_edt;
    Button register_btn, code_btn;
    public EventHandler eh;//事件接收器
    private TimeCount mTimeCount;//计时器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        tv = (TextView) findViewById(R.id.app_title);
        to_login = (TextView) findViewById(R.id.to_login);
        //“温商博客”设置自定义的字体
        Typeface face = Typeface.createFromAsset(getAssets(), "WenYue-XinQingNianTi-NC-W8.ttf");
        tv.setTypeface(face);
        pwd_edt = (EditText) findViewById(R.id.pwd_register);
        user_edt = (EditText) findViewById(R.id.user_edt_register);
        phone_edt = (EditText) findViewById(R.id.phone_edt);
        code_edt = (EditText) findViewById(R.id.code_edt);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setEnabled(false);//设置注册按钮不可被点击
        code_btn = (Button) findViewById(R.id.code_btn);
        code_btn.setEnabled(false);//设置获取验证码按钮不可被点击
        del = (ImageView) findViewById(R.id.img_delete1);
        del2 = (ImageView) findViewById(R.id.img_delete2);
        del3 = (ImageView) findViewById(R.id.img_delete3);
        justLook = (TextView)findViewById(R.id.just_look2);
        mTimeCount = new TimeCount(60000, 1000);
        init();

        //点击获取验证码监听
        code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel(phone_edt.getText().toString().trim())) {
                    SMSSDK.getVerificationCode("+86", phone_edt.getText().toString().trim());//发送验证码
                    Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    mTimeCount.start();
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //随便看看跳转到首页
        justLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });


        //用户输入框设置变化监听
        user_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                user_edt.setHint("你的昵称");//输入前显示提示信息
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                user_edt.setHint(null);//如果有输入将提示信息删掉
                //如果用户框和密码框手机号码框满足都有数据 将注册按钮背景设置为深蓝 并且可以被点击
                if (!(pwd_edt.getText().toString().isEmpty() || phone_edt.getText().toString().isEmpty())) {
                    register_btn.setBackgroundResource(R.drawable.darkblue_border_radius);
                    register_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                user_edt.setHint("你的昵称");//输入后显示提示信息（不为空不显示）
                //如果用户框和密码框不满足都有数据 将注册按钮背景设置为浅蓝 并且不可被点击
                if ((user_edt.getText().toString().trim().isEmpty()) || pwd_edt.getText().toString().trim().isEmpty() || phone_edt.getText().toString().trim().isEmpty()) {
                    register_btn.setBackgroundResource(R.drawable.wathet_border_radius);
                    register_btn.setEnabled(false);
                }
            }
        });
        //用户输入框获取失去焦点事件监听
        user_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    del.setVisibility(View.VISIBLE);//设置删除图片可见
                } else {
                    del.setVisibility(View.INVISIBLE);//设置删除图片不可见
                }
            }
        });

        //密码输入框设置同上功能的监听
        pwd_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pwd_edt.setHint("密码");//如果没有输入显示提示信息
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwd_edt.setHint(null);//如果有输入将提示信息删掉
                //如果用户框和密码框手机号码框满足都有数据 将注册按钮背景设置为深蓝 并且可以被点击
                if (!(user_edt.getText().toString().isEmpty() || phone_edt.getText().toString().isEmpty())) {
                    register_btn.setBackgroundResource(R.drawable.darkblue_border_radius);
                    register_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                pwd_edt.setHint("密码");//如果没有输入显示提示信息
                //如果用户框和密码框不满足都有数据 将注册按钮背景设置为浅蓝 并且不可被点击
                if ((user_edt.getText().toString().trim().isEmpty()) || pwd_edt.getText().toString().trim().isEmpty() || phone_edt.getText().toString().trim().isEmpty()) {
                    register_btn.setBackgroundResource(R.drawable.wathet_border_radius);
                    register_btn.setEnabled(false);
                }
            }
        });

        //密码输入框获取失去焦点事件监听
        pwd_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    del3.setVisibility(View.VISIBLE);
                } else {
                    del3.setVisibility(View.INVISIBLE);
                }
            }
        });

        phone_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                phone_edt.setHint("手机号码");//如果没有输入显示提示信息
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone_edt.setHint(null);//如果有输入将提示信息删掉
                //如果用户框和密码框手机号码框满足都有数据 将注册按钮背景设置为深蓝 并且可以被点击
                if ((user_edt.getText().toString().trim().isEmpty()) || pwd_edt.getText().toString().trim().isEmpty() || phone_edt.getText().toString().trim().isEmpty()) {
                    register_btn.setBackgroundResource(R.drawable.darkblue_border_radius);
                    register_btn.setEnabled(true);
                }
                code_btn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone_edt.setHint("手机号码");//如果没有输入显示提示信息
                //如果用户框和密码框不满足都有数据 将注册按钮背景设置为浅蓝 并且不可被点击
                if ((user_edt.getText().toString().isEmpty()) || pwd_edt.getText().toString().isEmpty() || phone_edt.getText().toString().isEmpty()) {
                    register_btn.setBackgroundResource(R.drawable.wathet_border_radius);
                    register_btn.setEnabled(false);
                }
            }
        });

        //手机号码输入框获取失去焦点事件监听
        phone_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    del2.setVisibility(View.VISIBLE);
                } else {
                    del2.setVisibility(View.INVISIBLE);
                }
            }
        });
        //删除图片设置监听
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_edt.setText("");//删除输入框中的内容
            }
        });
        //删除图片设置监听
        del2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_edt.setText("");//删除输入框中的内容
            }
        });
        //删除图片设置监听
        del3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd_edt.setText("");//删除输入框中的内容
            }
        });

        //注册按钮设置监听(未完成)
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.submitVerificationCode("+86",phone_edt.getText().toString().trim(),code_edt.getText().toString().trim());//提交验证
            }
        });

        //点击登录 跳转登录页面
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginAcitivity.class);
                RegisterActivity.this.finish();
                startActivity(intent);
            }
        });
    }

    //初始化事件接收器
    private void init() {
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                if (result == SMSSDK.RESULT_COMPLETE) {//回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
                        RegisterActivity.this.finish();
                        startActivity(new Intent(RegisterActivity.this, LoginAcitivity.class)); //页面跳转
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码成功

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) { //返回支持发送验证码的国家列表

                    }
                }else {
                    ((Throwable) data).printStackTrace();
                    Looper.prepare();
                    Toast.makeText(RegisterActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    mTimeCount.onFinish();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    //计时器
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            code_btn.setClickable(false);
            code_btn.setText(l / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            code_btn.setClickable(true);
            code_btn.setText("发送验证码");//计时结束后重新设定按钮值
        }
    }

    //正则匹配手机号码
    public boolean checkTel(String tel) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    @Override
    public void finish() {
        super.finish();
        SMSSDK.unregisterEventHandler(eh);
    }
}
