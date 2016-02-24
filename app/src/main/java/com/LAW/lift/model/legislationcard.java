package com.LAW.lift.model;

public class legislationcard {
    String tamilname, tamilactno, tamilenactedby, tamilreceived, tamilpublished, tamilcame,tamilsalient,tamilbrief,tamilfulltext;
    public legislationcard(String tamilname, String tamilactno, String tamilenactedby, String tamilreceived, String tamilpublished, String tamilcame, String tamilsalient, String tamilbrief, String tamilfulltext) {
        this.tamilname = tamilname;
        this.tamilactno = tamilactno;
        this.tamilenactedby = tamilenactedby;
        this.tamilreceived = tamilreceived;
        this.tamilpublished = tamilpublished;
        this.tamilcame = tamilcame;
        this.tamilsalient = tamilsalient;
        this.tamilbrief = tamilbrief;
        this.tamilfulltext = tamilfulltext;

    }

    public String gettamilname() {
        return tamilname;
    }
    public String gettamilactno() {
        return tamilactno;
    }
    public String gettamilenactedby() {
        return tamilenactedby;
    }
    public String gettamilreceived() {
        return tamilreceived;
    }
    public String gettamilpublished() {
        return tamilpublished;
    }
    public String gettamilcame() {
        return tamilcame;
    }
    public String gettamilsalient() {
        return tamilsalient;
    }
    public String gettamilbrief() {
        return tamilbrief;
    }
    public String gettamilfulltext() {
        return tamilfulltext;
    }
    //public String getLine2() {return line2;}

    //public String getLine3() { return  line3; }

}
