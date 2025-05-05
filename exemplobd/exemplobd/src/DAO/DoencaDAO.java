package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.Conexao;
import entidades.Doenca;

public class DoencaDAO {

    public void cadastrarDoenca(Doenca doenca) {
        String sql = "INSERT INTO DOENCA (NOME, NUMERO_CASOS, NUMERO_MORTES, PORCENTAGEM_POPULACAO) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, doenca.getNome());
            ps.setInt(2, doenca.getNumeroCasos());
            ps.setInt(3, doenca.getNumeroMortes());
            ps.setDouble(4, doenca.getPorcentagemPopulacao());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
