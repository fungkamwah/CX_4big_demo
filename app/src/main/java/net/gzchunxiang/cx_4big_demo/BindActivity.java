package net.gzchunxiang.cx_4big_demo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class BindActivity extends AppCompatActivity {

    Intent serviceIntent;

    //保持所启动的Service的IBinder对象,同时定义一个ServiceConnection对象
    TestService2.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {

        //Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("------Service DisConnected-------");
        }

        //Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("------Service Connected-------");
            binder = (TestService2.MyBinder) service;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bind);
        serviceIntent = new Intent(this,TestService2.class);
//        serviceIntent.setAction("net.gzchunxiang.cx_4big_demo.TEST_SERVICE2");
    }

    public void bind(View view) {
        //绑定service
        bindService(serviceIntent, conn, Service.BIND_AUTO_CREATE);
    }

    public void unBind(View view) {
        //解除service绑定
        unbindService(conn);
    }

    public void status(View view) {
        Toast.makeText(getApplicationContext(), "Service的count的值为:"
                + binder.getCount(), Toast.LENGTH_SHORT).show();
    }
}
