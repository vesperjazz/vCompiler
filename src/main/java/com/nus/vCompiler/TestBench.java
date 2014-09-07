//package VHDL;
package com.nus.vCompiler;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;




public class TestBench {
    private String TestBenchText;

    public void setTestBenchText(String TB) {
        if (TB == null) {
            TestBenchText = "";
        } else {
            TestBenchText = TB;
        }
    }

    public String getTestBench() {
        if (TestBenchText == null) {
            return "Test Null";
        } else {
           return TestBenchText;
        }
    }
    public static void main(String[] args) {
    }

    private void getPorts(ArrayList<Port> ports, StringBuilder line) {
        Port temp_port = null;
        String portName = "";
        String temp_string = "";

        int temp_int;
        while (line.length() != 0) {
            temp_port = new Port();
            //get PortNames
            while (line.length() != 0) {
                //JOptionPane.showMessageDialog(null, line.charAt(0));
                if (line.charAt(0) == ',') {
                    line.delete(0, 1);
                    temp_port.setPortName(portName);
                    getPorts(ports, line);
                    break;
                } else if (line.charAt(0) == ':') {
                    if (temp_port.getPortName().isEmpty()) {
                        temp_port.setPortName(portName);
                        portName = "";
                    }
                    line.delete(0, 1);
                    break;
                } else if ((line.charAt(0) != ' ') && (line.charAt(0) != '\t')) {

                    portName += line.charAt(0);
                    line.delete(0, 1);
                } else {
                    if (!portName.isEmpty()) {
                        temp_port.setPortName(portName);
                        portName = "";
                    }
                    line.delete(0, 1);
                }

            }
            while (line.indexOf(" ") == 0) {
                line.delete(0, 1);
            }

            if (!ports.isEmpty()) {
                temp_port.setDirection(ports.get(0).getDirection());
            } else {
                if (line.length() != 0) {
                    temp_int = line.indexOf(" ");
                    if (temp_int > -1) {
                        temp_port.setDirection(line.substring(0, temp_int).trim());
                        if (line.length() > temp_int + 1) {
                            line.delete(0, temp_int + 1);
                        }
                    }
                }
            }
            if (!ports.isEmpty()) {
                temp_port.setVariableType(ports.get(0).getVariableType());
            } else {

                temp_int = line.indexOf(";");

                if (temp_int > -1) {
                    temp_string = line.substring(0, temp_int).trim();
                } else {
                    temp_string = line.toString();
                }
                line.delete(0, temp_int + 1);
                if (temp_string.toLowerCase().contains("std_logic_vector")) {
                    int[] size = new int[2];
                    int i = 0;

                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(temp_string);

                    while (matcher.find()) {
                        size[i++] = Integer.parseInt(matcher.group());
                        if (i > 1) {
                            break;
                        }
                    }
                    if (size[0] > size[1]){
                        temp_port.setVariableType("STD_LOGIC_VECTOR(" + size[0] + " DOWNTO " + size[1] + ")");
                    }else{
                        temp_port.setVariableType("STD_LOGIC_VECTOR(" + size[0] + " TO " + size[1] + ")");
                    }
                } else {
                    temp_port.setVariableType("STD_LOGIC");
                }
            }
            if (!temp_port.getPortName().isEmpty()) {
                ports.add(0, temp_port);
            }
            break;
        }

    }

