package com.lbv3.backend_.Controller;

import com.lbv3.backend_.dao.PersonRepository;
import com.lbv3.backend_.model.Personen;
import com.lbv3.backend_.model.RegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hier werden die Request bearbeitet
 * @author Enma Ronquillo, Lara Akgün
 *
 * @version 1.0
 */
@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Autowired
    private PersonRepository personRepository;

    /**
     * User wird in DB gesucht und die mitgegebene Daten werden abgeglichen
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/blog")
    public String blog(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
        logger.info("Request /blog");
        Personen p = null;
        try {
            logger.info("Person in DB gesucht");
            p = personRepository.findByUsername(username);
        } catch (Exception e){
            logger.info("Keine Person mit dem Username: " + p.getUsername());
            e.printStackTrace();
            return "0";
        }

        String pass = hashPassword(password);
        if(pass.equals(p.getPassword())){
            logger.info("Passwörter stimmen überein");
            return "1"; // Passwörter stimmen überein
        }else{
            logger.error("Passwörter stimmen nicht überein");
            return "0"; // Passwörter stimmen nicht überein
        }

    }


    /**
     * Speichert der neue User in der DB ein
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/register",  method= RequestMethod.POST)
    public String register(@RequestBody RegisterDTO body) {
        logger.info("Request /register");
        if (!isNotNullAndEmpty(body.getUsername())) {
            return "Username is empty";
        }
        if(personRepository.findByUsername(body.getUsername()) != null){
            logger.error("Username ist vergeben");
            return "Username ist vergeben";
        }
        if(body.getPassword() == null || body.getPassword().length() < 10){
            logger.warn("Passwort zu kurz");
            return "Passwort zu kurz";
        }
        if (!isNotNullAndEmpty(body.getGender())) {
            return "Gender is empty";
        }
        if (!isNotNullAndEmpty(body.getState())) {
            return "State is empty";
        }
        String password = hashPassword(body.getPassword());
        Personen person = new Personen();
        person.setUsername(body.getUsername());
        person.setPassword(password);
        System.out.println("pass " + password);
        person.setAge(body.getAge());
        person.setGender(body.getGender());
        person.setState(body.getState());

       try{
           logger.info(person.getUsername() + " wird in DB gespeichert");
           personRepository.save(person);
       }catch (Exception e){
           String txt = "Fehler beim speichern von: " + person.getUsername();
           logger.error(txt);
           e.printStackTrace();
           return txt; // 0 Heisst fehler passiert
       }
        return "1"; // Methode erfolgreich ausgeführt
    }

    /**
     * Methode: Passwort wird mit SHA-256 verschlüsselt
     * @param pass
     * @return
     */
    public String hashPassword(String pass) {

        String generatedPassword = null;
        try {
            logger.info("Password wird verschlüsselt");
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error("Fehler beim der Verschlüsselung");
            e.printStackTrace();
        }

        return generatedPassword;
    }

    private boolean isNotNullAndEmpty(String value){
        if (value != null && !value.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }
}
