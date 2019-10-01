/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import static controle.de.biblioteca.HomeController.homeController;
import db.Database;
import java.util.UUID;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 *
 * @author melksedek
 */
public class Tabela_usuario extends RecursiveTreeObject<Tabela_usuario> {
    String id;
    String nome;
    String login;
    String senha;
    String tipo;
    
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public void gerarId(){
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        this.id = id.substring(0,5); 
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public HBox getButtonsAction(){
        
       

        HBox grupo = new HBox(5);        
        grupo.setAlignment(Pos.CENTER);
       
        JFXButton excluir = new JFXButton("Excluir");
      
        
      
        
        excluir.setOnAction((event)->{
            
                new Database().excluir_usuario(this.getId());
                homeController.rows_tabela_usuarios.removeIf(usuario -> usuario.id.equals(this.getId()));
        });
        
        
       
       
        //emprestar.setStyle("-fx-background-color:#0277BD;  -fx-text-fill:#fff"); 
        excluir.setStyle("-fx-background-color:#c62828; -fx-text-fill:#fff");
        
        
        grupo.getChildren().addAll(excluir);
 

        
        return grupo;
    }
    
    @Override
    public String toString() { 
           return this.nome; 
    }
}
