package com.example.lich96tb.chatfriend;

/**
 * Created by lich96tb on 8/2/2017.
 */

public class Chat {
    private String ten,noidung,thoigian,id;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chat() {
    }

    public Chat(String ten, String noidung, String thoigian, String id) {
        this.ten = ten;
        this.noidung = noidung;
        this.thoigian = thoigian;
        this.id = id;
    }
}
