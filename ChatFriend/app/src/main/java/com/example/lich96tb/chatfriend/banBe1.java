package com.example.lich96tb.chatfriend;

/**
 * Created by lich96tb on 8/7/2017.
 */

public class banBe1 {
    private String anh, ten, mail, id;
    private int online,thu;

    public banBe1() {
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public banBe1(String anh, String ten, String mail, String id) {
        this.anh = anh;
        this.ten = ten;
        this.mail = mail;
        this.id = id;
    }

    public banBe1(String anh, String ten, String mail, String id, int online) {
        this.anh = anh;
        this.ten = ten;
        this.mail = mail;
        this.id = id;
        this.online = online;
    }

    public banBe1(String anh, String ten, String mail, String id, int online, int thu) {
        this.anh = anh;
        this.ten = ten;
        this.mail = mail;
        this.id = id;
        this.online = online;
        this.thu = thu;
    }
}
