/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmls.emprestimos;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import controle.de.biblioteca.HomeController;
import static controle.de.biblioteca.HomeController.showbox;
import db.Database;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import modelos.Emprestimo;
import modelos.Tabela_livro;
import modelos.Tabela_usuario;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * FXML Controller class
 *
 * @author melksedek
 */
public class EmprestimosController implements Initializable {

    @FXML
    private JFXComboBox<Tabela_livro> emp_selecionar_livro;
    
    @FXML
    private JFXComboBox<Tabela_usuario> emp_buscar_usuario;
    
    @FXML
    private JFXButton emp_btn_add_livro;
    
    @FXML
    private JFXListView<Tabela_livro> emp_list_livros;
    
    @FXML
    private JFXButton emp_btn_emprestar;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        

        //Busca o livro no seu JFXComboBox
        
        JFXAutoCompletePopup<Tabela_livro> autoCompleteLivro = new JFXAutoCompletePopup<>();
        autoCompleteLivro.setSelectionHandler(event -> {
            emp_selecionar_livro.setValue(event.getObject());
        });

        TextField editor_livro = emp_selecionar_livro.getEditor();
        editor_livro.textProperty().addListener(observable -> {
            
            if(!editor_livro.getText().isEmpty()){
                ArrayList<Tabela_livro> items = new Database().busca_livro(editor_livro.getText(),10);
           
                items.forEach(item ->{
                    
                    Integer index =  IntStream.range(0, emp_selecionar_livro.getItems().size())
                            .filter(i -> emp_selecionar_livro.getItems().get(i).getTitulo().equals(item.getTitulo()))
                            .findAny()
                            .orElse(-1);
                   
                    
                    if(index < 0){
                        System.out.println(emp_selecionar_livro.getItems().contains(item));
                        emp_selecionar_livro.getItems().add(item);
                        
                        autoCompleteLivro.getSuggestions().add(item);
                    }
                 
                }); 
            }
            
            
            autoCompleteLivro.filter(item -> item.getTitulo().contains(editor_livro.getText()));
           
            if (autoCompleteLivro.getFilteredSuggestions().isEmpty() || emp_selecionar_livro.showingProperty().get()) {
                autoCompleteLivro.hide();
                
            }else {
                autoCompleteLivro.show(editor_livro);
            }
        });
         
        
        emp_btn_add_livro.setOnAction((action)->{
           
          
           
           Integer index_livro =  emp_selecionar_livro.getSelectionModel().getSelectedIndex();
           Tabela_livro livro_selecionado = emp_selecionar_livro.getItems().get(index_livro);
           
           Integer index_usuario =  emp_buscar_usuario.getSelectionModel().getSelectedIndex();
           Tabela_usuario usuario_selecionado = emp_buscar_usuario.getItems().get(index_usuario);
           
           System.out.println(usuario_selecionado.getTipo());
           if(usuario_selecionado.getTipo().equals("Professor")){
               if(emp_list_livros.getItems().size() < 5){
                   if(!emp_list_livros.getItems().contains(livro_selecionado)){
                        emp_list_livros.getItems().add(livro_selecionado);
                        
                    }
               }
           }else{
                if(emp_list_livros.getItems().size() < 3){
                   if(!emp_list_livros.getItems().contains(livro_selecionado)){
                        emp_list_livros.getItems().add(livro_selecionado);
                        
                    }
                }
           }
           
           
           
           
        
        });
        
        
          //Busca o usuario no seu JFXComboBox
        
        JFXAutoCompletePopup<Tabela_usuario> autoCompleteUsuario = new JFXAutoCompletePopup<>();
        autoCompleteUsuario.setSelectionHandler(event -> {
            emp_buscar_usuario.setValue(event.getObject());
        });
        
        emp_buscar_usuario.setOnAction(action ->{
            
            Integer index_usuario =  emp_buscar_usuario.getSelectionModel().getSelectedIndex();
            
            if(index_usuario != -1){
                emp_selecionar_livro.disableProperty().set(false);
                emp_btn_add_livro.disableProperty().set(false);
              
            }
            
        
        });
        
        TextField editor_usuario = emp_buscar_usuario.getEditor();
        editor_usuario.textProperty().addListener(observable -> {
            
            if(!editor_usuario.getText().isEmpty()){
                
                ArrayList<Tabela_usuario> items = new Database().busca_usuario(editor_usuario.getText(),10);
           
                items.forEach(item ->{
                    
                     Integer index =  IntStream.range(0, emp_buscar_usuario.getItems().size())
                            .filter(i -> emp_buscar_usuario.getItems().get(i).getNome().equals(item.getNome()))
                            .findAny()
                            .orElse(-1);
                    if(index < 0){
                        emp_buscar_usuario.getItems().add(item);
                        autoCompleteUsuario.getSuggestions().add(item);
                    }

                }); 
            }
            
           
            autoCompleteUsuario.filter(item -> item.getNome().contains(editor_usuario.getText()));
           
            if (autoCompleteUsuario.getFilteredSuggestions().isEmpty() || emp_buscar_usuario.showingProperty().get()) {
                autoCompleteUsuario.hide();                 
            }else {
                autoCompleteUsuario.show(editor_usuario);
            }
        });
        
        
        
        
        
        emp_btn_emprestar.setOnAction(action ->{
                  
            if(emp_list_livros.getItems().size() != 0){
                
                Integer index_usuario =  emp_buscar_usuario.getSelectionModel().getSelectedIndex();
                Tabela_usuario usuario_selecionado = emp_buscar_usuario.getItems().get(index_usuario); // Pega o objeto do usuario que está selecionado
                
                DateTime dataHoje = new DateTime(); 
                
                ArrayList<Integer> livros_ids = new ArrayList<>(); // Array armazenará temporariamente os ids(ISBN) de cada livro
                ArrayList<String> livros_data_emprestimo = new ArrayList<>();
                emp_list_livros.getItems().forEach(livro ->{
                    
                    livros_ids.add(livro.getISBN()); // adiciando o ISBN(id) no array
                    livros_data_emprestimo.add(dataHoje.toString(DateTimeFormat.forPattern("dd/MM/yyyy"))); // a data do emprestimo do livro
                });
                
                
                Emprestimo emprestimo  = new Emprestimo();
                           emprestimo.setUser(usuario_selecionado); // id do usuario que pegou o livro emprestado
                           emprestimo.setLivros(livros_ids.toString()); // Lista de livros(ids) que será adicionado na coluna IDS_LIVROS da tabela emprestimos do banco de dados
                           emprestimo.setData(livros_data_emprestimo.toString()); 
                
                HomeController.homeController.rows_tabela_emprestimo.add(emprestimo); // Adiciona na tabela emprestimo
                new Database().set_emprestimo(emprestimo); // Salva o emprestimo no banco de dados
                           
                showbox.close_box_emprestimo();
            }
                  
        
        });
        
        
      
    }    

    


    
}
