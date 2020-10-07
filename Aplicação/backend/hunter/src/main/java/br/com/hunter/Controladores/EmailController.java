package br.com.hunter.Controladores;

import br.com.hunter.Modelos.Gamer;
import br.com.hunter.Repositorios.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
            Gamer gamerUnico = gamerRepository.findFistByEmail(email);
            sendEmail(email,gamerUnico.getSenha());

            return ResponseEntity.ok("email enviado");

        } else {
            return ResponseEntity.noContent().build();
        }




    }


//        public void sendEmail1(String email, String senha) {
//
//            String to = email;//change accordingly
//            String from = "hunter.compani@gmail.com";//change accordingly
//
//            //Get the session object
//            Properties properties = new Properties();
//            properties.put("mail.smtp.host", "smtp.gmail.com");
//            properties.put("mail.smtp.socketFactory.port", "587");
//            properties.put("mail.smtp.socketFactory.class",
//                    "javax.net.ssl.SSLSocketFactory");
//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.port", "587");
//            properties.put("mail.smtp.starttls.enable", "true");
//            properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//
//            Session session = Session.getDefaultInstance(properties,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication("hunter.compania@gmail.com",
//                                    "#Gfgrupo4");
//                        }
//                    });
//
//            session.setDebug(true);
//
//            //compose the message
//            try {
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress(from));
//                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//                message.setSubject("Enviando email de recuperação de senha");
//                message.setText("Olá " + email + ", este é um email de recuperação de senha"
//                        + "\n sua senha é: " + senha);
//
//                // Send message
//                Transport.send(message);
//                System.out.println("Mensagem enviada com sucesso....");
//
//            } catch (MessagingException mex) {
//                mex.printStackTrace();
//            }
//        }
}
