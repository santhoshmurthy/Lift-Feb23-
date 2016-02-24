package com.LAW.lift.model;

/**
 * Created by santhoshis on 2/10/2016.
 */
public class highcourtcard {




    String highpara, contexthigh, questionhigh, answerhigh, referencehigh, texthigh;
    public highcourtcard(String highpara,String contexthigh,String questionhigh,String answerhigh,String referencehigh,String texthigh) {
        this.highpara = highpara;
        this.contexthigh = contexthigh;
        this.questionhigh = questionhigh;
        this.answerhigh = answerhigh;
        this.referencehigh = referencehigh;
        this.texthigh = texthigh;


    }

    public String gethighpara() {
        return highpara;
    }
    public String getcontexthigh() {
        return contexthigh;
    }
    public String getquestionhigh() {
        return questionhigh;
    }
    public String getanswerhigh() {
        return answerhigh;
    }
    public String getreferencehigh() {
        return referencehigh;
    }
    public String gettexthigh() {
        return texthigh;
    }

    //public String getLine2() {return line2;}

    //public String getLine3() { return  line3; }


}


