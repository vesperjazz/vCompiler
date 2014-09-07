package com.nus.vCompiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import org.apache.commons.lang3.*;
public class Compile {
	String message;
	public String getMessage()
	{
		return message;
		
	}
	VCD vcd = new VCD();
	public VCD getVcd() {
		return vcd;
	}
	public void setVcd(VCD vcd) {
		this.vcd = vcd;
	}
	String vcdMessage;
	String vcdIndex;
	double vcdEnd;
	
	public double getVcdEnd() {
		return vcdEnd;
	}
	public void setVcdEnd(double vcdEnd) {
		this.vcdEnd = vcdEnd;
	}
	public String getVCDMessage()
	{
		return vcdMessage;
		
	}	
	public String getVCDIndex()
	{
		return vcdIndex;
		
	}		
	String absolutePath;
	public void setAbsolutePath(String path)
	{
		absolutePath = path;
	}	
	public String getAbsolutePath()
	{
		return absolutePath;
	}
	//------------------------------------------------------------------------------------------------------------------------//
    //Simulate() will be called when the button Simulate is clicked. It will call GTKWave to run and take the testbench output as input.
    //The waveform will be printed according to the testbench output

    public boolean Simulate(String range,String problemNum, String code) {

        //textArea.append("Simulating...\n");
        try {
          //  int num = Integer.parseInt(range);
                if (build(problemNum, code)) {
                	String entityName = getEntityName(code);
        	        String filePath = getAbsolutePath();
        	        vcdMessage ="";
        	        String fileName = entityName +".vhd";
        	        System.out.println("\nfile canonical path " + filePath.replace(fileName, ""));                	
                    create_simulate_VCD(filePath.replace(fileName, ""),entityName, entityName, range);
            		BufferedReader br = null;
            		 
            		try {
             
            			String sCurrentLine;
             
            			br = new BufferedReader(new FileReader(entityName+".vcd"));
             
            			while ((sCurrentLine = br.readLine()) != null) {
            				//System.out.println(sCurrentLine);
            				vcdMessage +=sCurrentLine + "\n";
            				//vcdMessage +=sCurrentLine;
            			}
            			splitVCD();
            			//System.out.println(vcdMessage);	
            			br.close();
            		} catch (IOException e) {
            			e.printStackTrace();
            		} finally {
            			try {
            				if (br != null)br.close();
            			} catch (IOException ex) {
            				ex.printStackTrace();
            			}
            		}
                    return true;
                }
                return false;
           // }
        } catch (NumberFormatException ex) {
        	 return false;
         //   print_to_console("Please enter the number between 10 to 5000");
        }
    }
    public void splitVCD()
    {
    	String text = vcdMessage;
        Data container = new Data();
        BufferedReader br = null;
        
        String top = text.trim().replaceAll("[\n\r]", " ");
        Pattern pattern = Pattern.compile("(.*?)\\$enddefinitions \\$end");
        Matcher matcher = pattern.matcher(top);
        while (matcher.find()) {
            
            System.out.println("Everything : " + matcher.group(1));      
            
            String declaration =  matcher.group(1);
            Pattern dateValue = Pattern.compile("\\$date(.*?)\\$end");
            Matcher dateMatcher = dateValue.matcher(declaration);
            while (dateMatcher.find()) {
               System.out.println("Date : " + dateMatcher.group(1));
               container.setDate(dateMatcher.group(1).trim());
           }               
            Pattern versionValue = Pattern.compile("\\$version(.*?)\\$end");
            Matcher versionMatcher = versionValue.matcher(declaration);
            while (versionMatcher.find()) {
               System.out.println("Version : " + versionMatcher.group(1).trim());
               container.setVersion(versionMatcher.group(1).trim());
           }         
            
            Pattern timeValue = Pattern.compile("\\$timescale(.*?)\\$end");
            Matcher timeMatcher = timeValue.matcher(declaration);
            while (timeMatcher.find()) {
                String time = timeMatcher.group(1).trim();
                String[] words = time.split(" ");  
                Timescale timescale = new Timescale();
                for (String word : words)  
                {  
                   System.out.println(word);  
                   
                }  
                timescale.setTime_number(words[0]);
                timescale.setTime_unit("s");
               // timescale.setTime_unit(words[1]);
                System.out.println("Time : " + timeMatcher.group(1));
                
                container.setTime(timescale);
           }       
            
            
            String scopeString =  matcher.group(1);
            Pattern scopeValue = Pattern.compile("\\$scope(.*?)\\$end");
            Matcher scopeMatcher = scopeValue.matcher(scopeString);
           // if (scopeScopeMatcher.find())
            while (scopeMatcher.find()) {
	                String[] scopeList = scopeMatcher.group(1).trim().split("\\s+");
	                Scope scope = new Scope();
	                scope.setType(scopeList[0]);
	                scope.setName(scopeList[1]);            	
            	
            	
            	
            	
	               System.out.println("Scope : " + scopeMatcher.group(1));
	               String lines[] = matcher.group(1).split("\\$scope (.*?) \\$end");
	               System.out.println("lines : " + lines[1]);
	               Pattern varValue = Pattern.compile("\\$var(.*?)\\$end");
	               
	                Matcher varMatcher = varValue.matcher(lines[1]);            
	            
	          //  Pattern varValue = Pattern.compile("\\$var(.*?)\\$end");
	          //  Matcher varMatcher = varValue.matcher(declaration);
	            ArrayList<Vars> vars = new ArrayList<Vars>();
	            while (varMatcher.find()) {
	               String var = varMatcher.group(1).trim();
	               String[] words = var.split(" ");  
	                
	
	                
	               
	                for (int i = 0; i <words.length;i+=4)
	                {  
	                    Vars lvar = new Vars();
	                    Signal signal = new Signal();
	                    signal.setScope(scope);
	                    signal.setType(words[i]);
	                    lvar.setWidth(words[i+1]);
	                    lvar.setID(words[i+2].trim());
	                    signal.setName(words[i+3]);
	                      
	                    //Vars[] vars = new Vars[words.length/4];
	                    lvar.setSignal(signal);
	                    vars.add(lvar);                  
	                }  
	                //type size ID Name
	                
	
	
	
	                System.out.println("var : " + varMatcher.group(1));
	           }     
	           container.setVars(vars);
	        }    
        }
        System.out.println("Bottom Test");
        
        int max = 0;
        //System.out.println(bottom);
        String[] result = pattern.split(text);
       // System.out.println(Arrays.toString(result));
        String bottom = result[1].trim();
        String lines[] = bottom.split("\\r?\\n");
        //System.out.println(lines[0]);
        String timeRange ="";
        for (String word : lines)  
        {  
           if (word.charAt(0)=='#')
           {
            System.out.println("Time range : " + word.substring(1));
            timeRange = word.substring(1);
            int temp = Integer.parseInt(timeRange)/1000000;
            timeRange = Integer.toString(temp);            
           }
           else
           {
             String[] name = word.split("\\s+");
             String time, id;
            if (name.length == 2) {
               time = name[0];
               id = name[1];
                System.out.println("Two : " + word); 
                System.out.println("topTitle : " + time); 
                System.out.println("subTitle : " + id); 
                ArrayList<Vars> vars = new ArrayList<Vars>();
                vars = container.getVars();
                for (int i=0;i<vars.size();i++)
                {
                    Vars var = vars.get(i);
                    //System.out.println("var.getID() : " + var.getID()); 
                   // System.out.println("id : " + id); 
                    if (var.getID().equals(id))
                    {
                        Timeline timeline = var.getTimeline();
                        ArrayList<String> ltime = timeline.getTime();
                        ArrayList<String> lValue = timeline.getValue();

                        //timeline.setTime(ltime);
                        if (time.charAt(0)=='b')
                        {
                            
                         System.out.println("Time range : " + timeRange);
                         time = time.substring(1);
                        }    
                        ltime.add(timeRange);
                        lValue.add(time);   
                        
                        timeline.setValue(lValue);
                    }
                }
            }
            else
            System.out.println("One : " + word);  
            ArrayList<Vars> vars = new ArrayList<Vars>();
            vars = container.getVars();
            for (int i=0;i<vars.size();i++)
            {
                Vars var = vars.get(i);
                if (var.getID().equals(word.substring(1)))
                {
                    Timeline timeline = var.getTimeline();
                    ArrayList<String> ltime = timeline.getTime();
                    ArrayList<String> lValue = timeline.getValue();
                    lValue.add(word.substring(0,1));
                    ltime.add(timeRange);   

                    timeline.setValue(lValue);
                }
            } 
            container.setEnd((int)(Integer.parseInt(timeRange)*1.1));
            
           }
        }   
        
        System.out.println("container : " + container.getPrint());  
        vcdMessage =  container.getPrint();
        vcdIndex  =  container.getPrintID();
        vcdEnd = Integer.parseInt(timeRange)*1.1;
        vcd.setMessage(container.getPrint());
        vcd.setIndex(container.getPrint());
        vcd.setEnd(Integer.parseInt(timeRange));        
     
}        
        
    
//------------------------------------------------------------------------------------------------------------------------//	
  //------------------------------------------------------------------------------------------------------------------------//
    //supporting function of Simulate()

