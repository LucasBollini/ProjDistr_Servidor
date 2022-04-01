package PkgServer;

import PkgGlobais.VarsGlobais;
import java.io.IOException;
import java.io.OutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import PkgEntidades.EntServico;
import PkgEntidades.EntUsuario;

public class FuncoesServer {
    
    EntUsuario objUsuario;
    OutputStream escritor;
    String enderecoCliente;
    
    public FuncoesServer(OutputStream escritor, String enderecoCliente){
        this.escritor = escritor;
        this.enderecoCliente = enderecoCliente;
        this.objUsuario = null;
    }
    
    private void enviarMsg(String msg) throws IOException{
        msg.replace("\n", "\\n");
        escritor.write(msg.getBytes());
        VarsGlobais.areaLog.append("E" + enderecoCliente + ": " + msg + "\n");
    }
    
    private void funcChatR(JSONObject objeto) throws IOException{
        enviarMsg("{\"id\":\"chat\",\"data\":{\"ip\":\"" + objeto.get("ip") + "\",\"message\":\"" + objeto.getString("message") + "\"}}");
    }
    
    private void funcChat(JSONObject objeto) throws IOException{
        try{
            objeto = objeto.getJSONObject("data");
            for(ConexaoCli obj : VarsGlobais.listaConexoes)
                if(obj.funcsServer.objUsuario != null)
                    obj.funcsServer.exec(new JSONObject(("{\"id\":\"chatR\",\"ip\":\"" + enderecoCliente + "\",\"message\":\"" + ("@" + objUsuario.name + ": " + objeto.getString("message")) + "\"}")));
        }catch(JSONException e){}
    }
    
    private void funcServiceUpdate(JSONObject objeto) throws IOException{
        try{            
            objeto = objeto.getJSONObject("data");
            
            EntServico servicoTemp = null;
            for(EntServico elemento: VarsGlobais.listaServicos)
                if(elemento.id.equals(objeto.getString("id")) && !elemento.status.equals(objeto.getString("status")))
                    servicoTemp = elemento;
            
            if(servicoTemp != null){
                try{ if(!objeto.getString("id_client").isEmpty())           servicoTemp.id_client = objeto.getString("id_client");}                 catch(JSONException e){}
                try{ if(!objeto.getString("id_provider").isEmpty())         servicoTemp.id_provider = objeto.getString("id_provider");}             catch(JSONException e){}
                try{ if(!objeto.getString("type").isEmpty())                servicoTemp.type = objeto.getString("type");}                           catch(JSONException e){}
                try{ if(!objeto.getString("description").isEmpty())         servicoTemp.description = objeto.getString("description");}             catch(JSONException e){}
                try{ if(!objeto.getString("value").isEmpty())               servicoTemp.value = objeto.getString("value");}                         catch(JSONException e){}
                try{ if(!objeto.getString("id_user_provider").isEmpty())    servicoTemp.id_user_provider = objeto.getString("id_user_provider");}   catch(JSONException e){}
                try{ if(!objeto.getString("id_user_client").isEmpty())      servicoTemp.id_user_client = objeto.getString("id_user_client");}       catch(JSONException e){}
                try{ if(!objeto.getString("user_client_place").isEmpty())   servicoTemp.user_client_place = objeto.getString("user_client_place");} catch(JSONException e){}
                try{ if(!objeto.getString("service_place").isEmpty())       servicoTemp.service_place = objeto.getString("service_place");}         catch(JSONException e){}
                try{ if(!objeto.getString("date").isEmpty())                servicoTemp.date = objeto.getString("date");}                           catch(JSONException e){}
                try{ if(!objeto.getString("hour").isEmpty())                servicoTemp.hour = objeto.getString("hour");}                           catch(JSONException e){}
                try{ if(!objeto.getString("status").isEmpty())              servicoTemp.status = objeto.getString("status");}                       catch(JSONException e){}

                enviarMsg("{\"id\":\"success\",\"data\":{\"desc\": \"success update service\"}}");
                return;
            }
        }catch(Exception e){}
        enviarMsg("{\"id\":\"error\",\"data\":{\"desc\": \"error update service\"}}");
    }
    
