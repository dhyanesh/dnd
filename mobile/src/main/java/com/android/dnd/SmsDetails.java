package com.android.dnd;

import java.util.Date;

/**
 * Created by dhyanesh on 1/26/16.
 */
public class SmsDetails {
    @Override
    public String toString() {
        return "SmsDetails{" +
                "address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }

    private String address;
    private String body;
    private Date date;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }
}
