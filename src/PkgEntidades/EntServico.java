package PkgEntidades;

public class EntServico {
    
    public String id_client, id_provider, type, description, value, id_user_provider, id_user_client, user_client_place, service_place, date, hour, status, id;
    
    public EntServico(String id_client, String type, String description, String value, String user_client_place, String service_place, String date, String hour, String status){
        this.id = "" + this.hashCode();
        this.id_client=id_client;
        this.id_provider="";
        this.type=type;
        this.description=description;
        this.value=value;
        this.id_user_provider="";
        this.id_user_client=id_client;
        this.user_client_place=user_client_place;
        this.service_place=service_place;
        this.date=date;
        this.hour=hour;
        this.status=status;
    }
}