    private void funcServiceList(JSONObject objeto) throws IOException{
        try{
            objeto = objeto.getJSONObject("data");
            JSONArray vetor = new JSONArray();
            boolean flag;
            
            String  id, id_client, id_provider, type, description, value, id_user_provider, id_user_client, user_client_place, service_place, date, hour, status;
            
            try{id = objeto.getString("id");                                }catch(JSONException e){id = "";}
            try{id_client = objeto.getString("id_client");                  }catch(JSONException e){id_client = "";}
            try{id_provider = objeto.getString("id_provider");              }catch(JSONException e){id_provider = "";}
            try{type = objeto.getString("type");                            }catch(JSONException e){type = "";}
            try{description = objeto.getString("description");              }catch(JSONException e){description = "";}
            try{value = objeto.getString("value");                          }catch(JSONException e){value = "";}
            try{id_user_provider = objeto.getString("id_user_provider");    }catch(JSONException e){id_user_provider = "";}
            try{id_user_client = objeto.getString("id_user_client");        }catch(JSONException e){id_user_client = "";}
            try{user_client_place = objeto.getString("user_client_place");  }catch(JSONException e){user_client_place = "";}
            try{service_place = objeto.getString("service_place");          }catch(JSONException e){service_place = "";}
            try{date = objeto.getString("date");                            }catch(JSONException e){date = "";}
            try{hour = objeto.getString("hour");                            }catch(JSONException e){hour = "";}
            try{status = objeto.getString("status");                        }catch(JSONException e){status = "";}
            
            for(EntServico elemento : VarsGlobais.listaServicos){
                
                flag = true;
                
                if(!id.isEmpty() && !id.equals(elemento.id))flag = false;
                if(!id_client.isEmpty() && !id_client.equals(elemento.id_client))flag = false;
                if(!id_provider.isEmpty() && !id_provider.equals(elemento.id_provider))flag = false;
                if(!type.isEmpty() && !type.equals(elemento.type))flag = false;
                if(!description.isEmpty() && !description.equals(elemento.description))flag = false;
                if(!value.isEmpty() && !value.equals(elemento.value))flag = false;
                if(!id_user_provider.isEmpty() && !id_user_provider.equals(elemento.id_user_provider))flag = false;
                if(!id_user_client.isEmpty() && !id_user_client.equals(elemento.id_user_client))flag = false;
                if(!user_client_place.isEmpty() && !user_client_place.equals(elemento.user_client_place))flag = false;
                if(!service_place.isEmpty() && !service_place.equals(elemento.service_place))flag = false;
                if(!date.isEmpty() && !date.equals(elemento.date))flag = false;
                if(!hour.isEmpty() && !hour.equals(elemento.hour))flag = false;
                if(!status.isEmpty() && !status.equals(elemento.status))flag = false;
                
                if(flag){
                    vetor.put(new JSONObject(   "{\"id\": \"" + elemento.id +
                                                "\",\"id_client\": \"" + elemento.id_client +
                                                "\",\"id_provider\": \"" + elemento.id_provider +
                                                "\",\"type\": \"" + elemento.type +
                                                "\",\"description\": \"" + elemento.description +
                                                "\",\"value\": \"" + elemento.value +
                                                "\",\"id_user_provider\": \"" + elemento.id_user_provider +
                                                "\",\"id_user_client\": \"" + elemento.id_user_client +
                                                "\",\"user_client_place\": \"" + elemento.user_client_place +
                                                "\",\"service_place\": \"" + elemento.service_place +
                                                "\",\"date\": \"" + elemento.date +
                                                "\",\"hour\": \"" + elemento.hour +
                                                "\",\"status\": \"" + elemento.status + "\"}".replace("\n", "\\n")));
                }
            }
            
            enviarMsg("{\"id\": \"success\",\"data\": {\"services\":" + vetor.toString() + "}}");
            return;
            
        }catch(Exception e){System.out.print(e + "");}
        enviarMsg("\"id\":\"error\",\"data\":{\"desc\": \"error list services\",\"services\": []}");
    }
    
