/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.de.biblioteca;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.Database;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import modelos.Emprestimo;
import modelos.Tabela_livro;
import modelos.Tabela_usuario;

/**
 *
 * @author melksedek
 */
public class HomeController implements Initializable {
   
    @FXML
    private AnchorPane root;
    
    @FXML
    private StackPane root_stackPane;
    @FXML
    private Label label;
    
    @FXML
    private JFXButton btn_registrar_livro;
    
    @FXML
    private JFXButton btn_registrar_usuario;   
    
    @FXML
    private JFXButton btn_registrar_emprestimo;
    
    @FXML
    private JFXTreeTableView<Tabela_livro> tabela_livros;
    
    @FXML
    private JFXTreeTableView<Tabela_usuario> tabela_usuarios;
    
    @FXML
    private JFXTreeTableView<Emprestimo> tabela_emprestimos;
    
    @FXML
    private JFXComboBox<String> emp_lelecionar_livro;
    
    private JFXButton btn ;
    
    public static HomeController homeController;
    public static ShowBox showbox;
    public static Emprestimo emprestimo_data;
    public JFXDialog dialog_cadastro_livro;
    public JFXDialog dialog_cadastro_usuario;
    
    public ObservableList<Tabela_livro> rows_tabela_livres;
    public ObservableList<Tabela_usuario> rows_tabela_usuarios;
    public ObservableList<Emprestimo> rows_tabela_emprestimo;
    
    public Boolean update ;
    
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        showbox = new ShowBox(root_stackPane);
        homeController = this;
        
 
        JFXTreeTableColumn<Tabela_livro,Integer> ISBN = new JFXTreeTableColumn<>("ISBN");
        
        JFXTreeTableColumn<Tabela_livro,String> titulo = new JFXTreeTableColumn<>("Titulo");
        JFXTreeTableColumn<Tabela_livro,String> autores = new JFXTreeTableColumn<>("Autores");
        JFXTreeTableColumn<Tabela_livro,String> edicao = new JFXTreeTableColumn<>("Edição");
        JFXTreeTableColumn<Tabela_livro,String> editora = new JFXTreeTableColumn<>("Editora");
        JFXTreeTableColumn<Tabela_livro,Integer> ano = new JFXTreeTableColumn<>("Ano");
        JFXTreeTableColumn<Tabela_livro,HBox> buttons = new JFXTreeTableColumn<>("Ações");
    //    buttons.prefWidthProperty().bind(personTable.widthProperty().divide(4));
        ISBN.prefWidthProperty().bind(tabela_livros.widthProperty().subtract(261).divide(6));
        titulo.prefWidthProperty().bind(tabela_livros.widthProperty().subtract(261).divide(6));
        autores.prefWidthProperty().bind(tabela_livros.widthProperty().subtract(261).divide(6));
        edicao.prefWidthProperty().bind(tabela_livros.widthProperty().subtract(261).divide(6));
        ano.prefWidthProperty().bind(tabela_livros.widthProperty().subtract(261).divide(6));
        editora.prefWidthProperty().bind(tabela_livros.widthProperty().subtract(261).divide(6));
        
