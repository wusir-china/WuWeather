package com.wusir.wuweather;

import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;
import com.wusir.StatusBarCompat;
import com.wusir.customView.CheckView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class CheckCodeActivity extends AppCompatActivity implements View.OnClickListener{
    private CheckView mCheckView;
    private EditText mEditPass;
    private Button mSubmit;
    private Button mRef,btn_bottomsheet,showDialog;
    private BottomSheetBehavior behavior;
    private BottomSheetDialog dialog;
    // 验证码：
    private String mCheckCode = null;
    private Handler mHandler=new Handler();
    private Runnable mState=new Runnable() {
        @Override
        public void run() {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);
        StatusBarCompat.compat(this,R.color.colorGreen);
        // 初始化控件
        mCheckView = (CheckView) findViewById(R.id.checkView);
        mEditPass = (EditText) findViewById(R.id.checkTest);
        //设置edittext为密码输入模式
        mEditPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mSubmit = (Button) findViewById(R.id.submit);
        mRef = (Button) findViewById(R.id.ref);
        mSubmit.setOnClickListener(this);
        mRef.setOnClickListener(this);
        // 生成验证码
        mCheckView.invaliChenkCode();
         // Android 2.2及以下用HttpClient，Android 2.3及以上用HttpURLConnection
//        try {
//            URL url = new URL("http://localhost:8080/xxx.do");
//            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("post");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //一、
//        CookieSyncManager.createInstance(this);
//        CookieManager cookieManager=CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();
//        cookieManager.setCookie("url/host","cookies");
//        CookieSyncManager.getInstance().sync();
        //二、
//        CookieStore cookieStore= OkGo.getInstance().getCookieJar().getCookieStore();
//        //1.查看okgo管理的cookie中，某个url所对应的全部cookie
//        HttpUrl httpUrl=HttpUrl.parse("http://server.jeasonlzy.com/OkHttpUtils/method/");
//        List<Cookie> cookies=cookieStore.getCookie(httpUrl);
//        //2.查看okgo管理的所有cookie
//        List<Cookie> allCookie=cookieStore.getAllCookie();
//        //3.手动向okgo管理的cookie中，添加一些自己的cookie
//        Cookie.Builder builder1=new Cookie.Builder();
//        Cookie cookie=builder1.name("mykey").value("myvalue").domain(httpUrl.host()).build();
//        cookieStore.saveCookie(httpUrl,cookie);
//        //4.手动把okgo管理的某个url对应的全部cookie移除
//        cookieStore.removeCookie(httpUrl);
//        //5.手动把okgo管理的全部cookie移除
//        cookieStore.removeAllCookie();
        btn_bottomsheet = (Button) findViewById(R.id.btn_bottomsheet);
        btn_bottomsheet.setOnClickListener(this);
        View ll_bottom = (View) findViewById(R.id.ll_bottom);
        behavior=BottomSheetBehavior.from(ll_bottom);
        mHandler.postDelayed(mState,50);
        showDialog= (Button) findViewById(R.id.showDialog);
        showDialog.setOnClickListener(this);
    }
    private void showDialog(){
        final BottomSheetDialog dialog=new BottomSheetDialog(this);
        View dialogView= LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,null);
        ListView listView= (ListView) dialogView.findViewById(R.id.listview);
        String[] array=new String[]{"item-1","item-2","item-3","item-4","item-5","item-6","item-7"};
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        listView.setAdapter(adapter);
        dialog.setContentView(dialogView);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (mEditPass.getText().toString().equals(mCheckView.getCheckCode())) {
                    Toast.makeText(this, "通过", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "未通过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ref:
                // 生成新的验证码
                mCheckView.invaliChenkCode();
                break;
            case R.id.btn_bottomsheet:
                if(behavior.getState()==BottomSheetBehavior.STATE_HIDDEN){
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    btn_bottomsheet.setText("隐藏底部弹窗");
                }else{
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    btn_bottomsheet.setText("显示底部弹窗");
                }
                break;
            case R.id.showDialog:
                showDialog();
                break;
            default:
                break;
        }
    }
}
