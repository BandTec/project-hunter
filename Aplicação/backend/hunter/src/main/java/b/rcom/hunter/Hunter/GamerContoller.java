package b.rcom.hunter.Hunter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GamerContoller {

    @Autowired
    private GamerRepository gr;

    @GetMapping("/usuario/{email}/{senha}")
    private @ResponseBody String consultarUsuario(
                             @PathVariable("email") String email,
                             @PathVariable("senha") String senha){

        //gr.findAll();
        return "Login Autorizado !";
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    String adicionarUsuario(
            @RequestParam String login,
            @RequestParam String senha
    ) {
        Gamer gamer = new Gamer();
        gamer.setEmail(login);
        gamer.setSenha(senha);
        gr.save(gamer);

        return "Usuário adicionado!";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deletarUsuario(
            @RequestParam Integer id
    ) {
        gr.deleteById(id);
        return "Usuário deletado!";
    }

    @GetMapping(path = "/usuarios")
    public @ResponseBody Iterable<Gamer> listarUsuarios() {
        return gr.findAll();
    }



}
