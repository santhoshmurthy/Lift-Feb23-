package com.LAW.lift.model;

/**
 * Created by santhoshis on 2/10/2016.
 */
public class forumcard {




    String thoughttext, juniortext, author;
    public forumcard(String thoughttext,String juniortext,String author) {
        this.thoughttext = thoughttext;
        this.juniortext = juniortext;
        this.author = author;



    }

    public String getthoughttext() {
        return thoughttext;
    }
    public String getjuniortext() {
        return juniortext;
    }
    public String getauthor() {
        return author;
    }


    //public String getLine2() {return line2;}

    //public String getLine3() { return  line3; }


}




