package com.xuehai.web.entity;

/**
 * Created by 39450 on 2018/10/1.
 */
public class UserEntity {

    private Integer id;
    private String userName;
    private int userAge;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }

    public UserEntity(Integer id, String userName, int userAge) {
        this.id = id;
        this.userName = userName;
        this.userAge = userAge;
    }

    public UserEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
