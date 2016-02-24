package com.LAW.lift.model;

public class Card {
    String lawname, lawactno, lawenactedby, lawreceived, lawpublished, lawcame,lawsalient,lawbrief,lawfull;
    public Card(String lawname,String lawactno,String lawenactedby,String lawreceived,String lawpublished,String lawcame,String lawsalient,String lawbrief,String lawfulltext) {
        this.lawname = lawname;
        this.lawactno = lawactno;
        this.lawenactedby = lawenactedby;
        this.lawreceived = lawreceived;
        this.lawpublished = lawpublished;
        this.lawcame = lawcame;
        this.lawsalient = lawsalient;
        this.lawbrief = lawbrief;
        this.lawfull = lawfulltext;

    }

    public String getlawname() {
        return lawname;
    }
    public String getlawactno() {
        return lawactno;
    }
    public String getlawenactedby() {
        return lawenactedby;
    }
    public String getlawreceived() {
        return lawreceived;
    }
    public String getlawpublished() {
        return lawpublished;
    }
    public String getlawcame() {
        return lawcame;
    }
    public String getlawsalient() {
        return lawsalient;
    }
    public String getlawbrief() {
        return lawbrief;
    }
    public String getlawfulltext() {
        return lawfull;
    }
    //public String getLine2() {return line2;}

    //public String getLine3() { return  line3; }

}
