# Programacao-Orientada-A-Objetos
Repositório conterá o projeto de Programação Orientada a Objetos. **Todos os dados são fictícios e tem a finalidade de testar os conhecimentos aprendidos em aula!**

## Configurações iniciais
- [X] Criar o Banco de Dados;
- [X] Criar o datasource(-ds) e colocar ele na pasta deploy do Jboss;
- [X] Passar a pasta META-INF para dentro do meu scr;
- [X] Colocar dentro do pacote service o GenericService;
- [X] Configurar página inicial do projeto;


### Modelagem e Mapeamento das classes:
- [X] Modelagem da classe **Funcionário**;
- [X] Modelagem da classe **Filial**;
- [X] Modelagem da classe **Endereco**;


### Tela Inicial do projeto:
- [X] Crie uma página inicial;
- [X] Configure está página como a tela inicial do projeto;
- [X] Crie um link nela praa a tela **Gerenciar Filial**;
- [X] Crie um link nela praa a tela **Gerenciar Funcionário**;
- [X] Crie um link nela praa a tela **Gerenciar Relatório de Funcionários**;

### Tela Gerenciar Filial:
- [X] Cadastrar filial
- [X] Editar Filial;
- [X] Listar Filial;
- [X] Ordenar filiais pelo nome;
- [X] Incluir todos os dados da Filial e **endereço** da mesma.
- [ ] Adicionar um campo que mostra o número de funcionários daquela filial;

### Tela Gerenciar Funcionários:
- [X] Cadastrar Funcionário;
- [X] Editar Funcionário;
- [X] Listar Funcionário;
- [X] Excluir Funcionário;
- [X] Incluir todos os dados de Funcionários e seus respectivo **endereço**;
- [X] Ordenar os funcionários pelo nome;
- [X] Certifique-se de que a edição de funcionários não permita a troca de filial e exiba um alerta caso o usuário tente fazê-lo;
- [X] Exibir uma mensagem caso algum campo fique em branco;

### Implementar filtro na tela de Gerenciar Funcionários:
- [X] Adicionar a funcionalidade de listagem de funcionário pelo nome ou parte dele ***USE LIKE**;
- [X] Caso campo fique em branco listar todos os funcionários;

### Tela de Relatórios:
- [X] Filtre funcionários por filial;
- [X] Filtre funcionários por faixa salarial;
- [X] Implemente campos de seleção para filial;
- [X] Implemente campos de input para a faixa salarial;
- [X] Liste os funcionários por salário em **ORDEM DECRESCENTE**;
- [X] Incluir os dados solicitados **NOME, SALARIO, CPF E NOME DA FILIAL**
- [X] Considere também a opção de "Todas as Filiais" no filtro;

### Formatação e validação dos dados:
- [X] Formatação adequada para moeda, CPF e CNPJ nas listagens;
- [X] Todos os dados de Filial são obrigatórios;
- [X] Todos os dados de Funcionário são obrigatórios;
- [X] Caso fique em branco um dos campos deve-se emitir um alerta na tela;
- [X] Implementar msgs de aviso caso as buscas e filtros não retornem nenhum valor;
