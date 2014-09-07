package com.nus.vCompiler;

import java.util.logging.Logger;

/**
 *
 * @author Yee Tang
 */
public class Vars {
    private String ID;
    private Signal signal;
    private String width;
    private Timeline timeline = new Timeline();

    public Vars() {
    }
    private static final Logger LOG = Logger.getLogger(Vars.class.getName());

    public String getPrint() {
    	String result;
    	if (Integer.parseInt(width.trim()) ==1)
    		result ="\""+ID.replaceAll("\"", "\\\\\"")+"\":{"+signal.getPrint()+",\"width\":"+width+","+timeline.getPrintNoMark()+"}";
    	else
    		result ="\""+ID.replaceAll("\"", "\\\\\"")+"\":{"+signal.getPrint()+",\"width\":"+width+","+timeline.getPrint()+"}";  		
        return result;
    }  
    public String getPrintID() {
        String result ="{\"id\":\""+ID.replaceAll("\"", "\\\\\"")+"\",\"signal\":{\"name\":\""+signal.getName()+"\",\"scope\":\""+signal.getScope().getName()+"\",\"type\":\""+signal.getType()+"\"}}";
        return result;
    }             
     
    
    @Override
    public String toString() {
        return "Vars{" + "ID=" + ID + ", signal=" + signal + ", width=" + width + ", timeline=" + timeline + '}';
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
