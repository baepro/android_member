package kr.baepro.member;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baepro21 on 2017. 8. 2..
 */

public class DeleteProc extends StringRequest {

    final static private String URL = "http://192.168.10.3:8080/DBConnection?mode=DELETE";
    private Map<String, String> params;



    //Constructor, id와 response를 받아서 처리함
    public DeleteProc(String id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
