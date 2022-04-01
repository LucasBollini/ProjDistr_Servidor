package PkgServer;

import PkgGlobais.VarsGlobais;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

public class ConexaoCli implements Runnable{
    
    byte[] leituraB;
    String leituraT;
    String enderecoCliente = "";
    public Socket cliente;
    InputStream leitor;
    FuncoesServer funcsServer;
    
    
    private void initConex() throws Exception{
        cliente = VarsGlobais.servidor.accept();
        new Thread(new ConexaoCli()).start();
        VarsGlobais.listaConexoes.add(this);
        enderecoCliente = "" + cliente.getInetAddress() + ":" + cliente.getPort();
        leitor = cliente.getInputStream();
        funcsServer = new FuncoesServer(cliente.getOutputStream(), enderecoCliente);
        VarsGlobais.areaLog.append("Conexão iniciada com: " + enderecoCliente + "\n");
    }
    
    private void loopLeitura() throws Exception{
        while(VarsGlobais.servidor != null && !cliente.isClosed()){
            try{
                leituraT = "";
                cliente.setSoTimeout(0);
                try{
                    while(true){
                        leituraB = new byte[1];
                        if(leitor.read(leituraB) == -1)
                            throw new IOException();
                        if(new String(leituraB).equals("{") || new String(leituraB).equals("\""))
                            break;
                    }
                    leituraT += new String(leituraB);
                    cliente.setSoTimeout(200);
                    while(true){
                        leituraB = new byte[10];
                        if(leitor.read(leituraB) == -1)
                            throw new IOException();
                        leituraT += new String(leituraB);
                        try{
                            new JSONObject(leituraT);
                            break;
                        }catch(JSONException e){}
                    }
                }catch(SocketTimeoutException e){}
                VarsGlobais.areaLog.append("R - " + enderecoCliente + ": " + leituraT + "\n"); 
                funcsServer.exec(new JSONObject(leituraT));
            }catch(JSONException e){
                cliente.getOutputStream().write("{\"id\": \"error\",\"data\": {\"desc\": \"JSON invalido\"}}".getBytes());
                VarsGlobais.areaLog.append("R - " + enderecoCliente + ": " + leituraT + "\nE - " + enderecoCliente + ": {\"id\": \"error\",\"data\": {\"desc\": \"JSON invalido\"}}\n");
            }
        }
    }
    
    @Override
    public void run() {
        try{
            initConex();
            loopLeitura();
        }catch(Exception e){}
        
        VarsGlobais.listaConexoes.remove(this);
        if(!enderecoCliente.isEmpty())
            VarsGlobais.areaLog.append("Conexão encerrada com: " + enderecoCliente +"\n");
        
        try{
            cliente.close();
        }catch(Exception e){}
    }
}