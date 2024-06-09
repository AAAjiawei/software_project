package com.violetks.entity;

import java.util.Date;

public class Examination {
    private int eid;
    private String eName = "";
    private String tPhone = "";
    private String qList = "";
    private String sClass = "";
    private Date addTime;

    public Examination() {}

    public int getEid() {return eid;}
    public void setEid(int eid) {this.eid = eid;}

    public String geteName() {return eName;}
    public void seteName(String eName) {this.eName = eName;}

    public String gettPhone() {return tPhone;}
    public void settPhone(String tPhone) {this.tPhone = tPhone;}

    public String getqList() {return qList;}
    public void setqList(String qList) {this.qList = qList;}

    public String getsClass() {return sClass;}
    public void setsClass(String sClass) {this.sClass = sClass;}

    public Date getAddTime() {return addTime;}
    public void setAddTime(Date addTime) {this.addTime = addTime;}
}
