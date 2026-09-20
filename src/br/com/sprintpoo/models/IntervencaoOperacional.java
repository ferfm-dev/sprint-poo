package br.com.sprintpoo.models;

public abstract class IntervencaoOperacional {
    protected Long id; // NOVO: chave primária no banco (null enquanto não persistido)
    protected String descricao;
    protected float extensaoKM;

    public IntervencaoOperacional(String descricao, float extensaoKM) {
        this.descricao = descricao;
        this.extensaoKM = extensaoKM;
    }

    // Construtor usado pelo DAO ao reconstruir a partir do banco
    public IntervencaoOperacional(Long id, String descricao, float extensaoKM) {
        this(descricao, extensaoKM);
        this.id = id;
    }

    public abstract void executarServico();

    // NOVO: usado pelo DAO para gravar/identificar o tipo (coluna TIPO no banco)
    public abstract String getTipo();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public float getExtensaoKM() { return extensaoKM; }
}
