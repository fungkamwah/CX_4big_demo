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
