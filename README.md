Projeto de Monitoramento Bovino fazendo uso do SinalGo com o proposito do uso do mesmo para simulação de sensores 
em um determinado campo, fazendo uso de nodes para simulação de bovinos e os nós de sincronização para envio de dados para um
servidor RMI e log em arquivo de texto.

-- Instruções --
Faça o uso da ferramenta NetBeans para abrir o projeto do SinalGo, importe o projeto e inicie os serviços nessa ordem:
1º Classe do servidor
2º Classe do cliente
3º SinalGo

Com o SinalGo aberto, escolha a opção 'wsn1' no menu lateral e clique em 'Ok'.
Após o projeto ter sido iniciado, adicione 4 (quatro) elementos Nodes do tipo Sink, com a opção NoMobility ativada
nas posições 15,15; 15,85; 85,15 e 85,85. Após isso, adicione quantos elementos NodeBovino desejar dentro ou fora da 
área demarcada pelos Sinks.

O roteamento é automático após clicar no botão verde, o 'run'.