    public void create_simulate_VCD(String path,String fileName, String entityName, String range) {
        //String currDir = System.getProperty("user.dir");
        String os = System.getProperty("os.name").toLowerCase();
        //String entityName = getEntityName(path + fileName);

        if (os.contains("linux") || os.contains("unix")) {
            run(path, "ghdl", "-e", "-fexplicit", "--ieee=synopsys", entityName);
            run(path, "ghdl", "-r", "-fexplicit", "--ieee=synopsys", fileName, "--vcd=" + entityName + ".vcd", "--stop-time=" + range + "ns");
        } else if (os.contains("win")) {
            run(path, "ghdl", "-e", "-fexplicit", "--ieee=synopsys", entityName);
            run(path, "ghdl", "-r", "-fexplicit", "--ieee=synopsys", entityName, "--vcd=" + entityName + ".vcd", "--stop-time=" + range + "ns");
        } else if (os.contains("mac")) {
            run(path, "/usr/local/bin/ghdl", "-e", "-fexplicit", "--ieee=synopsys", entityName);
            run(path, "/usr/local/bin/ghdl", "-r", "-fexplicit", "--ieee=synopsys", entityName, "--vcd=" + entityName + ".vcd", "--stop-time=" + range + "ns");
        }
    }    
	public boolean submit(String problemNum, String code)  {
       	String data = code;
       	System.out.print(problemNum);
        String entityName = getEntityName(code);
		File file =new File(entityName + ".vhd");
		file.delete();
		//if file doesnt exists, then create it
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file.getName(),true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(data);
	        bufferWritter.close();	

            String filePath = file.getAbsolutePath();
	  	  if (!analiseVHD(filePath.replace(file.getName(), ""), file.getName())) {
	  		  message = "\nBuild failed!\n";
		       return false;
		   } else {
			   absolutePath = file.getAbsolutePath();
			   message = "\nBuild successful!\n";
		      // return true;
		   } 	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("test");
	
		

			
		
		/** try {
   //     boolean isSubmited = true;**/
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
  //      httpclient.getConnectionManager().shutdown();                
  //      message = "\nTest\n";

        //httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);


       HttpPost httppost = new HttpPost("http://mamps.nus.edu.sg:8080/VHDL_Server/TestServlet");
        //HttpPost httppost = new HttpPost("http://localhost:8084/VHDL_Server/TestServlet");


        File sendFile = new File(file.getAbsolutePath());
    //    message = "\nTest\n";
        
        //Formating file
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(sendFile, "text/plain");
        ContentBody cbString = null;
		try {
			cbString = new StringBody(problemNum, "text/plain", Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("test\n");
        
        //Add to Entity
        mpEntity.addPart("probNum", cbString);
        mpEntity.addPart("file", cbFile);

        httppost.setEntity(mpEntity);


        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println(httppost.getEntity().getContentType());
        HttpResponse response = null;
        
        try {
        	 message = "\nUploading File\n";
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();


            if (resEntity != null) {
                System.out.println(EntityUtils.toString(resEntity));
            }
          /**  if (resEntity != null) {
                resEntity.consumeContent();
            }**/

            httpclient.getConnectionManager().shutdown();
            System.out.println("Close");
        } catch (IOException ex) {
        	 message = "\nFile NOT submitted...\n";
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
 /**       if (response != null) {
            int index = 0;
            for (Header h : response.getAllHeaders()) {
                if (h.getName().toLowerCase().contains("error")) {
                    isSubmited = false;
                    total_result = "----------";
                    message ="\nError on submitted file.\n";
                    message =message + "\n" + h.getValue() + "\n";
                    break;
                }
                if (h.getName().toLowerCase().contains("result")) {
                    //JOptionPane.showMessageDialog(null, total_result);
                    total_result = total_result.replaceFirst("-", h.getValue());
                    index++;
                }
                //JOptionPane.showMessageDialog(null, h.getName() + " : " + h.getValue());
            }
            //JOptionPane.showMessageDialog(null, total_result);
            if (isSubmited) {
                if (total_result.equals("----------")) {
                	message = "\nCompilation error or your file does not match the format...\n";
                } else {
                	message = "\nFile submitted...\n";
                }
            } else {
            	message = "\nFile Not submitted...\n";
            }
        }

       
    } catch (UnsupportedEncodingException ex) {
    	message = "\nFile NOT submitted...\n";
        JOptionPane.showMessageDialog(null, ex.getMessage() + "ONE");
        Logger.getLogger(VCompiler.class.getName()).log(Level.SEVERE, null, ex);
    }**/
//   }
//} else {
//    print_to_console("\nFile NOT submitted...\n");
// }

//}
//    return true;		
		
		
		
		return true;
	}
	public boolean build(String problemNum, String code)  {
       	String data = code;
        String entityName = getEntityName(code);
		File file =new File(entityName + ".vhd");
		file.delete();
		//if file doesnt exists, then create it
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file.getName(),true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(data);
	        bufferWritter.close();	

            String filePath = file.getAbsolutePath();
	  	  if (!analiseVHD(filePath.replace(file.getName(), ""), file.getName())) {
	  		  message = "\nBuild failed!\n";
		       return false;
		   } else {
			   absolutePath = file.getAbsolutePath();
			   message = "\nBuild successful!\n";
		      // return true;
		   } 	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("test");
	
		

			
		
		/** try {
   //     boolean isSubmited = true;**/
  //      HttpClient httpclient = new DefaultHttpClient();
  //      httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
  //      httpclient.getConnectionManager().shutdown();                
  //      message = "\nTest\n";

        //httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);


   //    HttpPost httppost = new HttpPost("http://mamps.nus.edu.sg:8080/VHDL_Server/TestServlet");
        //HttpPost httppost = new HttpPost("http://localhost:8084/VHDL_Server/TestServlet");

//
   //     File sendFile = new File(file.getAbsolutePath());
    //    message = "\nTest\n";
        
        //Formating file
    /**    MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(sendFile, "text/plain");
        ContentBody cbString = null;
		try {
			cbString = new StringBody(problemNum, "text/plain", Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("test\n");
        
        //Add to Entity
        mpEntity.addPart("probNum", cbString);
        mpEntity.addPart("file", cbFile);

        httppost.setEntity(mpEntity);


        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println(httppost.getEntity().getContentType());
        HttpResponse response = null;
        
        try {
        	 message = "\nUploading File\n";
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();


            if (resEntity != null) {
                System.out.println(EntityUtils.toString(resEntity));
            }
            if (resEntity != null) {
                resEntity.consumeContent();
            }

            httpclient.getConnectionManager().shutdown();
        } catch (IOException ex) {
        	 message = "\nFile NOT submitted...\n";
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }**/
 /**       if (response != null) {
            int index = 0;
            for (Header h : response.getAllHeaders()) {
                if (h.getName().toLowerCase().contains("error")) {
                    isSubmited = false;
                    total_result = "----------";
                    message ="\nError on submitted file.\n";
                    message =message + "\n" + h.getValue() + "\n";
                    break;
                }
                if (h.getName().toLowerCase().contains("result")) {
                    //JOptionPane.showMessageDialog(null, total_result);
                    total_result = total_result.replaceFirst("-", h.getValue());
                    index++;
                }
                //JOptionPane.showMessageDialog(null, h.getName() + " : " + h.getValue());
            }
            //JOptionPane.showMessageDialog(null, total_result);
            if (isSubmited) {
                if (total_result.equals("----------")) {
                	message = "\nCompilation error or your file does not match the format...\n";
                } else {
                	message = "\nFile submitted...\n";
                }
            } else {
            	message = "\nFile Not submitted...\n";
            }
        }

       
    } catch (UnsupportedEncodingException ex) {
    	message = "\nFile NOT submitted...\n";
        JOptionPane.showMessageDialog(null, ex.getMessage() + "ONE");
        Logger.getLogger(VCompiler.class.getName()).log(Level.SEVERE, null, ex);
    }**/
//   }
//} else {
//    print_to_console("\nFile NOT submitted...\n");
// }

//}
//    return true;		
		
		
		
		return true;
	}
	 public boolean analiseVHD(String path, String fileName) {

	        boolean buildSucess = true;

	        String os = System.getProperty("os.name").toLowerCase();
	        if (os.toLowerCase().contains("linux") || os.toLowerCase().contains("unix")) {
	            buildSucess = run(path, "ghdl", "-a", "-fexplicit", "--ieee=synopsys", fileName);
	        } else if (os.contains("win")) {
	            buildSucess = run(path, "ghdl", "-a", "-fexplicit", "--ieee=synopsys", fileName);
	        } else if (os.contains("mac")) {
	            //print_to_console("Sorry, MAC not cooperating with us. He likes to be solo :-(\n");
	            //return false;
	            buildSucess = run(path, "/usr/local/bin/ghdl", "-a", "-fexplicit", "--ieee=synopsys", fileName);
	        }
	        if (buildSucess) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	    private String getEntityName(String file) {
	        String entityName = null;
	        String temp_string = "";
	        int temp_int;
	        Scanner input = new Scanner(file);
	        try {
	           // File aFile = new File(fileName);

	           // BufferedReader input = new BufferedReader(new FileReader(aFile));
	            boolean chkEntity = false;
	            String line = null; // not declared within while loop
	            //try {
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
	                                entityName = line.trim().substring(temp_int + 6).trim();
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

	                return entityName;

	            } finally {
	                input.close();
	            }
	        
	        //} catch (IOException e) {
	       //     return null;
	      // }
	    }
	//------------------------------------------------------------------------------------------------------------------------//
	//function to help plugin to run another PC program using command line and parameters passed in

	    private boolean run(String path, String... command) {
	        boolean bln = true;
	        try {
	            ProcessBuilder pb = new ProcessBuilder(command);
	            pb.directory(new File(path));
	            pb.redirectErrorStream(true);
	            Process p = pb.start();
	            p.waitFor();
	            InputStream is = p.getInputStream();
	            //JOptionPane.showMessageDialog(null, pb.directory());
	            //JOptionPane.showMessageDialog(null, "\nCommand " + Arrays.asList(command) + " reported");
	            System.out.println("\nCommand " + Arrays.asList(command) + " reported");

	           // DataInputStream ls_in = new DataInputStream(p.getInputStream());
	            //DataInputStream ls_ee = new DataInputStream(p.getErrorStream());
	           // String content;
	            //while ((content = ls_in.readLine()) != null) {
	                ///OUTPUT
	                //JOptionPane.showMessageDialog(null, content + "\n");
	            	//System.out.println("\nContent " + content + " reported successful");
	              //  print_to_console(content + "\n");
	            //}
	            //while ((content = ls_ee.readLine()) != null) {
	               // bln = false;
	                ///OUTPUT FOR ERRORS
	                //JOptionPane.showMessageDialog(null, content + "\n");
	            	//System.out.println("\nContent " + content + " reported fail");
	                //print_to_console(content + "\n");
	            //}

	            int b;
	            while ((b = is.read()) >= 0) {
	                System.out.write(b);
	            }
	            is.close();
	            p.destroy();
	        } catch (InterruptedException ex) {
	            JOptionPane.showMessageDialog(null, "\nCommand " + Arrays.asList(command) + " reported " + ex.getMessage());
	            bln = false;
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "\nCommand " + Arrays.asList(command) + " reported " + e.getMessage());
	            //System.err.println("\nCommand " + Arrays.asList(command) + " reported " + e);
	            bln = false;
	        }
	        return bln;
	    }
}
