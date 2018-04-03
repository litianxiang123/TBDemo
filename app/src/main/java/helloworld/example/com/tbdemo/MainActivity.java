package helloworld.example.com.tbdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private TextView mBtn;
    private LinearLayout mLinear;

    private boolean islogin;


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
        mBtn = (TextView) findViewById(R.id.btn);
        mBtn.setText("点击跳转");
        mBtn.setTag(false);

//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean flag = (boolean) mBtn.getTag();//当点击时，首先判断是否已经点击过
//                if (!flag) {
//                    mBtn.setText("返回");
//                    mBtn.setTag(true);
//                    log();
//                } else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                    v = inflater.inflate(R.layout.update_manage_dialog, null);
//                    TextView content = (TextView) v.findViewById(R.id.dialog_content);
//                    Button btn_sure = (Button) v.findViewById(R.id.dialog_btn_sure);
//                    Button btn_cancel = (Button) v.findViewById(R.id.dialog_btn_cancel);
//                    //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
//                    final Dialog dialog = builder.create();
//                    dialog.show();
//                    dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
//                    //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
//                    btn_sure.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                            mBtn.setText("点击跳转");
//                            mBtn.setTag(false);
//                            logout();
//                            //Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View arg0) {
//                            dialog.dismiss();
//                            //Toast.makeText(MainActivity.this, "no", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//        });
        mBtn.setOnClickListener(this);
        mLinear = (LinearLayout) findViewById(R.id.linear);



        mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (islogin == true){

                }

                boolean flag = (boolean) mBtn.getTag();

                if (!flag){
                    mBtn.setText("解除授权");
                    mBtn.setTag(true);
                    log();
                }else {
                    mBtn.setText("点击跳转");
                    mBtn.setTag(false);
                    logout();
                }

            }
        });
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                break;
        }
    }
}
