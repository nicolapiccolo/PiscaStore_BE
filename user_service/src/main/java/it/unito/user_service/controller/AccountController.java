package it.unito.user_service.controller;

import it.unito.user_service.entity.Address;
import it.unito.user_service.entity.User;
import it.unito.user_service.repository.AddressRepository;
import it.unito.user_service.repository.UserRepository;
import it.unito.user_service.security.services.UserDetailsImpl;
import it.unito.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            //_user.setUsername(user.getUsername());


            Set<Address> addresses =  _user.getAddresses();
            System.out.println("Is Empty? " + addresses.isEmpty()); //no address in registration

            if(addresses.isEmpty()){
                if(!user.getAddresses().isEmpty()) //address provided in user update
                {
                    //create new address linked to this user

                    Set<Address> reqAddresses = user.getAddresses();

                    System.out.println(reqAddresses.size());

                    for(Address a: reqAddresses){
                        a.setUser(_user);
                        addressRepository.save(a);
                    }

                    _user.setAddresses(reqAddresses);
                }
            }
            else{ //modify existing address
                if(!user.getAddresses().isEmpty()) //address provided in user update
                {
                    System.out.println("Modify existing address");

                    Set<Address> reqAddresses = user.getAddresses();
                    System.out.println(reqAddresses.size());


                    for (Address a : addresses) {
                        System.out.println("Id of existing address: " + a.getId());
                        Optional<Address> addressData = addressRepository.findById(a.getId());

                        if (addressData.isPresent()) {
                            Address n_add = reqAddresses.iterator().next();

                            Address _address = addressData.get();
                            _address.setStreet(n_add.getStreet());
                            _address.setCity(n_add.getCity());
                            _address.setCountry(n_add.getCountry());
                            _address.setZipCode(n_add.getZipCode());

                            addressRepository.save(_address);
                        } else return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);

                    }
                }
            }




            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            //return new ResponseEntity<>("user updated", HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
