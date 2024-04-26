package edu.mdc.capstone.amplify.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.mdc.capstone.amplify.models.AudioFile;
import edu.mdc.capstone.amplify.repositories.AudioFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
//@RequestMapping("/")
public class AmplifyController {

	@Autowired
	private UserService userServ;

//	*************************************************************** GET Requests ***************************************************************

		/*@GetMapping("/")
		public String index(Model model) {		//Need to address naming conventions for readability
			//Pass in the empty "User" and "LoginUser" objects to the model for later use
			model.addAttribute("newUser", new User());
			model.addAttribute("newLogin", new LoginUser());
			return "login";
		}*/

		@GetMapping("/")
		public String index(Model model) {		//Need to address naming conventions for readability
			//Pass in the empty "User" and "LoginUser" objects to the model for later use
			model.addAttribute("newUser", new User());
			model.addAttribute("newLogin", new LoginUser());
			return "loginTwo";
		}

		@GetMapping("/register")
		public String register(Model model) {
			System.out.println("loading registration page.....");
			model.addAttribute("newUser", new User());
			model.addAttribute("newLogin", new LoginUser());
			return "register";
		}

		@GetMapping("/dashboard")
		public String home() {
			return "index"; //Need to address naming conventions for readability
		}




//		*************************************************************** POST Requests ***************************************************************


		@PostMapping("/register")
	    public String register(@Valid @ModelAttribute("newUser") User newUser,
	            BindingResult result, Model model, HttpSession session) {
//			Make call to the user service to register the new user
			userServ.register(newUser, result);

	        if(result.hasErrors()) {
	        	model.addAttribute("newLogin", new LoginUser());
	            //return "register.jsp";
	            return "register";
	        }

	        //session.setAttribute("user_id", newUser.getId());
	        //session.setAttribute("userName", newUser.getuserName());
	        //session.setAttribute("userName", newUser.getUserName());

	        //return "redirect:/dashboard";
			return "redirect:/"; //redirect to login page after registration
	    }

		@GetMapping("/logout")
		public String logout(HttpSession httpSession) {
			httpSession.invalidate();
			return "redirect:/";
		}


		/* @PostMapping("/login")
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
		        //session.setAttribute("userName", loggedIn.getuserName());
		        session.setAttribute("userName", loggedIn.getUserName());

		        return "redirect:/dashboard";
		    }*/


