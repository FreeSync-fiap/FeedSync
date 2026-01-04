# FeedSync - Plataforma de Feedbacks (Fase 4)

Fala, pessoal! Esse é o repositório do **FeedSync**, o projeto que desenvolvemos para o Tech Challenge da Fase 4.

A ideia aqui foi criar uma solução para gerenciar avaliações de cursos e aulas. O sistema recebe os feedbacks dos alunos, avisa os admins se tiver algo urgente (tipo uma aula com áudio ruim) e ainda gera uns relatórios consolidados pra gente entender o que tá rolando.

## O que a gente usou (Stack)

O projeto é basicamente Java com Spring, mas a arquitetura é híbrida pra atender os requisitos de Cloud e Serverless:

* **Java 21** (Versão atualizada, claro).
* **Spring Boot 3** (Pra API principal).
* **Google Cloud Functions** (Para as partes Serverless: Notificações e Relatórios).
* **Firestore** (Banco NoSQL do Google).
* **Docker & Docker Compose** (Pra rodar tudo local sem dor de cabeça).
* **Firebase Emulator** (Pra simular o banco na sua máquina sem gastar crédito na nuvem).

## Como funciona a Arquitetura?

A gente dividiu o sistema em três partes pra respeitar a responsabilidade única:

1.  **FeedSync API (`feedsync-api`):** É o "cérebro". Gerencia usuários, cursos e recebe os feedbacks. Se o feedback for normal, ele só salva. Se for urgente, ele chama a função de notificação.
2.  **Notification Service (`notification-service`):** É uma função serverless. Ela só acorda quando chega um feedback urgente pra "fingir" que envia um e-mail pros admins (aparece no log).
3.  **Report Service (`report-service`):** Outra função serverless. Ela varre o banco, calcula as médias de notas e vê quais palavras a galera tá usando mais nos comentários.

## Como rodar na sua máquina

A gente configurou o Docker pra levantar tudo de uma vez, inclusive o emulador do banco de dados.

### Pré-requisitos
* Ter o **Docker** e o **Docker Compose** instalados e rodando.

### Passo a passo

1.  Clone esse repositório na sua máquina.
2.  Abra o terminal na pasta raiz do projeto (onde tá o `docker-compose.yml`).
3.  Rode o comando:

```bash
docker-compose up --build
