package com.lbv3.backend_.Controller;

import com.lbv3.backend_.dao.PersonRepository;
import com.lbv3.backend_.model.Personen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Autowired
    private PersonRepository personRepository;

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

        String pass = verschluesselung(password);
        if(pass.equals(p.getPassword())){
            logger.info("Passwörter stimmen überein");
            return "1"; // Passwörter stimmen überein
        }else{
            logger.error("Passwörter stimmen nicht überein");
            return "0"; // Passwörter stimmen nicht überein
        }

    }


    @RequestMapping(value = "/register",  method= RequestMethod.POST)
    public String register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "age") String age, @RequestParam(value = "gender") String gender, @RequestParam(value = "state") String state) {
        logger.info("Request /register");
       // DAO dao = new DAO();
        /** Todo username, password & gender validation */
        if(personRepository.findByUsername(username) != null){
            logger.error("Username ist vergeben");
            return "0";
        }
        if(password.length() < 10){
            logger.warn("Passwort zu kurz");
            return "0";
        }
        password = verschluesselung(password);
        Personen person = new Personen();
        person.setUsername(username);
        person.setPassword(password);
        System.out.println("pass " + password);
        person.setAge(Integer.parseInt(age));
        person.setGender(gender);
        person.setState(state);

       try{
           logger.info(person.getUsername() + " wird in DB gespeichert");
           personRepository.save(person);
       }catch (Exception e){
           logger.error("Fehler beim speichern von: " + person.getUsername());
           e.printStackTrace();
           return "0"; // 0 Heisst fehler passiert
       }
        return "1"; // Methode erfolgreich ausgeführt

    }

    public String verschluesselung(String pass) {

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
}
