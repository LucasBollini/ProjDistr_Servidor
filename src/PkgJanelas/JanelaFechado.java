package PkgJanelas;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JanelaFechado extends JFrame{
    
    public JanelaFechado(JFrame janelaPrincipal){
        
        JPanel area = new JPanel();
        area.setPreferredSize(new Dimension(300, 40));
        area.setLayout(null);
        
        JLabel msg = new JLabel("Server fechado com Sucesso");
        msg.setBounds(50, 5, 250, 35);
        area.add(msg);
        
        setTitle("Server Fechado");
        setContentPane(area);
        setResizable(false);
        pack();
        setVisible(true);
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                janelaPrincipal.revalidate();
                janelaPrincipal.repaint();
                janelaPrincipal.setVisible(true);
                dispose();
            }
        });
    }
}