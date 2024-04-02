package edu.mdc.capstone.amplify.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.mdc.capstone.amplify.models.LoginUser;
import edu.mdc.capstone.amplify.models.Tracks;
import edu.mdc.capstone.amplify.models.User;
import edu.mdc.capstone.amplify.repositories.TracksRepository;
import edu.mdc.capstone.amplify.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class AmplifyController {
	
	@Autowired
	private UserService userServ;
	
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

			userServ.register(newUser, result);
	        
	        if(result.hasErrors()) {
	        	model.addAttribute("newLogin", new LoginUser());
	            return "register.jsp";
	        }
	        
	        session.setAttribute("user_id", newUser.getId());
	        session.setAttribute("userName", newUser.getuserName());
	    
	        return "redirect:/dashboard";
	    }    
		
		
		 @PostMapping("/login")
		    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
		            BindingResult result, Model model, HttpSession session) {
		    	
//				Make call to the user service to sign in the new user

		    	User loggedIn = userServ.login(newLogin, result);		
		    
		        if(result.hasErrors()) {
		            model.addAttribute("newUser", new User());
		            return "login.jsp";
		        }
		    
//		        Add the ID and the userName of the logged in user to session for later use
		        session.setAttribute("user_id", loggedIn.getId());
		        session.setAttribute("userName", loggedIn.getuserName());
		    
		        return "redirect:/dashboard";
		    }
		 
		 @Controller
		 @RequestMapping("/audio")
		 public class TracksController {

			    @Autowired
			    private TracksRepository tracksRepository;

			    @GetMapping
			    public String getTracks(Model model) {
			        model.addAttribute("tracks", tracksRepository.findAll());
			        return "tracks.jsp";
			    }

			    @PostMapping("/upload")
			    public ResponseEntity<String> uploadAudio(@RequestParam("file") MultipartFile file) {
			        try {
			            Tracks audioFile = new Tracks();
			            audioFile.setTitle(file.getOriginalFilename());
			            audioFile.setAudioData(file.getBytes());
			            tracksRepository.save(audioFile);
			            return ResponseEntity.status(HttpStatus.OK).body("Audio file uploaded successfully.");
			        } catch (IOException e) {
			            e.printStackTrace();
			            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload audio file.");
			        }
			    }

			    @GetMapping("/stream/{id}")
			    public ResponseEntity<byte[]> streamAudio(@PathVariable Long id) {
			        Tracks audioFile = tracksRepository.findById(id).orElse(null);
			        if (audioFile != null) {
			            return ResponseEntity.ok()
			                    .header("Content-Type", "audio/mp3") // Adjust content type based on the audio format
			                    .body(audioFile.getAudioData());
			        } else {
			            return ResponseEntity.notFound().build();
			        }
			    }

			    @DeleteMapping("/{id}")
			    public ResponseEntity<String> deleteAudio(@PathVariable Long id) {
			        Tracks audioFile = tracksRepository.findById(id).orElse(null);
			        if (audioFile != null) {
			            tracksRepository.delete(audioFile);
			            return ResponseEntity.ok().body("Audio file deleted successfully.");
			        } else {
			            return ResponseEntity.notFound().build();
			        }
			    }
			}
}
