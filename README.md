<p align="center">
  <img src="https://i.imgur.com/0DzkjFk.png" alt="CarteiraVacinal Logo">
</p>
<p align="center"> 
  <a href="https://github.com/Jorgeluisreis/CarteiraVacinal-backend"> 
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Jorgeluisreis/CarteiraVacinal-backend"> 
  </a> 
  <a href="https://github.com/Jorgeluisreis/CarteiraVacinal-backend/issues"> 
    <img alt="GitHub issues" src="https://img.shields.io/github/issues/Jorgeluisreis/CarteiraVacinal-backend"> 
  </a> 
  <a href="https://github.com/Jorgeluisreis/Carteiravacinal-frontend/pulls"> 
    <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/Jorgeluisreis/CarteiraVacinal-backend"> 
  </a> 
  <a href="https://github.com/Jorgeluisreis/CarteiraVacinal-backend"> 
    <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/Jorgeluisreis/CarteiraVacinal-backend"> 
  </a> 
  <a href="https://github.com/Jorgeluisreis/CarteiraVacinal-backend?tab=MIT-1-ov-file"> 
    <img alt="License" src="https://img.shields.io/github/license/Jorgeluisreis/CarteiraVacinal-backend"> 
  </a> 
</p>

---

## CarteiraVacinal - Backend

