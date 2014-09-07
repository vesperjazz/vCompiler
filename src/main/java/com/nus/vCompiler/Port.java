package com.nus.vCompiler;

public class Port {
	private String portName;
	private String direction;
	private String variableType;
        public Port(){
            portName = "";
            direction ="";
            variableType ="";
        }
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getVariableType() {
		return variableType;
	}
	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}
	
}
