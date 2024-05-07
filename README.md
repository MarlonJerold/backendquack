# Aplicativo de Gestão Financeira 📱 🏦 💳 

*Este aplicativo oferece uma solução abrangente para gestão financeira pessoal. Com ele, os usuários podem registrar suas despesas e receitas de forma simples e rápida. Além disso, é possível definir orçamentos personalizados para diferentes categorias de gastos, ajudando a manter o controle financeiro*.

*A ferramenta também disponibiliza relatórios detalhados das transações realizadas e do desempenho financeiro do usuário ao longo do tempo. Isso proporciona uma visão clara da situação financeira e auxilia na tomada de decisões*.

*Para facilitar ainda mais a administração financeira, o aplicativo permite o cadastro de cartões, onde os usuários podem visualizar os limites disponíveis e acompanhar os gastos realizados*.

*Além disso, a funcionalidade de transferência facilita a movimentação de recursos entre contas internas, proporcionando maior flexibilidade e controle sobre o dinheiro*.

# Tópicos

- [**Recursos Principais**](#recursos-principais)
- [**Vídeos Demonstrativos**](#vídeos-demonstrativos)
- [**Stack que estou utilizando**](#stack-que-estou-utilizando)
  - [**Backend**](#backend)
  - [**Mobile**](#mobile)
- [**Arquitetura Geral**](#arquitetura-geral)
  - [**Componentes Principais**](#componentes-principais)
  - [**Fluxo de Funcionamento**](#fluxo-de-funcionamento)
- [**Processo de desenvolvimento**](#processo-de-desenvolvimento)
 
## Recursos Principais

- 💸 Controle de Despesas e Receitas: Registre suas despesas e receitas de forma fácil e rápida.
- 📊 Orçamentos Personalizados: Defina orçamentos personalizados para diferentes categorias de gastos.
- 📈 Relatórios Detalhados: Visualize relatórios detalhados de suas transações e desempenho financeiro.
- 💳 Cadastro de cartões: Mantenha seus Cartões e visualize seus limites disponíveis.
- 🔄 Transferência: Realização de transferências internas entre contas.

## Vídeos Demonstrativos

### Login

[Gravação de tela de 2024-04-29 18-57-49.webm](https://github.com/MarlonJerold/backendquack/assets/63025001/f308a30d-dcdd-4882-a694-5904c9c9eca6)

Estou utilizando Flutter para o Mobile, e Java com Spring Boot para o backend, o login está sendo realizado via API, com a criação de sistema de autenticação utilizando Spring Security, com JWT.

## Stack que estou utilizando 

O projeto está sendo uma API REST, estou utilizando principios de orientação a objeto para criação de serviços, implementando padrões de projeto como Strategy. A api utiliza de técnicas de ORM para persistência no banco de dados, o que significa que estou utilizando JPA para o mapeamento e o hibernate para fazer a mágica acontecer. 

## Backend
[![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)](https://www.java.com/) [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot) [![JPA](https://img.shields.io/badge/JPA-6600cc?style=flat-square&logo=java&logoColor=white)](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html) [![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=hibernate)](https://hibernate.org/) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)](https://www.postgresql.org/) [![H2 Database](https://img.shields.io/badge/H2_Database-00457C?style=flat-square&logo=h2)](https://www.h2database.com/html/main.html) [![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/) [![Render](https://img.shields.io/badge/Render-333333?style=flat-square&logo=render)](https://render.com/)

## Mobile
[![Dart](https://img.shields.io/badge/Dart-0175C2?style=flat-square&logo=dart&logoColor=white)](https://dart.dev/) [![Flutter](https://img.shields.io/badge/Flutter-02569B?style=flat-square&logo=flutter&logoColor=white)](https://flutter.dev/)

## Arquitetura Geral:
O sistema será uma API REST baseada em princípios de orientação a objetos e implementará o padrão de projeto Strategy para flexibilidade e extensibilidade. A persistência de dados será realizada usando JPA para mapeamento objeto-relacional e Hibernate como provedor JPA.

### Componentes Principais:
#### Controladores (Controllers):
- Responsáveis por receber as requisições HTTP e roteá-las para os serviços apropriados.
#### Serviços (Services):
- Implementam a lógica de negócios.
- Utilizam o padrão Strategy para permitir diferentes estratégias de processamento.
#### Repositórios (Repositories):
- Interfaces que definem operações de acesso a dados.
- Implementadas com JPA para interagir com o banco de dados.
####  Entidades (Entities):
- Modelam os dados do domínio.
- Anotadas com JPA para mapeamento com as tabelas do banco de dados.
####  DTOs (Data Transfer Objects):
- Representam os objetos que serão transferidos entre a API e o cliente.
- Podem ser usados para desacoplar a estrutura dos dados expostos pela API da estrutura interna das entidades.

### Fluxo de Funcionamento:
- 1 O cliente envia uma requisição HTTP para a API.
- 2 O Controlador recebe a requisição e a roteia para o Serviço apropriado.
- 3 O Serviço executa a lógica de negócios necessária, possivelmente utilizando diferentes estratégias conforme implementado pelo padrão Strategy.
- 4 O Serviço pode interagir com os Repositórios para acessar ou modificar dados no banco de dados.
- 5 Se necessário, os dados são convertidos em DTOs para serem enviados de volta ao cliente.

## Processo de desenvolvimento

O sistema veio com a necessidade minha de ajustar minhas despesas pessoas, e junto com a necessidade de várias pessoas em minha volta, dei início ao projeto criando um protótipo de baixa fidelidade no figma em seguida de alta fidelidade o linkd é [Figma](https://www.figma.com/file/jo9J4yQd5JDAaGcqWEPQow/Untitled?type=design&node-id=0-1&mode=design&t=qLfASTdTKsndbC27-0).

<img src="https://github.com/MarlonJerold/backendquack/assets/63025001/0d66ee42-b56d-4c5a-8d00-e9016bbd49d1" alt="sua imagem" width="300"/>

Depois do protótipo pronto, dei início ao desenvolvimento mobile. Enquanto mapeava as primeiras telas, comecei a criar o backend. Após desenvolver algumas telas, realizei uma pesquisa na internet, onde obtive mais de 100 pessoas interessadas em utilizar um aplicativo de finanças pessoais. Armazenei essas informações em um formulário no [Google Forms](https://docs.google.com/forms/d/117vmQ1WlsruxiFLah2dq90qXcvnDWjWYGkGxZlU_Yk4/edit). Ao acessar, você verá as perguntas que realizei. Através delas, recebi feedbacks de pessoas interessadas e sugestões valiosas que estão sendo consideradas. Nesse cenário, estou buscando compreender o comportamento e as dores dos usuários, visando oferecer uma melhor qualidade no aplicativo.

### Metas

Tornar o aplicativo disponível na play store para consumo público, ainda faltam muitas coisas, mas está sendo uma experiência muito gratificante e espero finaliza-lo logo.
Até mais!

Atenciosamente,
*Marlon, o Pato.*
