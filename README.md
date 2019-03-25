# CX_4big_demo
Android上手练习5.四大组件（二）

安卓的四大组件是Activity（活动）、Service（服务）、BroadcastReceiver（广播接收器）、ContentProvider（内容提供者）。

## 1 Activity
之前已多次使用Activity。

创建新项目。

layout_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:orientation="vertical"
    android:layout_height="match_parent"
    android:gravity="center_horizontal"
    tools:context=".MainActivity">


    <LinearLayout
        android:layout_margin="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="startService"
            android:text="start"/>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="stopService"
            android:text="stop"/>

    </LinearLayout>

    <Button
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="toBindActivity"
        android:text="to_bind_activity"/>
    <Button
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="toBroadcastActivity"
        android:text="to_broadcast_activity"/>
    <Button
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="toProviderActivity"
        android:text="to_provider_activity"/>

</LinearLayout>
```

MainActivity.java
```
package net.gzchunxiang.cx_4big_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    //启动TestService1
    public void startService(View v){

    }

    //停止TestService1
    public void stopService(View v){

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

```


AndroidManifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="net.gzchunxiang.cx_4big_demo">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".BindActivity"/>
        <activity android:name=".BroadcastActivity"/>
        <activity android:name=".ProviderActivity"/>
    </application>

</manifest>
```



## 2 Service
Service是Android提供一个允许长时间留驻后台的一个组件，最常见的用法就是做轮询操作！或者想在后台做一些事情，比如后台下载更新！

### 2.1 startService

创建TestService1.java
```
package net.gzchunxiang.cx_4big_demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService1 extends Service {
    private final String TAG = "TestService1";
    //必须要实现的方法  
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind方法被调用!");
        return null;
    }

    //Service被创建时调用  
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate方法被调用!");
        super.onCreate();
    }

    //Service被启动时调用  
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand方法被调用!");
        return super.onStartCommand(intent, flags, startId);
        
    }

    //Service被关闭之前回调  
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestory方法被调用!");
        super.onDestroy();
    }
}
```

修改MainActivity.java中的方法
```
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
```

AndroidManifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="net.gzchunxiang.cx_4big_demo">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".BindActivity"/>
        <activity android:name=".BroadcastActivity"/>
        <activity android:name=".ProviderActivity"/>


        <!-- 配置Service组件 -->
        <service android:name=".TestService1"/>
        <service android:name=".TestService2"/>

    </application>

</manifest>
```

运行后，点击START，连续多次点START，查看logcat。

![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190322-104631.png)
![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190322-104736.png)

点击STOP

![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190322-105234.png)

### 2.3 BindService

创建TestService2.java
```
package net.gzchunxiang.cx_4big_demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestService2 extends Service {
    private final String TAG = "TestService2";
    private int count;
    private boolean quit;

    //定义onBinder方法所返回的对象
    private MyBinder binder = new MyBinder();
    public class MyBinder extends Binder
    {
        public int getCount()
        {
            return count;
        }
    }

    //必须实现的方法,绑定改Service时回调该方法  
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind方法被调用!");
        return binder;
    }

    //Service被创建时回调
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate方法被调用!");
        //创建一个线程动态地修改count的值
        new Thread()
        {
            public void run()
            {
                while(!quit)
                {
                    try
                    {
                        Thread.sleep(1000);
                    }catch(InterruptedException e){e.printStackTrace();}
                    count++;
                }
            };
        }.start();

    }

    //Service断开连接时回调
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind方法被调用!");
        return true;
    }

    //Service被关闭前回调
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        Log.i(TAG, "onDestroyed方法被调用!");
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind方法被调用!");
        super.onRebind(intent);
    }
}
```
BindActivity.java
```
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
        serviceIntent = new Intent(this, TestService2.class);
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

```

layout_bind.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".BindActivity">

    <Button
        android:onClick="bind"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="bind"/>
    <Button
        android:onClick="unBind"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="unbind"/>
    <Button
        android:onClick="status"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="status"/>

</LinearLayout>
```


AndroidManifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="net.gzchunxiang.cx_4big_demo">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".BindActivity"/>
        <activity android:name=".BroadcastActivity"/>
        <activity android:name=".ProviderActivity"/>


        <!-- 配置Service组件 -->
        <service android:name=".TestService1"/>
        <service android:name=".TestService2"/>

    </application>

</manifest>
```


## 3 BroadcastReceiver

### 3.1 动态注册广播接收

创建MyBRRecever.java
```
package net.gzchunxiang.cx_4big_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBRReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"网络状态发生改变~",Toast.LENGTH_SHORT).show();
    }
}

```

然后在MainActivity.java中
```
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

```

启动并切换网络，应用会接收到该广播并Toast提示。

### 3.2 静态注册广播
例如应用没被启动的时候，就不能动态注册了。可以静态注册一个广播，例如系统关于开机完成的广播。


BootCompleteReceiver.java
```
package net.gzchunxiang.cx_4big_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT.equals(intent.getAction()))
            Toast.makeText(context, "开机完毕~", Toast.LENGTH_LONG).show();
    }
}