    public boolean createTestBench(String code) {

        //StringBuilder contents = new StringBuilder();
        String outputContents;
        String entityName = "";
        StringBuilder all_ports = new StringBuilder();
        ArrayList<Port> ports = new ArrayList<Port>();
        ArrayList<Port> temp_ports = new ArrayList<Port>();
        boolean chkEntity = false;
        boolean foundClk = false;
        boolean chkPort = false;
        String clk = "";
        String temp_string = "";
        int temp_int;
        Scanner input = new Scanner(code);
        try {
            String line = null; // not declared within while loop
            // Find Entity Name
            while ((line = input.nextLine()) != null) {
                if (chkEntity) {
                    entityName = line.trim();
                    temp_int = entityName.indexOf(" ");
                    if (temp_int > 0) {
                        entityName = entityName.substring(0, temp_int).trim();
                    }
                    if (entityName.length() > 0) {
                        break;
                    }
                } else {
                    
                    temp_string = line.toLowerCase();
                    
                    temp_int = temp_string.indexOf("entity");
                    
                    if (temp_int > -1) {
                        if (temp_int < temp_string.indexOf("--") || temp_string.indexOf("--") < 0) {
                            chkEntity = true;
                            entityName = line.substring(temp_int + 6).trim();
                            temp_int = entityName.indexOf(" ");
                            if (temp_int > -1) {
                                entityName = entityName.substring(0,
                                        temp_int).trim();
                            }
                            if (entityName.length() > 0) {
                                break;
                                
                            }
                        }
                    }
                }
            }
            
            // Get the texts contains ports
            do {
                temp_string = line.toLowerCase();
                
                if (temp_string.indexOf("end") > -1) {
                    if (temp_string.indexOf("end") < temp_string.indexOf("--") || temp_string.indexOf("--") < 0) {
                        break; // exit from loop;
                    }
                }
                if (!chkPort) {
                    temp_int = temp_string.indexOf("port");
                    if (temp_int > -1) {
                        if (temp_int < temp_string.indexOf("--") || temp_string.indexOf("--") < 0) {
                            chkPort = true;
                            all_ports.append(line.substring(temp_int));
                        }
                    }
                } else {
                    all_ports.append(line).append(" ");
                }
                //contents.append(System.getProperty("line.separator"));
            } while ((line = input.nextLine()) != null);
            
            //JOptionPane.showMessageDialog(null, all_ports);
            all_ports.delete(0, all_ports.indexOf("(") + 1);
            
            while (all_ports.length() != 0) {
                getPorts(temp_ports, all_ports);
                for (int i = 0; i < temp_ports.size(); i++) {
                    ports.add(temp_ports.get(i));
                    if (temp_ports.get(i).getPortName().equalsIgnoreCase("clk") || temp_ports.get(i).getPortName().equalsIgnoreCase("clock")) {
                        foundClk = true;
                        clk = temp_ports.get(i).getPortName();
                    }
                }
                temp_ports.clear();
            }
            
            if (ports.isEmpty()) {
                return false;
            }
            
            //String TBEntity = removeExtention(outputFileName);
            String TBEntity =entityName +"_tb";
            // Create test bench
            outputContents = "LIBRARY ieee;\n"
                    + "USE ieee.std_logic_1164.ALL;\n\n" + "ENTITY "
                    + TBEntity + " IS\n" + "END " + TBEntity
                    + ";\n\n" + "ARCHITECTURE behavior OF "
                    + TBEntity + " IS \n\n" + "\tCOMPONENT "
                    + entityName + "\n\tPORT(\n";
            for (int i = 0; i < ports.size(); i++) {
                outputContents += "\t\t" + ports.get(i).getPortName()
                        + " : " + ports.get(i).getDirection() + " "
                        + ports.get(i).getVariableType();
                if (i == ports.size() - 1) {
                    outputContents += ");\n";
                } else {
                    outputContents += ";\n";
                }
            }
            outputContents += "\tEND COMPONENT;\n\n";
            
            for (int i = 0; i < ports.size(); i++) {
                outputContents += "\tsignal " + ports.get(i).getPortName()
                        + " : " + ports.get(i).getVariableType();
                if (ports.get(i).getVariableType().equalsIgnoreCase("std_logic")) {
                    outputContents += " := '0';\n";
                } else {
                    outputContents += " := (others => '0');\n";
                }
            }
            
            if (foundClk) {
                outputContents += "\n\tconstant clk_period : time := 10 ns;\n\n";
            }
            
            outputContents += "BEGIN\n\n\t-- Instantiate the Unit Under Test (UUT)\n"
                    + "\tuut: " + entityName + " PORT MAP (\n";
            
            for (int i = 0; i < ports.size(); i++) {
                outputContents += "\t\t" + ports.get(i).getPortName() + " => " + ports.get(i).getPortName();
                if (i == ports.size() - 1) {
                    outputContents += ");\n";
                } else {
                    outputContents += ",\n";
                }
            }
            if (foundClk) {
                outputContents += "\n\n\t-- Clock process definitions\n"
                        + "\tclk_process : process\n"
                        + "\tbegin\n"
                        + "\t\t" + clk + " <= '0';\n"
                        + "\t\twait for clk_period/2;\n"
                        + "\t\t" + clk + " <= '1';\n"
                        + "\t\twait for clk_period/2;\n"
                        + "\tend process;\n\n\n";
            }
            
            outputContents += "\t-- Stimulus process\n"
                    + "\tstim_proc: process\n"
                    + "\tbegin\n" + "\t\t-- hold reset state for 100 ns.\n";
            
         //   if (foundClk) {
         //       outputContents += "\t\twait for clk_period*10;\n\n";
         //   } else {
         //       outputContents += "\t\twait for 100 ns;\n";
         //   }
            
        
            if (foundClk) {
                outputContents += "\t\twait for clk_period*10;\n\n";
            } else {
                   outputContents += "\t\twait for 10 ns;\n";
               }
            for (int i = 0; i < ports.size(); i++) {
                if(ports.get(i).getDirection().contains("in"))
                {

                     if (ports.get(i).getVariableType().toLowerCase().contains("std_logic_vector")) {
                     	String temp_string1 = ports.get(i).getVariableType();                    	 
                         int[] size = new int[2];
                         int z = 0;

                         Pattern pattern = Pattern.compile("\\d+");
                         Matcher matcher = pattern.matcher(temp_string1);

                         while (matcher.find()) {
                             size[z++] = Integer.parseInt(matcher.group());
                             if (z > 1) {
                                 break;
                             }
                         }
                        // outputContents += size[0];
                        // outputContents += size[1];
                         int value =0;
                         if (size[0]>size[1])
                         {
                        	 value = size[0] - size[1]+1;
                         }
                         else
                         {
                        	 value = size[1] - size[0]+1;                     	 
                         }
                         for (int y = 0; y <= Math.pow(2,value)-1; y++)
                         {
                        	// outputContents +=  String.format("%0" + value + "d", Integer.toBinaryString(y))+"\n";
                        	 outputContents +=  "\t\t" +ports.get(i).getPortName()+"<=\""+String.format("%0" + value + "d", Integer.parseInt(Integer.toBinaryString(y)))+"\";\n";
                             if (foundClk) {
                                 outputContents += "\t\twait for clk_period*10;\n\n";
                             } else {
                                 outputContents += "\t\twait for 10 ns;\n";
                             }      
                         }
                      
                     } 
                	 

	                
	                
                }
              
            }    
            
            outputContents += "\t\t-- insert stimulus here \n\n\n\n\n\n\n"
                    //+ "\t\twait for 100 ns;\n"
                    //+ "\t\tassert false report \"end of test\" severity note;\n"
                    + "\t\twait;\n" + "\tend process;\nEND behavior;";
     
            //System.out.println(outputContents);
            //Create test Bench
            //File tbFile = new File(path + outputFileName);
            //setContents(tbFile, outputContents);
            setTestBenchText(outputContents);
            return true;
            
        } finally {
            input.close();
        }
    }

    public String getContents(File aFile) {
        StringBuilder contents = new StringBuilder();

        try {

            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; // not declared within while loop

                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }
    public String intToString(int num, int digits) {
        assert digits > 0 : "Invalid number of digits";

        // create variable length array of zeros
        char[] zeros = new char[digits];
        Arrays.fill(zeros, '0');
        // format number as String
        DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

        return df.format(num);
    }    
}
