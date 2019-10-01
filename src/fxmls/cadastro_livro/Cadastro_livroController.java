/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmls.cadastro_livro;
import controle.de.biblioteca.HomeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static controle.de.biblioteca.HomeController.homeController;
import static controle.de.biblioteca.HomeController.showbox;
import db.Database;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelos.Tabela_livro;
/**
 * FXML Controller class
 *
 * @author melksedek
 */
public class Cadastro_livroController implements Initializable {
    @FXML
    private JFXTextField cadastro_livro_ISBN;

    @FXML
    private JFXTextField cadastro_livro_titulo;

    @FXML
    private JFXTextField cadastro_livro_autores;

    @FXML
    private JFXTextField cadastro_livro_edicao;

    @FXML
    private JFXTextField cadastro_livro_editora;

    @FXML
    private JFXTextField cadastro_livro_ano;

    @FXML
    private JFXButton btn_cadastro_livro_salvar;
    
    @FXML
    private JFXButton btn_cadastro_livro_cancelar;
    
   
    @FXML
    private Label cadastro_titulo;
  
    /**
     * Initializes the controller class.
     */
    
      public static<Tabela_livro> int find(Tabela_livro[] a, Tabela_livro target){
               return IntStream.range(0, a.length)
                .filter(i -> target.equals(a[i]))
                .findFirst()
                .orElse(-1);	// return -1 if target is not found
       }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       if(homeController.update){
            cadastro_titulo.setText("Atualizar livro ");
            cadastro_livro_ISBN.disableProperty().set(true);
   
        }else{
            cadastro_titulo.setText("Cadastrar livro");
            System.out.println("Não é para atualizar ");
        }
       System.out.println(homeController.update);
       
       
        btn_cadastro_livro_salvar.setOnAction((event)->{
            Tabela_livro linha = new Tabela_livro();
                        
                     linha.setISBN(Integer.parseInt(cadastro_livro_ISBN.getText()));
                     linha.setTitulo(cadastro_livro_titulo.getText());
                     linha.setAutores(cadastro_livro_autores.getText());
                     linha.setEdicao(cadastro_livro_edicao.getText());
                     linha.setEditora(cadastro_livro_editora.getText());
                     linha.setAno(Integer.parseInt(cadastro_livro_ano.getText()));
            
            if(homeController.update){
               
              Integer index =  IntStream.range(0, HomeController.homeController.rows_tabela_livres.size())
                .filter(i -> HomeController.homeController.rows_tabela_livres.get(i).getISBN().equals(Integer.parseInt(cadastro_livro_ISBN.getText())))
                .findAny()
                .orElse(-1);
              
              if(index >-1){
                  new Database().update_livro(linha);
                  HomeController.homeController.rows_tabela_livres.set(index, linha);
              }
             
            }else{
                HomeController.homeController.rows_tabela_livres.add(linha);
                new Database().set_livro(linha);
              
            }
             showbox.close_box_livro();    
                     
            
        });
        
        btn_cadastro_livro_cancelar.setOnAction((event)->{
            
            showbox.close_box_livro();
    
        });
    
}
    
}