        buttons.prefWidthProperty().set(260);

        
        ISBN.setCellValueFactory((param)-> new SimpleObjectProperty(param.getValue().getValue().getISBN()));
        titulo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getValue().getTitulo()));
        autores.setCellValueFactory(( param)-> new SimpleStringProperty(param.getValue().getValue().getAutores()));
        edicao.setCellValueFactory((param)-> new SimpleStringProperty(param.getValue().getValue().getEdicao()));
        editora.setCellValueFactory((param)-> new SimpleStringProperty(param.getValue().getValue().getEditora()));
        ano.setCellValueFactory((param)-> new SimpleObjectProperty<>(param.getValue().getValue().getAno()));
        buttons.setCellValueFactory((param)-> new SimpleObjectProperty<>(param.getValue().getValue().getButtonsAction()));
       
       rows_tabela_livres = FXCollections.observableArrayList();
        
       
        ArrayList<Tabela_livro> livros = new Database().get_livros() ; //null; 
        
        if(livros != null){
          livros.forEach((livro)->{
                rows_tabela_livres.add(livro);
        });   
        }
       
          
        final TreeItem<Tabela_livro> content = new RecursiveTreeItem<Tabela_livro>(rows_tabela_livres, RecursiveTreeObject::getChildren);
        tabela_livros.getColumns().setAll(ISBN,titulo,autores,edicao,editora,ano,buttons);
        tabela_livros.setRoot(content);
        tabela_livros.setShowRoot(false);
        
        
         /* INICIO CONSTRUÇAO DA TABELA USUARIO  */
         
        JFXTreeTableColumn<Tabela_usuario,String> Nome = new JFXTreeTableColumn<>("Nome");
        
        JFXTreeTableColumn<Tabela_usuario,String> Login = new JFXTreeTableColumn<>("Login");
        JFXTreeTableColumn<Tabela_usuario,String> Tipo = new JFXTreeTableColumn<>("Tipo");
        JFXTreeTableColumn<Tabela_usuario,HBox> buttons_user = new JFXTreeTableColumn<>("Ações");
    //    buttons.prefWidthProperty().bind(personTable.widthProperty().divide(4));
        Nome.prefWidthProperty().bind(tabela_usuarios.widthProperty().subtract(261).divide(3));
      
        Login.prefWidthProperty().bind(tabela_usuarios.widthProperty().subtract(261).divide(3));
        Tipo.prefWidthProperty().bind(tabela_usuarios.widthProperty().subtract(261).divide(3));      
        
        buttons_user.prefWidthProperty().set(260);

        
        Nome.setCellValueFactory((param)-> new SimpleObjectProperty(param.getValue().getValue().getNome()));
        Login.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getValue().getLogin()));
        Tipo.setCellValueFactory(( param)-> new SimpleStringProperty(param.getValue().getValue().getTipo()));      
        buttons_user.setCellValueFactory((param)-> new SimpleObjectProperty<>(param.getValue().getValue().getButtonsAction()));
       
       rows_tabela_usuarios = FXCollections.observableArrayList();
        
        
        ArrayList<Tabela_usuario> usuarios = new Database().get_all_usuario(); //null; 
        
        if(usuarios != null){
            usuarios.forEach((usuario)->{
                rows_tabela_usuarios.add(usuario);
            });   
        }
       
          
        final TreeItem<Tabela_usuario> content_user = new RecursiveTreeItem<Tabela_usuario>(rows_tabela_usuarios, RecursiveTreeObject::getChildren);
        tabela_usuarios.getColumns().setAll(Nome,Login,Tipo,buttons_user);
        tabela_usuarios.setRoot(content_user);
        tabela_usuarios.setShowRoot(false);
        
        /* FIM CONSTRUÇAO DA TABELA USUARIO  */
        
        
        
         /* INICIO CONSTRUÇAO DA TABELA EMPRESTIMO  */
         
        JFXTreeTableColumn<Emprestimo,String> Usuario = new JFXTreeTableColumn<>("Usuario");        
        JFXTreeTableColumn<Emprestimo,String> Livros = new JFXTreeTableColumn<>("Livros emprestados");
        JFXTreeTableColumn<Emprestimo,String> Data = new JFXTreeTableColumn<>("Periodo");
        
        JFXTreeTableColumn<Emprestimo,HBox> buttons_emprestimo = new JFXTreeTableColumn<>("Ações");
    //    buttons.prefWidthProperty().bind(personTable.widthProperty().divide(4));
    
        Usuario.prefWidthProperty().bind(tabela_emprestimos.widthProperty().subtract(261).divide(3));      
        Livros.prefWidthProperty().bind(tabela_emprestimos.widthProperty().subtract(261).divide(3));
        Data.prefWidthProperty().bind(tabela_emprestimos.widthProperty().subtract(261).divide(3));       
        buttons_emprestimo.prefWidthProperty().set(260);

        
        Usuario.setCellValueFactory((param)-> new SimpleObjectProperty(param.getValue().getValue().getUser()));
        Livros.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getValue().getQuantLivros()));
        Data.setCellValueFactory(( param)-> new SimpleStringProperty(param.getValue().getValue().getPeriodo()));      
        buttons_emprestimo.setCellValueFactory((param)-> new SimpleObjectProperty<>(param.getValue().getValue().getButtonsAction()));
       
       rows_tabela_emprestimo = FXCollections.observableArrayList();
        
       
        ArrayList<Emprestimo> emprestimos = new Database().get_all_emprestimos(); //null; 
        
        if(emprestimos != null){
            emprestimos.forEach((emprestimo)->{
                System.out.println("Emprestimo usuario "+emprestimo.getUser());
                rows_tabela_emprestimo.add(emprestimo);
            });   
        }
       
          
        final TreeItem<Emprestimo> content_emprestimo = new RecursiveTreeItem<Emprestimo>(rows_tabela_emprestimo, RecursiveTreeObject::getChildren);
        tabela_emprestimos.getColumns().setAll(Usuario,Livros,Data,buttons_emprestimo);
        tabela_emprestimos.setRoot(content_emprestimo);
        tabela_emprestimos.setShowRoot(false);
        
        /* FIM CONSTRUÇAO DA TABELA EMPRESTIMO  */
       
        btn_registrar_livro.setOnAction((action)->{
            update = false;
            showbox.show_box_livro(null);                
        });
        
        btn_registrar_usuario.setOnAction((action)->{
              update = false;
              showbox.show_box_usuario(null);
             
        });
        
        btn_registrar_emprestimo.setOnAction((action)->{
            update = false;
            showbox.show_box_emprestimo(null);
        });
        
     
    
    }    
    
}
