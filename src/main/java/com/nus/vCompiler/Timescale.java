package com.nus.vCompiler;


import java.util.logging.Logger;

/**
 *
 * @author Yee Tang
 */
public class Timescale {

    public Timescale() {
    }
    private static final Logger LOG = Logger.getLogger(Timescale.class.getName());
    private String time_number;
    private String time_unit;


    public String getPrint() {
        return "\"timescale\":{\"number\":"+time_number+",\"unit\":\""+time_unit+"\"}";
    }
          
      
    
    @Override
    public String toString() {
        return "Timescale{" + "time_number=" + time_number + ", time_unit=" + time_unit + '}';
    }
   

    public String getTime_number() {
        return time_number;
    }

    public void setTime_number(String time_number) {
        this.time_number = time_number;
    }

    public String getTime_unit() {
        return time_unit;
    }

    public void setTime_unit(String time_unit) {
        this.time_unit = time_unit;
    }
}
