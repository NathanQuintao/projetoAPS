import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CadastroDoenca extends JFrame {

    private JTextField nomeField, cidadeField, casosField, mortesField, porcentagemField;

    public CadastroDoenca() {
        setTitle("Cadastro de Doenças");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2)); // 8 linhas agora

        // Campos
        add(new JLabel("Nome da Doença:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Cidade:"));
        cidadeField = new JTextField();
        add(cidadeField);

        add(new JLabel("Número de Casos:"));
        casosField = new JTextField();
        add(casosField);

        add(new JLabel("Número de Mortes:"));
        mortesField = new JTextField();
        add(mortesField);

        add(new JLabel("% da População:"));
        porcentagemField = new JTextField();
        add(porcentagemField);

        // Botão Salvar
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(this::salvarDoenca);
        add(salvarButton);

        // Botão Excluir
        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(this::excluirDoenca);
        add(excluirButton);

        // Botão Mostrar Gráfico
        JButton graficoButton = new JButton("Mostrar Gráfico");
        graficoButton.addActionListener(this::mostrarGrafico);
        add(graficoButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvarDoenca(ActionEvent e) {
        String nome = nomeField.getText();
        String cidade = cidadeField.getText();
        int casos = Integer.parseInt(casosField.getText());
        int mortes = Integer.parseInt(mortesField.getText());
        double porcentagem = Double.parseDouble(porcentagemField.getText());

        String url = "jdbc:mysql://localhost:3306/bdaps";
        String usuario = "root";
        String senha = "mysql";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            String sql = "INSERT INTO doenca (nome, cidade, numeroCasos, numeroMortes, porcentagemPopulacao) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cidade);
            stmt.setInt(3, casos);
            stmt.setInt(4, mortes);
            stmt.setDouble(5, porcentagem);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Doença salva com sucesso!");
            limparCampos();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco.");
        }
    }

    private void excluirDoenca(ActionEvent e) {
        String nome = nomeField.getText();
        String cidade = cidadeField.getText();

        if (nome.isEmpty() || cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da doença e a cidade para excluir.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/bdaps";
        String usuario = "root";
        String senha = "mysql";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            String sql = "DELETE FROM doenca WHERE nome = ? AND cidade = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cidade);

            int afetados = stmt.executeUpdate();
            if (afetados > 0) {
                JOptionPane.showMessageDialog(this, "Registro excluído com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado com esse nome e cidade.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao excluir do banco.");
        }
    }

    private void mostrarGrafico(ActionEvent e) {
        GraficoDoenca grafico = new GraficoDoenca();
        grafico.setSize(800, 600);
        grafico.setLocationRelativeTo(this);
        grafico.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        grafico.setVisible(true);
    }

    private void limparCampos() {
        nomeField.setText("");
        cidadeField.setText("");
        casosField.setText("");
        mortesField.setText("");
        porcentagemField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CadastroDoenca::new);
    }
}
