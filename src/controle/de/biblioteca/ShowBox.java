/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.de.biblioteca;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import modelos.Tabela_livro;
import modelos.Tabela_usuario;

/**
 *
 * @author melksedek
 */
public class ShowBox {
    
         private StackPane root_stackPane;
         private JFXDialog dialog_cadastro_livro;
         private JFXDialog dialog_cadastro_usuario;
         private JFXDialog dialog_cadastro_emprestimo;
        
         
         ShowBox(StackPane root) {
            this.root_stackPane  = root;
        }
    
        
     
    
    
       public void show_box_livro(Tabela_livro livro){
             try {
                    //   Node node = (Node)FXMLLoader.load(getClass().getResource("/fxmls/cadastro_usuario/cadastro_usuario.fxml"));
                        Node node = (Node) FXMLLoader.load(getClass().getResource("/fxmls/cadastro_livro/cadastro_livro.fxml"));
                        
                        if(livro != null){
                            
                            
                            JFXTextField cadastro_livro_ISBN = (JFXTextField) node.lookup("#cadastro_livro_ISBN");                           
                            cadastro_livro_ISBN.setText(livro.getISBN().toString());
                            JFXTextField cadastro_livro_titulo = (JFXTextField) node.lookup("#cadastro_livro_titulo");
                            cadastro_livro_titulo.setText(livro.getTitulo());
                            JFXTextField cadastro_livro_autores = (JFXTextField) node.lookup("#cadastro_livro_autores");
                            cadastro_livro_autores.setText(livro.getAutores());
                            JFXTextField cadastro_livro_edicao = (JFXTextField) node.lookup("#cadastro_livro_edicao");
                            cadastro_livro_edicao.setText(livro.getEdicao());
                            JFXTextField cadastro_livro_editora = (JFXTextField) node.lookup("#cadastro_livro_editora");
                            cadastro_livro_editora.setText(livro.getEditora());
                            JFXTextField cadastro_livro_ano = (JFXTextField) node.lookup("#cadastro_livro_ano");
                            cadastro_livro_ano.setText(livro.getAno().toString());
                           
                        }
                        
                        dialog_cadastro_livro = new JFXDialog();
                        dialog_cadastro_livro.setOnDialogClosed((handler)-> root_stackPane.setVisible(false));                      
                        dialog_cadastro_livro.setContent((Region) node);

                        root_stackPane.setVisible(true);
                        dialog_cadastro_livro.show(root_stackPane);
                    
                } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void show_box_usuario(Tabela_usuario usuario){    
               
        
        try {

                Node node = (Node)FXMLLoader.load(getClass().getResource("/fxmls/cadastro_usuario/cadastro_usuario.fxml"));
                if(usuario != null){
                    //JFXTextField cadastro_livro_ISBN = (JFXTextField) node.lookup("#cadastro_livro_ISBN"); 
                }
                 
                dialog_cadastro_usuario= new JFXDialog();
                dialog_cadastro_usuario.setOnDialogClosed((handler)-> root_stackPane.setVisible(false));
                
                dialog_cadastro_usuario.setContent((Region) node);

                root_stackPane.setVisible(true);
                dialog_cadastro_usuario.show(root_stackPane);

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void show_box_emprestimo(Tabela_usuario usuario){
          
        try{

                Node node = (Node)FXMLLoader.load(getClass().getResource("/fxmls/emprestimos/emprestimos.fxml"));
                if(usuario != null){
                    //JFXTextField cadastro_livro_ISBN = (JFXTextField) node.lookup("#cadastro_livro_ISBN"); 
                }                 
                dialog_cadastro_emprestimo= new JFXDialog();
                dialog_cadastro_emprestimo.setOnDialogClosed((handler)-> root_stackPane.setVisible(false));
                
                dialog_cadastro_emprestimo.setContent((Region) node);

                root_stackPane.setVisible(true);
                dialog_cadastro_emprestimo.show(root_stackPane);

        }catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public void close_box_livro() {
        dialog_cadastro_livro.close();
    }

    public void close_box_usuario() {
         dialog_cadastro_usuario.close();
    }
    
    public void close_box_emprestimo() {
         dialog_cadastro_emprestimo.close();
    }
}
