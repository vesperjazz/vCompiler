package com.nus.vCompiler;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Yee Tang
 */
public class Data {
    private Timescale time;
    private String version;
    private String warning="";
    private String date;
    private ArrayList<Vars> vars = new ArrayList<Vars>();
    private int start;
    private int end;
    public Data() {
    }
    private static final Logger LOG = Logger.getLogger(Data.class.getName());


    public Timescale getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Data{" + "time=" + time + ", version=" + version + ", warining=" + warning + ", date=" + date + ", vars=" + vars + '}';
    }

    public String getPrint() {
        String result = "{"+time.getPrint()+",\"start\":" +start+",\"end\":" +end +",\"vars\":{";
        for (int i=0;i<vars.size();i++)
        {
            Vars var = vars.get(i);
            result +=var.getPrint();
            if (i!=vars.size()-1)
                result +=",";
        }
        result +="},\"version\":\""+version+"\",\"warning\":\""+warning+"\"}";
        return result;
    } 
    public String getPrintID(){
        String result ="[";
        for (int i=0;i<vars.size();i++)
        {
            Vars var = vars.get(i);
            result +=var.getPrintID();
            if (i!=vars.size()-1)
                result +=",";
            else
                result +="]";
        } 
        return result;
    }
      
        


    public void setTime(Timescale time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarining(String warining) {
        this.warning = warining;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Vars> getVars() {
        return vars;
    }

    public void setVars(ArrayList<Vars> vars) {
        this.vars = vars;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }



}
