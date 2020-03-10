package b.rcom.hunter.Hunter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("gamer")
public class GamerContoller {

    Gamer gamer = new Gamer();
    ConnectURL dadosConexao = new ConnectURL();
    JdbcTemplate template = new JdbcTemplate(dadosConexao.getDataSource());


    @GetMapping(path = "/login/{email}/{senha}")
    private @ResponseBody
    String consultarUsuario(
            @PathVariable("email") String email,
            @PathVariable("senha") String senha) {


        List<Map<String, Object>> select = template.queryForList(
                "select * from gamer where email = ? and senha = ?", email, senha);


        if (!select.isEmpty()) {

            for (Map<String, Object> row : select) {
                email = (String) row.get("email");
                senha = (String) row.get("senha");
            }
            gamer.setEmail(email);
            gamer.setSenha(senha);
            gamer.setAutentitcado(true);


                return "Login Efetuado com sucesso";
            }else{
            gamer.setAutentitcado(false);
            return "Usuário Não Encontrado ou Não existente, " +
                    "\nVisite /cadastrar/{email/{senha}, substituindo os valores entre chaves pare efetuar o cadastro";
        }
    }

    @GetMapping(path = "/login")
    private @ResponseBody String statusLogin(){
        if (gamer.isAutenticado()){
            return "Usuário Online";
        }else{
            return "Login ainda não efetuado, " +
                    "\npara entrar visite /login/{email/{senha} substituindo os valores entre chaver para efetuar o login";
        }
    }


    @GetMapping(path = "/logoff")
    private @ResponseBody void logoff() {

        gamer.setAutentitcado(false);


    }

    @PostMapping(path = "/cadastrar/{email}/{senha}")
    public @ResponseBody
    String adicionarUsuario(
            @RequestParam String email,
            @RequestParam String senha
    ) {

        template.update(
                "insert into gamer(email, senha) values " +
                        "(?,?)", gamer.getEmail(), gamer.getSenha()
        );
        Gamer gamer = new Gamer(email, senha);
//        dadosConexao.cadastrarUsuario(gamer);

        return "Usuário adicionado!";
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deletarUsuario() {

       dadosConexao.deletarUsuario(gamer);

       return  "Usuário Cadastrado";
    }

    @GetMapping(path = "/usuarios")
    public @ResponseBody Iterable<Gamer> listarUsuarios() {
       return listarUsuarios();  }



}
