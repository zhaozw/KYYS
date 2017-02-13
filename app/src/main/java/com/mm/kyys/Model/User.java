package com.mm.kyys.Model;

/**
 * Created by 27740 on 2017/1/24.
 */

public class User {

    private String id;
    private String name;
    private String photo;
    private int identity;//身份  0：管理员 1：医生 2：普通用户
    private String HX_name;
    private String HX_pwd;

    public User() {
    }

    public User(String id, String name, String photo, int identity, String HX_name, String HX_pwd) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.identity = identity;
        this.HX_name = HX_name;
        this.HX_pwd = HX_pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", identity=" + identity +
                ", HX_name='" + HX_name + '\'' +
                ", HX_pwd='" + HX_pwd + '\'' +
                '}';
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getHX_name() {
        return HX_name;
    }

    public void setHX_name(String HX_name) {
        this.HX_name = HX_name;
    }

    public String getHX_pwd() {
        return HX_pwd;
    }

    public void setHX_pwd(String HX_pwd) {
        this.HX_pwd = HX_pwd;
    }
}
