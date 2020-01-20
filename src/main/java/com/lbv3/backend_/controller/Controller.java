package com.lbv3.backend_.controller;

import com.lbv3.backend_.dao.PersonRepository;
import com.lbv3.backend_.model.Person;
import com.lbv3.backend_.model.RegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Handles response and requests from frontend
 * @author Lara Akgün
 * @author Enma Ronquillo
 * @version 08.11.2019
 */

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Autowired
    private PersonRepository personRepository;

    /**
     * 
     * @return Json string
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/productList", method = RequestMethod.POST)
    public Json productList() {
        return Json(new {id="1", product="product", price="11.50", description="description"});
    }

    /**
     * Saves the new user in the DB
     * @return error message or 1 -> if all fine
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody RegisterDTO body) {
        logger.info("Request /register");
        //Makes all the backend validation for each field
        if (!isNotNullAndEmpty(body.getUsername())) {
            return "Username is empty";
        }
        if (personRepository.findByUsername(body.getUsername()) != null) {
            logger.error("Username is taken");
            return "Username is taken";
        }
        if (body.getPassword() == null || body.getPassword().length() < 10) {
            logger.warn("Password too short");
            return "Password too short";
        }
        if (!isNotNullAndEmpty(body.getGender())) {
            return "Gender is empty";
        }
        if (!isNotNullAndEmpty(body.getState())) {
            return "State is empty";
        }
        if (body.getAge() <0){
            return "Age needs to be more than 0";
        }

        //Writes user in DB
        String password = hashPassword(body.getPassword());
        Person person = new Person();
        person.setUsername(body.getUsername().trim());
        person.setPassword(password);
        System.out.println("pass " + password);
        person.setAge(body.getAge());
        person.setGender(body.getGender());
        person.setState(body.getState());

        try {
            logger.info(person.getUsername() + " is stored in DB");
            personRepository.save(person);
        } catch (Exception e) {
            String txt = "Error saving: " + person.getUsername();
            logger.error(txt);
            e.printStackTrace();
            return txt;
        }
        return "1"; // Method executed successfully
    }

    /**
     * Login
     * @return error message or 1 -> if all fine
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody RegisterDTO body) {
        if (!isNotNullAndEmpty(body.getUsername())) {
            logger.info("Username is empty");
            return "Username is empty";
        }
        Person user = personRepository.findByUsername(body.getUsername());
        //Compare the password from the DB with the one from the user input
        String hashedPassword = hashPassword(body.getPassword());

        if (user != null && hashedPassword.equals(user.getPassword())) {
            logger.info("User logged in");
            return "1";
        } else {
            logger.info("You have entered an invalid username or password");
            return "You have entered an invalid username or password";
        }
    }

    /**
     * Method: Password is encrypted with SHA-256
     * @param password
     * @return encrypted password
     */
    private String hashPassword(String password) {

        String generatedPassword = null;
        try {
            logger.info("Password is encrypted");
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error in encryption");
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Method: Password is encrypted with SHA-256
     * @param value
     * @return boolean -> depending if value is empty and null
     */
    private boolean isNotNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
