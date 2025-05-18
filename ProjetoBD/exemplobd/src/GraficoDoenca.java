import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import entidades.Doenca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class GraficoDoenca extends JFrame {

    private ChartPanel painel;
    private JPanel painelBotoes;
    private DefaultCategoryDataset datasetAtual;
    private JFreeChart grafico;

    public GraficoDoenca() {
        setTitle("Estatísticas de Doenças por Cidade");
        setLayout(new BorderLayout());

        // Dataset inicial (número de casos)
        datasetAtual = obterDadosDoBanco("numeroCasos");

        // Criação inicial do gráfico
        grafico = ChartFactory.createBarChart(
                "Número de Casos por Cidade",
                "Cidade",
                "Quantidade",
                datasetAtual
        );

        painel = new ChartPanel(grafico);
        add(painel, BorderLayout.CENTER);

        // Painel com botões
        painelBotoes = new JPanel();

        JButton btnCasos = new JButton("Mostrar Casos");
        btnCasos.addActionListener((ActionEvent e) -> atualizarGrafico("numeroCasos", "Número de Casos por Cidade"));

        JButton btnMortes = new JButton("Mostrar Mortes");
        btnMortes.addActionListener((ActionEvent e) -> atualizarGrafico("numeroMortes", "Número de Mortes por Cidade"));

        JButton btnPorcentagem = new JButton("Mostrar % Mortes");
        btnPorcentagem.addActionListener((ActionEvent e) -> atualizarGraficoPorcentagem());

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener((ActionEvent e) -> dispose()); // Fecha a janela do gráfico

        painelBotoes.add(btnCasos);
        painelBotoes.add(btnMortes);
        painelBotoes.add(btnPorcentagem);
        painelBotoes.add(btnVoltar); // Adicionado botão Voltar

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private DefaultCategoryDataset obterDadosDoBanco(String coluna) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String url = "jdbc:mysql://localhost:3306/bdaps";
        String usuario = "root";
        String senha = "mysql";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            String sql = "SELECT cidade, SUM(" + coluna + ") AS total FROM doenca GROUP BY cidade";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String cidade = rs.getString("cidade");
                int valor = rs.getInt("total");
                dataset.addValue(valor, coluna.equals("numeroCasos") ? "Casos" : "Mortes", cidade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados.");
        }

        return dataset;
    }

    private void atualizarGrafico(String coluna, String titulo) {
        datasetAtual = obterDadosDoBanco(coluna);
        grafico = ChartFactory.createBarChart(
                titulo,
                "Cidade",
                "Quantidade",
                datasetAtual
        );
        painel.setChart(grafico);
    }

    private void atualizarGraficoPorcentagem() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String url = "jdbc:mysql://localhost:3306/bdaps";
        String usuario = "root";
        String senha = "mysql";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            String sql = "SELECT cidade, SUM(numeroCasos) AS totalCasos, SUM(numeroMortes) AS totalMortes FROM doenca GROUP BY cidade";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String cidade = rs.getString("cidade");
                int casos = rs.getInt("totalCasos");
                int mortes = rs.getInt("totalMortes");

                if (casos > 0) {
                    double porcentagem = (mortes / (double) casos) * 100.0;
                    dataset.addValue(porcentagem, "% Mortes", cidade);
                } else {
                    dataset.addValue(0.0, "% Mortes", cidade);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados.");
        }

        grafico = ChartFactory.createBarChart(
                "Porcentagem de Mortes por Cidade",
                "Cidade",
                "Porcentagem (%)",
                dataset
        );
        painel.setChart(grafico);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraficoDoenca exemplo = new GraficoDoenca();
            exemplo.setSize(800, 600);
            exemplo.setLocationRelativeTo(null);
            exemplo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            exemplo.setVisible(true);
        });
    }

    public static void gerar(Doenca doenca) {
        throw new UnsupportedOperationException("Unimplemented method 'gerar'");
    }
}
