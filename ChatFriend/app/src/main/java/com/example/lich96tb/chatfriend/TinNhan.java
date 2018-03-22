package com.example.lich96tb.chatfriend;

/**
 * Created by lich96tb on 9/16/2017.
 */

public class TinNhan {
    private String anh,ten,id,noidung;
    int online;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public TinNhan() {
    }

    public TinNhan(String anh, String ten, String id, String noidung) {
        this.anh = anh;
        this.ten = ten;
        this.id = id;
        this.noidung = noidung;
    }

    public TinNhan(String anh, String ten, String id, String noidung, int online) {
        this.anh = anh;
        this.ten = ten;
        this.id = id;
        this.noidung = noidung;
        this.online = online;
    }
}
