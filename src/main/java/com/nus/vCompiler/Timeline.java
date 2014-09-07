package com.nus.vCompiler;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Yee Tang
 */
public class Timeline {

    private ArrayList<String> time = new ArrayList<String>();
    private ArrayList<String> value = new ArrayList<String>();

    
    public String getPrintNoMark() {
        String result ="\"data\":{";
        for (int i=0;i<time.size();i++)
        {
            String ltime = time.get(i);
            String lvalue = value.get(i);
            result +="\""+ltime+"\":"+lvalue+"";
            if (i!=time.size()-1)
                result +=",";
        }
        result +="}";
        
        return result;
    }  
    public String getPrint() {
        String result ="\"data\":{";
        for (int i=0;i<time.size();i++)
        {
            String ltime = time.get(i);
            String lvalue = value.get(i);
            result +="\""+ltime+"\":\""+lvalue+"\"";
            if (i!=time.size()-1)
                result +=",";
        }
        result +="}";
        
        return result;
    }  

    public Timeline() {
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }
    private static final Logger LOG = Logger.getLogger(Timeline.class.getName());

    @Override
    public String toString() {
        return "Timeline{" + "time=" + time + ", value=" + value + '}';
    }


}
