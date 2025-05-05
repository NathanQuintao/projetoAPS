package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.Conexao;
import entidades.Usuario; 

public class usuarioDAO {

    public void cadastrarUsuario(Usuario usuario) { 

        String sql = "INSERT INTO USUARIO (NOME, LOGIN, SENHA) VALUES (?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
