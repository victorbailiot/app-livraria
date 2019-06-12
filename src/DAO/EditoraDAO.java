package DAO;

import Model.Editora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditoraDAO extends ConnectionMgr
{
    public EditoraDAO()
    {
    }

    public void inserir(Editora editora)
    {
        String sql = "insert into editora (nome, site, endereco, bairro, telefone, municipio_id) VALUES (?, ?, ?, ?, ?, 1)";

        try
        {
            super.InitConnection();

            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, editora.getNome());
            stmt.setString(2, editora.getSite());
            stmt.setString(3, editora.getEndereco());
            stmt.setString(4, editora.getBairro());
            stmt.setString(5, editora.getTelefone());
            //stmt.setString(6, editora.getMunicipio_id());
            stmt.execute();

            super.CloseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList listarTodos()
    {
        String sql = "select id, nome, site, endereco, bairro, telefone from editoras";
        List<Editora> editoras = new ArrayList<>();

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Editora editora = new Editora();
                editora.setId(resultados.getInt("id"));
                editora.setNome(resultados.getString("nome"));
                editora.setSite(resultados.getString("site"));
                editora.setEndereco(resultados.getString("endereco"));
                editora.setBairro(resultados.getString("bairro"));
                editora.setTelefone(resultados.getString("telefone"));
                //editora.setMunicipio_id(resultados.getString("municipio_id"));
                editoras.add(editora);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(editoras);
        return data;
    }

    public void alterar(Editora editora)
    {
        String sql = "update autores set nome = ?, site = ?, endereco = ?, bairro = ?, telefone = ? where id = ?";

        try {

            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, editora.getNome());
            stmt.setString(2, editora.getSite());
            stmt.setString(3, editora.getEndereco());
            stmt.setString(4, editora.getBairro());
            stmt.setString(5, editora.getTelefone());
            stmt.setString(6, editora.getMunicipio_id());

            stmt.execute();

            System.out.println("Alterado " + editora.getNome());
            super.CloseConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Editora editora) {
        String sql = "delete from editoras where id = ?";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, editora.getId());
            //Executar
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar");
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    public Editora listarPorId(Editora editora)
    {
        String sql = "select id, nome, email from autores where id = ?";

        try {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, editora.getId());

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Encontra resultado
            while(resultados.next()) {
                editora.setId(resultados.getInt("id"));
                editora.setNome(resultados.getString("nome"));
                editora.setSite(resultados.getString("site"));
                editora.setEndereco(resultados.getString("endereco"));
                editora.setBairro(resultados.getString("bairro"));
                editora.setTelefone(resultados.getString("telefone"));
                editora.setMunicipio_id(resultados.getString("municipio_id"));
            }

            super.CloseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return editora;
    }
}
