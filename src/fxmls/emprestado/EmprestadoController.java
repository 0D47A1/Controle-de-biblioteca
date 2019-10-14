/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmls.emprestado;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import controle.de.biblioteca.HomeController;

import static controle.de.biblioteca.HomeController.emprestimo_data;
import static controle.de.biblioteca.HomeController.showbox;
import db.Database;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import modelos.Tabela_livro;
import modelos.Tabela_usuario;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

/**
 * FXML Controller class
 *
 * @author melksedek
 */
public class EmprestadoController implements Initializable {
   
    
    @FXML
    private Label cad_user_titulo;

    @FXML
    private JFXListView<Tabela_livro> empre_list;

    @FXML
    private Label empre_usuario;

    @FXML
    private Label empre_multa;

    @FXML
    private Label empre_data_emprestimo;

    @FXML
    private Label empre_data_vencimento;

    @FXML
    private JFXButton empre_btn_devolver;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
          if(emprestimo_data != null){
                    
                    
                    ArrayList<String> list = new ArrayList(Arrays.asList(emprestimo_data.getLivros().replace("[","").replace("]","").replace(" ", "").split(","))); //transforma a lista de livros em texto para Array
                    ArrayList<String> list_data = new ArrayList(Arrays.asList(emprestimo_data.getData().replace("[","").replace("]","").replace(" ", "").split(",")));
                    
                    list.forEach(id->{        

                            empre_list.getItems().add(new Database().get_livro(Integer.valueOf(id.trim())));
                    });
                    
                    empre_list.setOnMousePressed((event)->{
                        
                        DateTime data_emprestimo = new DateTime(list_data.get(empre_list.getSelectionModel().getSelectedIndex()).trim()); //pega o index do livro na empre_list para selecionar a data do emprestimo
                        
                        Tabela_usuario usuario = emprestimo_data.getUser();
                        DateTime data_entrega;
                        if(usuario.getTipo().equals("Professor")){  // verifica se é professor
                            data_entrega = data_emprestimo.plusDays(15); // e soma 15 dias para ter a data de entrega
                        }else{
                            data_entrega = data_emprestimo.plusDays(7); // soma 7 se for aluno
                        }
                        
                        Integer dias_atraso = Days.daysBetween(data_entrega, new DateTime()).getDays();
                        
                        if(dias_atraso > 0){
                            NumberFormat format = DecimalFormat.getInstance();
                            format.setMinimumFractionDigits(2);
                            format.setMaximumFractionDigits(2);                         
                            
                            empre_multa.setText("R$ "+format.format(dias_atraso));
                        }else{
                            empre_multa.setText("R$ 00,00");
                        }
                       
                       
                        empre_data_emprestimo.setText(data_emprestimo.toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
                        empre_data_vencimento.setText(data_entrega.toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
                        
                        empre_btn_devolver.setDisable(false);

                    });
                    
                    

                    empre_usuario.setText(emprestimo_data.getUser().toString());
                    
                    empre_btn_devolver.setOnAction(action ->{ 
                            
                            if(empre_list.getItems().size()>1){
                                
                                    Tabela_livro livro_selecionado = empre_list.getSelectionModel().getSelectedItem(); 
                                    int index = list.indexOf(livro_selecionado.getISBN().toString()); // pega o index do livro selecionado

                                    list.remove(index); // remove do array o livro e a data
                                    list_data.remove(index); 
                                    
                                    emprestimo_data.setLivros(list.toString()); // atualiza os livros e a data do objeto emprestimo_data
                                    emprestimo_data.setData(list_data.toString());            


                                    Integer row =  IntStream.range(0, HomeController.homeController.rows_tabela_emprestimo.size())
                                                     .filter(i -> HomeController.homeController.rows_tabela_emprestimo.get(i).getId().equals(emprestimo_data.getId()))
                                                     .findAny()
                                                     .orElse(-1); // pega o index desse emprestimo na tabela

                                    if(row >-1){
                                        HomeController.homeController.rows_tabela_emprestimo.set(row, emprestimo_data); // atualiza a informação nova na tabela emprestimo
                                        new Database().update_emprestimo(emprestimo_data); // salva o objeto atualizado no banco de dados
                                     }
                                
                            }else{
                                    int row =  IntStream.range(0, HomeController.homeController.rows_tabela_emprestimo.size())
                                                     .filter(i -> HomeController.homeController.rows_tabela_emprestimo.get(i).getId().equals(emprestimo_data.getId()))
                                                     .findAny()
                                                     .orElse(-1); // pega o index desse emprestimo na tabela

                                    if(row >-1){
                                        HomeController.homeController.rows_tabela_emprestimo.remove(row); // remove o ultimo emprestimo da tabela
                                        new Database().delete_emprestimo(emprestimo_data.getId()); // remove o ultimo emprestimo do banco de dados
                                     }
                                    
                                    
                            }
                        
                                                      
                           showbox.close_box_emprestimo_detalhe();
                            
                    });
                    
                          
 
          }  
    }    
    
}
