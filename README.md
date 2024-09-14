
# Sistema de Gerenciamento de Tarefas

### Desafio Técnico

## Índice
1. [Descrição](#descrição)
2. [Funcionalidades](#funcionalidades)
3. [Tecnologias Utilizadas](#tecnologias-utilizadas)
4. [Requisitos](#requisitos)
5. [Instalação](#instalação)
6. [Uso](#uso)
7. [Testes](#testes)
8. [Documentação da API](#documentação-da-api)
9. [Contribuições](#contribuições)

---

## Descrição

O **Sistema de Gerenciamento de Tarefas** é uma aplicação web que permite aos usuários criar e gerenciar listas de tarefas com itens associados. A aplicação oferece funcionalidades para criação, edição, remoção e organização de tarefas, bem como filtragem e destaque de itens prioritários. O sistema foi desenvolvido com base nos seguintes requisitos funcionais e de negócio:

- **Criação de Listas e Itens**: Os usuários podem criar listas e adicionar tarefas com diferentes prioridades.
- **Gerenciamento de Itens**: Possibilidade de alterar o estado dos itens (completado ou não).
- **Visualização**: Ordenação e destaque para melhor usabilidade e organização.

---

## Funcionalidades

1. **Criação e Gerenciamento de Listas**: 
   - Criação de listas personalizadas.
   - Adição e remoção de itens de tarefas.

2. **Estado dos Itens**: 
   - Alterar o estado de conclusão das tarefas.

3. **Filtragem e Ordenação**:
   - Filtragem de itens concluídos e em aberto.
   - Destaque de itens prioritários.

4. **Prioridade**:
   - Definir itens prioritários dentro das listas para facilitar a organização.

---

## Tecnologias Utilizadas

- **Java**: Para o desenvolvimento da API.
- **Spring Boot**: Framework utilizado para construção da aplicação.
- **Spring Data JPA**: Para a camada de persistência de dados.
- **MySQL**: Banco de dados relacional utilizado em produção.
- **Swagger**: Documentação da API (disponível em `/swagger-ui/index.html`).
- **JUnit**: Testes unitários.
- **Maven**: Gerenciamento de dependências e build.

---

## Requisitos

Para rodar este projeto, você precisará de:

- **Java 17+**
- **Maven**
- **MySQL**
- **Spring boot 3**

---

## Instalação

1. Clone o repositório:
   ```bash
   git clone
   cd task-management


2. Configure o banco de dados:
  ```bash
Certifique-se de que você tenha o MySQL rodando.
Crie o banco de dados utilizando o SQL fornecido

CREATE DATABASE IF NOT EXISTS task;
USE task;

CREATE TABLE tb_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE tb_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    completed BOOLEAN,
    highlighted BOOLEAN,
    priority INT,
    user_id BIGINT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);

INSERT INTO tb_user (name) VALUES ('Alice'), ('Bob'), ('Charlie');
INSERT INTO tb_task (name, completed, priority, highlighted, user_id) VALUES 
    ('Task 1', false, 5, 1, 1),
    ('Task 2', true, 4, 1, 1),
    ('Task 3', false, 3, 1, 2),
    ('Task 4', true, 2, 1, 3);
```
3. Rode o projeto localmente:
 ```bash
 mvn spring-boot:run
```
4. Acesse a documentação da API no navegador:
```bash
http://localhost:8080/swagger-ui/index.html#/
```
## Uso

Após iniciar o projeto, você pode usar a interface do Swagger para explorar a API. Algumas das operações principais disponíveis são:

1. **Listar todas as tarefas**: `GET /tasks`
2. **Criar uma nova tarefa**: `POST /tasks`
3. **Atualizar uma tarefa existente**: `PUT /tasks/{id}`
4. **Deletar uma tarefa**: `DELETE /tasks/{id}`

Exemplos de endpoints estão disponíveis na [Documentação da API](#documentação-da-api).

## Testes

Este projeto inclui testes automatizados utilizando **JUnit**. Para executar os testes, utilize o comando:

```bash
mvn test
```
## Documentação da API

A API do **Sistema de Gerenciamento de Tarefas** está documentada utilizando **Swagger**. Esta documentação fornece uma visão interativa dos endpoints disponíveis e permite que você teste as funcionalidades da API diretamente através da interface do Swagger UI.

### Acessando a Documentação

Para visualizar e interagir com a documentação da API, acesse o seguinte link no seu navegador:

- [Swagger UI - Documentação da API](http://localhost:8080/swagger-ui/index.html#/)

### O que você pode fazer na Swagger UI

1. **Explorar Endpoints**: Navegue pelos diferentes endpoints da API disponíveis e veja suas descrições.
2. **Testar Endpoints**: Utilize a interface interativa para enviar requisições e visualizar as respostas diretamente no navegador.
3. **Ver Detalhes**: Confira detalhes sobre os parâmetros de entrada e as respostas esperadas para cada endpoint.
4. **Consultar Exemplos**: Veja exemplos de requisições e respostas para entender melhor como interagir com a API.

A documentação Swagger fornece uma maneira prática e eficaz de entender e testar a API sem a necessidade de ferramentas adicionais.





