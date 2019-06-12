package Controller;

import DAO.EditoraDAO;
import Model.Editora;
import javafx.beans.property.*;
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
    @FXML private TableView<Editora> tableView = new TableView<>();
    @FXML private TableColumn<Editora,Integer> colId = new TableColumn<>("ID");
    @FXML private TableColumn<Editora,String> colNome = new TableColumn<>("NOME");
    @FXML private TableColumn<Editora,String> colEnd = new TableColumn<>("ENDERECO");
    @FXML private TableColumn<Editora,String> colBairro = new TableColumn<>("BAIRRO");
    @FXML private TableColumn<Editora,String> colTel = new TableColumn<>("TELEFONE");
    //@FXML private TableColumn<Editora,String> colMun = new TableColumn<>("MUNICIPIO_ID");
    @FXML private Button btnList,btnSalvar,btnDeletar;
    @FXML private TextField txfNome,txfEmail;

    private Editora editora = new Editora();
    private EditoraDAO editoraDao = new EditoraDAO();
    private Editora ObjetoSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        InitTable();
    }

    public void InitTable() {
        ObjetoSelecionado = null;
        colId.setCellValueFactory( new PropertyValueFactory<Editora,Integer>("id") );

        colNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(SendCommitNome);

        colEnd.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndereco()) );
        colEnd.setCellFactory(TextFieldTableCell.forTableColumn());
        colEnd.setOnEditCommit(SendCommitEndereco);

        colBairro.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getBairro()) );
        colBairro.setCellFactory(TextFieldTableCell.forTableColumn());
        colBairro.setOnEditCommit(SendCommitNome);

        colTel.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTelefone()) );
        colTel.setCellFactory(TextFieldTableCell.forTableColumn());
        colTel.setOnEditCommit(SendCommitNome);

        //colMun.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTelefone()) );
        //colMun.setCellFactory(TextFieldTableCell.forTableColumn());
        //colMun.setOnEditCommit(SendCommitNome);

        tableView.setItems(editoraDao.listarTodos());
        tableView.setOnMouseClicked(TableClick);
    }

    private EventHandler<MouseEvent> TableClick = evt -> {
        ObjetoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (ObjetoSelecionado != null)
            System.out.println("Selecionado: " + ObjetoSelecionado.getNome());
    };

    private EventHandler<TableColumn.CellEditEvent<Editora, String> > SendCommitNome = evt -> {
        ((Editora) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setNome(evt.getNewValue());
        editoraDao.alterar( ((Editora) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Editora, String> > SendCommitEndereco = evt -> {
        ((Editora) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setEndereco(evt.getNewValue());
        editoraDao.alterar( ((Editora) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    public void Deletar()
    {
        try
        {
            if(ObjetoSelecionado.getId() != 0)
            {
                InitTable();
            }

        }catch (Exception e){
            System.out.println("Erro ao Deletar");
            System.out.println(e);
        }

    }

    public void Inserir()
    {
        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de autores");
        alert.setHeaderText("Autor cadastrado com sucesso");
        alert.showAndWait();

        InitTable();
    }

    private void limparCampos()
    {
        txfNome.setText("");
        txfEmail.setText("");
        txfNome.requestFocus();
    }


}
