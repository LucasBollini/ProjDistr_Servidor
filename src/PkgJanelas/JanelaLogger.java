package PkgJanelas;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import PkgGlobais.VarsGlobais;
import PkgServer.ConexaoCli;

public class JanelaLogger extends JFrame{
    
    JanelaLogger(JFrame janelaPrincipal){
        
        JPanel area = new JPanel();
        area.setPreferredSize(new Dimension(1300, 600));
        area.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane(VarsGlobais.areaLog);
        scrollPane.setBounds(10, 10, 1280, 580);
        area.add(scrollPane);
        
        setTitle("Server");
        setContentPane(area);
        setResizable(false);
        pack();
        setVisible(true);
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    VarsGlobais.fecharServer();
                } catch (Exception ex) {}
                new JanelaFechado(janelaPrincipal);
                 dispose();
            }
        });
        
        new Thread(new ConexaoCli()).start();
        
    }
}