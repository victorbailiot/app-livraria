package DAO;

import Model.Municipio;
import Model.Uf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO extends ConnectionMgr
{
    public MunicipioDAO() {
    }

    public void Inserir(Municipio mun)
    {
        String sql = "INSERT INTO municipio (NOME, UF_ID) VALUES (?, ?)";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, mun.getNome());
            stmt.setInt(2, mun.getUf_id());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList listarTodos()
    {
        String sql = "select id, nome, estado_id, from municipio";

        List<Municipio> muns = new ArrayList<>();

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Municipio mun = new Municipio();
                mun.setId(resultados.getInt(1));
                mun.setNome(resultados.getString(2));
                muns.add(mun);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos municipios");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(muns);
        return data;
    }

    public void Deletar(Municipio mun)
    {
        String sql = "delete from municipio where id = ?";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, mun.getId());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Municipio");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
