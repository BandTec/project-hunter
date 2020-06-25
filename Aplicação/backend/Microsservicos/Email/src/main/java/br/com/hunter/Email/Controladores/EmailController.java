package br.com.hunter.Email.Controladores;

import br.com.hunter.Email.Modelos.Gamer;
import br.com.hunter.Email.Repositorios.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://hunterproject.herokuapp.com")
public class EmailController {

    @Autowired
    private GamerRepository gamerRepository;

    @Autowired
    private JavaMailSender javaMailSender;


    void sendEmail(String email, String senha) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Enviando email de recuperação de senha");
        msg.setText("Olá " + email + ", este é um email de recuperação de senha" + "\n sua senha é: " + senha);

        javaMailSender.send(msg);

    }

    @GetMapping("/{email}")
    private ResponseEntity mandarEmail(@PathVariable("email") String email) {
        List<Gamer> gamer = gamerRepository.findByEmail(email);

        if(!gamer.isEmpty()){
            Gamer gamerUnico = gamerRepository.findOneByEmail(email);
            sendEmail(email,gamerUnico.getSenha());

            return ResponseEntity.ok("email enviado");

        } else {
            return ResponseEntity.noContent().build();
        }

    }

}

