package com.mm.kyys.Model;

/**
 * Created by 27740 on 2017/1/23.
 */

public class Section {

    private String name;
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
    }
}
