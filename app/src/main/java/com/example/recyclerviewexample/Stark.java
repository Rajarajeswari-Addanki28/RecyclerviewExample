package com.example.recyclerviewexample;

class Stark {
    String  title;
    String img;
    String p,pd;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }
    public Stark(String title, String img,String p,String pd) {
        this.title=title;
        this.img=img;
        this.p=p;
        this.pd=pd;

    }
    public  Stark(){

    }
}
