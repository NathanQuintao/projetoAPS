package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import entidades.Doenca;

public class DoencaDAO {

    public void cadastrarDoenca(Doenca doenca) {
        String sql = "INSERT INTO doenca (cidade, nome, numeroCasos, numeroMortes, porcentagemPopulacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doenca.getCidade());
            stmt.setString(2, doenca.getNome());
            stmt.setInt(3, doenca.getNumeroCasos());
            stmt.setInt(4, doenca.getNumeroMortes());
            stmt.setDouble(5, doenca.getPorcentagemPopulacao());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Doenca buscarPorNome(String nome) throws Exception {
    Doenca doenca = null;

    String sql = "SELECT * FROM doenca WHERE nome = ?";
    Connection conn = Conexao.conectar();
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nome);
    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {
        doenca = new Doenca();
        doenca.setCidade(rs.getString("cidade"));
        doenca.setNome(rs.getString("nome"));
        doenca.setNumeroCasos(rs.getInt("casos"));
        doenca.setNumeroMortes(rs.getInt("mortes"));
        doenca.setPorcentagemPopulacao(rs.getDouble("porcentagem"));
    }
    rs.close();
    stmt.close();
    conn.close();

    return doenca;
}
}