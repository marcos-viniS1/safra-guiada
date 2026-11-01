[caf_smart_readme.md](https://github.com/user-attachments/files/34246253/caf_smart_readme.md)
# ☕ Café Smart - Plataforma de Manejo e Gestão Cafeeira

O **Café Smart** é uma solução para gestão agrícola focada na cafeicultura. Projetado sobre uma arquitetura de **Monólito Modular**, o sistema centraliza o controle do domínio agrícola (propriedades, lavouras e talhões), planejamento do manejo anual de tarefas, monitoramento fitossanitário com emissão de alertas e controle financeiro por hectare e saca.

---

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework Backend:** Spring Boot 3.3.x
- **Persistência & ORM:** Spring Data JPA / Hibernate
- **Banco de Dados:** PostgreSQL 16
- **Containerização:** Docker & Docker Compose
- **Ferramentas de Suporte:** Lombok, Maven Wrapper (`mvnw`), VS Code / Thunder Client / DBeaver

---

## 🏗️ Estrutura da Arquitetura (Monólito Modular)

```txt
src/main/java/com/cafesmart/
├── CafesmartApplication.java
├── propriedade/   # Core agrícola (Propriedades, Lavouras e Talhões)
├── calendario/    # Gestão de tarefas e ciclo do manejo anual do café
├── agronomia/     # Inspeções fitossanitárias e alertas (Ferrugem, Broca, etc.)
└── custos/        # Rastreabilidade financeira e cálculo de custo por hectare/saca
```

---

## 📐 Modelo de Domínio e Recursos

### 1. Core Agrícola (`propriedade`)
Relacionamento hierárquico: `Propriedade` **(1 : N)** ➔ `Lavoura` **(1 : N)** ➔ `Talhao`
- **Propriedade:** Unidade produtiva principal (Nome, Produtor, Localização).
- **Lavoura:** Divisão macro da área de cultivo.
- **Talhão:** Unidade operacional de manejo (Variedade de café, Área em hectares, Ano de plantio, Espaçamento).

### 2. Calendário e Manejo Anual (`calendario`)
- **Gestão de Tarefas:** Cadastro e controle do status (`PREVISTA`, `REALIZADA`, `ADIADA`, `CANCELADA`).
- **Geração Automática do Plano de Manejo:** Criação automatizada das 6 operações chave do ciclo cafeeiro (adubação pós-florada, controle de ferrugem, enchimento de grãos, amostragem de broca, preparação de colheita e tratos pós-colheita).

### 3. Agronomia e Alertas Fitossanitários (`agronomia`)
- **Amostragens de Campo:** Registro de contagem de pragas e doenças em folhas e frutos.
- **Cálculo Automático de Alerta:** Classificação do nível de risco (`NORMAL`, `ATENCAO`, `CRITICO`) com base nos limites de dano econômico:
  - **Ferrugem do Cafeeiro:** $\ge 5.0\%$ (Atenção), $\ge 10.0\%$ (Crítico)
  - **Broca-do-Café:** $\ge 3.0\%$ (Atenção), $\ge 5.0\%$ (Crítico)
  - **Bicho Mineiro:** $\ge 5.0\%$ (Atenção), $\ge 10.0\%$ (Crítico)
  - **Cercosporiose:** $\ge 4.0\%$ (Atenção), $\ge 8.0\%$ (Crítico)

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- **JDK 21** configurado no ambiente
- **Docker Desktop** instalado e em execução

### Passos para Inicialização

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/marcos-viniS1/cafe-smart.git
   cd cafe-smart
   ```

2. **Iniciar o container do PostgreSQL:**
   ```bash
   docker compose up -d
   ```

3. **Executar a aplicação Spring Boot:**
   - **Via Terminal (Windows):**
     ```cmd
     mvnw spring-boot:run
     ```
   - **Via IDE (VS Code):** Execute diretamente pela classe `CafesmartApplication.java`.

4. Servidor estará ativo em: `http://localhost:8080`

---

## 📌 Documentação da API (Endpoints)

### 🏡 Módulo de Propriedades (`/api/propriedades`, `/api/lavouras`, `/api/talhoes`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/propriedades` | Cadastra uma nova propriedade |
| `GET` | `/api/propriedades` | Lista todas as propriedades |
| `POST` | `/api/lavouras/propriedade/{propriedadeId}` | Cadastra lavoura associada a uma propriedade |
| `GET` | `/api/lavouras/propriedade/{propriedadeId}` | Lista lavouras de uma propriedade |
| `POST` | `/api/talhoes/lavoura/{lavouraId}` | Cadastra talhão associado a uma lavoura |
| `GET` | `/api/talhoes/lavoura/{lavouraId}` | Lista talhões de uma lavoura |

### 📅 Módulo de Calendário (`/api/tarefas`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/tarefas/talhao/{talhaoId}` | Cadastra tarefa manual para um talhão |
| `POST` | `/api/tarefas/talhao/{talhaoId}/gerar-manejo-padrao` | Gera automaticamente as 6 tarefas do ciclo anual |
| `GET` | `/api/tarefas/talhao/{talhaoId}` | Lista todas as tarefas de um talhão |
| `GET` | `/api/tarefas/talhao/{talhaoId}/mes/{mes}` | Filtra tarefas de um talhão por mês (1-12) |
| `PATCH` | `/api/tarefas/{id}/status?status=REALIZADA` | Atualiza o status de uma tarefa |

### 🔬 Módulo de Agronomia (`/api/agronomia`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/agronomia/talhao/{talhaoId}/amostragem` | Registra amostragem de praga/doença e calcula o alerta |
| `GET` | `/api/agronomia/talhao/{talhaoId}/amostragens` | Lista o histórico de amostragem do talhão |
| `GET` | `/api/agronomia/talhao/{talhaoId}/alertas` | Lista apenas as amostragens com alerta (`ATENCAO` ou `CRITICO`) |

---

## 👨‍💻 Autor

Desenvolvido por **Marcos Vinícius**.
