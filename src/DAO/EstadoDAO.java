package DAO;

import Model.Estado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EstadoDAO {
    private Connection conexao;

    public void conectar() {conexao = new ConnectionFactory().getConnection();}

    public void inserir(Estado estado) {
        //conexão com o banco
        conectar();

        String sql = "insert into estado (uf) values (?)";

        try{
            //preparar conexão
            PreparedStatement st = conexao.prepareStatement(sql);

        }catch (SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
