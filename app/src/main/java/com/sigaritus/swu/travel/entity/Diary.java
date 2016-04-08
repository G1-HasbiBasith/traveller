package com.sigaritus.swu.travel.entity;

/**
 * Created by Administrator on 2016/4/7.
 */
public class Diary {
    private String bookUrl;
    private String title;
    private String headImage;
    private String userName;
    private String userHeadImg;
    private String startTime;
    private int routeDays;
    private int bookImgNum;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private String text;
    private boolean elite;

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getRouteDays() {
        return routeDays;
    }

    public void setRouteDays(int routeDays) {
        this.routeDays = routeDays;
    }

    public int getBookImgNum() {
        return bookImgNum;
    }

    public void setBookImgNum(int bookImgNum) {
        this.bookImgNum = bookImgNum;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }
}