    private void funcServiceCreate(JSONObject objeto) throws IOException{
        try{
            objeto = objeto.getJSONObject("data");

            String  userId = objUsuario.id,
                    type = objeto.getString("type"),
                    value = objeto.getString("value"),
                    user_client_place = objeto.getString("user_client_place"),
                    service_place = objeto.getString("service_place"),
                    date = objeto.getString("date"),
                    hour = "",
                    description = "";

            try{   hour = objeto.getString("hour");                                     }catch(Exception e){}
            try{   description = objeto.getString("description").replace("\n", "\\n");  }catch(Exception e){}

            if(!type.isEmpty() && !value.isEmpty() && !user_client_place.isEmpty() && !service_place.isEmpty() && !date.isEmpty()){
                EntServico temp = new EntServico(userId, type, description.replace("\n", "\\n"), value, user_client_place, service_place, date, hour, "aberto");
                VarsGlobais.listaServicos.add(temp);
                enviarMsg("{\"id\": \"success\",\"data\":{\"id\": \"" + temp.id + "\",\"desc\": \"success create service\"}}");
                return;
            }
        }catch(Exception e){}
        enviarMsg("{\"id\":\"error\",\"data\":{\"desc\": \"error create service\"}}");
    }
    
    private void funcUserList(JSONObject objeto) throws IOException{
        try{
            objeto = objeto.getJSONObject("data");
            JSONArray vetor = new JSONArray();
            boolean flag;
            
            String  id, name, cpf, email, password, address, phone;
            try{id = objeto.getString("id");            }catch(JSONException e){id = "";}
            try{name = objeto.getString("name");        }catch(JSONException e){name = "";}
            try{cpf = objeto.getString("cpf");          }catch(JSONException e){cpf = "";}
            try{email = objeto.getString("email");      }catch(JSONException e){email = "";}
            try{password = objeto.getString("password");}catch(JSONException e){password = "";}
            try{address = objeto.getString("address");  }catch(JSONException e){address = "";}
            try{phone = objeto.getString("phone");      }catch(JSONException e){phone = "";}
            
            
            for(EntUsuario elemento : VarsGlobais.listaUsuarios){
                flag = true;
                
                if(!id.isEmpty() && !id.equals(elemento.id))
                        flag = false;
                if(!name.isEmpty() && !name.equals(elemento.name))
                        flag = false;
                if(!cpf.isEmpty() && !cpf.equals(elemento.cpf))
                        flag = false;
                if(!email.isEmpty() && !email.equals(elemento.email))
                        flag = false;
                if(!password.isEmpty() && !password.equals(elemento.password))
                        flag = false;
                if(!address.isEmpty() && !address.equals(elemento.address))
                        flag = false;
                if(!phone.isEmpty() && !phone.equals(elemento.phone))
                        flag = false;
                
                if(flag){
                    vetor.put(new JSONObject(   "{\"id\": \"" + elemento.id + 
                                                "\",\"name\": \"" + elemento.name + 
                                                "\",\"cpf\": \"" + elemento.cpf +
                                                "\", \"email\": \"" + elemento.email +
                                                "\",\"password\": \"" + elemento.password +
                                                "\",\"address\": \"" + elemento.address +
                                                "\",\"phone\": \"" + elemento.phone + "\"}"));
                }
            }
            
            enviarMsg("{\"id\": \"success\",\"data\": {\"users\":" + vetor.toString() + "}}");
            return;
            
        }catch(Exception e){}
        enviarMsg("\"id\":\"error\",\"data\":{\"desc\": \"error list users\",\"users\": []}");
    }
    
