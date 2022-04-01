package servidor_distr;

import PkgJanelas.JanelaPorta;

public class Servidor_Distr {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.d3d","false");
        new JanelaPorta();
    }   
}