O projeto **CarteiraVacinal** é uma iniciativa da Hackathon de 2025 da Turma 4 do [**Programa 1000Devs**](https://www.jnjmedtech.com/pt-br/patient/1000-devs-talentos-para-o-bem-na-saude), organizada pela [mesttra](https://www.mesttra.com/) em parceria com a [Johnson & Johnson Medtech](https://www.jnjmedtech.com/pt-br) e o [Hospital Israelita Albert Einstein](https://www.einstein.br/n/). O Professor [Rogério de Freitas](https://www.linkedin.com/in/rogerio-freitas-ribeiro-690a9712/) organizou o evento com o objetivo de desenvolver uma solução para gerenciamento de vacinas, permitindo o cadastro e acompanhamento das imunizações de pacientes.

Este repositório contém o **Backend** do projeto, desenvolvido com **Java, Maven e Spark**, com o uso do **jdbc** para garantir conectividade com o banco de dados. O backend foi projetado para ser consumido por Clients de APIs, como o **Postman** e pelo frontend, criado especialmente para esta aplicação, desenvolvida em **HTML, CSS, Javascript e Bootstrap**, disponível clicando [aqui](http://github.com/Jorgeluisreis/CarteiraVacinal-frontend/).

---

## 🎯 Funcionalidades

Esta WebAPI permite que seja feitas requisições HTTP, contendo interações de **CRUD (Criar, Ler, Atualizar e Deletar)**, sendo elas:

* ✅ **Gerenciamento de Pacientes:** Cadastrar, editar e excluir pacientes.
* ✅ **Gerenciamento de Imunizações:** Cadastrar, editar e excluir imunizações associadas a um paciente.
* ✅ **Consulta de Dados:** Exibir informações sobre pacientes e suas vacinas aplicadas.

---

## 🏛️ Arquitetura da Aplicação

<p align="center">

<img src="https://i.imgur.com/LRjJOzC.png" alt="Arquitetura">

</p>

## 🖧 Fluxo da Tríade (Backend, Frontend e Banco de Dados)

No backend, usei o **HikariCP** para gerenciar as conexões do Banco de dados.

O **HikariCP** é um pool de conexões JDBC para bancos de dados, projetado para ser extremamente rápido, leve e eficiente. Ele é amplamente utilizado em aplicações Java que precisam gerenciar conexões com bancos de dados de forma otimizada.

**Principais Características do HikariCP**
* Alto desempenho – Considerado um dos pools de conexão mais rápidos disponíveis.
* Baixo consumo de memória – Usa menos threads e menos recursos que outros pools, como C3P0 ou Apache DBCP.
* Recuperação automática – Se uma conexão falha, o HikariCP pode restaurá-la automaticamente.
* Configuração simplificada – Possui poucas configurações, mas altamente otimizadas por padrão.
* Suporte a timeout e validação de conexões – Evita conexões zumbi ou mal configuradas.

<p align="center">

<img src="https://i.imgur.com/5zhXdMR.png" alt="Fluxo da Tríade">

</p>


## 🛠️ Tecnologias Utilizadas  

- ![Java](https://img.shields.io/badge/Java-21-red) - Linguagem de programação utilizada.  
- ![Maven](https://img.shields.io/badge/Maven-brown) - Gerenciador de dependências e automação de builds.  
- ![Spark Java](https://img.shields.io/badge/Spark%20Java-orange) - Framework para criação de APIs REST em Java.  
- ![Docker](https://img.shields.io/badge/Docker-blue) - Conteinerização da aplicação.  
- ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-black) - Integração contínua (CI/CD) para gerar a imagem Docker.  

---

## 👥 Desenvolvedor

Para esta parte do projeto sendo o Backend, o [Jorge Luis](https://www.linkedin.com/in/ojorge-luis) se encarregou de criar todo o Backend para ser consumido e usado pelo [Frontend](http://github.com/Jorgeluisreis/CarteiraVacinal-frontend/):

<div align="center">

  ![Jorge Luis](https://i.imgur.com/7583zV4.jpeg)  
  [Jorge Luis](https://www.linkedin.com/in/ojorge-luis/)

</div>



---

## 🔨 Como foi o Desenvolvimento ?

### 📋 Planejamento e Organização

O desenvolvimento do **Backend da CarteiraVacinal** foi gerenciado utilizando **GitHub Projects** no modelo **Kanban**, permitindo que eu me exercitasse  com uma das metodologias mais utilizadas para gerenciamento de projetos. As atividades foram organizadas e marcadas com a tag **Backend**, e atribuia um **card** para indicar que estava trabalhando na respectiva tarefa e em qual estágio de desenvolvimento ela se encontrava. Você pode ter acesso ao quadro Kanban clicando [aqui](https://github.com/users/Jorgeluisreis/projects/3).

### 🛠️ Ferramentas e Metodologias Utilizadas

Para manter um fluxo de trabalho eficiente e organizado, utilizei as seguintes práticas:

- **Controle de versão:** Git/GitHub, seguindo o fluxo de trabalho do [**Git Flow**](https://www.alura.com.br/artigos/git-flow-o-que-e-como-quando-utilizar?srsltid=AfmBOooBiS3a1dsk_xR0egEMRSRXmrnG7fxjR3xazdDlqF4Zb3YqE-8d).
- **Convenção de commits:** Aplicamos a metodologia [**Conventional Commits**](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657) para manter um histórico de mudanças padronizado e legível.
- **Gerenciamento de tarefas:** Cada atividade era associada a uma [**issue**](https://github.com/Jorgeluisreis/CarteiraVacinal-backend/issues?q=is%3Aissue%20state%3Aclosed), detalhando a tarefa a ser executada, testada e revisada.
- **Revisão de código:** A equipe utilizou [**Pull Requests (PRs)**](https://github.com/Jorgeluisreis/CarteiraVacinal-backend/pulls?q=is%3Apr+is%3Aclosed) para revisar e validar as alterações antes de integrá-las ao código principal.

### 🗂️ Organização e Gerenciamento de Tarefas

1. **Abertura de uma branch de trabalho:**  
   - A branch era criada seguindo a metodologia [**Git Flow**](https://www.alura.com.br/artigos/git-flow-o-que-e-como-quando-utilizar?srsltid=AfmBOooBiS3a1dsk_xR0egEMRSRXmrnG7fxjR3xazdDlqF4Zb3YqE-8d), com o mesmo nome do card atribuído ao colaborador.
   - Todo o desenvolvimento era realizado nessa branch.

2. **Criação de issues:**  
   - Cada [**issue**](https://github.com/Jorgeluisreis/CarteiraVacinal-backend/issues?q=is%3Aissue%20state%3Aclosed) detalhava a tarefa, seus critérios de aceitação e testes necessários.
   - O líder técnico, [**Jorge Luis**](https://www.linkedin.com/in/ojorge-luis), revisava e validava as tarefas.

3. **Commits estruturados:**  
   - Todos os commits seguiam a convenção [**Conventional Commits**](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657), permitindo melhor rastreabilidade e documentação.

4. **Criação de Pull Requests (PRs):**  
   - Após a conclusão de uma tarefa, um [**PR**](https://github.com/Jorgeluisreis/CarteiraVacinal-backend/pulls?q=is%3Apr+is%3Aclosed) era criado para a branch **development**.  
   - A descrição do PR incluía detalhes sobre as funcionalidades desenvolvidas, modificações e eventuais correções realizadas.  
   - O líder técnico revisava e testava a implementação antes de realizar o merge.

### 🔄 Fluxo de Desenvolvimento

1. Desenvolvimento da funcionalidade em uma branch específica. 
2. Commits organizados seguindo a convenção de commits.
3. Criação de Pull Request (PR) para a branch development.
4. Revisão e testes pelo líder técnico.
5. Merge na branch development após aprovação. 
6. **Ação do GitHub Actions:**  
   - Após o merge, o **GitHub Actions** era acionado automaticamente.  
   - O frontend era **compilado e uma imagem Docker era gerada**.  
   - Essa imagem era utilizada na infraestrutura do site para garantir a implantação contínua.

Esse fluxo garantiu um desenvolvimento ágil, organizado e alinhado às boas práticas do mercado.

---

## 💬 Depoimentos

### **Jorge Luis**

<div style="display: flex; align-items: flex-start; margin-bottom: 20px;">
  <img src="https://i.imgur.com/7583zV4.jpeg" alt="Jorge Luis" style="width: 70px; height: 70px; border-radius: 50%; margin-right: 15px; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);">
  <div>
    <div style="font-weight: bold;">
      <a href="https://www.linkedin.com/in/ojorge-luis/" target="_blank">LinkedIn</a>
    </div>
    <p>
      "Assim como no Frontend, fui liderança técnica no Backend. Por ser mais familiarizado, foquei no Desenvolvimento, aplicando padrões de usos, como a Programação Orientada a Objetos, Repository Pattern, Injeção de Dependência para manter o fluxo de Desenvolvimento saudável, o mais genérico e legível possível sempre buscando validações centralizadas, assim de evitar demais verificações pelo Frontend. Foi uma experiência incrível, o uso do Java e o Spark foram essenciais pela praticidade e pela manutenção futura (se houver) do projeto."
    </p>
  </div>
</div>

---

## 🚀 Como Rodar o Backend

Siga os passos abaixo para rodar o **Backend** localmente:

1. **Clone o repositório**:  
   Primeiro, clone o repositório do backend:
   ```sh
   git clone https://github.com/Jorgeluisreis/CarteiraVacinal-backend.git
   cd CarteiraVacinal-backend
   ```

2. **Instalação do Maven**:
Certifique-se de ter o Maven instalado no seu computador. Caso não tenha, clique [aqui](https://dicasdeprogramacao.com.br/como-instalar-o-maven-no-windows/) para ter acesso ao tutorial.


3. **Configuração do Banco de Dados**:
Para rodar o backend, **é necessário ter o MySQL instalado e configurado**. Após configurar o banco de dados, execute o script de criação das tabelas contido na pasta ``data`` para criar as tabelas e dados necessários. O arquivo se chama ``script_sql_vacina.txt``.

4. **Configuração do Arquivo**
 ``.env``:
Na pasta ``src/main/resources``, crie um arquivo ``.env`` com as seguintes variáveis de configuração:
    ```bash
    DB_HOST=Endereço do Banco de Dados 
    DB_PORT=Porta do Banco de dados
    DB_NAME=vacinacao
    DB_USER=root
    DB_PASSWORD=Senha criada
    ```
    Com essas configurações, você estará pronto para interagir com o banco de dados local.
---

5. **Rodando o Backend**:
Com o Maven instalado e o banco de dados configurado, basta rodar o comando abaixo para compilar e rodar a aplicação:
    ```bash
    mvn clean install
    mvn exec:java
    ```
    O backend estará disponível para interações via API. 🚀
---

## 🚀 Como Consumir a API com Postman

### O que é o Postman?

O **Postman** é uma ferramenta popular para testar e consumir APIs. Ele permite que você faça requisições HTTP de forma simples e intuitiva, além de visualizar respostas, testar diferentes cenários e automatizar testes de APIs. Usar o Postman é uma forma prática de interagir com a **API do Backend do projeto CarteiraVacinal**, permitindo que você envie dados, faça consultas e visualize as respostas da API sem precisar escrever código.

### Por que Usar o Postman nesta Aplicação?

Usar o **Postman** para consumir a API facilita o processo de desenvolvimento e testes. Com ele, você pode:

- Testar as rotas da API de forma rápida e fácil.
- Interagir tanto com a versão local quanto com a versão em produção da API.
- Explorar todas as funcionalidades disponíveis (CRUD de Pacientes, Imunizações, etc.).
- Organizar e salvar requisições para reutilização posterior.

### Como Importar a Collection para o Postman

1. **Baixar o Arquivo da Collection**:  
   Na pasta `data` do repositório, você encontrará um arquivo chamado `CarteiraVacinal.postman_collection`. Esse arquivo contém todas as rotas da API, prontas para serem usadas no **Postman**.

2. **Abrir o Postman**:  
   Caso não tenha o Postman instalado, você pode baixá-lo gratuitamente no [site oficial](https://www.postman.com/downloads/), caso já tenha o **Postman** mas não sabe usar, clique [aqui](https://www.youtube.com/watch?v=64-O-dDR7ic-) para assistir um tutorial introdutório.


3. **Importar a Collection**:
   - Abra o **Postman**.
   - Clique em **Import** no canto superior esquerdo da tela.
   - Selecione o arquivo `CarteiraVacinal.postman_collection.json` que você baixou da pasta `data`.
   - Após a importação, todas as rotas estarão disponíveis no **Postman**.

4. **Configuração da URL**:  
   Ao importar a collection, as rotas da API estarão configuradas para o ambiente de produção. Caso estiver rodando localmente, a URL da API é ``http://localhost:3300``.

5. **Consumindo a API**:  
   Agora, você pode começar a testar a API, realizando operações como:
   - **Cadastrar um paciente** (POST)
   - **Consultar informações de vacinas aplicadas** (GET)
   - **Atualizar dados de pacientes** (PUT)
   - **Excluir um paciente** (DELETE)

    Agora, basta explorar, testar e interagir com a API de forma prática e eficiente utilizando o **Postman**. 🚀

---
## 📜 Licença

Este projeto é de código aberto e com a Licença MIT, além de estar disponível para uso educacional e colaborativo. Clique [aqui](https://github.com/Jorgeluisreis/CarteiraVacinal-frontend/tree/development?tab=License-1-ov-file) para saber mais.

---

💡 Projeto desenvolvido para a Hackathon 2025 do [**Programa 1000Devs**](https://www.jnjmedtech.com/pt-br/patient/1000-devs-talentos-para-o-bem-na-saude) 🎯
