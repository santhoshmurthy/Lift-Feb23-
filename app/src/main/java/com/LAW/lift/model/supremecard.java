package com.LAW.lift.model;


public class supremecard {






    String titlepara, contextpara, questionpara, answerpara, referencepara, texttouch;
    public supremecard(String titlepara,String contextpara,String questionpara,String answerpara,String referencepara,String texttouch) {
        this.titlepara = titlepara;
        this.contextpara = contextpara;
        this.questionpara = questionpara;
        this.answerpara = answerpara;
        this.referencepara = referencepara;
        this.texttouch = texttouch;


    }

    public String gettitlepara() {
        return titlepara;
    }
    public String getcontextpara() {
        return contextpara;
    }
    public String getquestionpara() {
        return questionpara;
    }
    public String getanswerpara() {
        return answerpara;
    }
    public String getreferencepara() {
        return referencepara;
    }
    public String gettexttouch() {
        return texttouch;
    }

    //public String getLine2() {return line2;}

    //public String getLine3() { return  line3; }


}
