
import DAO.usuarioDAO;
import entidades.Usuario;

public class App {
    public static void main(String[] args) {
        Usuario u = new Usuario();
        u.setNome("Nathan");
        u.setLogin("nathan");
        u.setSenha("1234");

        new usuarioDAO().cadastrarUsuario(u);
    }
}