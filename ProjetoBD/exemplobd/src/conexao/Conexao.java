package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String url = "jdbc:mysql://localhost:3306/bdaps";
    private static final String user = "root"; // ← alterado aqui
    private static final String password = "mysql"; // ← e aqui (senha vazia ou sua senha correta)

    private static Connection conn;

    public static Connection getConexao() {
        try {
            if (conn == null) {
                System.out.println("Tentando conectar ao banco de dados...");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Conexão estabelecida!");
            }
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Connection conectar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conectar'");
    }
}
