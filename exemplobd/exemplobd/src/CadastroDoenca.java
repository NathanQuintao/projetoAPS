import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.DoencaDAO;
import DAO.usuarioDAO;
import conexao.Conexao;
import entidades.Doenca;

public class CadastroDoenca extends JFrame {

    private JTextField txtNome, txtCasos, txtMortes, txtPorcentagem;
    private JButton btnSalvar;

    public boolean autenticar(String login, String senha) {
    String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, login);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        return rs.next(); // Se encontrou, login está correto

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public CadastroDoenca() {
        setTitle("Cadastro de Doenças");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        
        panel.add(new JLabel("Nome da Doença:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        panel.add(new JLabel("Número de Casos:"));
        txtCasos = new JTextField();
        panel.add(txtCasos);

        panel.add(new JLabel("Número de Mortes:"));
        txtMortes = new JTextField();
        panel.add(txtMortes);

        panel.add(new JLabel("Porcentagem da População (%):"));
        txtPorcentagem = new JTextField();
        panel.add(txtPorcentagem);

        btnSalvar = new JButton("Salvar");
        panel.add(btnSalvar);

        JButton btnGrafico = new JButton("Gerar Gráfico");
        panel.add(btnGrafico);  
        
        panel.add(new JLabel(""));

        add(panel);

        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDoenca();
            }
        });

        btnGrafico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafico();
            }
});
        ;
    }

    private void salvarDoenca() {
        try {
            Doenca doenca = new Doenca();
            doenca.setNome(txtNome.getText());
            doenca.setNumeroCasos(Integer.parseInt(txtCasos.getText()));
            doenca.setNumeroMortes(Integer.parseInt(txtMortes.getText()));
            doenca.setPorcentagemPopulacao(Double.parseDouble(txtPorcentagem.getText()));

            DoencaDAO dao = new DoencaDAO();
            dao.cadastrarDoenca(doenca);

            JOptionPane.showMessageDialog(this, "Doença cadastrada com sucesso!");

            txtNome.setText("");
            txtCasos.setText("");
            txtMortes.setText("");
            txtPorcentagem.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: preencha os campos numéricos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar doença: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void gerarGrafico() {
        try {
            String nome = txtNome.getText();
            DoencaDAO dao = new DoencaDAO();
            Doenca doenca = dao.buscarPorNome(nome);
    
            if (doenca != null) {
                GraficoDoenca.gerar(doenca);
            } else {
                JOptionPane.showMessageDialog(this, "Doença não encontrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

   public static void main(String[] args) {
    String login = JOptionPane.showInputDialog("Digite o login:");
    String senha = JOptionPane.showInputDialog("Digite a senha:");

    usuarioDAO usuarioDAO = new usuarioDAO(); // ou UsuarioDAO, dependendo do nome
    boolean autenticado = usuarioDAO.autenticar(login, senha);

    if (autenticado) {
        JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
        new CadastroDoenca().setVisible(true); // abre a parte do sistema
    } else {
        JOptionPane.showMessageDialog(null, "Login ou senha incorretos.");
        System.exit(0); // encerra o programa se login falhar
    }
}
}

