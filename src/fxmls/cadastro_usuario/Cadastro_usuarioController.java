/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmls.cadastro_usuario;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import controle.de.biblioteca.HomeController;
import static controle.de.biblioteca.HomeController.homeController;
import static controle.de.biblioteca.HomeController.showbox;
import db.Database;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modelos.Tabela_livro;
import modelos.Tabela_usuario;

/**
 * FXML Controller class
 *
 * @author melksedek
 */
public class Cadastro_usuarioController implements Initializable {

    
    @FXML
    private Label cad_user_titulo;

    @FXML
    private JFXButton cad_user_salvar;

    @FXML
    private JFXButton cad_user_cancelar;

    @FXML
    private JFXTextField cad_user_nome;

    @FXML
    private JFXTextField cad_user_login;

    @FXML
    private JFXComboBox<Label> cad_user_tipo;

    @FXML
    private JFXPasswordField cad_user_senha;

    @FXML
    private JFXToggleButton cad_is_login;
   
    @FXML
    void cad_user_salvar(ActionEvent event) {
            
        
        Tabela_usuario linha = new Tabela_usuario();                      
                           
                       linha.setNome(cad_user_nome.getText());
                       linha.setLogin(cad_user_login.getText());
                       linha.setTipo(cad_user_tipo.getSelectionModel().getSelectedItem().getText());
                       linha.setSenha(cad_user_senha.getText());
                 
            
                       if(homeController.update){

                          Integer index =  IntStream.range(0, HomeController.homeController.rows_tabela_usuarios.size())
                            .filter(i -> HomeController.homeController.rows_tabela_usuarios.get(i).getNome().equals(linha.getNome()))
                            .findAny()
                            .orElse(-1);

                          if(index >-1){
                              new Database().update_usuario(linha);
                              HomeController.homeController.rows_tabela_usuarios.set(index, linha);
                          }

                        }else{
                            linha.gerarId();
                            HomeController.homeController.rows_tabela_usuarios.add(linha);
                            new Database().set_usuario(linha);

                        }
                        showbox.close_box_usuario(); 
    }
    @FXML
    void is_login(ActionEvent event) {
        boolean is_login = ((JFXToggleButton) event.getSource()).isSelected();
                cad_user_senha.disableProperty().set(!is_login);
                if(is_login){
                    if(!cad_user_nome.textProperty().get().equals("")){
                        cad_user_senha.disableProperty().set(false);
                        String[] s = cad_user_nome.textProperty().get().trim().split(" ");
                        String new_login =  s[0]+"."+s[s.length-1];
                        cad_user_login.textProperty().set(new_login);
                        
                        
                    }else{
                         cad_user_senha.disableProperty().set(true);
                         ((JFXToggleButton) event.getSource()).selectedProperty().set(false);
                    }
                    
                }else{
                    cad_user_login.textProperty().set("");
                }
              
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        cad_user_tipo.getItems().add(new Label("Professor"));
        cad_user_tipo.getItems().add(new Label("Aluno"));
        
        cad_user_tipo.setPromptText("Tipo:");
         if(homeController.update){
            cad_user_titulo.setText("Atualizar usuário ");
       
   
        }else{
            cad_user_titulo.setText("Cadastrar usuário");
         
        }
         
          cad_user_cancelar.setOnAction((event)->{
            
            showbox.close_box_usuario();
    
        });
    }    
    
}
