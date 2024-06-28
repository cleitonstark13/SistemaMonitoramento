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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMonitor extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 6316740636615215427L;
	private JPanel serverPanel;
	private List<Server> servers;
	private Image backgroundImage;
	private List<Timer> timers;
	private int servidoresPorLinha = 4; // Valor padrão
	private JButton configuracoesButton, addButton;
	private JTextField ipField, hostnameField;
	private int squareWidth = 185; // Tamanho padrão
    private int squareHeight = 185; // Tamanho padrão

	public ServerMonitor() {
		initializeFrame();
		initializeGUI();
		addActionListeners();
		addKeyListener();
	}

	private void initializeFrame() {
		setTitle("Monitoramento de Servidores");
		ImageIcon imagemTituloJanela = new ImageIcon(getClass().getResource("/IMG/logo.png"));
		setIconImage(imagemTituloJanela.getImage());
		setMinimumSize(new Dimension(650,185));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
	}

	@SuppressWarnings("serial")
	public void initializeGUI() {
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
		backgroundImage = new ImageIcon(getClass().getResource("/IMG/fundo.png")).getImage();
		ImageIcon logoSistema = new ImageIcon(getClass().getResource("/IMG/logoSistema.png"));
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
		JPanel titlePanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		titlePanel.setOpaque(false); // Tornar o painel transparente
		titlePanel.setBorder(BorderFactory.createLineBorder(minhaCorBorder, 2));

		// Título do sistema com imagem
		JLabel titleLabel = new JLabel(logoSistema);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza a imagem
		titleLabel.setVerticalAlignment(SwingConstants.CENTER); // Centraliza a imagem
		titleLabel.setForeground(Color.WHITE);
		titlePanel.add(titleLabel, BorderLayout.CENTER); // Adiciona o título ao centro e centralizado horizontalmente
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

		// Painel para o botão de configurações alinhado à direita
		JPanel ConfiguracaoPanel = new JPanel(new BorderLayout());
		ConfiguracaoPanel.setOpaque(false); // Tornar o painel transparente

		configuracoesButton = new JButton(buttonConfiguracao);
		configuracoesButton.setContentAreaFilled(false); // Não preencher área do botão
		configuracoesButton.setBorderPainted(false); // Não pintar a borda

		ConfiguracaoPanel.add(configuracoesButton, BorderLayout.CENTER); // Adiciona o botão de configurações ao painel

		// Adiciona o painel do botão de configurações à região leste do BorderLayout do
		// titlePanel
		titlePanel.add(ConfiguracaoPanel, BorderLayout.EAST);

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
		hostnameLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 12
		hostnameLabel.setForeground(Color.WHITE); // Define a cor do texto como branca
		hostnameLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Centraliza a imagem
		hostnameLabel.setVerticalAlignment(SwingConstants.CENTER); // Centraliza a imagem

		JLabel ipLabel = new JLabel("IP:");
		ipLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 12
		ipLabel.setForeground(Color.WHITE); // Define a cor do texto como branca
		ipLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Centraliza a imagem
		ipLabel.setVerticalAlignment(SwingConstants.CENTER); // Centraliza a imagem

		hostnameField = new JTextField();
		hostnameField.setBackground(Color.WHITE); // Define o fundo como branco

		ipField = new JTextField();
		ipField.setBackground(Color.WHITE); // Define o fundo como branco

		addButton = new JButton("Adicionar Servidor", buttonSalvar);
		addButton.setFont(new Font("Arial", Font.BOLD, 12)); // Define fonte Arial e tamanho 12
		addButton.setForeground(Color.WHITE);
		addButton.setBackground(minhaCorAzul);

		JLabel creditosLabel = new JLabel("© Cleiton Júnior - v. 2.2 | Todos os direitos reservados.");
		creditosLabel.setFont(new Font("Arial", Font.BOLD, 10));
		creditosLabel.setForeground(Color.WHITE);
		creditosLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza a imagem
		creditosLabel.setVerticalAlignment(SwingConstants.CENTER); // Centraliza a imagem

		addServerPanel.add(hostnameLabel);
		addServerPanel.add(hostnameField);
		addServerPanel.add(ipLabel);
		addServerPanel.add(ipField);
		addServerPanel.add(creditosLabel);
		addServerPanel.add(addButton);

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
	}
	
	// Método que seta o tamanho dos quadradinhos do layout de monitoramento
	 public void setSquareSize(int width, int height) {
	        this.squareWidth = width;
	        this.squareHeight = height;
	        updateServerPanelLayout();
	        pack(); // Redimensiona a janela após a atualização do layout
	    }

	// Método que adiciona um servidor através do JButton addButton
	public void addServidor() {
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
		// Define as cores personalizadas
		Color minhaCor = new Color(35, 39, 43);
		Color minhaCorBorder = new Color(45, 50, 55);
		Color minhaCorVermelha = new Color(234, 45, 64);
		Color greenBackground = new Color(0, 16, 2);
		Color redBackground = new Color(154, 0, 4);

		// Cria um novo painel para o servidor
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(minhaCor);
		panel.setBorder(BorderFactory.createLineBorder(minhaCorBorder, 2)); // Define a borda com cor personalizada

		// Define tamanho fixo para o painel do servidor
        panel.setPreferredSize(new Dimension(squareWidth, squareHeight)); // Ajusta as dimensões conforme necessário

		// Cria o label para exibir o status do servidor (nome do host)
		JLabel statusLabel = new JLabel(server.getHostname(), SwingConstants.CENTER);
		statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
		statusLabel.setForeground(Color.WHITE); // Define a cor do texto como branco

		// Cria o label para o fundo com base no status do servidor
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setLayout(new BorderLayout());
		backgroundLabel.add(statusLabel, BorderLayout.CENTER);

		// Verifica o status do servidor para definir o fundo correto
		boolean isServerAlive = isServerAlive(server.getIp());
		if (isServerAlive) {
			backgroundLabel.setBackground(greenBackground);
		} else {
			backgroundLabel.setBackground(redBackground);
		}
		backgroundLabel.setOpaque(true); // Garante que o fundo colorido seja visível

		// Cria o label para indicar o tipo de servidor
		JLabel hostnameLabel = new JLabel("SERVIDOR", SwingConstants.CENTER);
		hostnameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		hostnameLabel.setForeground(Color.WHITE); // Define a cor do texto como branco

		// Cria o painel para o label do tipo de servidor
		JPanel hostnamePanel = new JPanel(new BorderLayout());
		hostnamePanel.add(hostnameLabel, BorderLayout.CENTER);
		hostnamePanel.setOpaque(false); // Torna o painel transparente

		// Adiciona os componentes ao painel do servidor
		panel.add(hostnamePanel, BorderLayout.NORTH); // Adiciona o painel do tipo de servidor na parte superior
		panel.add(backgroundLabel, BorderLayout.CENTER); // Adiciona o label de fundo no centro

		// Cria o botão de exclusão para remover o servidor
		ImageIcon buttonExcluir = new ImageIcon(getClass().getResource("/IMG/excluir.png"));
		JButton deleteButton = new JButton("Excluir", buttonExcluir);
		deleteButton.setForeground(minhaCorVermelha);
		deleteButton.setBackground(Color.WHITE);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeServer(server); // Remove o servidor da lista
				serverPanel.remove(panel); // Remove o painel do servidor da interface
				serverPanel.revalidate(); // Atualiza a interface
				serverPanel.repaint(); // Redesenha a interface
				SwingUtilities.invokeLater(() -> pack()); // Redimensiona a janela
			}
		});
		panel.add(deleteButton, BorderLayout.SOUTH); // Adiciona o botão de exclusão na parte inferior do painel do
														// servidor

		// Adiciona o painel do servidor ao painel principal
		serverPanel.add(panel);
		serverPanel.revalidate(); // Atualiza a interface
		serverPanel.repaint(); // Redesenha a interface

		// Inicia um timer para verificar periodicamente o status do servidor
		Timer timer = new Timer(true);
		timers.add(timer);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				boolean isAlive = isServerAlive(server.getIp());
				System.out.println("Verificando status do servidor " + server.getHostname() + ": "
						+ (isAlive ? "Ligado" : "Desligado"));
				SwingUtilities.invokeLater(() -> {
					if (isAlive) {
						backgroundLabel.setBackground(greenBackground);
					} else {
						backgroundLabel.setBackground(redBackground);
					}
					backgroundLabel.repaint(); // Garante que a cor de fundo seja atualizada
				});
			}
		}, 0, 10000); // Atualiza a cada 10 segundos

		// Redimensiona a janela após adicionar o servidor
		SwingUtilities.invokeLater(this::pack);
	}

	// Método que verifica se um servidor está acessível na rede através do seu IP
	private boolean isServerAlive(String ip) {
		try {
			InetAddress address = InetAddress.getByName(ip);
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

	// Regras para a utilização dos JButtons
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			addServidor();
		}
	}

	private void addKeyListener() {
		hostnameField.addKeyListener(this);
		ipField.addKeyListener(this);
	}

	private void addActionListeners() {
		addButton.addActionListener(this);
		configuracoesButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addButton) {
			addServidor();
		} else if (e.getSource() == configuracoesButton) {
			abrirConfiguracoes();
		}

	}
}
