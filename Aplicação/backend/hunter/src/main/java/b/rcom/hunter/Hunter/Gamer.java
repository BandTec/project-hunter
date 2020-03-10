package b.rcom.hunter.Hunter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class Gamer  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String idGamer;

    private String nome;

    private String cpf;

    private String email;

    private String senha;

    private String telefone;


    public void setIdGamer(String idGamer) {
        this.idGamer = idGamer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


   // public Collection<? extends GrantedAuthority> getAuthorities() {
  //      return null;
  //  }


    public boolean isAccountNonExpired() {
        return false;
    }


    public boolean isAccountNonLocked() {
        return false;
    }


    public boolean isCredentialsNonExpired() {
        return false;
    }


    public boolean isEnabled() {
        return false;
    }
}
