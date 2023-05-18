package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    EditText et_id, et_pass;
    Button btn_login, btn_toRegister, btn_toMain;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText)findViewById(R.id.et_id);
        et_pass = (EditText)findViewById(R.id.et_pass);

        // 로그인 시도
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_id = et_id.getText().toString();
                String input_pass = et_pass.getText().toString();

                // 프레퍼런스에 저장된 아이디-패스워드와 일치하는지 확인
                String pass = "";
                sharedPreferences = getSharedPreferences("privateData", MainActivity.MODE_PRIVATE);
                Set<String> dataSet = sharedPreferences.getStringSet(input_id, null);
                if (dataSet != null) pass = (String) dataSet.iterator().next();
                if (dataSet == null || !pass.equals(input_pass)) {    // 없는 아이디 or 틀린 비밀번호
                    Toast.makeText(getApplicationContext(), pass+"다시 시도하세요.", Toast.LENGTH_LONG).show();
                } else {
                    // 메인 화면으로 전환
                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("ID", input_id);
                    startActivity(intent);
                }
                et_id.setText("");
                et_pass.setText("");
            }
        });

        // 회원가입 화면으로 전환
        btn_toRegister = (Button) findViewById(R.id.btn_toRegister);
        btn_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent2);
            }
        });

        // 메인 화면으로 전환
        btn_toMain = (Button) findViewById(R.id.btn_toMain);
        btn_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                intent3.putExtra("ID", "");
                startActivity(intent3);
            }
        });
    }
}