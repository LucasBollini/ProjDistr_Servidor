package PkgGlobais;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import javax.swing.JTextArea;
import PkgEntidades.EntServico;
import PkgEntidades.EntUsuario;
import PkgServer.ConexaoCli;

public class VarsGlobais {
    
    public static ServerSocket servidor;
    
    public static Vector<EntUsuario> listaUsuarios = new Vector<EntUsuario>();
    public static Vector<EntServico> listaServicos = new Vector<EntServico>();
    public static Vector<ConexaoCli> listaConexoes = new Vector<>();
    public static JTextArea areaLog = new JTextArea();
    
    public static void abrirServer(int porta) throws IOException{
        servidor = new ServerSocket(porta);
    }
    
    public static void fecharServer() throws IOException{
        listaConexoes.forEach(entrada -> {try{ entrada.cliente.close();}catch(Exception ex){}});
        servidor.close();
        servidor = null;
    }
}