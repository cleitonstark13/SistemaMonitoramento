/*
 * AUTOR CLEITON ALVES E SILVA JÚNIOR
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfiguracoesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JSpinner spinnerServidoresPorLinha;
	private JButton btnSalvar;
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
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int servidoresPorLinha = (int) spinnerServidoresPorLinha.getValue();
				((ServerMonitor) parent).setServidoresPorLinha(servidoresPorLinha);
				dispose();
			}
		});

		panel.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar", buttonCancelar);
		btnCancelar.setForeground(minhaCorAzul);
		btnCancelar.setBackground(Color.WHITE);
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancelar);

		getContentPane().add(panel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(parent);
	}
}
