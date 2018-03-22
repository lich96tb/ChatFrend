package com.example.lich96tb.chatfriend;

/**
 * Created by lich96tb on 9/13/2017.
 */

public class News {
    private String title,link,imageURL,thoiGian,kenh;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getKenh() {
        return kenh;
    }

    public void setKenh(String kenh) {
        this.kenh = kenh;
    }

    public News() {
    }

    public News(String title, String link, String imageURL, String thoiGian, String kenh) {
        this.title = title;
        this.link = link;
        this.imageURL = imageURL;
        this.thoiGian = thoiGian;
        this.kenh = kenh;
    }
}
