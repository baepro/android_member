package kr.baepro.member;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText regIdText = (EditText) findViewById(R.id.regIdText);
        final EditText regPwdText = (EditText) findViewById(R.id.regPwdText);
        final EditText regNameText = (EditText) findViewById(R.id.regNameText);
        final EditText regAgeText = (EditText) findViewById(R.id.regAgeText);
        final Button enrollBtn = (Button) findViewById(R.id.enrollBtn);










        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = regIdText.getText().toString();
                final String pwd = regPwdText.getText().toString();
                final String name = regNameText.getText().toString();
                final String age = regAgeText.getText().toString();
//                final String id = "idd";
//                final String pwd = "pwdd";
//                final String name = "namee";
//                final String age = "agee";

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            boolean success = root.getBoolean("success");
                            System.out.println("success=>" + success);
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원가입 성공").setPositiveButton("확인", null).create().show();

                                //로그인 화면으로 이동
                                //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                //RegisterActivity.this.startActivity(intent);//해당 인턴트 실행
                                finish(); //회원가입 Activity가 종료됨

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원가입 실패").setNegativeButton("재시도", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //회원가입신청부분, RegisterProc에 매개변수를 넘겨주고 함수를 실행함
                RegisterProc registerProc = new RegisterProc(id, pwd, name, age, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerProc);
            }
        });


    }
}
