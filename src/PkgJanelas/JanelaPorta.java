package PkgJanelas;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import PkgGlobais.VarsGlobais;

public class JanelaPorta extends JFrame{
    
    JanelaPorta estaJanela = this;
    
    public JanelaPorta(){
        
        JPanel area = new JPanel();
        area.setPreferredSize(new Dimension(260,45));
        area.setLayout(null);
        
        JLabel lblPorta = new JLabel("Porta:");
        lblPorta.setBounds(10, 10, 50, 25);
        area.add(lblPorta);
        
        JTextField txtPorta = new JTextField();
        txtPorta.setBounds(70, 10, 100, 25);
        area.add(txtPorta);
        
        JButton btnLigar = new JButton("Ligar");
        btnLigar.setBounds(180, 10, 70, 25);
        area.add(btnLigar);
        btnLigar.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    VarsGlobais.abrirServer(Integer.parseInt(txtPorta.getText()));
                    setVisible(false);
                    new JanelaLogger(estaJanela);
                } catch (Exception ex) {
                    txtPorta.setText("Porta inválida");
                }
            }
        });
        
        setTitle("Server");
        setContentPane(area);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }
}