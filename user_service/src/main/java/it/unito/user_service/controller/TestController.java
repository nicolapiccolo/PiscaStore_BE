package it.unito.user_service.controller;

import it.unito.user_service.messaging.User;
import it.unito.user_service.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class TestController {

	private RabbitMQSender rabbitMqSender;

	private String message = "Message sent successfully";

	@Autowired
	public TestController(RabbitMQSender rabbitMqSender) {
		this.rabbitMqSender = rabbitMqSender;
	}

	@PostMapping("/user")
	public String publishUserDetails(@RequestBody User user) {
		rabbitMqSender.send(user);
		return message;
	}

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/response")
	public ResponseEntity<?> response(){
		return new ResponseEntity("Product saved ", HttpStatus.OK);
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
