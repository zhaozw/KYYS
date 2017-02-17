package com.mm.kyys.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 27740 on 2017/1/23.
 */

public class Section implements Parcelable {
    /**
     * did : 1
     * hid :
     * name : 内科
     * lastUpdateTime : 2016-05-19T00:00:00
     * flag : 0
     * type : 1
     * did2 : 0
     * img :
     */

    private String did;                     //用户id
    private String hid;                     //备用
    private String name;                    //科室名
    private String lastUpdateTime;          //上次更新时间
    private int flag;                       //删除标记
    private int type;                       //分类等级
    private String did2;                    //父id
    private String img;                     //科室图标  暂无



    public Section() {
    }

    public Section(String did, String hid, String name, String lastUpdateTime, int flag, int type, String did2, String img) {
        this.did = did;
        this.hid = hid;
        this.name = name;
        this.lastUpdateTime = lastUpdateTime;
        this.flag = flag;
        this.type = type;
        this.did2 = did2;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Section{" +
                "did='" + did + '\'' +
                ", hid='" + hid + '\'' +
                ", name='" + name + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", flag=" + flag +
                ", type=" + type +
                ", did2='" + did2 + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDid2() {
        return did2;
    }

    public void setDid2(String did2) {
        this.did2 = did2;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.did);
        dest.writeString(this.hid);
        dest.writeString(this.name);
        dest.writeString(this.lastUpdateTime);
        dest.writeInt(this.flag);
        dest.writeInt(this.type);
        dest.writeString(this.did2);
        dest.writeString(this.img);
    }

    protected Section(Parcel in) {
        this.did = in.readString();
        this.hid = in.readString();
        this.name = in.readString();
        this.lastUpdateTime = in.readString();
        this.flag = in.readInt();
        this.type = in.readInt();
        this.did2 = in.readString();
        this.img = in.readString();
    }

    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };


/*    private String name;
    private int pic_no;

    public Section() {
    }

    public Section(String name, int pic_no) {
        this.name = name;
        this.pic_no = pic_no;
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", pic_no=" + pic_no +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic_no() {
        return pic_no;
    }

    public void setPic_no(int pic_no) {
        this.pic_no = pic_no;
    }*/



}
