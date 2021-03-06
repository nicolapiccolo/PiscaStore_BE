package it.unito.user_service.controller;

import it.unito.user_service.entity.Address;
import it.unito.user_service.entity.User;
import it.unito.user_service.payload.response.MessageResponse;
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
            System.out.println(a.get(0));
            if(!a.isEmpty()) return ResponseEntity.ok(a);
            else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable("id") Long id){
        Optional<Address> a_opt = addressRepository.findById(id);
        if(a_opt.isPresent()){
            Address address = a_opt.get();
            return new ResponseEntity(address,HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/address/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return new ResponseEntity("Deleted",HttpStatus.OK);
        }
        else return new ResponseEntity("Product not exists",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<?> setAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Address address){
        Optional<User> userData = userRepository.findById(userDetails.getId());
        User _user = userData.get();
        address.setUser(_user);
        addressRepository.save(address);
        return ResponseEntity.ok(new MessageResponse("Address registered successfully!"));
    }

    @GetMapping("/current")
    public ResponseEntity<?> getName(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long user_id = userDetails.getId();
        return new ResponseEntity(String.valueOf(user_id),HttpStatus.OK);
    }


    @GetMapping("/current/info")
    public ResponseEntity<User> getInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long user_id = userDetails.getId();
        System.out.println(userDetails.getPhone());
        User current = userRepository.getById(user_id);

        User u = new User(current.getName(),current.getSurname(),current.getUsername(),current.getEmail(),current.getPhone());

        System.out.println("USER; " + u);

        return new ResponseEntity(u,HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<?> saveUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody User user){

        Optional<User> userData = userRepository.findById(userDetails.getId());

        if(userData.isPresent()) {
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setSurname(user.getSurname());
            _user.setEmail(user.getEmail());
            _user.setPhone(user.getPhone());

            /*

            /*Set<Address> addresses =  _user.getAddresses();
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
            }*/





            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            //return new ResponseEntity<>("user updated", HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
