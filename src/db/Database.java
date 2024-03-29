/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Emprestimo;
import modelos.Tabela_livro;
import modelos.Tabela_usuario;
/**
 *
 * @author melksedek
 */
public class Database {
    private Connection conn = connection();
    public static void main() {
        
    }
  
    
    private Connection connection(){
         Connection connection = null ;
         try{
                String reader = new File("banco.db").getCanonicalPath();
                System.out.println(reader);
                connection = DriverManager.getConnection("jdbc:sqlite:"+reader);

                Statement statement = connection.createStatement();
                
                    statement.execute("CREATE TABLE IF NOT EXISTS TABELA_LIVROS( ID INTEGER, ISBN INTEGER, TITULO STRING, AUTORES STRING, EDICAO STRING, EDITORA STRING, ANO INTEGER )");
                    statement.execute("CREATE TABLE IF NOT EXISTS TABELA_USUARIOS( ID INTEGER, NOME STRING, LOGIN STRING, SENHA STRING, TIPO STRING)");
                    statement.execute("CREATE TABLE IF NOT EXISTS TABELA_EMPRESTIMOS( ID INTEGER, ID_USER STRING, IDS_LIVROS STRING, DATA STRING, MULTA STRING)");
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }catch(IOException ex){
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
        return connection;
    }
    
    public ArrayList<Tabela_livro> get_livros() {
            
        
        
        ArrayList<Tabela_livro> list = new ArrayList<>();   
            try{
                
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TABELA_LIVROS");
                ResultSet resultSet = stmt.executeQuery();
                
                
                while (resultSet.next()) {
                    
                    Tabela_livro livro = new Tabela_livro();
                    livro.setISBN(resultSet.getString("ISBN"));
                    livro.setTitulo(resultSet.getString("TITULO"));
                    livro.setAutores(resultSet.getString("AUTORES"));
                    livro.setEdicao(resultSet.getString("EDICAO"));
                    livro.setEditora(resultSet.getString("EDITORA"));
                    livro.setAno(resultSet.getInt("ANO"));
                                        
                    list.add(livro);
                }
                
               
                
            }catch(SQLException e){
                
            }
            
             return list;
    }
    
    public ArrayList<Tabela_livro> busca_livro(String busca, Integer limite) {
            
       
        
        ArrayList<Tabela_livro> list = new ArrayList<>();   
            try{
                
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM TABELA_LIVROS WHERE TITULO LIKE ? OR ISBN LIKE ? OR EDITORA LIKE ? LIMIT ?");                
                                  statement.setString(1, "%"+busca+"%");
                                  statement.setString(2, "%"+busca+"%");
                                  statement.setString(3, "%"+busca+"%");
                                  statement.setInt(4, limite);
                              
                ResultSet resultSet = statement.executeQuery();
                
                
                while (resultSet.next()) {
                    
                    Tabela_livro livro = new Tabela_livro();
                    livro.setISBN(resultSet.getString("ISBN"));
                    livro.setTitulo(resultSet.getString("TITULO"));
                    livro.setAutores(resultSet.getString("AUTORES"));
                    livro.setEdicao(resultSet.getString("EDICAO"));
                    livro.setEditora(resultSet.getString("EDITORA"));
                    livro.setAno(resultSet.getInt("ANO"));
                                        
                    list.add(livro);
                }
                
               
                
            }catch(SQLException e){
                
            }
            
             return list;
    }
    
    public void set_livro(Tabela_livro livro)  {
                
                try{
              
                PreparedStatement statement = conn.prepareStatement("INSERT INTO TABELA_LIVROS( ISBN, TITULO, AUTORES, EDICAO, EDITORA, ANO) VALUES (?, ?, ?, ?, ?, ?)");
                                  statement.setString(1, livro.getISBN());
                                  statement.setString(2, livro.getTitulo());
                                  statement.setString(3, livro.getAutores());
                                  statement.setString(4, livro.getEdicao());
                                  statement.setString(5, livro.getEditora());
                                  statement.setInt(6, livro.getAno());
                                  
                                  statement.executeUpdate();
                }catch(SQLException e){
                    
                }
        
    }
    
     public Tabela_livro get_livro(String id) {
            
        
        Tabela_livro livro = new Tabela_livro();
         
            try{
                
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM TABELA_LIVROS WHERE ISBN = ?");                
                                  statement.setString(1, id);                                  
                              
                ResultSet resultSet = statement.executeQuery();             
                    
                
                livro.setISBN(resultSet.getString("ISBN"));
                livro.setTitulo(resultSet.getString("TITULO"));
                livro.setAutores(resultSet.getString("AUTORES"));
                livro.setEdicao(resultSet.getString("EDICAO"));
                livro.setEditora(resultSet.getString("EDITORA"));
                livro.setAno(resultSet.getInt("ANO"));
                
            }catch(SQLException e){
                
            }
            
             return livro;
    }
     
    public void update_livro(Tabela_livro livro){
           try{
                
                PreparedStatement statement = conn.prepareStatement("UPDATE TABELA_LIVROS SET ISBN=?, TITULO=?, AUTORES=?, EDICAO=?, EDITORA=?, ANO=? WHERE ISBN = ?");
                                  statement.setString(1, livro.getISBN());
                                  statement.setString(2, livro.getTitulo());
                                  statement.setString(3, livro.getAutores());
                                  statement.setString(4, livro.getEdicao());
                                  statement.setString(5, livro.getEditora());
                                  statement.setInt(6, livro.getAno());
                                  statement.setString(7, livro.getISBN());
                                  
                                  statement.executeUpdate();
                }catch(SQLException e){
                    
                }
    }
    
    public void excluir_livro(String ISBN){
              
                try{
                    
                    PreparedStatement statement = conn.prepareStatement("DELETE FROM TABELA_LIVROS WHERE ISBN = ?");
                                      statement.setString(1, ISBN);  
                                      statement.executeUpdate();
                }catch(SQLException e){
                    
                }
    }
    
    public ArrayList<Tabela_usuario> get_all_usuario(){
        
        
        ArrayList<Tabela_usuario> list = new ArrayList<>();   
            try{
                
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TABELA_USUARIOS");
                ResultSet resultSet = stmt.executeQuery();
                
                
                while (resultSet.next()) {
                    
                    Tabela_usuario usuario = new Tabela_usuario();
                    usuario.setId(resultSet.getString("ID"));
                    usuario.setNome(resultSet.getString("NOME"));
                    usuario.setLogin(resultSet.getString("LOGIN"));
                    usuario.setSenha(resultSet.getString("SENHA"));
                    usuario.setTipo(resultSet.getString("TIPO"));
                  
                                        
                    list.add(usuario);
                }
                
               
                
            }catch(SQLException e){
                
            }
            
             return list;
    }
    
    public ArrayList<Tabela_usuario> busca_usuario(String busca, Integer limite) {
   
        
        ArrayList<Tabela_usuario> list = new ArrayList<>();   
            try{
             
                                 
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TABELA_USUARIOS WHERE NOME LIKE ? OR LOGIN LIKE ? LIMIT ?");
                                  stmt.setString(1, "%"+busca+"%");
                                  stmt.setString(2, "%"+busca+"%");
                                  stmt.setInt(3, limite);
                ResultSet resultSet = stmt.executeQuery();
                
                
                while (resultSet.next()) {
                    
                    Tabela_usuario usuario = new Tabela_usuario();
                    usuario.setId(resultSet.getString("ID"));
                    usuario.setNome(resultSet.getString("NOME"));
                    usuario.setLogin(resultSet.getString("LOGIN"));
                    usuario.setSenha(resultSet.getString("SENHA"));
                    usuario.setTipo(resultSet.getString("TIPO"));
                  
                                        
                    list.add(usuario);
                }
                
               
                
            }catch(SQLException e){
                
            }
            
             return list;
    }
    
      public Tabela_usuario get_user(String id) {
    
            Tabela_usuario usuario = new Tabela_usuario();
            try{
             
                                 
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TABELA_USUARIOS WHERE ID = ?");
                                  stmt.setString(1, id);
                     
                ResultSet resultSet = stmt.executeQuery();            
                    
                    usuario.setId(resultSet.getString("ID"));
                    usuario.setNome(resultSet.getString("NOME"));
                    usuario.setLogin(resultSet.getString("LOGIN"));
                    usuario.setSenha(resultSet.getString("SENHA"));
                    usuario.setTipo(resultSet.getString("TIPO"));                  
                                        
                   
          
               
                
            }catch(SQLException e){
                
            }
            
             return usuario;
    }
    
    public void set_usuario(Tabela_usuario usuario){
           
                try{
                 
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO TABELA_USUARIOS( ID, NOME, LOGIN, SENHA, TIPO) VALUES (?, ?, ?, ?, ?)");
                                      statement.setString(1, usuario.getId());
                                      statement.setString(2, usuario.getNome());
                                      statement.setString(3, usuario.getLogin());
                                      statement.setString(4, usuario.getSenha());
                                      statement.setString(5, usuario.getTipo());
                                   

                                      statement.executeUpdate();
                }catch(SQLException e){
                    
                }
    }
    
    public void update_usuario(Tabela_usuario usuario){
          try{
                
                PreparedStatement statement = conn.prepareStatement("UPDATE TABELA_USUARIOS SET NOME=?, LOGIN=?, SENHA=?, TIPO=? WHERE ID = ?");
                                  statement.setString(1, usuario.getId());
                                  statement.setString(2, usuario.getNome());
                                  statement.setString(3, usuario.getLogin());
                                  statement.setString(4, usuario.getSenha());
                                  statement.setString(5, usuario.getTipo());                      
                                  
                                  statement.executeUpdate();
                }catch(SQLException e){
                    
                }
    }
    public void excluir_usuario(String id){
         
                try{
            
                    PreparedStatement statement = conn.prepareStatement("DELETE FROM TABELA_USUARIOS WHERE ID = ?");
                                      statement.setString(1, id);  
                                      statement.executeUpdate();
                }catch(SQLException e){
                    
                }
    }
    
    public void set_emprestimo(Emprestimo emprestimo){
           emprestimo.gerarId();
           try{
        
                    
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO TABELA_EMPRESTIMOS( ID, ID_USER, IDS_LIVROS, DATA) VALUES (?, ?, ?, ?)");
                                      statement.setString(1, emprestimo.getId());
                                      statement.setString(2, emprestimo.getUser().getId());
                                      statement.setString(3, emprestimo.getLivros());
                                      statement.setString(4, emprestimo.getData());

                                      statement.executeUpdate();
                }catch(SQLException e){
                    
                }
    }
    
    
     public ArrayList<Emprestimo> get_all_emprestimos(){
           ArrayList<Emprestimo> emprestimos = new ArrayList<>();
           try{
                 
                    
                    PreparedStatement statement = conn.prepareStatement("SELECT * FROM TABELA_EMPRESTIMOS");
                                  

                    ResultSet resultado =  statement.executeQuery();
                    
                    while (resultado.next()) {
                           Emprestimo emprestimo = new Emprestimo();
                                      emprestimo.setId(resultado.getString("ID"));
                                      emprestimo.setUser(get_user(resultado.getString("ID_USER")));
                                      emprestimo.setLivros(resultado.getString("IDS_LIVROS"));
                                      emprestimo.setData(resultado.getString("DATA"));
                                      
                            emprestimos.add(emprestimo);
                    }
                }catch(SQLException e){
                    
                }
           
           return emprestimos;
    }
     
      public Emprestimo get_emprestimo_byUser(String ID_USER){
          
          
           
            Emprestimo emprestimo = new Emprestimo();
            try{
                     
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TABELA_EMPRESTIMOS WHERE ID_USER = ?");
                                  stmt.setString(1, ID_USER);
                     
                ResultSet resultado = stmt.executeQuery(); 
                
                if(resultado.next()){
                    System.out.println("Usuario encontrado "+resultado.getString("ID_USER"));  
                    System.out.println("Usuario encontrado "+resultado.getString("ID_USER"));
                    emprestimo.setId(resultado.getString("ID"));
                    emprestimo.setUser(get_user(resultado.getString("ID_USER")));
                    emprestimo.setLivros(resultado.getString("IDS_LIVROS"));
                    emprestimo.setData(resultado.getString("DATA"));  
                }else{
                    return null;
                }
                
                
                          
                  
                
            }catch(SQLException e){
                
            }finally{

            }
            
             return emprestimo;
          
         
    }

    public void update_emprestimo(Emprestimo emprestimo) {
    
        try{
                
                System.out.println("Emprestimo update livros "+emprestimo.getLivros());
                PreparedStatement statement = conn.prepareStatement("UPDATE TABELA_EMPRESTIMOS SET ID_USER= ?, IDS_LIVROS= ?, DATA= ? WHERE ID = ?");
                                  statement.setString(1, emprestimo.getUser().getId());
                                  statement.setString(2, emprestimo.getLivros());
                                  statement.setString(3, emprestimo.getData());
                                  statement.setString(4, emprestimo.getId());
                                                  
                                  
                                  statement.executeUpdate();
                }catch(SQLException e){
                    
                }
    }

    public void delete_emprestimo(String id) {
           
            try{
                
                PreparedStatement statement = conn.prepareStatement("DELETE FROM TABELA_EMPRESTIMOS WHERE ID = ?");
                                  statement.setString(1, id);  
                                  statement.executeUpdate();
            }catch(SQLException e){

            }
    }

    
}
