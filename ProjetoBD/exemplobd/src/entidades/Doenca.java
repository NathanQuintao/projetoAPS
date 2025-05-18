package entidades;

public class Doenca {

    private int id;
    private String cidade;
    private String nome;
    private int numeroCasos;
    private int numeroMortes;
    private double porcentagemPopulacao;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getNumeroCasos() {
        return numeroCasos;
    }
    public void setNumeroCasos(int numeroCasos) {
        this.numeroCasos = numeroCasos;
    }
    public int getNumeroMortes() {
        return numeroMortes;
    }
    public void setNumeroMortes(int numeroMortes) {
        this.numeroMortes = numeroMortes;
    }
    public double getPorcentagemPopulacao() {
        return porcentagemPopulacao;
    }
    public void setPorcentagemPopulacao(double porcentagemPopulacao) {
        this.porcentagemPopulacao = porcentagemPopulacao;
    }
}