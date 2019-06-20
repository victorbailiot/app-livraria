package DAO;

import Model.Editora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditoraDAO extends ConnectionMgr {
    public EditoraDAO() {
    }

    public void Inserir(Editora edt)
    {
        String sql = "insert into editoras(nome, site, endereco, bairro, telefone) values  (?, ?, ?, ?, ?)";
        try {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, edt.getNome());
            stmt.setString(2, edt.getSite());
            stmt.setString(3, edt.getEndereco());
            stmt.setString(4, edt.getBairro());
            stmt.setString(5, edt.getTelefone());
            stmt.execute();
            super.CloseConnection();
        }

        catch (SQLException e) {
            System.out.println("Erro inserir editora");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public ObservableList ListarTodos() {
        String sql = "select id, nome, site, endereco, bairro, telefone from editoras";
        List<Editora> liste = new ArrayList<>();

        try {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Editora editora = new Editora();
                editora.setId(resultados.getInt(1));
                editora.setNome(resultados.getString(2));
                editora.setSite(resultados.getString(3));
                editora.setEndereco(resultados.getString(4));
                editora.setBairro(resultados.getString(5));
                editora.setTelefone(resultados.getString(6));
                liste.add(editora);
            }
            super.CloseConnection();
        }

        catch (SQLException e) {
            System.out.println("Erro Editoras");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(liste);
        return data;
    }

    public void Alterar(Editora edt) {
        String sql = "update editoras set nome = ?, site = ?, endereco = ?, bairro = ?, telefone = ?, where id = ?";
        try {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, edt.getNome());
            stmt.setString(2, edt.getSite());
            stmt.setString(3, edt.getEndereco());
            stmt.setString(4, edt.getBairro());
            stmt.setString(5, edt.getTelefone());
            stmt.execute();
            System.out.println("Alterado " + edt.getNome());
            super.CloseConnection();

        } catch (SQLException e) {
            System.out.println("Erro ao Alterar Editora");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void Deletar(Editora edt) {
        String sql = "delete from editoras where id = ?";
        try {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, edt.getId());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Editora");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
