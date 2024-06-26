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
	private JSpinner spinnerServidoresPorLinha;
	private JButton btnSalvar, btnCancelar;
	private Image backgroundImage;

	public ConfiguracoesDialog(JFrame parent) {
		super(parent, "Configurações", true);

		backgroundImage = new ImageIcon(getClass().getResource("/IMG/fundo.png")).getImage(); // Carrega a imagem do

		@SuppressWarnings("serial")
		JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10)) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel lblServidoresPorLinha = new JLabel("Servidores por Linha:");
		lblServidoresPorLinha.setFont(new Font("Arial", Font.BOLD, 12));
		lblServidoresPorLinha.setForeground(Color.WHITE);
		panel.add(lblServidoresPorLinha);

		ImageIcon buttonSalvar = new ImageIcon(getClass().getResource("/IMG/salvar.png"));
		ImageIcon buttonCancelar = new ImageIcon(getClass().getResource("/IMG/cancelar.png"));

		spinnerServidoresPorLinha = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
		panel.add(spinnerServidoresPorLinha);

		Color minhaCorAzul = new Color(44, 93, 170);

		btnSalvar = new JButton("Salvar", buttonSalvar);
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(minhaCorAzul);

		panel.add(btnSalvar);

		btnCancelar = new JButton("Cancelar", buttonCancelar);
		btnCancelar.setForeground(minhaCorAzul);
		btnCancelar.setBackground(Color.WHITE);

		panel.add(btnCancelar);

		getContentPane().add(panel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(parent);

		// Adicione esta linha para garantir que os listeners sejam adicionados
		addActionListeners();
	}

	private void addActionListeners() {
		btnSalvar.addActionListener(this);
		btnCancelar.addActionListener(this);
	}

	public void salvarColunas() {
		int servidoresPorLinha = (int) spinnerServidoresPorLinha.getValue();
		((ServerMonitor) getParent()).setServidoresPorLinha(servidoresPorLinha);
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
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
