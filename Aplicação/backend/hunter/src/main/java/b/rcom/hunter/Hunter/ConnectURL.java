package b.rcom.hunter.Hunter;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class ConnectURL {
    private BasicDataSource dataSource;

    public ConnectURL(){



        /* Conexão Via Spring JDBC  MSSQL*/
          dataSource = new BasicDataSource();
          dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          dataSource.setUrl("jdbc:sqlserver://banco01191117.database.windows.net:1433;database=Banco01191117");


        dataSource.setUsername("Gustavo01191117");
        dataSource.setPassword("#Gf50422207802");


    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }


    private String email;
    private String senha;
    private String insertQuery;
    private String deleteQuery;



    public Gamer consultarUsuario(String email, String senha, Gamer gamer) {
        List<Map<String, Object>> select = template().queryForList(
                "select * from gamer where email = ? and senha = ?", email, senha);

        if (select != null) {

            for (Map<String, Object> row : select) {
                email = (String) row.get("email");
                senha = (String) row.get("senha");
            }
            gamer = new Gamer(email, senha);
            gamer.setAutentitcado(true);
            return gamer;
        }else{
            System.out.println("Usuario não encontrado");
            return null;
        }
    }


    public void cadastrarUsuario(Gamer user) {
        template().update(
                "insert into gamer(email, senha) values " +
                        "(?,?)", user.getEmail(), user.getSenha()
        );
    }
    public void deletarUsuario(Gamer user) {
        template().update(
                "delete from gamer where email = ?", user.getEmail()
        );
    }
    public JdbcTemplate template() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
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
}
