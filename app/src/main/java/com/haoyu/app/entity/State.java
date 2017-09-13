package com.haoyu.app.entity;

/**
 * 创建日期：2017/2/7 on 16:41
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class State {
    private String state;
    private String content;

    public State() {
    }

    public State(String state, String content) {
        this.state = state;
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
