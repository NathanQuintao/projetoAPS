package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public Doenca buscarPorNome(String nome) throws Exception {
    Doenca doenca = null;

    String sql = "SELECT * FROM doenca WHERE nome = ?";
    Connection conn = Conexao.conectar();
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nome);
    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {
        doenca = new Doenca();
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
