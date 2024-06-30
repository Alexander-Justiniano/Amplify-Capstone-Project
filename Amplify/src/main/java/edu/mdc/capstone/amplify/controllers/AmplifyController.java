package edu.mdc.capstone.amplify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mdc.capstone.amplify.models.LoginUser;
import edu.mdc.capstone.amplify.models.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class AmplifyController {
	
	
//	*************************************************************** GET Requests ***************************************************************

		@GetMapping("/")
		public String index(Model model) {		//Need to address naming conventions for readability
			//Pass in the empty "User" and "LoginUser" objects to the model for later use
			model.addAttribute("newUser", new User());
			model.addAttribute("newLogin", new LoginUser());  
			return "login.jsp";
		}
		
		@GetMapping("/register")
		public String register(Model model) {
			model.addAttribute("newUser", new User());
			model.addAttribute("newLogin", new LoginUser());
			return "register.jsp";
		}
		
		@GetMapping("/dashboard")
		public String home() {
			return "index.jsp"; //Need to address naming conventions for readability
		}
		
		
		
		
//		*************************************************************** POST Requests ***************************************************************
		
		
		@PostMapping("/register")
	    public String register(@Valid @ModelAttribute("newUser") User newUser, 
	            BindingResult result, Model model, HttpSession session) {

//			Make call to the user service to register the new user
//	    	Example:
//			userServ.register(newUser, result);
	        
	        if(result.hasErrors()) {
	        	model.addAttribute("newLogin", new LoginUser());
	            return "register.jsp";
	        }
	        
	        session.setAttribute("user_id", newUser.getId());
	        session.setAttribute("userName", newUser.getUserName());
	    
	        return "redirect:/dashboard";
	    }    
		
		
		 @PostMapping("/login")
		    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
		            BindingResult result, Model model, HttpSession session) {
		    	
//				Make call to the user service to sign in the new user
//		    	Example:
//		    	User loggedIn = userServ.login(newLogin, result);		
		    
		        if(result.hasErrors()) {
		            model.addAttribute("newUser", new User());
		            return "login.jsp";
		        }
		    
//		        Add the ID and the Username of the logged in user to session for later use
//		        session.setAttribute("user_id", loggedIn.getId());
//		        session.setAttribute("userName", loggedIn.getUserName());
		    
		        return "redirect:/dashboard";
		    }
}