    private void funcUserUpdate(JSONObject objeto) throws IOException{
        try{            
            objeto = objeto.getJSONObject("data");
            
            EntUsuario usuarioTemp = null;
            for(EntUsuario elemento : VarsGlobais.listaUsuarios)
                if(elemento.id.equals(objeto.getString("id")))
                    usuarioTemp = elemento;
            
            if(usuarioTemp != null){
                try{ if(!objeto.getString("name").isEmpty())    usuarioTemp.name = objeto.getString("name");        }catch(JSONException e){}
                try{ if(!objeto.getString("cpf").isEmpty())     usuarioTemp.cpf = objeto.getString("cpf");          }catch(JSONException e){}
                try{ if(!objeto.getString("email").isEmpty())   usuarioTemp.email = objeto.getString("email");      }catch(JSONException e){}
                try{ if(!objeto.getString("password").isEmpty())usuarioTemp.password = objeto.getString("password");}catch(JSONException e){}
                try{ if(!objeto.getString("address").isEmpty()) usuarioTemp.address = objeto.getString("address");  }catch(JSONException e){}
                try{ if(!objeto.getString("phone").isEmpty())   usuarioTemp.phone = objeto.getString("phone");      }catch(JSONException e){}

                enviarMsg("{\"id\":\"success\",\"data\":{\"desc\": \"success update user\"}}");
                return;
            }
        }catch(Exception e){}
        enviarMsg("{\"id\":\"error\",\"data\":{\"desc\": \"error update user\"}}");
    }
    
    private void funcUserCreate(JSONObject objeto) throws IOException{
        try{
            objeto = objeto.getJSONObject("data");
            
            String name = objeto.getString("name"),
                   cpf = objeto.getString("cpf"),
                   email = objeto.getString("email"),
                   password = objeto.getString("password"),
                   address = "", 
                   phone = "";
            
            try{   address = objeto.getString("address");   }catch(Exception e){}
            try{   phone = objeto.getString("phone");       }catch(Exception e){}
            
            if(!name.isEmpty() && !cpf.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                EntUsuario temp = new EntUsuario(name, cpf, email, password, address, phone);
                VarsGlobais.listaUsuarios.add(temp);
                enviarMsg("{\"id\": \"success\",\"data\":{\"id\": \"" + temp.id + "\",\"desc\": \"success create user\"}}");
                return;
            }
        }catch(Exception e){}
        enviarMsg("{\"id\":\"error\",\"data\":{\"desc\": \"error create user\"}}");
    }
    
    private void funcLogin(JSONObject objeto) throws IOException{
        try{
            objeto = objeto.getJSONObject("data");
            String email = objeto.getString("email");
            String password = objeto.getString("password");
            for(EntUsuario elemento : VarsGlobais.listaUsuarios)
                if(elemento.email.equals(email) && elemento.password.equals(password)){
                    objUsuario = elemento;
                    enviarMsg("{\"id\": \"success\",\"data\": {\"id\": \"" + objUsuario.id + "\", \"desc\": \"success login\"}}");
                    return;
                }
        }catch(Exception e){}
        enviarMsg("{\"id\": \"error\",\"data\": {\"desc\": \"invalid password or user name\"}}");
    }
    
    private void funcLogout() throws IOException{
        try{
            if(objUsuario != null){
                enviarMsg("{\"id\": \"success\",\"data\": {\"desc\": \"success logout\"}}");
                objUsuario = null;
                return;
            }
        }catch(Exception e){}
        enviarMsg("{\"id\": \"error\",\"data\": {\"desc\": \"error on logout\"}}");
    }
    
    private void naoSuportado() throws IOException{
        enviarMsg("{\"id\": \"error\",\"data\": {\"desc\": \"funcao nao suportada\"}}");
    }
    
    public synchronized void exec(JSONObject objeto) throws IOException{
        
        String acao = "";
        try{
            acao += objeto.getString("id");
        }catch(Exception e){}
        try{
            acao += objeto.getString("type");
        }catch(Exception e){}
        try{
            acao += objeto.getString("tipo");
        }catch(Exception e){}
        
        switch(acao){

            case "usercreate": funcUserCreate(objeto); break;
            case "userupdate": funcUserUpdate(objeto); break;
            case "userlist": funcUserList(objeto); break;
            case "servicecreate": funcServiceCreate(objeto); break;
            case "servicelist": funcServiceList(objeto); break;
            case "serviceupdate": funcServiceUpdate(objeto); break;
            case "logout": funcLogout(); break;
            case "login": funcLogin(objeto); break;
            case "chat": funcChat(objeto); break;
            case "chatR": funcChatR(objeto); break;
            
            default: naoSuportado(); break;
        }
    } 
}