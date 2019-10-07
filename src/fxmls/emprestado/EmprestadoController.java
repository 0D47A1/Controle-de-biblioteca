/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmls.emprestado;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modelos.Tabela_livro;

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
        // TODO
    }    
    
}
