package br.com.sprintpoo.models;

public class EquipeManutencao {

    private Long id;
    private String nome;
    private String especialidade; // "ROCADA", "PULVERIZACAO" ou "MISTA"
    private int quantidadeMembros;
    private boolean disponivel;

    public EquipeManutencao(String nome, String especialidade, int quantidadeMembros) {
        setNome(nome);
        setEspecialidade(especialidade);
        setQuantidadeMembros(quantidadeMembros);
        this.disponivel = true;
    }

    // Construtor usado pelo DAO ao reconstruir a partir do banco
    public EquipeManutencao(Long id, String nome, String especialidade, int quantidadeMembros, boolean disponivel) {
        this(nome, especialidade, quantidadeMembros);
        this.id = id;
        this.disponivel = disponivel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) {
            this.nome = nome;
            return;
        }
        System.out.println("Nome inválido.");
    }

    public String getEspecialidade() { return especialidade; }

    public void setEspecialidade(String especialidade) {
        if (especialidade != null && !especialidade.isEmpty()) {
            this.especialidade = especialidade.toUpperCase();
            return;
        }
        System.out.println("Especialidade inválida.");
    }

    public int getQuantidadeMembros() { return quantidadeMembros; }

    public void setQuantidadeMembros(int quantidadeMembros) {
        if (quantidadeMembros > 0) {
            this.quantidadeMembros = quantidadeMembros;
            return;
        }
        System.out.println("Quantidade inválida.");
    }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public void exibirInfo() {
        System.out.println("=========================");
        System.out.println("Equipe: " + nome);
        System.out.println("-------------------------");
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Membros: " + quantidadeMembros);
        System.out.println("Disponível: " + (disponivel ? "Sim" : "Não"));
        System.out.println("=========================");
    }
}
