package b.rcom.hunter.Hunter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GamerContoller {

    @Autowired
    private GamerRepository gr;

    @GetMapping("/usuario/{email}")
    private Gamer getUsuario(@PathVariable("email") String email){
       Gamer gamer = gr.findByEmail(email);

        return gamer;
    }


}
