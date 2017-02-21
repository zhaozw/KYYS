package com.mm.kyys.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 27740 on 2017/1/24.
 */

public class User implements Parcelable {
    /**
     * userID : b0587d985dcf4aa3a37a5cdb63e91c0a
     * account : 666666
     * password : b131dae347cd8ba47ed65e5ab37e3d27
     * lastUpdateTime : 2017-02-14T17:08:43
     * flag : 0
     * userName : 新来的666666
     * type : 0
     * img : http://101.201.31.32:8055/Images/user_20160519000000000.png
     * mobiles :
     * userEx : 3152
     */

    private String userID;
    private String account;
    private String password;
    private String lastUpdateTime;
    private int flag;
    private String userName;
    private int type;           //0:普通用户  1:医生
    private String img;
    private String mobiles;
    private String userEx;

    public User() {
    }

    /**
     * @param userID    用户环信id
     * @param userName  用户昵称
     * @param img       用户头像
     */
    public User(String userID, String userName, String img) {
        this.userID = userID;
        this.userName = userName;
        this.img = img;
    }

    public User(String account, String password, String userID, String lastUpdateTime, int flag, String userName, int type, String img, String mobiles, String userEx) {
        this.account = account;
        this.password = password;
        this.userID = userID;
        this.lastUpdateTime = lastUpdateTime;
        this.flag = flag;
        this.userName = userName;
        this.type = type;
        this.img = img;
        this.mobiles = mobiles;
        this.userEx = userEx;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", flag=" + flag +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                ", img='" + img + '\'' +
                ", mobiles='" + mobiles + '\'' +
                ", userEx='" + userEx + '\'' +
                '}';
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getUserEx() {
        return userEx;
    }

    public void setUserEx(String userEx) {
        this.userEx = userEx;
    }

    /*private String id;
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
    }*/


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.account);
        dest.writeString(this.password);
        dest.writeString(this.lastUpdateTime);
        dest.writeInt(this.flag);
        dest.writeString(this.userName);
        dest.writeInt(this.type);
        dest.writeString(this.img);
        dest.writeString(this.mobiles);
        dest.writeString(this.userEx);
    }

    protected User(Parcel in) {
        this.userID = in.readString();
        this.account = in.readString();
        this.password = in.readString();
        this.lastUpdateTime = in.readString();
        this.flag = in.readInt();
        this.userName = in.readString();
        this.type = in.readInt();
        this.img = in.readString();
        this.mobiles = in.readString();
        this.userEx = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
