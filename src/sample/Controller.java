
package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;




public class Controller {

    String ciutatSel;
    @FXML
    ListView list = new ListView();
    @FXML
    MenuButton ciutats;
    @FXML
    CheckMenuItem bcn;
    @FXML
    CheckMenuItem bdln;
    @FXML
    CheckMenuItem grn;
    @FXML
    CheckMenuItem sbd;
    @FXML
    ImageView icon0, icon1, icon2, icon3, icon4, icon5, icon6,icondia,icondema,icondemp;
    @FXML
    Label tempmin0, tempmin1, tempmin2, tempmin3, tempmin4, tempmin5, tempmin6,
            tempmax0, tempmax1, tempmax2, tempmax3, tempmax4, tempmax5, tempmax6,
            pres0, pres1, pres2, pres3, pres4, pres5, pres6,
            hum0, hum1, hum2, hum3, hum4, hum5, hum6,
            dirvent0, dirvent1, dirvent2, dirvent3, dirvent4, dirvent5, dirvent6,
            velvent0, velvent1, velvent2, velvent3, velvent4, velvent5, velvent6;
    @FXML
    Tab dia, dia1, dia2, dia3, dia4, dia5, dia6;
    @FXML
    Text tempdia,tempdema,tempdemp,lvldemp;
    OpenWeatherMap temps;
    Image image;

    /**
     * Metode tancar la app
     *
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     *Metode que mostra la informació de l'aplicació
     * @param actionEvent
     */
    public void about(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Aplicació del temps");
        alert.setContentText("Podem veure la previsió del temps");
        alert.showAndWait();
    }

