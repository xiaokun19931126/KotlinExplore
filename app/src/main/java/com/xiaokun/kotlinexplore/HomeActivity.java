package com.xiaokun.kotlinexplore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.xiaokun.kotlinexplore.contact.ContactsActivity;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/12/18
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public Button mContact;
    public Button mCoroutine;

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        mContact = findViewById(R.id.contact);
        mCoroutine = findViewById(R.id.coroutine);
        mContact.setOnClickListener(this);
        mCoroutine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact:
                startActivity(new Intent(HomeActivity.this, ContactsActivity.class));
                break;
            case R.id.coroutine:

                break;
            default:
                break;
        }
    }
}
