# Mini Autorizador

A VR processa diariamente diversas transações de Vale Refeição e Vale Alimentação, entre outras. De forma resumida, as transações iniciadas nas maquininhas de cartão chegam até uma de nossas aplicações, conhecida como *autorizador*, que realiza uma série de verificações e análises, conhecidas como *regras de autorização*.

Regras de autorização:
- O cartão existir
- A senha do cartão for a correta
- O cartão possuir saldo disponível

## Observações durante o desenvolvimento

- **Criptografia de Dados**: Embora seja essencial criptografar dados sensíveis no banco de dados, como números de cartão e senhas, esse não foi o foco deste desafio. Portanto, a criptografia não foi aplicada.
- **Histórico de Transações**: Um histórico de transações é mantido. Ao criar um cartão, uma transação de entrada é registrada. Todas as transações efetuadas via endpoint `/transacoes` são consideradas saídas.
- **Controle de Concorrência**: Para garantir a consistência dos dados durante as transações, o método `salvar` foi anotado com `@Transactional`. Isso assegura que todo o acesso ao banco de dados durante a transação ocorra em uma única transação.
- **Lock na Tabela de Saldo**: Além do `@Transactional`, foi adicionado um lock na tabela de saldo para evitar acessos concorrentes ao saldo do cartão. É reconhecido que isso pode causar problemas de lock no banco de dados.
- **Processamento Síncrono**: Considerou-se a implementação de uma fila para processamento de transações. No entanto, como é necessário fornecer uma resposta imediata na maquininha, optou-se por um processo síncrono.
- **Padrão de Projeto**: Durante o processo de realização de uma transação, são realizadas diversas validações. Por uma questão de simplicidade, optei por não introduzir uma complexidade adicional. No entanto, uma abordagem alternativa seria a aplicação do padrão Chain of Responsibility. Nesse padrão, cada validação seria encapsulada em uma classe separada e encadeada com as demais até o ponto de persistência dos dados. Isso proporcionaria uma estrutura mais flexível e escalável para lidar com as diferentes etapas de validação. Caso precisar adicionar uma validação adicional futura, seria só adicionar mais uma classe com esse encapsulamento no meio da corrente de validações.
## Versões utilizadas
- Java 21
- Spring Boot 3.3.0
- Maven 3.8.7
- JaCoCo 0.8.11

## Rodar o serviço
Um arquivo docker foi fornecido com configurações para MySQL e MongoDB. Optei por usar o MySQL devido à minha maior familiaridade com bancos de dados relacionais. Adicionei o arquivo `docker-compose.yml` na pasta `docker` do projeto.

Apos subir o docker, podemos rodar o serviço. Você pode fazer isso rodando os seguintes comando na raiz do projeto
```
mvn clean install
```
E quando terminar rodar o comando seguinte para subir o serviço
```
mvn spring-boot:run
```

## Cobertura de código através de testes

A cobertura atingiu 100%. Todos os testes foram escritos para validar as regras de negócio e verificar se os resultados estão de acordo com o esperado.

Após executar o comando mvn clean install, você pode acessar o relatório do JaCoCo no arquivo `index.html`. Este arquivo está localizado no diretório `target/site`, partindo da raiz do projeto. O relatório fornecerá uma visão detalhada da cobertura de código do projeto.

## Endpoints

As autenticações são do tipo BASIC.

- **Usuário**: `user`
- **Senha**: `password`
- Todos os endpoints retornarão HTTP Status `401` caso não estejam autenticados.

## Criar Cartão

**[POST]** `http://localhost:8080/cartoes`

**Body:**
```json
{
    "senha": "123456",
    "numeroCartao": "6549873025123123"
}
```

### Possíveis respostas:
#### Sucesso
Status code `201`
```json
{
    "senha": "123456",
    "numeroCartao": "6549873025123123"
}
```

#### Cartão já existente
Status code `422`
```json
{
    "senha": "123456",
    "numeroCartao": "6549873025123123"
}
```

## Consultar Saldo

**[GET]** `http://localhost:8080/cartoes/{numeroCartao}`

Obs: {numeroCartao} deve ser substituído pelo numero do cartão.

### Possíveis respostas:
#### Sucesso
- Http Status `200`
- Body `495.15`

#### Cartão não existe
- Http Status `404`
- Sem body

## Realizar uma transação

**[POST]** `http://localhost:8080/transacoes`
```json
{ 
    "numeroCartao": "6549873025634501",
    "senhaCartao": "1234",
    "valor": 10.00
}
```

### Possíveis respostas:
#### Sucesso
- Http Status `201`
- Body `OK`

#### Caso alguma regra tenha sido barrada
- Http Status `422`
- Body `SALDO_INSUFICIENTE | SENHA_INVALIDA | CARTAO_INEXISTENTE` (dependendo da regra que impediu a autorização)