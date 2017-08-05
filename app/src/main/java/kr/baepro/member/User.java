package kr.baepro.member;

/**
 * Created by baepro21 on 2017. 8. 2..
 */

public class User {
    String userId;
    String userPwd;
    String userName;
    String userAge;

    public User(String userId, String userPwd, String userName, String userAge) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
}
