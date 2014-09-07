package com.nus.vCompiler;


import java.util.logging.Logger;

/**
 *
 * @author Yee Tang
 */
public class Signal {
    private String name;
    private Scope scope;
    private String type;

    public String getPrint() {
        String result ="\"signals\":[{\"name\":\""+name+"\",\"scope\":\""+scope.getName()+"\",\"type\":\""+type+"\"}]";
        return result;
    }  
            

    public Signal() {
    }
    private static final Logger LOG = Logger.getLogger(Signal.class.getName());

    @Override
    public String toString() {
        return "Signal{" + "name=" + name + ", scope=" + scope + ", type=" + type + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
