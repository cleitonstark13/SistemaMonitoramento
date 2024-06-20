# SistemaMonitoramento
Sistema Monitor de Servidores desenvolvido em Java, utilizando a biblioteca Swing para a criação de uma interface gráfica.

Descrição do Sistema

Nome do Sistema: Monitor de Servidores
Desenvolvedor: Cleiton Alves e Silva Júnior

Funcionalidades Principais

1. Monitoramento de Servidores:

1.1 Permite adicionar servidores por meio de hostname e IP.
1.2 Verifique periodicamente a disponibilidade dos servidores.
1.3 Mostra o status dos servidores (online/offline) na interface gráfica.

2. Interface Gráfica:

2.1 Interface amigável e intuitiva utilizando Swing.
2.2 Painel principal com layout dinâmico, ajustando a quantidade de servidores por linha.
2.3 Imagens personalizadas para o fundo e ícones, como o logotipo do sistema, botões de salvar e configuração.

3. Configurações:

3.1 Janela de configurações que permite definir o número de servidores dispostos por linha.

4. Persistência de Dados:

4.1 Salve a lista de servidores em um arquivo (servers.txt).
4.2 Carregar automaticamente os servidores salvos ao iniciar o sistema.

- Estrutura do Código

-- GUI.ServerMonitor: Janela principal de monitoramento, contendo uma interface gráfica e uma lógica de monitoramento dos servidores.
-- SERVER.Server: Classe que representa um servidor, contendo hostname e IP.
-- SERVER.ServerFileHandler: manipula a leitura e escrita dos dados dos servidores no arquivo servers.txt.
-- GUI.ConfiguracoesDialog: Janela de configurações para ajustar o layout da interface principal.
-- MAIN.Main: Classe principal que inicia o aplicativo.

Execução do Sistema
Para executar o sistema, basta rodar a classe MAIN.Main. O sistema abrirá uma janela onde você pode adicionar, configurar e monitorar os servidores.

Como Adicionar Servidores
1. Insira o nome do host e o IP do servidor nos campos protegidos.
2. Clique no botão "Adicionar Servidor" para adicionar o servidor à lista e iniciar o monitoramento.

Configurações
1. Acesse a janela de configurações clicando no botão de configurações na interface principal.
2. Defina o número de servidores por linha conforme sua preferência e clique em "Salvar".

Monitoramento
O sistema verifica a cada 5 segundos a disponibilidade de cada servidor e atualiza a interface gráfica com o status atual.

