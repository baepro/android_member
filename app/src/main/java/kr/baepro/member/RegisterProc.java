package kr.baepro.member;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baepro21 on 2017. 7. 25..
 */

public class RegisterProc extends StringRequest {

    final static private String URL = "http://192.168.10.3:8080/DBConnection?mode=REGISTER";
    private Map<String, String> params;

    public RegisterProc(String id, String pwd, String name, String age, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();//해시맵초기화
        params.put("id", id);
        params.put("pwd", pwd);
        params.put("name", name);
        params.put("age", age);
        //System.out.println("id="+id+", pwd="+pwd+", name="+name+", age="+age);
        System.out.println("params=" + params);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
