package br.com.sprintpoo.models;

public abstract class IntervencaoOperacional {
    protected String descricao;
    protected float extensaoKM;

    public IntervencaoOperacional(String descricao, float extensaoKM) {
        this.descricao = descricao;
        this.extensaoKM = extensaoKM;
    }

    public abstract void executarServico();

    public String getDescricao() { return descricao; }
    public float getExtensaoKM() { return extensaoKM; }
}