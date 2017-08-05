package kr.baepro.member;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by baepro21 on 2017. 8. 2..
 */

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private Activity parentActivity;
    private List<User> saveList;

    //Constructor
    public UserListAdapter(Context context, List<User> userList, Activity parentActivity, List<User> saveList) {//parentActivity는 자신을 불러온 부모의 Activity임
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity; //parentActivity 매칭
        this.saveList = saveList;//변수 saveList는 매개변수 saveList와 같다고 걸어줌
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {//userList의 특정 사용자를 반환
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {//사용자 상세정보
        View v = View.inflate(context, R.layout.user, null);
        final TextView userIdTextView = (TextView) v.findViewById(R.id.userIdTextView);
        TextView userPwdTextView = (TextView) v.findViewById(R.id.userPwdTextView);
        TextView userNameTextView = (TextView) v.findViewById(R.id.userNameTextView);
        TextView userAgeTextView = (TextView) v.findViewById(R.id.userAgeTextView);
        Button delBtn = (Button) v.findViewById(R.id.delBtn);

        userIdTextView.setText(userList.get(i).getUserId());
        userPwdTextView.setText(userList.get(i).getUserPwd());
        userNameTextView.setText(userList.get(i).getUserName());
        userAgeTextView.setText(userList.get(i).getUserAge());

        //특정 태그의 ID값을 반환할 수 있도록 함
        v.setTag(userList.get(i).getUserId());

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = userIdTextView.getText().toString();
                //버튼 클릭시 Response Listener(결과에 대한 값을 받아줄 수 있는)를 만들어 줄 수 있도록 함
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {//특정 사이트로 response가 전달이 되었을 때..
                        try {
                            JSONObject root = new JSONObject(response);
                            boolean success = root.getBoolean("success");
                            System.out.println("success=>" + success);
                            if(success) {
                                userList.remove(i); //해당 아이디 삭제
                                for (int i = 0; i < saveList.size(); i++) {
                                    //현재 회원정보가 userId와 동일한 id를 가지고 있을 경우 해당 회원을 삭제해줌
                                    if(saveList.get(i).getUserId().equals(userIdTextView.getText().toString())) {
                                        saveList.remove(i);
                                        break;
                                    }
                                }
                                notifyDataSetChanged(); //데이터가 변경되었음을 adapter에게 알려줌
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                DeleteProc deleteProc = new DeleteProc(userIdTextView.getText().toString(), responseListener);

                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteProc);
            }
        });

        return v;
    }
}
