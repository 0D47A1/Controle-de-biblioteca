/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import static controle.de.biblioteca.HomeController.emprestimo_data;
import static controle.de.biblioteca.HomeController.homeController;
import static controle.de.biblioteca.HomeController.showbox;
import db.Database;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author melksedek
 */
public class Emprestimo extends RecursiveTreeObject<Emprestimo>{

    private String id;
    private Tabela_usuario user;
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
    

    public Tabela_usuario getUser() {
        return user;
    }

    public void setUser(Tabela_usuario user) {
        this.user = user;
    }

    public AnchorPane getCombox_livros(){
       JFXComboBox<Tabela_livro> livros = new JFXComboBox();
        AnchorPane anchor  = new AnchorPane();
	List<String> list =  Arrays.asList(this.livros.replace("[","").replace("]","").split(","));
        
        list.forEach(id->{        
               
                livros.getItems().add(new Database().get_livro(Integer.valueOf(id.trim())));
        });
        livros.setValue(livros.getItems().get(0));
        anchor.setTopAnchor(livros,00.0);
        anchor.setBottomAnchor(livros,00.0);
        anchor.setLeftAnchor(livros,00.0);
        anchor.setRightAnchor(livros,00.0);
        anchor.getChildren().add(livros);
        return anchor;
    }
    
    public String getPeriodo(){
        String[] datas = this.data.replace("[","").replace("]","").replace(" ","").split(",");
        
        String periodo =  new DateTime(datas[0]).toString(DateTimeFormat.forPattern("dd/MM/yyyy"))+" - "+new DateTime(datas[datas.length-1]).toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
        return periodo;
    }
    public HBox getButtonsAction(){
        
        HBox grupo = new HBox(5);        
        grupo.setAlignment(Pos.CENTER);
       
        JFXButton devolver = new JFXButton("Detalhes");      
        
        devolver.setOnAction((event)->{
             emprestimo_data = this;
             showbox.show_box_emprestimo_detalhe();
                
        });
        
        devolver.setStyle("-fx-background-color:#0277BD;  -fx-text-fill:#fff");        
        
        grupo.getChildren().addAll(devolver);        
        return grupo;
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
    
    public String getQuantLivros(){
            String[] array = this.livros.replace("[","").replace("]","").split(",");
            if(array.length > 1){
                return array.length+" Livros";
            }else{
                return array.length+" Livro";
            }
         
    }
    
    
}
