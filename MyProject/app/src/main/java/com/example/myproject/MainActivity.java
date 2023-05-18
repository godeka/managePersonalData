package com.example.myproject;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button btn_private;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_private = (Button) findViewById(R.id.btn_private);
        btn_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);

                Intent intent = getIntent();
                String current_id = intent.getStringExtra("ID");

                // 비회원이면 - 회원가입할지 물어보고 이동
                if (current_id.equals("")) {
                    ad.setMessage("회원 가입을 하시겠습니까?");
                    ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentRg = new Intent(getApplicationContext(), RegisterActivity.class);
                            startActivity(intentRg);
                            dialogInterface.dismiss();
                        }
                    });
                    ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }

                // 회원이면 - 회원정보 보여주기
                else {
                    ad.setTitle("회원 정보");
                    SharedPreferences sp = getSharedPreferences("privateData", MODE_PRIVATE);
                    Set<String> set = sp.getStringSet(current_id, null);
                    Iterator<String> it = set.iterator();   it.next();    // 비밀번호 제외
                    while (it.hasNext()) {
                        ad.setMessage(it.next());
                    }
                }
                ad.show();     
            }
        });
    }
}