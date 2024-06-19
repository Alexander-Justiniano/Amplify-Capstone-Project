package edu.mdc.capstone.amplify.controllers;

import edu.mdc.capstone.amplify.models.Song;
import edu.mdc.capstone.amplify.models.User;
import edu.mdc.capstone.amplify.repositories.SongRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/song")
    public String loadSongPage(Model model, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/logout";
        }
        model.addAttribute("songList", songRepository.findByUser(loggedInUser));
        model.addAttribute("song", new Song());
        return "song";
    }

    @PostMapping("/saveSong")
    public String saveSong(@ModelAttribute("song") Song song, @RequestParam("file") MultipartFile file, BindingResult bindingResult, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/logout";
        }

        if (bindingResult.hasErrors()) {
            return "song";
        }

        try {
            String originalFileName = file.getOriginalFilename();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            String uniqueFileName = timestamp + "_" + originalFileName;

            String directory = "src/main/webapp/uploads/";
            File uploadDirectory = new File(directory);

            // Save the file to the specified directory
            File uploadedFile = new File(uploadDirectory.getAbsolutePath() + File.separator + uniqueFileName);
            file.transferTo(uploadedFile);

            song.setFileName(uniqueFileName);
            song.setUser(loggedInUser);
            songRepository.save(song);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/song";
    }

    @PostMapping("/deleteSong/{id}")
    public String deleteSong(@PathVariable Long id, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/logout";
        }
        try {
            String directory = "src/main/webapp/uploads/";
            Song song = songRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid file ID"));
            String fileNameToDelete = song.getFileName();

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

            songRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/song";
    }

    @GetMapping("/allSongs")
    @ResponseBody
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @GetMapping("/playSong/{id}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> playSong(@PathVariable Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid song ID"));
        String directory = "src/main/webapp/uploads/";
        File audioFile = new File(directory + song.getFileName());

        if (audioFile.exists()) {
            FileSystemResource resource = new FileSystemResource(audioFile);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg"); // Assuming the files are MP3
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
