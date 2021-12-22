package it.unito.user_service.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import it.unito.user_service.entity.Address;
import it.unito.user_service.entity.Role;
import it.unito.user_service.entity.User;
import it.unito.user_service.enums.RoleNumber;
import it.unito.user_service.payload.request.LoginRequest;
import it.unito.user_service.payload.request.SignupRequest;
import it.unito.user_service.payload.response.JwtResponse;
import it.unito.user_service.payload.response.MessageResponse;
import it.unito.user_service.repository.AddressRepository;
import it.unito.user_service.repository.RoleRepository;
import it.unito.user_service.repository.UserRepository;
import it.unito.user_service.security.jwt.JwtUtils;
import it.unito.user_service.security.services.UserDetailsImpl;
import it.unito.user_service.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	/*
	@GetMapping("/user/addresses")
	public ResponseEntity<?> currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long user_id = userDetails.getId();
		Optional<User> user_opt = userRepository.findById(user_id);

		if(user_opt.isPresent()) {
			User user = user_opt.get();

			List<Address> a =  addressRepository.findByUser(user);
			if(!a.isEmpty()) return ResponseEntity.ok(a.get(0));
			else return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		else return new ResponseEntity(HttpStatus.NOT_FOUND);
	}*/


	private RabbitMQSender rabbitMqSender;

	private String message = "Message sent successfully";

	@Autowired
	public AuthController(RabbitMQSender rabbitMqSender) {
		this.rabbitMqSender = rabbitMqSender;
	}

	@PostMapping("/user")
	public String publishUserDetails(@RequestBody it.unito.user_service.messaging.User user) {
		rabbitMqSender.send(user);
		return message;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 userDetails.getName(),
												 userDetails.getSurname(),
												 userDetails.getPhone(),
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getName(),
							 signUpRequest.getSurname(),
							 signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 signUpRequest.getPhone(),
							 encoder.encode(signUpRequest.getPassword()));



		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleNumber.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);

			Role adminRole = roleRepository.findByName(RoleNumber.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(adminRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleNumber.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(RoleNumber.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleNumber.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}





		user.setRoles(roles);

		User u  = userRepository.save(user);

		Set<Address> addresses = signUpRequest.getAddresses(); //aggiunta lista di indirizzi

		System.out.println(addresses);
		for(Address a: addresses){
			a.setUser(u);
			addressRepository.save(a);
		}

		user.setAddresses(addresses);



		//send user to catalo service
		it.unito.user_service.messaging.User user_message = new it.unito.user_service.messaging.User(u.getId(),u.getName(),u.getSurname());
		rabbitMqSender.send(user_message);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!" + "\n" + message));
	}
}
