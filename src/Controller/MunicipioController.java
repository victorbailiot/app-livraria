package Controller;

import DAO.MunicipioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class MunicipioController implements Initializable
{
    @FXML private Button btnInserir,btnDeletar,btnRefresh;
    @FXML private TextField txfNome;
    @FXML private ComboBox<Uf> cbuf = new ComboBox();
    @FXML private TableView<Municipio> tableView = new TableView<>();
    @FXML private TableColumn<Municipio,Integer> colId = new TableColumn<>("ID");
    @FXML private TableColumn<Municipio,String> colNome = new TableColumn<>("NOME");
    @FXML private TableColumn<Municipio,Integer> colUfid = new TableColumn<>("ESTADO_ID");
    @FXML private TableColumn<Municipio,String> colUfNome = new TableColumn<>("ESTADO");

    private UfDAO ufd;
    private Municipio mun = new Municipio();
    private MunicipioDAO munDao = new MunicipioDAO();
    private Uf CbObjetoSelecionado;
    private Municipio TbObjetoSelecionado;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("Municipio Controller");
        InitTable();
        InitCombox();
    }


    public void InitTable()
    {
        System.out.println(munDao.listarTodos().size());
        TbObjetoSelecionado = null;
        tableView.setEditable(true);

        colId.setCellValueFactory( new PropertyValueFactory<Municipio,Integer>("id") );

        colUfid.setCellValueFactory((new PropertyValueFactory<Municipio,Integer>("estado_id")));

        colNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(SendCommitNome);

        colUfNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEstado()) );

        tableView.setOnMouseClicked(TableClick);
        tableView.setItems(munDao.listarTodos());

    }

    public void InitCombox()
    {
        ufd = new UfDAO();
        cbuf.setPromptText("Selecione um Estado");
        cbuf.cellFactoryProperty();
        cbuf.setOnMouseClicked(ComboClick);
        cbuf.setCellFactory(ComboFactory);

        cbuf.setConverter(new StringConverter<Uf>() {
            @Override
            public String toString(Uf uf) {
                if (uf == null){
                    return null;
                } else {
                    return uf.getNome();
                }
            }
            @Override
            public Uf fromString(String userId) {
                return null;
            }
        });

        cbuf.setOnAction(ComboAction);
    }



    public void Inserir()
    {
        mun.setNome(txfNome.getText());
        mun.setUf_id(CbObjetoSelecionado.getId());

        munDao.Inserir(mun);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de autores");
        alert.setHeaderText("Municipio cadastrado com sucesso");
        alert.showAndWait();
        limparCampos();
        InitTable();
    }

    public void Deletar()
    {
        try
        {
            if(TbObjetoSelecionado.getId() != 0)
            {
                munDao.Deletar(TbObjetoSelecionado);
                InitTable();
            }
        }catch (Exception e){
            System.out.println("Erro ao Deletar");
            System.out.println(e);
        }
    }

    private void limparCampos()
    {
        txfNome.setText("");
        InitCombox();
        txfNome.requestFocus();
    }



    private EventHandler<MouseEvent> TableClick = evt -> {
        TbObjetoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (TbObjetoSelecionado != null)
            System.out.println("Selecionado: " + TbObjetoSelecionado.getNome());
    };



    private EventHandler<TableColumn.CellEditEvent<Municipio, Integer> > SendCommitUfid = evt -> {
        ((Municipio) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setUf_id(evt.getNewValue());
        munDao.alterar( ((Municipio) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Municipio, String> > SendCommitNome = evt -> {
        ((Municipio) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setNome(evt.getNewValue());
        munDao.alterar( ((Municipio) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<MouseEvent> ComboClick = evt -> {
        ufd = new UfDAO();
        cbuf.setItems(ufd.listarTodos());
    };
    private EventHandler<ActionEvent> ComboAction = evt -> {
        CbObjetoSelecionado = cbuf.getSelectionModel().getSelectedItem();
        if (CbObjetoSelecionado != null)
            System.out.println("Selecionado: " + CbObjetoSelecionado.getNome());
    };

    private Callback<ListView<Uf>,ListCell<Uf>> ComboFactory = evt ->
    {
        return new ListCell<Uf>() {
            @Override
            protected void updateItem(Uf item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getUf() + " - " + item.getNome());
                }
            }
        };
    };

}
