package com.mm.kyys.Model;

/**
 * Created by 27740 on 2017/2/20.
 */

public class HxUserInfo {

    private String Hx_id;
    private String Hx_nick;
    private String Hx_img;

    public HxUserInfo() {
    }

    public HxUserInfo(String hx_id, String hx_nick, String hx_img) {
        Hx_id = hx_id;
        Hx_nick = hx_nick;
        Hx_img = hx_img;
    }

    @Override
    public String toString() {
        return "HxUserInfo{" +
                "Hx_id='" + Hx_id + '\'' +
                ", Hx_nick='" + Hx_nick + '\'' +
                ", Hx_img='" + Hx_img + '\'' +
                '}';
    }

    public String getHx_id() {
        return Hx_id;
    }

    public void setHx_id(String hx_id) {
        Hx_id = hx_id;
    }

    public String getHx_nick() {
        return Hx_nick;
    }

    public void setHx_nick(String hx_nick) {
        Hx_nick = hx_nick;
    }

    public String getHx_img() {
        return Hx_img;
    }

    public void setHx_img(String hx_img) {
        Hx_img = hx_img;
    }
}