			@PostMapping("/login")
			public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
								BindingResult result, Model model, HttpSession session) {

				// Make call to the user service to sign in the new user

				User loggedIn = userServ.login(newLogin, result);

				if(result.hasErrors()) {
					model.addAttribute("newUser", new User());
					return "loginTwo";
				}

				session.setAttribute("user", loggedIn);

				//Add the ID and the userName of the logged in user to session for later use
				//session.setAttribute("user_id", loggedIn.getId());
				//session.setAttribute("userName", loggedIn.getuserName());
				//session.setAttribute("userName", loggedIn.getUserName());

				return "redirect:/dashboard";
				//return "redirect:/audio";
			}

		 @Controller
		 @RequestMapping("/audio")
		 public class TracksController {

			 @Autowired
			 private AudioFileRepository audioFileRepository;

			 @Value("${app.url}")
			 private String propertyValue;

			 @Value("${upload.directory}")
			 private String directory;

			 @Value("${host.upload.directory}")
			 private String hostUploadDirectory;

			 @GetMapping
			 public String home(Model model, HttpSession httpSession) {
				 User loggedInUser = (User) httpSession.getAttribute("user");
				 if(loggedInUser == null) {
					 return "redirect:/logout";
				 }
				 //List<AudioFile> audioFiles = audioFileRepository.findAll();
				 List<AudioFile> audioFiles = audioFileRepository.findByUser(loggedInUser);
				 model.addAttribute("audioFiles", audioFiles);
				 return "audio";
			 }

			 @PostMapping("/upload")
			 public String uploadFile(@RequestParam("file") MultipartFile file, HttpSession httpSession) {
				 User loggedInUser = (User) httpSession.getAttribute("user");
				 if(loggedInUser == null) {
					 return "redirect:/logout";
				 }

				 try {
					 String originalFileName = file.getOriginalFilename();
					 // Generate a unique filename with the current timestamp
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
					 String timestamp = dateFormat.format(new Date());
					 String uniqueFileName = timestamp + "_" + originalFileName;

					 String directory = "src/main/webapp/uploads/";
					 File uploadDirectory = new File(directory);

					 // Save the file to the specified directory
					 File uploadedFile = new File(uploadDirectory.getAbsolutePath() + File.separator + uniqueFileName);
					 file.transferTo(uploadedFile);

					 AudioFile audioFile = new AudioFile();
					 audioFile.setName(uniqueFileName);
					 audioFile.setFilePath("");
					 audioFile.setUser(loggedInUser);
					 audioFileRepository.save(audioFile);
				 } catch (IOException e) {
					 e.printStackTrace();
				 }

				 return "redirect:/audio";
			 }


/*			 @PostMapping("/upload")
			 public String uploadFile(@RequestParam("file") MultipartFile file) {
				 try {
					 String originalFileName = file.getOriginalFilename();
					 // Generate a unique filename with the current timestamp
					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
					 String timestamp = dateFormat.format(new Date());
					 String uniqueFileName = timestamp + "_" + originalFileName;

					 String filePath = directory + uniqueFileName;

					 file.transferTo(new File(filePath));

					 String fileUrl = hostUploadDirectory + uniqueFileName;

					 // Save the file URL to the database
					 AudioFile audioFile = new AudioFile();
					 audioFile.setName(uniqueFileName);
					 audioFile.setFilePath(fileUrl);
					 audioFileRepository.save(audioFile);
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
				 return "redirect:/audio";
			 }*/

			 @GetMapping("/{fileName:.+}")
			 public ResponseEntity<Resource> serveAudioFile(@PathVariable String fileName) {
				 try {
					 Path filePath = Paths.get(directory, fileName);
					 Resource resource = new FileSystemResource(filePath.toFile());

					 if (resource.exists() && resource.isReadable()) {
						 return ResponseEntity.ok()
								 .body(resource);
					 } else {
						 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
					 }
				 }  catch (Exception e) {
					 e.printStackTrace();
					 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				 }
			 }

			 @GetMapping("/delete/{id}")
			 public String deleteFile(@PathVariable Long id) throws IOException {
				 AudioFile audioFile = audioFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid file ID"));

				/* Path filePath = Paths.get(directory, audioFile.getName());
				 if (Files.deleteIfExists(filePath)) {
					 System.out.println("File deleted successfully!");
				 } else {
					 System.out.println("File not found or could not be deleted.");
				 }*/

				 String directory = "src/main/webapp/uploads/";
				 String fileNameToDelete = audioFile.getName();

				 // Construct the full path to the file
				 String filePath = directory + fileNameToDelete;

				 // Get a reference to the file
				 File fileToDelete = new File(filePath);

				 // Verify if the file exists and is actually a file
				 if (fileToDelete.exists() && fileToDelete.isFile()) {
					 // Attempt to delete the file
					 if (fileToDelete.delete()) {
						 System.out.println("Deleted file: " + fileNameToDelete);
					 } else {
						 System.err.println("Failed to delete file: " + fileNameToDelete);
					 }
				 }

				 //audioFileRepository.delete(audioFile);
				 audioFileRepository.deleteById(audioFile.getId());
				 return "redirect:/audio";
			 }

//			    @Autowired
//			    private TracksRepository tracksRepository;
//
//			    @GetMapping
//			    public String getTracks(Model model) {
//			        model.addAttribute("tracks", tracksRepository.findAll());
//			        return "tracks.jsp";
//			    }
//
//			    @PostMapping("/upload")
//			    public ResponseEntity<String> uploadAudio(@RequestParam("file") MultipartFile file) {
//			        try {
//			            Tracks audioFile = new Tracks();
//			            audioFile.setTitle(file.getOriginalFilename());
//			            audioFile.setAudioData(file.getBytes());
//			            tracksRepository.save(audioFile);
//			            return ResponseEntity.status(HttpStatus.OK).body("Audio file uploaded successfully.");
//			        } catch (IOException e) {
//			            e.printStackTrace();
//			            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload audio file.");
//			        }
//			    }
//
//			    @GetMapping("/stream/{id}")
//			    public ResponseEntity<byte[]> streamAudio(@PathVariable Long id) {
//			        Tracks audioFile = tracksRepository.findById(id).orElse(null);
//			        if (audioFile != null) {
//			            return ResponseEntity.ok()
//			                    .header("Content-Type", "audio/mp3") // Adjust content type based on the audio format
//			                    .body(audioFile.getAudioData());
//			        } else {
//			            return ResponseEntity.notFound().build();
//			        }
//			    }
//
//			    @DeleteMapping("/{id}")
//			    public ResponseEntity<String> deleteAudio(@PathVariable Long id) {
//			        Tracks audioFile = tracksRepository.findById(id).orElse(null);
//			        if (audioFile != null) {
//			            tracksRepository.delete(audioFile);
//			            return ResponseEntity.ok().body("Audio file deleted successfully.");
//			        } else {
//			            return ResponseEntity.notFound().build();
//			        }
//			    }
			}
}