    /**
     *Funció que s'executa al iniciar l'aplicació
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void initialize() throws IOException, SAXException, ParserConfigurationException {
        /**
         * Per defecte cridem a la funció refresh que per defecte ens mostra la informació de la la ciutat
         * Barcelona.
         */
        refresh();
    }

    /**
     *
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void refresh() throws IOException, SAXException, ParserConfigurationException {
        /**
         * Omplim el ListView amb la informacó de tota la setmana.
         */
        list.setVisible(true);
        temps = new OpenWeatherMap();
        ObservableList<String> items = FXCollections.observableArrayList();
        ciutatSel = ciutats.getText();
        temps.refresh(items, ciutatSel);
        list.setItems(items);
        omplirImatge(0, icondia);
        omplirImatge(1,icondema);
        omplirImatge(2, icondemp);
        tempdia.setText(temps.tempMax.get(0) + "º");
        tempdema.setText(temps.tempMax.get(1)+"º");
        tempdemp.setText(temps.tempMax.get(2)+"º");
        lvldemp.setText(temps.dia.get(3));


        /**
         * Omplim totes les pestanyes del PanelTab.
         */
        rellenarTab(image, dia, icon0, pres0, hum0, tempmax0, tempmin0, velvent0, dirvent0, 0);
        rellenarTab(image, dia1, icon1, pres1, hum1, tempmax1, tempmin1, velvent1, dirvent1, 1);
        rellenarTab(image, dia2, icon2, pres2, hum2, tempmax2, tempmin2, velvent2, dirvent2, 2);
        rellenarTab(image, dia3, icon3, pres3, hum3, tempmax3, tempmin3, velvent3, dirvent3, 3);
        rellenarTab(image, dia4, icon4, pres4, hum4, tempmax4, tempmin4, velvent4, dirvent4, 4);
        rellenarTab(image, dia5, icon5, pres5, hum5, tempmax5, tempmin5, velvent5, dirvent5, 5);
        rellenarTab(image, dia6, icon6, pres6, hum6, tempmax6, tempmin6, velvent6, dirvent6, 6);
    }

    /**
     * Serveix per sel·lecionar la ciutat de la que volem extreure la informació
     *
     * @param actionEvent
     */
    public void menu(ActionEvent actionEvent) {
        bcn.setSelected(false);
        bdln.setSelected(false);
        grn.setSelected(false);
        sbd.setSelected(false);

        if (actionEvent.getSource().equals(bcn)) {
            bcn.setSelected(true);
            ciutats.setText(bcn.getText());
        } else if (actionEvent.getSource().equals(sbd)) {
            sbd.setSelected(true);
            ciutats.setText(sbd.getText());
        } else if (actionEvent.getSource().equals(grn)) {
            grn.setSelected(true);
            ciutats.setText(grn.getText());
        } else {
            bdln.setSelected(true);
            ciutats.setText(bdln.getText());
        }
    }

    /**
     * Funcio per obrir cada un dels Tabs.
     *
     * @param image
     * @param dia
     * @param icon0
     * @param pres0
     * @param hum0
     * @param tempmax0
     * @param tempmin0
     * @param velvent0
     * @param dirvent0
     * @param i
     */
    public void rellenarTab(Image image, Tab dia, ImageView icon0, Label pres0, Label hum0, Label tempmax0, Label tempmin0, Label velvent0, Label dirvent0, int i) {

        dia.setText(String.valueOf(temps.dia.get(i)));
        omplirImatge(i,icon0);
        pres0.setText("Pressión :  " + temps.presion.get(i));
        hum0.setText("Humedad :  " + temps.humidad.get(i));
        tempmax0.setText("Temperatura maxima :  " + temps.tempMax.get(i).toString());
        tempmin0.setText("Temperatura minima :  " + temps.tempMin.get(i).toString());
        velvent0.setText("Velocidad viento :  " + temps.velVent.get(i).toString() + " mps");
        dirvent0.setText("Dirección viento :  " + temps.dirVent.get(i));

    }

    /**
     *Funció per omplir amb un image un ImageView.
     * @param i
     * @param icon0
     */

    public void omplirImatge(int i, ImageView icon0){

        image = new Image(temps.iconid.get(i));
        icon0.setImage(image);
        icon0.setPreserveRatio(true);
        icon0.setSmooth(true);
        icon0.setCache(true);

    }


    public void graf(ActionEvent actionEvent) {

            Stage stage = new Stage();
            stage.setTitle("Temperaturas de la semana");
            //defining the axes
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("");
            //creating the chart
            final LineChart<Number,Number> lineChart =
                    new LineChart<Number,Number>(xAxis,yAxis);

            lineChart.setTitle("Temperaturas");
            //defining a series
            XYChart.Series series = new XYChart.Series();
            series.setName("Temperaturas máximas");


            series.getData().add(new XYChart.Data(1,temps.tempMax.get(0)));
            series.getData().add(new XYChart.Data(2, temps.tempMax.get(1)));
            series.getData().add(new XYChart.Data(3, temps.tempMax.get(2)));
            series.getData().add(new XYChart.Data(4, temps.tempMax.get(3)));
            series.getData().add(new XYChart.Data(5, temps.tempMax.get(4)));
            series.getData().add(new XYChart.Data(6, temps.tempMax.get(5)));
            series.getData().add(new XYChart.Data(7, temps.tempMax.get(6)));


        
            XYChart.Series series2 = new XYChart.Series();
            series2.setName("Temperaturas mínimas");


            series2.getData().add(new XYChart.Data(1,temps.tempMin.get(0)));
            series2.getData().add(new XYChart.Data(2, temps.tempMin.get(1)));
            series2.getData().add(new XYChart.Data(3, temps.tempMin.get(2)));
            series2.getData().add(new XYChart.Data(4, temps.tempMin.get(3)));
            series2.getData().add(new XYChart.Data(5, temps.tempMin.get(4)));
            series2.getData().add(new XYChart.Data(6, temps.tempMin.get(5)));
            series2.getData().add(new XYChart.Data(7, temps.tempMin.get(6)));

            lineChart.getData().addAll(series,series2);

            Scene scene  = new Scene(lineChart,800,600);
            stage.setScene(scene);
            stage.show();

    }
}
