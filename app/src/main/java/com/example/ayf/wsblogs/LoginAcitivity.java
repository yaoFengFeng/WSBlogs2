package com.example.ayf.wsblogs;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginAcitivity extends AppCompatActivity {
    TextView tv,register,forget_pwd,justLook;
    ImageView eye,del;
    Boolean isOpenEye = false;
    EditText pwd_edt,user_edt;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        tv = (TextView) findViewById(R.id.app_title);
        register = (TextView)findViewById(R.id.register);
        //“温商博客”设置自定义的字体
        Typeface face = Typeface.createFromAsset(getAssets(),"WenYue-XinQingNianTi-NC-W8.ttf");
        tv.setTypeface(face);
        pwd_edt = (EditText)findViewById(R.id.pwd);
        user_edt = (EditText)findViewById(R.id.user_edt);
        login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setEnabled(false);//设置按钮不可被点击
        justLook = (TextView)findViewById(R.id.just_look);
        eye = (ImageView)findViewById(R.id.eye);
        del = (ImageView)findViewById(R.id.img_delete);
        forget_pwd = (TextView)findViewById(R.id.forget_pwd);

        //忘记密码，跳转到重置密码页面
        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAcitivity.this.finish();
                startActivity(new Intent(LoginAcitivity.this,RetrievePasswordAcitvity.class));
            }
        });

        //随便看看跳转到首页
        justLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAcitivity.this.finish();
                startActivity(new Intent(LoginAcitivity.this,MainActivity.class));
            }
        });

        //眼睛图标设置监听
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpenEye = !isOpenEye;
                if (isOpenEye){
                    eye.setImageResource(R.drawable.open);//更改眼睛图片为闭眼图片
                    pwd_edt.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//将密码输入框设置为密码可见
                }else{
                    eye.setImageResource(R.drawable.close);//更改眼睛图片为睁眼图片
                    pwd_edt.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);//将密码输入框设置为密码不可见
                }
            }
        });
        //用户输入框设置变化监听
        user_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                user_edt.setHint("手机号|昵称");//输入前显示提示信息
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                user_edt.setHint(null);//如果有输入将提示信息删掉
                //如果用户框和密码框满足都有数据 将登录按钮背景设置为深蓝 并且可被点击
                if (!(pwd_edt.getText().toString().isEmpty())){
                    login_btn.setBackgroundResource(R.drawable.darkblue_border_radius);
                    login_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                user_edt.setHint("手机号|昵称");//输入后显示提示信息（不为空不显示）
                //如果用户框和密码框不满足都有数据 将登录按钮背景设置为浅蓝 并且不可被点击
                if ((user_edt.getText().toString().isEmpty())||pwd_edt.getText().toString().isEmpty()){
                    login_btn.setBackgroundResource(R.drawable.wathet_border_radius);
                    login_btn.setEnabled(false);
                }
            }
        });
        //用户输入框获取失去焦点事件监听
        user_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    del.setVisibility(View.VISIBLE);//设置删除图片可见
                }else{
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
                //如果用户框和密码框满足都有数据 将登录按钮背景设置为深蓝 并且可以被点击
                if (!(user_edt.getText().toString().isEmpty())){
                    login_btn.setBackgroundResource(R.drawable.darkblue_border_radius);
                    login_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                pwd_edt.setHint("密码");//如果没有输入显示提示信息
                //如果用户框和密码框不满足都有数据 将登录按钮背景设置为浅蓝 并且不可被点击
                if ((user_edt.getText().toString().isEmpty())||pwd_edt.getText().toString().isEmpty()){
                    login_btn.setBackgroundResource(R.drawable.wathet_border_radius);
                    login_btn.setEnabled(false);
                }
            }
        });
        //密码输入框获取失去焦点事件监听
        pwd_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    eye.setVisibility(View.VISIBLE);//设置眼睛图片可见
                }else{
                    eye.setVisibility(View.INVISIBLE);//设置眼睛图片不可见
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
        //登录按钮设置监听(未完成)
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //点击注册 跳转注册页面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAcitivity.this,RegisterActivity.class);
                LoginAcitivity.this.finish();
                startActivity(intent);
            }
        });
    }
}
