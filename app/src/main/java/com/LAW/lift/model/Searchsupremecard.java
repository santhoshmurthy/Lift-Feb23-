package com.LAW.lift.model;


public class Searchsupremecard {


  String casebookname,titlepara, contextpara, questionpara, answerpara, referencepara;
    public Searchsupremecard(String casebookname, String titlepara, String contextpara, String questionpara, String answerpara,String referencepara) {


        this.casebookname = casebookname;
        this.titlepara = titlepara;
        this.contextpara = contextpara;
        this.questionpara = questionpara;
        this.answerpara = answerpara;
        this.referencepara = referencepara;





    }
    public String getcasebookname() {
        return casebookname;
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




    //public String getLine2() {return line2;}

    //public String getLine3() { return  line3; }


}
