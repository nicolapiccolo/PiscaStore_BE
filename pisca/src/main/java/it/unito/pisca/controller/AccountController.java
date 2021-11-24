package it.unito.pisca.controller;

import it.unito.pisca.entity.Address;
import it.unito.pisca.entity.User;
import it.unito.pisca.repository.AddressRepository;
import it.unito.pisca.repository.UserRepository;
import it.unito.pisca.security.services.UserDetailsImpl;
import it.unito.pisca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/address")
    public ResponseEntity<?> getAddress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long user_id = userDetails.getId();
        Optional<User> user_opt = userRepository.findById(user_id);

        if(user_opt.isPresent()) {
            User user = user_opt.get();

            List<Address> a =  addressRepository.findByUser(user);
            if(!a.isEmpty()) return ResponseEntity.ok(a.get(0));
            else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/username")
    public ResponseEntity<?> getName(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long user_id = userDetails.getId();
        return ResponseEntity.ok("User ID: " + user_id);
    }

    /*
      @PutMapping("/tutorials/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    if (tutorialData.isPresent()) {
      Tutorial _tutorial = tutorialData.get();
      _tutorial.setTitle(tutorial.getTitle());
      _tutorial.setDescription(tutorial.getDescription());
      _tutorial.setPublished(tutorial.isPublished());
      return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
     */

    @PutMapping("/update")
    public ResponseEntity<?> saveUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody User user){

        Optional<User> userData = userRepository.findById(userDetails.getId());

        if(userData.isPresent()){
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setSurname(user.getSurname());
            _user.setEmail(user.getEmail());
            _user.setPhone(user.getPhone());
            _user.setUsername(user.getUsername());

            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