```

AndroidManifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="net.gzchunxiang.cx_4big_demo">


    <!-- 接收开机广播的权限 -->
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>


    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".BindActivity"/>
        <activity android:name=".BroadcastActivity"/>
        <activity android:name=".ProviderActivity"/>


        <!-- 配置Service组件 -->
        <service android:name=".TestService1"/>
        <service android:name=".TestService2"/>

        <receiver android:name=".BootCompleteReceiver">
            <intent-filter>
                <action android:name = "android.intent.cation.BOOT_COMPLETED"/>
            </intent-filter>
        </receiver>

        

    </application>

</manifest>
```

### 3.3 发送广播
除了接收系统的各种广播，可以发送自定的广播。

MyBroadcastReceiver.java
```
package net.gzchunxiang.cx_4big_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "com.example.broadcasttest.MY_BROADCAST";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_BOOT.equals(intent.getAction()))
            Toast.makeText(context, "广播已收到啦！~",Toast.LENGTH_SHORT).show();
    }
}
```

BroadcastActivity.java
```
package net.gzchunxiang.cx_4big_demo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BroadcastActivity extends AppCompatActivity {
    MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_broadcast);

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.MY_BROADCAST");
        registerReceiver(myBroadcastReceiver,intentFilter);
    }

    public void sendBroadcast(View view) {
        sendBroadcast(new Intent("com.example.broadcasttest.MY_BROADCAST"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}

```

## 4 ContentProvider
内容提供者，其实是数据库中的数据暴露出来供其他应用使用，参考：
[http://www.runoob.com/w3cnote/android-tutorial-contentprovider.html](http://www.runoob.com/w3cnote/android-tutorial-contentprovider.html)

一般都是读取系统应用提供的数据，如联系人等内容。


ProviderActivity.java
```
package net.gzchunxiang.cx_4big_demo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_provider);

    }

    public void getMsgs(View view) {

        if (Build.VERSION.SDK_INT >= 23) {
            //安卓6.0以后对一些敏感权限获取，需获得用户同意
            int checkPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 666);
                return;
            }
        }
        doGetMsgs();
    }

    public void getContacts(View view) {

        if (Build.VERSION.SDK_INT >= 23) {
            //安卓6.0以后对一些敏感权限获取，需获得用户同意
            int checkPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 777);
                return;
            }
        }
        doGetContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 666:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doGetMsgs();
                } else {
                    Toast.makeText(this, "没有读取短信权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 777:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doGetContacts();
                } else {
                    Toast.makeText(this, "没有读取联系人权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void doGetMsgs() {
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        //获取的是哪些列的信息
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        System.out.println("短信列表：" );
        while (cursor.moveToNext()) {
            String address = cursor.getString(0);
            String date = cursor.getString(1);
            String type = cursor.getString(2);
            String body = cursor.getString(3);
            System.out.println("地址:" + address);
            System.out.println("时间:" + date);
            System.out.println("类型:" + type);
            System.out.println("内容:" + body);
            System.out.println("======================");
        }
        cursor.close();
    }


    private void doGetContacts() {
        //查询raw_contacts表获得联系人的id
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //查询联系人数据
        Cursor cursor = resolver.query(uri, null, null, null, null);
        System.out.println("联系人列表：");
        while (cursor.moveToNext()) {
            //获取联系人姓名,手机号码
            String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println("姓名:" + cName);
            System.out.println("号码:" + cNum);
            System.out.println("======================");
        }
        cursor.close();
    }
}

```

layout_provider.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    tools:context=".ProviderActivity">

    <Button
        android:onClick="getMsgs"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:text="读取短信"/>

    <Button
        android:onClick="getContacts"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:text="读取联系人"/>

</LinearLayout>
```

AndroidManifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="net.gzchunxiang.cx_4big_demo">


    <!-- 接收开机广播的权限 -->
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>

    <!--读取短信权限-->
    <uses-permission android:name="android.permission.READ_SMS"/>

    <!--读取联系人信息权限-->
    <uses-permission android:name="android.permission.READ_CONTACTS"/>


    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".BindActivity"/>
        <activity android:name=".BroadcastActivity"/>
        <activity android:name=".ProviderActivity"/>


        <!-- 配置Service组件 -->
        <service android:name=".TestService1"/>
        <service android:name=".TestService2"/>

        <receiver android:name=".BootCompleteReceiver">
            <intent-filter>
                <action android:name = "android.intent.cation.BOOT_COMPLETED"/>
            </intent-filter>
        </receiver>


        <!--<receiver android:name=".MyBroadcastReceiver">-->
            <!--<intent-filter>-->
                <!--<action android:name="com.example.broadcasttest.MY_BROADCAST"/>-->
            <!--</intent-filter>-->
        <!--</receiver>-->


    </application>

</manifest>
```
