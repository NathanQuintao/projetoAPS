import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import entidades.Doenca;

public class GraficoDoenca {
    public static void gerar(Doenca doenca) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

dataset.addValue(doenca.getNumeroCasos(), "Casos", "");
dataset.addValue(doenca.getNumeroMortes(), "Mortes", "");
dataset.addValue(doenca.getPorcentagemPopulacao(), "População (%)", "");

JFreeChart grafico = ChartFactory.createBarChart(
    "Dados da Doença: " + doenca.getNome(),
    "Categoria",
    "Valor",
    dataset
);

ChartFrame frame = new ChartFrame("Gráfico da Doença", grafico);
frame.setSize(600, 400);
frame.setVisible(true);
}}

    

