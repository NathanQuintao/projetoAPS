import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DAO.DoencaDAO;
import entidades.Doenca;

public class CadastroDoenca extends JFrame {

    private JTextField txtNome, txtCasos, txtMortes, txtPorcentagem;
    private JButton btnSalvar;

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

        
        panel.add(new JLabel(""));

        add(panel);

        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDoenca();
            }
        });
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

    public static void main(String[] args) {
        new CadastroDoenca().setVisible(true);
    }
  
}

