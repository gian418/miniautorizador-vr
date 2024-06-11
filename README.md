# Mini Autorizador

A VR processa diariamente diversas transações de Vale Refeição e Vale Alimentação, entre outras. De forma resumida, as transações iniciadas nas maquininhas de cartão chegam até uma de nossas aplicações, conhecida como *autorizador*, que realiza uma série de verificações e análises, conhecidas como *regras de autorização*.

## Observações durante o desenvolvimento

- **Criptografia de Dados**: Embora seja essencial criptografar dados sensíveis no banco de dados, como números de cartão e senhas, esse não foi o foco deste desafio. Portanto, a criptografia não foi aplicada.
- **Histórico de Transações**: Um histórico de transações é mantido. Ao criar um cartão, uma transação de entrada é registrada. Todas as transações efetuadas via endpoint `/transacoes` são consideradas saídas.
- **Controle de Concorrência**: Para garantir a consistência dos dados durante as transações, o método `salvar` foi anotado com `@Transactional`. Isso assegura que todo o acesso ao banco de dados durante a transação ocorra em uma única transação.
- **Lock na Tabela de Saldo**: Além do `@Transactional`, foi adicionado um lock na tabela de saldo para evitar acessos concorrentes ao saldo do cartão. É reconhecido que isso pode causar problemas de lock no banco de dados.
- **Processamento Síncrono**: Considerou-se a implementação de uma fila para processamento de transações. No entanto, como é necessário fornecer uma resposta imediata na maquininha, optou-se por um processo síncrono.