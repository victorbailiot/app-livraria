package Controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

    public class InicioController implements Initializable {

        @FXML
        private AnchorPane holderPane;

        AnchorPane AfInicio, AfAutores, AfEditoras, AfLivros;
        @Override
        public void initialize(URL url, ResourceBundle rb)
        {
            try
            {
                AfInicio = FXMLLoader.load(getClass().getResource("/View/Tela_Inicio.fxml"));
                this.setNode(AfInicio);
            }catch (Exception e) {
                System.out.println("Erro ao iniciar InicioController");
                System.out.println(e);
            }
        }

        private void setNode(Node node)
        {
            holderPane.getChildren().clear();
            holderPane.getChildren().add((Node) node);

            FadeTransition ft = new FadeTransition(Duration.millis(600));
            ft.setNode(node);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();
        }

        @FXML
        private void toHome(ActionEvent event)
        {
            try
            {
                this.setNode(AfInicio);
            }catch (Exception e) {
                System.out.println(e);
            }
        }

        @FXML
        private void toAutores(ActionEvent event) {
            try
            {
                AfAutores = FXMLLoader.load(getClass().getResource("/View/Autores.fxml"));
                this.setNode(AfAutores);
            }catch (Exception e) {
                System.out.println("Erro ao toAutores");
                System.out.println(e);
            }

        }

        @FXML
        private void toEditoras(ActionEvent event)
        {
            try
            {
                AfEditoras = FXMLLoader.load(getClass().getResource("/View/Editoras.fxml"));
                setNode(AfEditoras);
            }catch (Exception e) {
                System.out.println("Erro ao toEditoras");
                System.out.println(e);
            }
        }

        @FXML
        private void toLivros(ActionEvent event)
        {
            try
            {
                AfLivros = FXMLLoader.load(getClass().getResource("/View/Livros.fxml"));
                setNode(AfLivros);
            }catch (Exception e) {
                System.out.println("Erro ao toMunicipio");
                System.out.println(e);
            }
        }
    }

