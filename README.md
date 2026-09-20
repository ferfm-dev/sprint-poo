# MOTIVA — Sprint 3 (Persistência com Oracle + JDBC puro)

## Estrutura

```
src/
├── db/ConexaoBD.java
├── dao/
│   ├── EquipeManutencaoDAO.java
│   ├── TrechoRodoviaDAO.java
│   ├── IntervencaoOperacionalDAO.java
│   └── RelatorioPrioridadeDAO.java
├── model/
│   ├── TrechoRodovia.java
│   ├── EquipeManutencao.java          (novo)
│   ├── IntervencaoOperacional.java
│   ├── RocadaMecanizada.java
│   ├── Pulverizacao.java
│   └── MonitoravelViaIoT.java
├── service/GeradorRelatorio.java      (novo)
└── main/Main.java
sql/
├── seu-script-criacao.sql
└── seu-script-dados.sql
lib/
└── (coloque aqui o ojdbc17.jar)
```

## Passo a passo

1. **Banco de dados**
   - Rode `sql/seu-script-criacao.sql` no Oracle do laboratório.
   - Rode `sql/seu-script-dados.sql` para popular com dados de teste.

2. **Credenciais**
   - Abra `src/db/ConexaoBD.java` e troque `USER` e `PASSWORD` pelo seu RM e senha do Oracle FIAP
     (e `HOST`/`PORTA`/`SERVICO` se o seu ambiente for diferente).

3. **Driver JDBC**
   - Copie o `ojdbc17.jar` para a pasta `lib/`.
   - Adicione esse jar ao classpath do seu projeto (no IntelliJ/Eclipse: Project Structure → Libraries → Add).

4. **Compilar e rodar**
   - Compile todos os arquivos em `src/` mantendo a estrutura de pacotes (`br.com.sprintpoo.*`).
   - Execute `br.com.sprintpoo.main.Main`.

## Decisões de modelagem (documentadas para a entrega)

- `TrechoRodovia`, `IntervencaoOperacional` e a nova `EquipeManutencao` ganharam um campo `id` (Long),
  necessário para localizar/atualizar/deletar registros no banco.
- `IntervencaoOperacional` (abstrata) é persistida em uma única tabela (`TB_INTERVENCAO_OPERACIONAL`)
  com uma coluna discriminadora `TIPO` (`ROCADA` ou `PULVERIZACAO`); o DAO reconstrói a subclasse correta
  ao ler do banco.
- `RelatorioPrioridadeDAO` usa um **record** (`RelatorioPrioridadeDAO.RelatorioPrioridade`) para representar
  a entidade, já que não existe uma classe de domínio própria para relatório.
- `GeradorRelatorio` classifica cada `TrechoRodovia` pelo `nivelVegetacaoCM`:
  - `>= 50 cm` → URGENTE
  - `30–49,9 cm` → CRÍTICO
  - `15–29,9 cm` → ATENÇÃO
  - `< 15 cm` → NORMAL

  Ajuste esses limites em `GeradorRelatorio` se o critério da sua sprint anterior for diferente.

## Problemas comuns

| Problema | Solução |
|---|---|
| `Driver not found` | Confirme que `ojdbc17.jar` está no classpath |
| `ORA-01017: invalid username/password` | Revise `USER`/`PASSWORD` em `ConexaoBD` |
| `ORA-00942: table or view does not exist` | Rode `seu-script-criacao.sql` primeiro |
| `ORA-02292: integrity constraint violated` | Há intervenções referenciando o trecho/equipe que você tentou deletar |
