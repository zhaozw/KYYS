package com.mm.kyys.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 27740 on 2017/1/23.
 */

public class Doctor implements Parcelable {

    /**
     * dcid : 1c7286fc561b44dea4b34aaea4f2fc48      医生id
     * did : d15d65b676d840cc875f6b359e958fff       二级分类id
     * name : 张显忠
     * type : 主任医师/教授
     * dtype :                                      备用
     * hospitals : 沈阳市红十字会医院
     * stars : 4
     * advantage : 各种胃、肠、肝、胆、胰腺疾病的内科治疗，经腹水浓缩回输系统治疗各种顽固性腹水；经内镜下治疗项目食管、胃肠道狭窄置入术及晚期肿瘤介入术，胃肠道息肉切除术，异物取出术，胆总管结石取石术等。
     * introduction :
     * wait : 0
     * lastUpdateTime : 2017-02-20T10:01:26
     * flag : 0
     * uid : 474690dbf61d45a58d245319eeed05d7       用户id
     * img :
     */

    private String dcid;
    private String did;
    private String name;
    private String type;
    private String dtype;
    private String hospitals;
    private int stars;
    private String advantage;
    private String introduction;
    private int wait;
    private String lastUpdateTime;
    private int flag;
    private String uid;
    private String img;

    public Doctor(String dcid, String did, String name, String type, String dtype, String hospitals, int stars, String advantage, String introduction, int wait, String lastUpdateTime, int flag, String uid, String img) {
        this.dcid = dcid;
        this.did = did;
        this.name = name;
        this.type = type;
        this.dtype = dtype;
        this.hospitals = hospitals;
        this.stars = stars;
        this.advantage = advantage;
        this.introduction = introduction;
        this.wait = wait;
        this.lastUpdateTime = lastUpdateTime;
        this.flag = flag;
        this.uid = uid;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "dcid='" + dcid + '\'' +
                ", did='" + did + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dtype='" + dtype + '\'' +
                ", hospitals='" + hospitals + '\'' +
                ", stars=" + stars +
                ", advantage='" + advantage + '\'' +
                ", introduction='" + introduction + '\'' +
                ", wait=" + wait +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", flag=" + flag +
                ", uid='" + uid + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getHospitals() {
        return hospitals;
    }

    public void setHospitals(String hospitals) {
        this.hospitals = hospitals;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        dest.writeString(this.dcid);
        dest.writeString(this.did);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.dtype);
        dest.writeString(this.hospitals);
        dest.writeInt(this.stars);
        dest.writeString(this.advantage);
        dest.writeString(this.introduction);
        dest.writeInt(this.wait);
        dest.writeString(this.lastUpdateTime);
        dest.writeInt(this.flag);
        dest.writeString(this.uid);
        dest.writeString(this.img);
    }

    public Doctor() {
    }

    protected Doctor(Parcel in) {
        this.dcid = in.readString();
        this.did = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.dtype = in.readString();
        this.hospitals = in.readString();
        this.stars = in.readInt();
        this.advantage = in.readString();
        this.introduction = in.readString();
        this.wait = in.readInt();
        this.lastUpdateTime = in.readString();
        this.flag = in.readInt();
        this.uid = in.readString();
        this.img = in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel source) {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}
