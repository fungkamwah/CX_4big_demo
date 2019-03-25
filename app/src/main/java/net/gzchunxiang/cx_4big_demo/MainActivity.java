package net.gzchunxiang.cx_4big_demo;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MyBRReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册广播
        myReceiver = new MyBRReceiver();
        IntentFilter itFilter = new IntentFilter();
        //本示例指定接收广播为系统在网络变化时发送的广播
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, itFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity关闭时取消注册
        unregisterReceiver(myReceiver);
    }

    //启动TestService1
    public void startService(View v){
        Intent intent = new Intent(this,TestService1.class);

        startService(intent);
    }

    //停止TestService1
    public void stopService(View v){
        Intent intent = new Intent(this,TestService1.class);
        stopService(intent);
    }



    public void toBindActivity(View v){
        Intent intent = new Intent(this,BindActivity.class);
        startActivity(intent);
    }

    public void toBroadcastActivity(View v){
        Intent intent = new Intent(this,BroadcastActivity.class);
        startActivity(intent);
    }

    public void toProviderActivity(View v){
        Intent intent = new Intent(this,ProviderActivity.class);
        startActivity(intent);
    }
}
