# Controle-de-biblioteca
### Trabalho da disciplina de Paradigma de programação

* Em Java, crie um programa para controle de uma biblioteca que atenda aos seguintes requisitos mínimos:* 
* Os dados de livro devem ser armazenados em uma base de dados relacional (postgres, mysql, sqlserver etc.)
* Os dados de outras entidades podem ser armazenados em listas em memória (quem desejar, pode armazená-los no banco também).
* Deve ser possível cadastrar livros: incluir livros, alterar livros, excluir livros, listar livros, buscar livros pelo ISBN, por parte do * título e pela editora.
* Cada livro deve conter informações acerca de: autores, edição, editora, nome, ano.
* Deve ser possível cadastrar usuários: incluir usuários, excluir usuários, listar usuários, buscar usuários por parte do nome e pelo login.
* Deve ser possível efetuar empréstimos: emprestar livros, renovar empréstimo, devolver livros, listar empréstimos, buscar empréstimos por * livro (ISBN e título), por exemplar de livro e por usuário.
* Deve ser possível efetuar reservas de livros: solicitar reserva de livro, cancelar reserva de livro.
* Deve ser possível pagar multas pendentes.
* Os alunos podem retirar até 3 livros, por uma semana. Os professores podem retirar até 5 livros, por 15 dias.
* Os empréstimos são renovados pelo mesmo período permitido para o usuário em questão, não sendo permitidos, no entanto, renovações de livros que estão com o exemplar reservado por algum usuário. 
* Usuários com multas só podem ter um único livro emprestado.
* Não é permitido aos usuários com multas reservarem livros.
* A multa é de R$ 1,00 por dia (útil ou não útil)

![Capturar](https://user-images.githubusercontent.com/9409514/66796344-bbf3a200-eedd-11e9-8788-bb86876413b4.jpg)


### Detalhes
* Feito em JavaFX
* Apache NetBeans 11.1
#### Bibliotecas usadas
* jfoenix-8.0.8  - Para a interface nos padrões do Material Design
* joda-time-2.10.4  - Para o cálculo e formatação de datas
* sqlite-jdbc-3.23.1 - Drive para fazer uso do sqlite

#### OBS: Pelo motivo de que o prazo da entrega foi finalizada, 14/10/2019, o projeto não está totalmente completo, mas funcional, e não será mais continuado 

# Professor Eduardo Piveta
  Universidade Federal de Santa Maria
