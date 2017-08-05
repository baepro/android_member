package kr.baepro.member;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class  MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        TextView idTextView = (TextView) findViewById(R.id.idTextView);
        TextView pwdTextView = (TextView) findViewById(R.id.pwdTextView);
        Button mngBtn = (Button) findViewById(R.id.mngBtn);

        Intent intent = getIntent();//Intent로 넘어온 값을 저장
        String id = intent.getStringExtra("id");
        String pwd = intent.getStringExtra("pwd");
        String msg = "환영합니다 " + id + "님";

        welcomeMsg.setText(msg);
        idTextView.setText(id);
        pwdTextView.setText(pwd);

        if(!id.equals("admin")) {
            //mngBtn.setEnabled(false);
            mngBtn.setVisibility(View.GONE);
        }

        mngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
    }

    //내부클래스 생성
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        //초기화
        @Override
        protected void onPreExecute() {
            target = "http://192.168.10.3:8080/DBConnection?mode=LIST";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(target);//해당 URL에 접속할 수 있도록 URL객체 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//urlConnection생성
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();//각각의 결과값들은 매 열마다 temp에 담겨서 StringBuilder안에 넣어지게 됨
                while ((temp = bufferedReader.readLine())!=null){//모든 라인을 읽음
                    stringBuilder.append(temp + "/n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim(); //해당 문자의 집합을 반환해 줌
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;//오류발생시 null값 반환
        }
        //상속만 받아주고 사용하지는 않음
        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //파싱이 끝난뒤 다음 Activity로 넘아가게 해줌
        @Override
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, MngActivity.class);
            intent.putExtra("userList", result);//파싱한 결과를 그대로 넘겨줌
            MainActivity.this.startActivity(intent);//다음 intent실행
        }
    }
}
