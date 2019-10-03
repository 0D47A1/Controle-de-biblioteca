/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.UUID;

/**
 *
 * @author melksedek
 */
public class Emprestimo extends RecursiveTreeObject<Emprestimo>{

    private String id;
    private String user;
    private String livros;
    private String data;
    private String multa;

    
     public void gerarId(){
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        this.id = id.substring(0,5); 
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLivros() {
        return livros;
    }

    public void setLivros(String livros) {
        this.livros = livros;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMulta() {
        return multa;
    }

    public void setMulta(String multa) {
        this.multa = multa;
    }
    
    
}
