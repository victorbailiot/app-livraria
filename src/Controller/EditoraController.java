package Controller;

import DAO.EditoraDAO;
import Model.Editora;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;


public class EditoraController  implements Initializable
{
    @FXML
    private TableView<Editora> tableView = new TableView<>();
    @FXML private TableColumn<Editora,Integer> colId = new TableColumn<>("ID");
    @FXML private TableColumn<Editora,String> colNome = new TableColumn<>("NOME");
    @FXML private TableColumn<Editora,String> colEnd = new TableColumn<>("ENDERECO");
    @FXML private TableColumn<Editora,String> colSite = new TableColumn<>("SITE");
    @FXML private TableColumn<Editora,String> colBairro = new TableColumn<>("BAIRRO");
    @FXML private TableColumn<Editora,String> colTel = new TableColumn<>("TELEFONE");
    @FXML private TableColumn<Editora,String> colMun = new TableColumn<>("MUNICIPIO");

    @FXML private Button btnList,btnSalvar,btnDeletar;
    @FXML private TextField txfNome,txfEnd, txfSite, txfBairro, txfTel;

    private Editora editora = new Editora();
    private EditoraDAO editoraDAO = new EditoraDAO();
    private Editora EdtObjetoSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        InitTable();
    }

    public void Inserir()
    {
        editora.setNome(txfNome.getText());
        editora.setEndereco(txfEnd.getText());
        editora.setSite(txfSite.getText());
        editora.setBairro(txfBairro.getText());
        editora.setTelefone(txfTel.getText());

        editoraDAO.Inserir(editora);

        limparCampos();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Editoras");
        alert.setHeaderText("Autor cadastrado com sucesso");
        alert.showAndWait();
        InitTable();
    }

    public void Deletar()
    {
        try
        {
            if(EdtObjetoSelecionado.getId() != 0)
            {
                editoraDAO.Deletar(EdtObjetoSelecionado);
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
        txfEnd.setText("");
        txfBairro.setText("");
        txfSite.setText("");
        txfTel.setText("");
        txfNome.requestFocus();
    }


    public void InitTable()
    {
        EdtObjetoSelecionado = null;
        tableView.setEditable(true);
        colId.setCellValueFactory( new PropertyValueFactory<Editora,Integer>("id") );

        colNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(SendCommitNome);

        colSite.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getSite()) );
        colSite.setCellFactory(TextFieldTableCell.forTableColumn());
        colSite.setOnEditCommit(SendCommitSite);

        colEnd.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndereco()) );
        colEnd.setCellFactory(TextFieldTableCell.forTableColumn());
        colEnd.setOnEditCommit(SendCommitEnd);

        colBairro.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getBairro()) );
        colBairro.setCellFactory(TextFieldTableCell.forTableColumn());
        colBairro.setOnEditCommit(SendCommitBairro);

        colTel.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTelefone()) );
        colTel.setCellFactory(TextFieldTableCell.forTableColumn());
        colTel.setOnEditCommit(SendCommitTel);

        tableView.setItems(editoraDAO.ListarTodos());
        tableView.setOnMouseClicked(TableClick);
    }

    private EventHandler<MouseEvent> TableClick = evt -> {
        EdtObjetoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (EdtObjetoSelecionado != null)
            System.out.println("Selecionado: " + EdtObjetoSelecionado.getNome());
    };

    private EventHandler<TableColumn.CellEditEvent<Editora, String> > SendCommitNome = evt -> {
        ((Editora) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setNome(evt.getNewValue());
        editoraDAO.Alterar( ((Editora) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Editora, String> > SendCommitSite = evt -> {
        ((Editora) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setSite(evt.getNewValue());
        editoraDAO.Alterar( ((Editora) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Editora, String> > SendCommitEnd = evt -> {
        ((Editora) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setEndereco(evt.getNewValue());
        editoraDAO.Alterar( ((Editora) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Editora, String> > SendCommitBairro = evt -> {
        ((Editora) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setBairro(evt.getNewValue());
        editoraDAO.Alterar( ((Editora) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Editora, String> > SendCommitTel = evt -> {
        ((Editora) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setTelefone(evt.getNewValue());
        editoraDAO.Alterar( ((Editora) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };





}
