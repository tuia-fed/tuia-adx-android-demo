package com.dt.adx.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import com.dt.adx.R;
import com.mediamain.android.FoxSDK;

/**
 * 测试工具类
 */
public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getSimpleName();

    private EditText editUrl;
    private String url = "com.icbc.androidclient://startType=PORTALINJECT&menuId=224434&injectParams=Y2hhbnR5cGU9aHd0cmF2ZWw=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editUrl = (EditText) findViewById(R.id.editUrl);
        Button btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener(v -> {
            try {
                if (!TextUtils.isEmpty(editUrl.getText().toString().trim())){
                    url = editUrl.getText().toString().trim();
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PackageManager packageManager = FoxSDK.getContext().getPackageManager();
                ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                FoxSDK.getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}