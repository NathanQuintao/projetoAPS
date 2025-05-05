package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;

public class usuarioDAO {
    public boolean autenticar(String login, String senha) {
        String sql = "SELECT * FROM login WHERE login = ? AND senha = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true se encontrou usuário

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

