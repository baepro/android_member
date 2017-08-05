package kr.baepro.member;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwdText = (EditText) findViewById(R.id.pwdText);
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        final TextView registerTextBtn = (TextView) findViewById(R.id.registerTextBtn);

        registerTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); //로그인화면에서 회원가입화면으로 이동
                LoginActivity.this.startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = idText.getText().toString();
                final String pwd = pwdText.getText().toString();

                //Response Listener
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        //System.out.println("response==" + response);
                        try {
                            JSONObject root = new JSONObject(response); //JSONObject를 생성하여 response를 저장
                            boolean success = root.getBoolean("success");
                            //System.out.println("success=>" + success);
                            if(success) {
                                JSONArray rows = root.getJSONArray("rows");
                                //System.out.println("rows=>" + rows);
                                //System.out.println("rows.length()=>" + rows.length());
                                for(int i=0; i<rows.length(); i++) {
                                    JSONObject obj = rows.getJSONObject(i);
                                    //System.out.println("obj=>" + obj);
                                    String id = obj.getString("id");
                                    String pwd = obj.getString("pwd");
                                    System.out.println("id=>" + id + ", pwd=>" + pwd);
                                }
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("pwd", pwd);
                                LoginActivity.this.startActivity(intent); //LoginActivity에서 새로운 Activity로 넘어갈 수 있도록 전환 해줌.

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); //LoginActivity에 띄워주는 Diallog
                                builder.setMessage("Login failed!").setNegativeButton("retry", null).create().show();
                            }
                        } catch (JSONException e) {
                            System.out.println("JSONException================");
                            e.printStackTrace();
                        }
                    }
                };
                LoginProc loginProc = new LoginProc(id, pwd, responseListener); //LoginProc를 통하여 매개변수 id, pwd, response를 받아옴
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this); //인터넷에 접속하여 request를 보내고 response를 받아옴
                queue.add(loginProc);
            }
        });
    }
}
