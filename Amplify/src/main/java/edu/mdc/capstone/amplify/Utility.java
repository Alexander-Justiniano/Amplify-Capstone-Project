package edu.mdc.capstone.amplify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
public class Utility {

		public static String getSiteURL(HttpServletRequest request) {
			String siteURL = request.getRequestURL().toString();
			return siteURL.replace(request.getServletPath(),"");
		}
	

}
