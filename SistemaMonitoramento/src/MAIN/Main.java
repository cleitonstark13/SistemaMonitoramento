/*
 * AUTOR CLEITON ALVES E SILVA JÚNIOR
 */
package MAIN;

import GUI.ServerMonitor;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                    ServerMonitor monitor = new ServerMonitor();
                    monitor.setVisible(true);
                
            }
        });
    }
}
