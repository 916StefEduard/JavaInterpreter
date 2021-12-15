package View;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI extends Application {
    public class FileData {
        SimpleStringProperty address;
        SimpleStringProperty path;
        FileData(String fileName, String path) {
            this.address = new SimpleStringProperty(fileName);
            this.path = new SimpleStringProperty(path);
        }
        public String getFileName(){
            return address.get();
        }
        public void setFileName(String fname){
            address.set(fname);
        }
        public String getPath(){
            return path.get();
        }
        public void setPath(String fpath){
            path.set(fpath);
        }
    }
    public void start(Stage stage) {
        Label label = new Label("File Data:");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        label.setFont(font);
        var table = new TableView<FileData>();
        final ObservableList<FileData> data = FXCollections.observableArrayList();
        data.add(new FileData("a","B"));
        var fileNameCol = new TableColumn("File Name");
        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        var pathCol = new TableColumn("Path");
        pathCol.setCellValueFactory(new PropertyValueFactory("path"));
        table.setItems(data);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().addAll(fileNameCol, pathCol);
        table.setMaxSize(350, 200);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 50, 50, 60));
        vbox.getChildren().addAll(label, table);
        Scene scene = new Scene(vbox, 595, 230);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}