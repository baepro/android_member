package kr.baepro.member;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baepro21 on 2017. 7. 25..
 */

public class LoginProc extends StringRequest {

    final static private String URL = String.format("http://192.168.10.3:8080/DBConnection?mode=LOGIN") ;
    private Map<String, String> params;
    //test
    public LoginProc(String id, String pwd, Response.Listener<String> listener) {

        super(Method.POST, URL, listener, null);
        params = new HashMap<>();//해시맵초기화
        params.put("id", id);
        params.put("pwd", pwd);
        System.out.println("params : " + params);

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
