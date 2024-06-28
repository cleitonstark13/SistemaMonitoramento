/*
 * AUTOR CLEITON ALVES E SILVA JÚNIOR
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConfiguracoesDialog extends JDialog implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JSpinner spinnerServidoresPorLinha, spinnerAlturaQuadrado, spinnerLarguraQuadrado;
	private JButton btnSalvar, btnCancelar;
	private Image backgroundImage;

	public ConfiguracoesDialog(JFrame parent) {
		super(parent, "Configurações", true);

		setMinimumSize(new Dimension(80,150));
		setResizable(false);
		backgroundImage = new ImageIcon(getClass().getResource("/IMG/fundo.png")).getImage(); // Carrega a imagem do
																								// sistema
		// Seta os valores salvos da dimensão e número de servidores por linha
		ServerMonitor serverMonitor = (ServerMonitor) parent;
	    int larguraInicial = serverMonitor.getSquareWidth();
	    int alturaInicial = serverMonitor.getSquareHeight();
	    int servidoresLinha = serverMonitor.getServidoresPorLinha();
	    
		@SuppressWarnings("serial")
		JPanel panel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel centerPanel = new JPanel(new GridLayout(2, 3, 5, 5));
		centerPanel.setOpaque(false); // Tornar o painel transparente

		JLabel lblLarguraQuadrado = new JLabel("Dimensões:");
		lblLarguraQuadrado.setFont(new Font("Arial", Font.BOLD, 12));
		lblLarguraQuadrado.setForeground(Color.WHITE);
		centerPanel.add(lblLarguraQuadrado);

		spinnerLarguraQuadrado = new JSpinner(new SpinnerNumberModel(larguraInicial, 50, 500, 5));
		centerPanel.add(spinnerLarguraQuadrado);

		spinnerAlturaQuadrado = new JSpinner(new SpinnerNumberModel(alturaInicial, 50, 500, 5));
		centerPanel.add(spinnerAlturaQuadrado);

		JLabel lblServidoresPorLinha = new JLabel("Servidores por Linha:");
		lblServidoresPorLinha.setFont(new Font("Arial", Font.BOLD, 12));
		lblServidoresPorLinha.setForeground(Color.WHITE);
		centerPanel.add(lblServidoresPorLinha);

		spinnerServidoresPorLinha = new JSpinner(new SpinnerNumberModel(servidoresLinha, 1, 10, 1));
		centerPanel.add(new JLabel());
		centerPanel.add(spinnerServidoresPorLinha);

		Color minhaCorAzul = new Color(44, 93, 170);

		ImageIcon buttonSalvar = new ImageIcon(getClass().getResource("/IMG/salvar.png"));
		ImageIcon buttonCancelar = new ImageIcon(getClass().getResource("/IMG/cancelar.png"));
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5)) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		btnSalvar = new JButton("Salvar", buttonSalvar);
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(minhaCorAzul);

		btnCancelar = new JButton("Cancelar", buttonCancelar);
		btnCancelar.setForeground(minhaCorAzul);
		btnCancelar.setBackground(Color.WHITE);

		buttonPanel.add(btnSalvar, BorderLayout.WEST);
		buttonPanel.add(btnCancelar, BorderLayout.EAST);

		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(panel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(parent);

		// Adicione esta linha para garantir que os listeners sejam adicionados
		addListeners();
	}

	private void addListeners() {
		btnSalvar.addActionListener(this);
		btnCancelar.addActionListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public void salvarColunas() {
		int servidoresPorLinha = (int) spinnerServidoresPorLinha.getValue();
		int larguraQuadrado = (int) spinnerLarguraQuadrado.getValue();
		int alturaQuadrado = (int) spinnerAlturaQuadrado.getValue();
		((ServerMonitor) getParent()).setServidoresPorLinha(servidoresPorLinha);
		((ServerMonitor) getParent()).setSquareSize(larguraQuadrado, alturaQuadrado);
		dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSalvar) {
			salvarColunas();
		} else if (e.getSource() == btnCancelar) {
			dispose();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			salvarColunas();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
