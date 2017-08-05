package kr.baepro.member;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MngActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter userListAdapter;
    private List<User> userList;
    private List<User> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng);
//        TextView userListTextView = (TextView) findViewById(R.id.userListTextView);
//        Intent intent = getIntent();//intent를 받아서 userListViewText에 출력함
//        userListTextView.setText(intent.getStringExtra("userList"));
        Intent intent = getIntent();

        //listView와 list를 초기화해줌
        listView = (ListView) findViewById(R.id.userListTextView);
        userList = new ArrayList<User>();
        saveList = new ArrayList<User>();

        //data test line
//        userList.add(new User("bae", "1111", "junseok", "43"));
//        userList.add(new User("seo", "2222", "yunkyong", "42"));
//        userList.add(new User("jinwoo", "3333", "baejinwoo", "10"));
//        userList.add(new User("seonghun", "4444", "baeseonghun", "5"));

        //adapter를 생성하고 listView에 adapter를 셋팅함
        userListAdapter = new UserListAdapter(getApplicationContext(), userList, this, saveList);//this는 자신(MngActivity)를 포함시켜줌
        listView.setAdapter(userListAdapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("rows");
            int count = 0;
            String userId, userPwd, userName, userAge;
            while(count < jsonArray.length()) {
                JSONObject arryObject = jsonArray.getJSONObject(count);
                userId = arryObject.getString("id");
                userPwd = arryObject.getString("pwd");
                userName = arryObject.getString("name");
                userAge = arryObject.getString("age");

                //해당 변수들로 User객체를 생성
                User user = new User(userId, userPwd, userName, userAge);
                if(!userId.equals("admin")) {//userId가 admin이 아닌 경우에만 리스트에 추가
                    userList.add(user);//userList에 추가
                    saveList.add(user);
                }

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EditText searchText = (EditText) findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchUser(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void searchUser(String searchText) {
        userList.clear();//userList안의 모든 내용을 지워줌
        for(int i = 0; i < saveList.size(); i++) {
            if(saveList.get(i).getUserId().contains(searchText)) {//해당 userId가 searchText라는 문구를 포함할때에 한해서..
                userList.add(saveList.get(i));//userList에 saveList를 추가시켜줌
            }
        }
        userListAdapter.notifyDataSetChanged();//adapter에서 값이 변경되었다는 것을 알려줌
    }
}
