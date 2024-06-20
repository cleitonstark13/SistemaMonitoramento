/*
 * AUTOR CLEITON ALVES E SILVA JÚNIOR
 */
package GUI;

import javax.swing.*;

import SERVER.Server;
import SERVER.ServerFileHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMonitor extends JFrame {

	private static final long serialVersionUID = 6316740636615215427L;
	private JPanel serverPanel;
	private List<Server> servers;
	private Image backgroundImage;
	private List<Timer> timers;
	private int servidoresPorLinha = 4; // Valor padrão

	@SuppressWarnings("serial")
	public ServerMonitor() {
		setTitle("Monitoramento de Servidores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		/*
		 * Minhas cores
		 */
		Color minhaCorBorder = new Color(23, 27, 31);
		Color minhaCorAzul = new Color(44, 93, 170);

		/*
		 * Imagem logo do Sistema de Monitoramento
		 */
		ImageIcon imagemTituloJanela = new ImageIcon(getClass().getResource("/IMG/logo.png"));
		setIconImage(imagemTituloJanela.getImage());
		backgroundImage = new ImageIcon(getClass().getResource("/IMG/fundo.png")).getImage(); // Carrega a imagem do
		ImageIcon logoSistema = new ImageIcon(getClass().getResource("/IMG/logoSistema.png"));																		// fundo
		ImageIcon buttonSalvar = new ImageIcon(getClass().getResource("/IMG/salvar.png"));
		ImageIcon buttonConfiguracao = new ImageIcon(getClass().getResource("/IMG/configuracao.png"));

		/*
		 * ArrayList
		 */
		servers = new ArrayList<>();
		timers = new ArrayList<>();

		/*
		 * Inicializa o painel do servidor antes de carregar os servidores do arquivo
		 */
		serverPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		serverPanel.setLayout(new GridLayout(0, 4, 10, 10)); // Layout com 4 colunas
		serverPanel.setOpaque(false); // Tornar o panel transparente
		serverPanel.setBorder(BorderFactory.createLineBorder(minhaCorBorder, 2));

		/*
		 * Carregar servidores do arquivo ao iniciar
		 */
		loadServersFromFile();

		/*
		 * Painel de título 
		 */
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		titlePanel.setOpaque(false); // Tornar o painel transparente
		titlePanel.setBorder(BorderFactory.createLineBorder(minhaCorBorder, 2));

		// Titúlo do sistema com imagem
		JLabel titleLabel = new JLabel(logoSistema); // Adiciona essa linha
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza a imagem
		titleLabel.setVerticalAlignment(SwingConstants.CENTER); // Centraliza a imagem
		titleLabel.setForeground(Color.WHITE);
		titlePanel.add(titleLabel, BorderLayout.CENTER); // Adiciona o título ao centro e centralizado horizontalmente

		// Painel para o botão de configurações alinhado à direita
		JPanel btnConfiguracaoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnConfiguracaoPanel.setOpaque(false); // Tornar o painel transparente

		JButton configuracoesButton = new JButton(buttonConfiguracao);
		configuracoesButton.setText("Configurações");
		configuracoesButton.setForeground(Color.WHITE);
		configuracoesButton.setContentAreaFilled(false); // Não preencher área do botão
		configuracoesButton.setBorderPainted(false); // Não pintar a borda
		configuracoesButton.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		configuracoesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirConfiguracoes();
			}
		});

		btnConfiguracaoPanel.add(configuracoesButton); // Adiciona o botão de configurações ao painel

		// Adiciona o painel do botão de configurações à região leste do BorderLayout do
		// titlePanel
		titlePanel.add(btnConfiguracaoPanel, BorderLayout.EAST);

		/*
		 * Painel de cadastro
		 */
		JPanel addServerPanel = new JPanel(new GridLayout(3, 2)) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		addServerPanel.setOpaque(false); // Tornar o panel transparente
		addServerPanel.setBorder(BorderFactory.createLineBorder(minhaCorBorder, 2));

		JLabel hostnameLabel = new JLabel("HOSTNAME:");
		hostnameLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 15
		hostnameLabel.setForeground(Color.WHITE); // Define a cor do texto como branca
		hostnameLabel.setBorder(BorderFactory.createEmptyBorder(0, 310, 0, 0));

		JLabel ipLabel = new JLabel("IP:");
		ipLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 15
		ipLabel.setForeground(Color.WHITE); // Define a cor do texto como branca
		ipLabel.setBorder(BorderFactory.createEmptyBorder(0, 363, 0, 0));

		JTextField hostnameField = new JTextField();
		hostnameField.setBackground(Color.WHITE); // Define o fundo como branco

		JTextField ipField = new JTextField();
		ipField.setBackground(Color.WHITE); // Define o fundo como branco

		JButton addButton = new JButton("Adicionar Servidor", buttonSalvar);
		addButton.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 12
		addButton.setForeground(Color.WHITE);
		addButton.setBackground(minhaCorAzul);

		JLabel creditosLabel = new JLabel("© Cleiton Júnior - v. 2.0 | Todos os direitos reservados.");
		creditosLabel.setFont(new Font("Arial", Font.BOLD, 10));
		creditosLabel.setForeground(Color.WHITE);
		creditosLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

		addServerPanel.add(hostnameLabel);
		addServerPanel.add(hostnameField);
		addServerPanel.add(ipLabel);
		addServerPanel.add(ipField);
		addServerPanel.add(creditosLabel);
		addServerPanel.add(addButton);

		// Adiciona ActionListener ao botão
		ActionListener addServerAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String hostname = hostnameField.getText();
				String ip = ipField.getText();
				if (!hostname.isEmpty() && !ip.isEmpty()) {
					addServer(new Server(hostname, ip));
					hostnameField.setText("");
					ipField.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
				}
			}
		};
		addButton.addActionListener(addServerAction);

		// Adiciona ActionListener aos campos de texto
		hostnameField.addActionListener(addServerAction);
		ipField.addActionListener(addServerAction);

		// Adiciona todos os componentes à janela
		add(titlePanel, BorderLayout.NORTH);
		add(new JScrollPane(serverPanel) {
			{
				setOpaque(false);
				getViewport().setOpaque(false);
			}
		}, BorderLayout.CENTER);
		add(addServerPanel, BorderLayout.SOUTH);

		/*
		 * Chama o método pack para dimensionar automaticamente a janela
		 */
		pack();
	}

	// Método que carrega o nome da empresa no arquivo Empresa.txt
	@SuppressWarnings("unused")
	private String carregarNomeEmpresa() {
		try (BufferedReader reader = new BufferedReader(new FileReader("empresa.txt"))) {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Método para abrir a janela de configurações
	private void abrirConfiguracoes() {
		ConfiguracoesDialog dialog = new ConfiguracoesDialog(this);
		dialog.setVisible(true);
	}

	// Método para atualizar o layout com base na quantidade de servidores por linha
	public void setServidoresPorLinha(int servidoresPorLinha) {
		this.servidoresPorLinha = servidoresPorLinha;
		updateServerPanelLayout();
		pack(); // Redimensiona a janela após a atualização do layout
	}

	// Método para atualizar o layout do painel de servidores com base na quantidade
	// escolhida
	private void updateServerPanelLayout() {
		int numServidores = servers.size();
		int servidoresPorLinha = this.servidoresPorLinha;

		// Calcular o número de linhas necessárias, arredondando para cima
		int numRows = (int) Math.ceil((double) numServidores / servidoresPorLinha);

		serverPanel.setLayout(new GridLayout(numRows, servidoresPorLinha, 10, 10));
		serverPanel.removeAll(); // Limpar os componentes atuais
		for (Server server : servers) {
			addServerToPanel(server); // Recriar os painéis dos servidores
		}
		serverPanel.revalidate();
		serverPanel.repaint();
	}

	// Método que adiciona os dados de um servidor no arquivo servers.txt

	private void addServer(Server server) {
		servers.add(server);
		ServerFileHandler.saveServers(servers); // Salvar servidores no arquivo
		addServerToPanel(server);
		SwingUtilities.invokeLater(this::pack);
	}

	// Método que adiciona os servidores salvos no arquivo servers.txt na interface
	// do sistema

	private void addServerToPanel(Server server) {
		Color minhaCor = new Color(35, 39, 43);
		Color minhaCorBorder = new Color(45, 50, 55);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(minhaCor);
		panel.setBorder(BorderFactory.createLineBorder(minhaCorBorder, 2)); // Define borda branca

		JLabel statusLabel = new JLabel(server.getHostname(), SwingConstants.CENTER);
		statusLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Define fonte Arial e tamanho 25
		statusLabel.setForeground(Color.WHITE); // Define a cor do texto como branca

		// Carrega fundo das imagens
		ImageIcon greenBackground = new ImageIcon(getClass().getResource("/IMG/fundoVerde.png"));
		ImageIcon redBackground = new ImageIcon(getClass().getResource("/IMG/fundoVermelho.png"));

		// Cria o Label das imagens
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setLayout(new BorderLayout());
		backgroundLabel.add(statusLabel, BorderLayout.CENTER);

		// JLabel com o nome do servidor
//	    JLabel hostnameLabel = new JLabel(server.getHostname(), SwingConstants.CENTER);
//	    hostnameLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 12
//	    hostnameLabel.setForeground(Color.WHITE); // Define a cor do texto como branca
//	    hostnameLabel.setOpaque(false); // Torna o fundo transparente

		// JLabel SERVIDOR
		JLabel hostnameLabel = new JLabel("SERVIDOR", SwingConstants.CENTER);
		hostnameLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 12
		hostnameLabel.setForeground(Color.WHITE); // Define a cor do texto como branca
		hostnameLabel.setOpaque(false); // Torna o fundo transparente

		JPanel hostnamePanel = new JPanel(new BorderLayout());
		hostnamePanel.add(hostnameLabel, BorderLayout.CENTER);
		hostnamePanel.setOpaque(false); // Torna o painel transparente

		panel.add(hostnamePanel, BorderLayout.NORTH);
		panel.add(backgroundLabel, BorderLayout.CENTER);

		// Adiciona botão de exclusão
		Color minhaCorVermelha = new Color(234, 45, 64);

		ImageIcon buttonExcluir = new ImageIcon(getClass().getResource("/IMG/excluir.png"));
		JButton deleteButton = new JButton("Excluir", buttonExcluir);
		deleteButton.setForeground(minhaCorVermelha);
		deleteButton.setBackground(Color.WHITE);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeServer(server);
				serverPanel.remove(panel);
				serverPanel.revalidate();
				serverPanel.repaint();
				SwingUtilities.invokeLater(ServerMonitor.this::pack);
			}
		});
		panel.add(deleteButton, BorderLayout.SOUTH);

		serverPanel.add(panel);
		serverPanel.revalidate();
		serverPanel.repaint();

		Timer timer = new Timer(true);
		timers.add(timer);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				boolean isAlive = isServerAlive(server.getIp());
				System.out.println("Verificando status do servidor " + server.getHostname() + ": "
						+ (isAlive ? "Ligado" : "Desligado"));
				backgroundLabel.setIcon(isAlive ? greenBackground : redBackground);
			}
		}, 0, 30000); // Atualiza a cada 30 segundos
	}

	// Método que verifica se um servidor está acessível na rede através do seu IP

	private boolean isServerAlive(String ip) {
		try {
			InetAddress address = InetAddress.getByName(ip); // classe InetAdderess
			return address.isReachable(2000); // Timeout de 2 segundos
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Método que carrega os dados dos servidores no arquivo servers.txt

	private void loadServersFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader("servers.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					Server server = new Server(parts[0], parts[1]);
					servers.add(server);
					addServerToPanel(server); // Adicionar ao painel e iniciar o monitoramento
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Método que remove os dados de um servidor no arquivo servers.txt

	private void removeServer(Server server) {
		servers.remove(server);
		ServerFileHandler.saveServers(servers);

		// Remove associated timer
		for (Timer timer : timers) {
			timer.cancel();
		}
		timers.clear();

		// Remove the panel corresponding to the server
		Component[] components = serverPanel.getComponents();
		for (Component component : components) {
			if (component instanceof JPanel) {
				JPanel panel = (JPanel) component;
				Component[] innerComponents = panel.getComponents();
				for (Component innerComponent : innerComponents) {
					if (innerComponent instanceof JLabel) {
						JLabel label = (JLabel) innerComponent;
						String labelText = label.getText();
						if (labelText != null && labelText.equals(server.getHostname())) {
							serverPanel.remove(panel);
							break;
						}
					}
				}
			}
		}

		SwingUtilities.invokeLater(this::pack); // Redimensionar a janela após remover o servidor
	}
}
