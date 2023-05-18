package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RegisterActivity extends AppCompatActivity {

    boolean userAgree;
    TextView tv_agree;
    RadioGroup rg_signagree;

    EditText et_id, et_pass, et_name, et_phone, et_address;
    Button btn_register;

    SharedPreferences sharedPreferences;
    String shared = "privateData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 개인정보 이용약관 및 동의
        tv_agree = (TextView) findViewById(R.id.tv_agree);
        tv_agree.setMovementMethod(new ScrollingMovementMethod());
        rg_signagree = (RadioGroup) findViewById(R.id.rg_signagree);
        rg_signagree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_agree) userAgree = true;
                else if (i == R.id.rb_disagree) userAgree = false;
            }
        });

        // 아이디 패스워드 및 개인정보 입력
        et_id = (EditText) findViewById(R.id.et_id);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (EditText) findViewById(R.id.et_address);

        // 회원가입 버튼 클릭
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editText 에 현재 입력되어있는 값을 가져오기
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                String userPhone = et_phone.getText().toString();
                String userAddress = et_address.getText().toString();

                sharedPreferences = getSharedPreferences(shared, MainActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (!userAgree) {
                    Toast.makeText(getApplicationContext(), "개인정보 이용약관에 동의해주세요.", Toast.LENGTH_LONG).show();
                } else if (userID.equals("") || userPass.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 모두 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (userName.equals("") || userPhone.equals("") || userAddress.equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (sharedPreferences.contains(userID)) {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_LONG).show();
                } else if (userPass.length() < 8) {
                    Toast.makeText(getApplicationContext(), "비밀번호는 최소 8자이어야 합니다.", Toast.LENGTH_LONG).show();
                } else if (!userPass.matches("^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,24}$")) {
                    Toast.makeText(getApplicationContext(), "비밀번호는 숫자, 문자, 특수문자의 조합으로 이루어져야 합니다.", Toast.LENGTH_LONG).show();
                } else {            // 모든 조건 통과 - 회원가입 성공!
                    HashSet<String> dataSet = new LinkedHashSet<String>();
                    dataSet.add(userPass);
                    dataSet.add(userName);
                    dataSet.add(userPhone);
                    dataSet.add(userAddress);
                    // 아이디 패스워드 및 개인정보 저장
                    editor.putStringSet(userID, dataSet);
                    editor.apply();
                    finish();       // 로그인 화면으로
                }
                // 회원가입 실패 시 - 모든 텍스트 입력 초기화
                et_id.setText("");
                et_pass.setText("");
                et_name.setText("");
                et_phone.setText("");
                et_address.setText("");
            }
        });

    }
}