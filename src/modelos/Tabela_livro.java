/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import static controle.de.biblioteca.HomeController.homeController;
import static controle.de.biblioteca.HomeController.showbox;
import db.Database;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author melksedek
 */
public class Tabela_livro extends RecursiveTreeObject<Tabela_livro>{

    String ISBN;
    String titulo;
    String autores;
    String edicao;
    String editora;
    Integer ano;

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public void setTitulo(String titulo) {
        this.titulo =  titulo;
    }

    public void setAutores(String autores) {
        this.autores =  autores;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getEdicao() {
        return edicao;
    }

    public String getEditora() {
        return editora;
    }

    public Integer getAno() {
        return ano;
    }

    public String getISBN() {
        return ISBN;
    }
    
    public HBox getButtonsAction(){
        
       

        HBox grupo = new HBox(5);
        grupo.setPrefWidth(300);
        grupo.setAlignment(Pos.CENTER);
        JFXButton editar = new JFXButton("Editar");
        JFXButton excluir = new JFXButton("Excluir");       
        
        editar.setOnAction((event)->{
            homeController.update = true;
            showbox.show_box_livro(this);
            System.out.println(ISBN);
        });
        
        excluir.setOnAction((event)->{
            
                new Database().excluir_livro(ISBN);
                homeController.rows_tabela_livres.removeIf(livro -> livro.ISBN.equals(ISBN));
        });
        
        
       
        editar.setStyle("-fx-background-color:#0277BD;  -fx-text-fill:#fff");
        
        excluir.setStyle("-fx-background-color:#c62828; -fx-text-fill:#fff");
        
        
        grupo.getChildren().addAll(editar,excluir);
 

        
        return grupo;
    }
    
    @Override
    public String toString() { 
           return this.titulo; 
    }
    
   

   
    
    
}
