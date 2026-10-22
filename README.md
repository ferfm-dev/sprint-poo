# MOTIVA API — Sprint 4 (Spring Boot + JPA + REST)

Transformação do sistema MOTIVA — anteriormente um protótipo de console com persistência via JDBC puro (Sprint 3) — em uma **API REST profissional** utilizando **Spring Boot** e **Spring Data JPA**, reaproveitando o banco de dados Oracle e a modelagem relacional da Sprint anterior.

---

## 🛠️ Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.2.x**
- **Spring Data JPA** (Hibernate)
- **Spring Web** (Tomcat embarcado)
- **Bean Validation** (Validação de contratos)
- **Oracle JDBC Driver (`ojdbc11`)**
- **Maven** (Gerenciamento de dependências e build)

---

## 🚀 Como Executar o Projeto

1. **Pré-requisitos:** Certifique-se de ter o **Java 17** e o **Maven** instalados em sua máquina.
2. **Configuração do Banco:** Configure as variáveis de ambiente ou edite o arquivo `src/main/resources/application.properties` apontando para o Oracle da FIAP:
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
   spring.datasource.username=seu_rm
   spring.datasource.password=sua_senha
   ```
3. **Executando via Maven Wrapper:**
   Abra o terminal na raiz do projeto e execute:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(No Windows CMD, utilize `mvnw spring-boot:run`)*.

---

## 📋 Documentação dos Endpoints REST

| Método HTTP | Rota | Ação | Código de Sucesso |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/trechos` | Listar todos os trechos cadastrados | `200 OK` |
| **GET** | `/api/trechos/{id}` | Buscar trecho específico por ID | `200 OK` / `404 Not Found` |
| **POST** | `/api/trechos` | Criar um novo trecho de rodovia | `201 Created` / `400 Bad Request` |
| **DELETE** | `/api/trechos/{id}` | Remover um trecho pelo ID | `204 No Content` / `404 Not Found` |
| **POST** | `/api/relatorios` | Gera o relatório de prioridade com base nos trechos atuais e persiste no histórico | `201 Created` |
| **GET** | `/api/relatorios` | Lista todo o histórico de relatórios gerados | `200 OK` |
| **GET** | `/api/relatorios/periodo?inicio=...&fim=...` | Consulta históricos de relatórios por período | `200 OK` |

---

## 💻 Exemplos de Requisições (cURL)

### 1. Criar um novo Trecho de Rodovia (`POST /api/trechos`)
```bash
curl -X POST http://localhost:8080/api/trechos \
-H "Content-Type: application/json" \
-d '{
  "titulo": "Rodovia dos Imigrantes - Km 45",
  "quilometroInicial": 40.0,
  "quilometroFinal": 50.0,
  "nivelVegetacaoCM": 65.5,
  "possuiIoT": true
}'
```

### 2. Listar todos os Trechos (`GET /api/trechos`)
```bash
curl -X GET http://localhost:8080/api/trechos
```

### 3. Gerar Relatório de Prioridade (`POST /api/relatorios`)
```bash
curl -X POST http://localhost:8080/api/relatorios
```

---

## ⚙️ Ajustes Técnicos Específicos (Sprint 4 x Oracle)

- **Mapeamento de Booleano para Constraint Oracle (`POSSUI_IOT`)**:
   - Como a tabela `TB_TRECHO_RODOVIA` reaproveitada da Sprint 3 possui uma *check constraint* (`ORA-02290`) que aceita apenas `'S'` (Sim) ou `'N'` (Não), a aplicação implementou um `AttributeConverter` customizado (`BooleanToSimNaoConverter`).
   - Isso permite que a API receba um valor booleano limpo (`true`/`false`) no JSON do contrato REST e o traduza automaticamente para caracteres compatíveis com o Oracle nos bastidores, sem alterar a estrutura do banco de dados relacional.

---

## 🧠 Perguntas de Reflexão (Documentação Acadêmica)

### 1. Por que o Repository é uma interface e não uma classe? Quem escreve a implementação e quando?
**Resposta:** O `Repository` é uma interface porque o Spring Data JPA utiliza o conceito de metaprogramação e proxies dinâmicos em tempo de execução. Nós declaramos apenas o contrato (assinaturas dos métodos) e o **Spring Framework (junto com o Hibernate)** escreve a implementação e gera o código SQL em tempo de execução de forma automática com base nos nomes dos métodos (derived queries) ou nas anotações.

### 2. O pattern DAO da Sprint 3 "morreu" na migração ou só mudou de forma? Explique com as suas palavras.
**Resposta:** O padrão DAO **não morreu, ele foi absorvido e abstraído** pelo framework. Na Sprint 3 escrevíamos manualmente a lógica de persistência e comandos de banco (Data Access Object). Na Sprint 4, a interface do Spring Data JPA cumpre exatamente o mesmo papel conceitual de intermediar o acesso aos dados, mas sem que o desenvolvedor precise escrever código repetitivo de conexão, `PreparedStatement` ou mapeamento de `ResultSet`.

### 3. Por que a validação de `nivelVegetacao >= 0` (Sprint 1) deve ficar no Service e não no Controller?
**Resposta:** Porque a validação de nível de vegetação não é uma simples regra de formato de requisição HTTP, mas sim uma **regra de negócio fundamental do domínio**. A camada de `Controller` deve servir apenas como uma ponte de tradução entre o protocolo HTTP e a aplicação. Manter a lógica no `Service` garante que, se o sistema for consumido por outra interface no futuro (como um CLI ou mensageria), as regras de integridade do domínio continuarão sendo aplicadas rigorosamente.

### 4. No JDBC puro vocês escreviam SQL. Onde está o SQL do `findByTipo()`? Quem o gerou?
**Resposta:** O SQL do `findByTipo()` (ou análogos de consulta baseados em nomes) **não foi escrito manualmente por nós**. Ele foi **gerado dinamicamente pelo Hibernate / Spring Data JPA** em tempo de execução. O framework interpreta o nome do método (`findBy...`), compreende os atributos da entidade mapeada com `@Entity` e traduz automaticamente para a sintaxe SQL compatível com o banco de dados relacional configurado.