package com.nus.vCompiler;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private String codeVHDL ="library IEEE;\nuse IEEE.STD_LOGIC_1164.ALL;\nentity combcct is\nport( \n\tA : in std_logic_vector (3 downto 0); \n\tZ : out std_logic \n\t);\nend combcct;\n\narchitecture beh of combcct is\nbegin\nprocess ( A )\nbegin\n\tcase (A) is\n\t\twhen \"0010\" => Z <='0';\n\t\twhen \"0011\" => Z <='0';\n\t\twhen \"0101\" => Z <='0';\n\t\twhen \"0111\" => Z <='0';\n\t\twhen \"1011\" => Z <='0';\n\t\twhen \"1101\" => Z <='0';\n\t\twhen others => Z <='1';\n\tend case;\nend process;\nend beh;";
	private String codeTB = "--This editor is for test bench";
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
    protected boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }
		return false;
        }

       	
	@RequestMapping(value="/editor", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {


		
	      
		model.addAttribute("VHDLmessage", codeVHDL);
        model.addAttribute("TBmessage", codeTB);

        
 
        
        //Spring uses InternalResourceViewResolver and return back index.jsp
        return "editorVHDL";
 
	}
	@RequestMapping(value="/practice", method = RequestMethod.GET)
	public String Practice(ModelMap model) {
 
		
		return "practice";
 
	} 	
	@RequestMapping(value="/SimWave", method = RequestMethod.GET)
	public String SimWave(ModelMap model) {
 
		
		return "SimWave";
 
	} 
	@RequestMapping(value="/SimWave", method = RequestMethod.POST)
	public String SimWavePost(@ModelAttribute("code") String code,@ModelAttribute("testBench") String testBench,
            BindingResult result,
            Model model){ 
		logger.info("{}", testBench);
	      // String vhdlResult;
	      	
	      Compile compile = new Compile();
	      logger.info("\nSuccess 1\n");
	        if (compile.build("2002",code)) {
	    	   logger.info("\nSuccess 2\n");
	    	   codeVHDL =code;
	    	   //return"SimWave";
	    	   if(compile.Simulate("1000","2002",testBench) )
	    	   {
	    		   logger.info("\nSuccess 2\n");
	    		   codeTB =testBench;	
	    		   logger.info("\nSuccess\n");	   		
	    		   model.addAttribute("VCDmessage", compile.getVCDMessage());
	    		   model.addAttribute("VCDIndex", compile.getVCDIndex());
	    		   model.addAttribute("VCDEnd", compile.getVcdEnd());
	    		   logger.info(compile.getVCDMessage());
	    		   return"SimWave";

	    	   }
	       } else {
		       	logger.info("\fail after compile\n");
	         	 return"SimWave";
	       }
	       logger.info("\fail\n");
	       return"SimWave";
	} 	

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		
		//	System.out.print(auth.getAuthorities()=="");
		/**if (hasRole("ROLE_ADMIN"))
		{
			//model.addAttribute("VHDLmessage", codeVHDL);
	        //model.addAttribute("TBmessage", codeTB);
	        return "practice";			
		}
		else
		{
			model.addAttribute("VHDLmessage", codeVHDL);
		    model.addAttribute("TBmessage", codeTB);
		    return printWelcome(model);
		}**/
		
		codeVHDL ="library IEEE;\nuse IEEE.STD_LOGIC_1164.ALL;\nentity combcct is\nport( \n\tA : in std_logic_vector (3 downto 0); \n\tZ : out std_logic \n\t);\nend combcct;\n\narchitecture beh of combcct is\nbegin\nprocess ( A )\nbegin\n\tcase (A) is\n\t\twhen \"0010\" => Z <='0';\n\t\twhen \"0011\" => Z <='0';\n\t\twhen \"0101\" => Z <='0';\n\t\twhen \"0111\" => Z <='0';\n\t\twhen \"1011\" => Z <='0';\n\t\twhen \"1101\" => Z <='0';\n\t\twhen others => Z <='1';\n\tend case;\nend process;\nend beh;";
		codeTB = "--This editor is for test bench";	
		return "/login";
 
	}
 
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		codeVHDL ="library IEEE;\nuse IEEE.STD_LOGIC_1164.ALL;\nentity combcct is\nport( \n\tA : in std_logic_vector (3 downto 0); \n\tZ : out std_logic \n\t);\nend combcct;\n\narchitecture beh of combcct is\nbegin\nprocess ( A )\nbegin\n\tcase (A) is\n\t\twhen \"0010\" => Z <='0';\n\t\twhen \"0011\" => Z <='0';\n\t\twhen \"0101\" => Z <='0';\n\t\twhen \"0111\" => Z <='0';\n\t\twhen \"1011\" => Z <='0';\n\t\twhen \"1101\" => Z <='0';\n\t\twhen others => Z <='1';\n\tend case;\nend process;\nend beh;";
		codeTB = "--This editor is for test bench";
		model.addAttribute("error", "true");
		return "index";
 
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		codeVHDL ="library IEEE;\nuse IEEE.STD_LOGIC_1164.ALL;\nentity combcct is\nport( \n\tA : in std_logic_vector (3 downto 0); \n\tZ : out std_logic \n\t);\nend combcct;\n\narchitecture beh of combcct is\nbegin\nprocess ( A )\nbegin\n\tcase (A) is\n\t\twhen \"0010\" => Z <='0';\n\t\twhen \"0011\" => Z <='0';\n\t\twhen \"0101\" => Z <='0';\n\t\twhen \"0111\" => Z <='0';\n\t\twhen \"1011\" => Z <='0';\n\t\twhen \"1101\" => Z <='0';\n\t\twhen others => Z <='1';\n\tend case;\nend process;\nend beh;";
		codeTB = "--This editor is for test bench";
		return "index";
 
	}
    @RequestMapping(value="/showMessage", method = RequestMethod.GET)
    public String showMessage(ModelMap model) {

            model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");

            //Spring uses InternalResourceViewResolver and return back index.jsp
            return "showMessage";
    }
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String createTestBench(ModelMap model,HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ServletContext sc = session.getServletContext();
        String x = sc.getRealPath("/");
        System.out.println(x);    	
    		//model.addAttribute("VHDLmessage", "library IEEE;\nuse IEEE.STD_LOGIC_1164.ALL;\nentity combcct is\nport( \n\tA : in std_logic_vector (3 downto 0); \n\tZ : out std_logic \n\t);\nend combcct;\n\narchitecture beh of combcct is\nbegin\nprocess ( A )\nbegin\n\tcase (A) is\n\t\twhen \"0010\" => Z <='0';\n\t\twhen \"0011\" => Z <='0';\n\t\twhen \"0101\" => Z <='0';\n\t\twhen \"0111\" => Z <='0';\n\t\twhen \"1011\" => Z <='0';\n\t\twhen \"1101\" => Z <='0';\n\t\twhen others => Z <='1';\n\tend case;\nend process;\nend beh;");
            //model.addAttribute("TBmessage", "--This editor is for test bench");

            //Spring uses InternalResourceViewResolver and return back index.jsp
		    //String name = principal.getName(); //get logged in username
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		String name;
    		if (auth.getName()!= "anonymousUser")
    		{
    		name = auth.getName(); //get logged in username	
    		System.out.print(name);
    		}
    		else
    		{
    			name = "";
    		}
		    model.addAttribute("username", name);    	
            return "index";
    } 


    
    @RequestMapping(value ="/editor", method = RequestMethod.POST)
    public String result(@ModelAttribute("code")String code,
                     BindingResult result,
                     Model model){
   // logger.info("{}", code);
    
       String vhdlResult;

       Compile compile = new Compile();
       if (compile.build("2002",code)) {
       	 //model.addAttribute("TBmessage", compile.getMessage());
    	   codeVHDL =code;
       
	       // if(result.hasErrors()){
	       //     model.addAttribute("message","Error");
	       //     return "editorVHDL";
	
	       // }else{
	        TestBench tb = new TestBench();
	    //(String outputFileName,String file)
	        if (tb.createTestBench(code)) {
	   //     buffer = jEdit.openFile(view, path + tbFileName + ".vhd");

	        	vhdlResult = tb.getTestBench();
	        	codeTB =vhdlResult;	
	        } else {
	   //    print_to_console("\nfail to create the test bench\n");
	        	vhdlResult = "fail";
	            logger.info("\nfail to create the test bench\n");
	        }
				model.addAttribute("VHDLmessage", code);        
	           	model.addAttribute("TBmessage",vhdlResult);
	           	return"editorVHDL";
       } else {
         	 model.addAttribute("TBmessage", "Fail");
         	 return"editorVHDL";
         }

       // }

    }    
    @RequestMapping(value ="/submit", method = RequestMethod.POST)
    public String submit(@ModelAttribute("code")String code, @ModelAttribute("problem")String problem,
                     BindingResult result,
                     Model model){
   // logger.info("{}", code);
    
     //  String vhdlResult;

       Compile compile = new Compile();
       if (compile.submit(problem,code)) {
    	   codeVHDL =code;
	       // TestBench tb = new TestBench();
	      //  if (tb.createTestBench(code)) {
	      //  	vhdlResult = tb.getTestBench();
	      //  	codeTB =vhdlResult;	
	      //  } else {
	      //  	vhdlResult = "fail";
	     //       logger.info("\nfail to create the test bench\n");
	     //   }
		//	model.addAttribute("VHDLmessage", "Done");        
	      //     	model.addAttribute("TBmessage",vhdlResult);
			model.addAttribute("VHDLmessage", codeVHDL);        
           	model.addAttribute("TBmessage",codeTB);    	   
	           	return"editorVHDL";
       } else {
       //  	 model.addAttribute("TBmessage", "Fail");
   		model.addAttribute("VHDLmessage", codeVHDL);        
       	model.addAttribute("TBmessage",codeTB);      	   
         	 return"editorVHDL";
         }
        
    }        
}
