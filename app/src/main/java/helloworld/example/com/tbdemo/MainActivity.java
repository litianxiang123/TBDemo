package helloworld.example.com.tbdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 点击跳转
     */
    private Button mBtn;
    /**
     * 退出
     */
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //集成阿里百川
        initBaiChuan();

    }

    private void initBaiChuan() {
        //阿里初始化

        AlibcTradeSDK.asyncInit(getApplication(), new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                Toast.makeText(MainActivity.this, "阿里百川初始化成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Toast.makeText(MainActivity.this, "阿里百川初始化" + code + "       " + msg, Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                log();
                break;
            case R.id.btn2:
                logout();
                break;
        }
    }

    private void log() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(MainActivity.this, new AlibcLoginCallback() {

            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "淘宝授权登录成功 ",
                        Toast.LENGTH_LONG).show();


                //获取淘宝用户信息
                Gson gson = new Gson();
                String s = gson.toJson(AlibcLogin.getInstance().getSession());

                String nick = AlibcLogin.getInstance().getSession().nick;
                String avatarUrl = AlibcLogin.getInstance().getSession().avatarUrl;
                System.out.println("albb" + avatarUrl);

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(MainActivity.this, "请您进行淘宝授权后再进行操作" + "淘宝授权登录失败信息=" + msg,
                        Toast.LENGTH_LONG).show();

            }
        });
    }

    private void logout() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(MainActivity.this, new LogoutCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "退出登录成功",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(MainActivity.this, "退出登录失败 " + code + msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
