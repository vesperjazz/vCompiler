package com.nus.vCompiler;

import java.util.logging.Logger;


/**
 *
 * @author Yee Tang
 */
public class Scope {
    private String type;
    private String name;
    private static final Logger LOG = Logger.getLogger(Scope.class.getName());
    
    
    
    @Override
    public String toString() {
        return "Scope{" + "type=" + type + ", name=" + name + '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
