package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionMgr
{
    private Connection conexao;

    public ConnectionMgr() {
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public Connection getConexao() {
        return conexao;
    }

    public void InitConnection() {
        System.out.println("Iniciando Conexão");
        try {
            conexao = DriverManager.getConnection ("jdbc:mysql://localhost:3306/db_livraria?useTimezone=true&serverTimezone=UTC", "root", "Suporte99");
        }
        catch (SQLException e){
            System.out.println("Erro ao conectar");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void CloseConnection()
    {
        try {
            conexao.close();
        }
        catch (SQLException e){
            System.out.println("Erro ao fechar conexão");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}