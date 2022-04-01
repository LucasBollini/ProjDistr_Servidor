package PkgEntidades;

public class EntUsuario {
    
    public String id, name, cpf, email, password, address, phone;
    
    public EntUsuario(String name, String cpf, String email, String password, String address, String phone){
        this.name = name;
        this.address = address;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.id = "" + this.hashCode();
    }
}