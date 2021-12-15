package View;
import Command.ExitCommand;
import Command.RunCommand;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.types.RefType;
import Model.value.*;
import Repo.Repo;
import Controller.Controller;
import Model.PrgState;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import Exception.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;


public class Main extends Application {

    public static Vector<Controller> mainFunction() throws Exception {
        StringValue outputPath = new StringValue("C:\\Users\\eddis\\WebstormProjects\\JavaProject\\src\\Data\\output.txt");
        StringValue inputPath = new StringValue("C:\\Users\\eddis\\WebstormProjects\\JavaProject\\src\\Data\\input.txt");

        //   ex 1:  int v; v = 2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        var typeChecker1 = new Dict<String, IType>();
        try {
         ex1.typeCheck(typeChecker1);
        } catch (Exception e) {
         e.printStackTrace();
        }
        var exeStack1 = new MyStack<IStmt>();
        var symTable1 = new Dict<String, IValue>();
        var out1 = new ArrayList<IValue>();
        var files1 = new Dict<StringValue, BufferedReader>();
        var heap1 = new Heap<Integer, IValue>();
        int id = 1;
        var prg1 = new PrgState(exeStack1, symTable1, out1, files1, heap1, ex1, id);
        var repo1 = new Repo(outputPath);
        repo1.addPrg(prg1);
        var controller1 = new Controller(repo1);

//         //   ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));
        var typeChecker2 = new Dict<String, IType>();
        try {
            ex2.typeCheck(typeChecker2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        var exeStack2 = new MyStack<IStmt>();
        var symTable2 = new Dict<String, IValue>();
        var out2 = new ArrayList<IValue>();
        var files2 = new Dict<StringValue, BufferedReader>();
        var heap2 = new Heap<Integer, IValue>();
        var prg2 = new PrgState(exeStack2, symTable2, out2, files2, heap2, ex2, id);
        var repo2 = new Repo(outputPath);
        repo2.addPrg(prg2);
        var controller2 = new Controller(repo2);

////         // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
             IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                     new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                     new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                             new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                             VarExp("v"))))));
             var typeChecker3 = new Dict<String, IType>();
             try {
                 ex3.typeCheck(typeChecker3);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             var exeStack3 = new MyStack<IStmt>();
             var symTable3 = new Dict<String, IValue>();
             var out3 = new ArrayList<IValue>();
             var file3 = new Dict<StringValue, BufferedReader>();
             var heap3 = new Heap<Integer, IValue>();
             var prg3 = new PrgState(exeStack3, symTable3, out3, file3, heap3, ex3, id);
             var repo3 = new Repo(outputPath);
             repo3.addPrg(prg3);
             var controller3 = new Controller(repo3);
//
////         // ex 4 : (open file:input.txt;(read data for variable:v from file:input.txt;print(v);close file:input.txt)))
             IStmt ex4 = new CompStmt(new VarDeclStmt("v", new IntType()),
                     new CompStmt(new OpenFileStmt(new VarExp(String.valueOf(inputPath))), new CompStmt(new ReadFileStmt(new VarExp(String.valueOf(inputPath)), new StringValue("v")),
                             new CompStmt(new PrintStmt(new VarExp("v")), new CloseFileStmt(new VarExp(String.valueOf(inputPath)))))));
             var typeChecker4 = new Dict<String, IType>();
             try {
                 ex4.typeCheck(typeChecker4);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             var exeStack4 = new MyStack<IStmt>();
             var symTable4 = new Dict<String, IValue>();
             var out4 = new ArrayList<IValue>();
             var file4 = new Dict<StringValue, BufferedReader>();
             var heap4 = new Heap<Integer, IValue>();
             var prg4 = new PrgState(exeStack4, symTable4, out4, file4, heap4, ex4, id);
             var repo4 = new Repo(outputPath);
             repo4.addPrg(prg4);
             var controller4 = new Controller(repo4);
//
////         //ex 5: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
             IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                     new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                             new CompStmt(new NewStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new HeapReadingExp(new VarExp("v"))), new PrintStmt(new ArithExp('+', new HeapReadingExp(new HeapReadingExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
             var typeChecker5 = new Dict<String, IType>();
             try {
                 ex5.typeCheck(typeChecker5);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             var exeStack5 = new MyStack<IStmt>();
             var symTable5 = new Dict<String, IValue>();
             var out5 = new ArrayList<IValue>();
             var file5 = new Dict<StringValue, BufferedReader>();
             var heap5 = new Heap<Integer, IValue>();
             var prg5 = new PrgState(exeStack5, symTable5, out5, file5, heap5, ex5, id);
             var repo5 = new Repo(outputPath);
             repo5.addPrg(prg5);
             var controller5 = new Controller(repo5);
//
////         //ex6 : Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
             IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                     new CompStmt(new PrintStmt(new HeapReadingExp(new VarExp("v"))), new CompStmt(new HeapAllocationStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp('+', new HeapReadingExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));
             var typeChecker6 = new Dict<String, IType>();
             try {
                 ex6.typeCheck(typeChecker6);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             var exeStack6 = new MyStack<IStmt>();
             var symTable6 = new Dict<String, IValue>();
             var out6 = new ArrayList<IValue>();
             var file6 = new Dict<StringValue, BufferedReader>();
             var heap6 = new Heap<Integer, IValue>();
             var prg6 = new PrgState(exeStack6, symTable6, out6, file6, heap6, ex6, id);
             var repo6 = new Repo(outputPath);
             repo6.addPrg(prg6);
            var controller6 = new Controller(repo6);
////
////         //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
             IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                     new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                             new CompStmt(new NewStmt("a", new VarExp("v")),
                                     new PrintStmt(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))))))));
             var typeChecker7 = new Dict<String, IType>();
             try {
                 ex7.typeCheck(typeChecker7);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             var exeStack7 = new MyStack<IStmt>();
             var symTable7 = new Dict<String, IValue>();
             var out7 = new ArrayList<IValue>();
             var file7 = new Dict<StringValue, BufferedReader>();
             var heap7 = new Heap<Integer, IValue>();
             var prg7 = new PrgState(exeStack7, symTable7, out7, file7, heap7, ex7, id);
             var repo7 = new Repo(outputPath);
             repo7.addPrg(prg7);
             var controller7 = new Controller(repo7);
////
////         //Ref 8:int v; v=4; (while (v>0) print(v);v=v-1);print(v)
             IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                     new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                             new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                             new PrintStmt(new VarExp("v")))));
             var typeChecker8 = new Dict<String, IType>();
             try {
                 ex8.typeCheck(typeChecker8);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             var exeStack8 = new MyStack<IStmt>();
             var symTable8 = new Dict<String, IValue>();
             var out8 = new ArrayList<IValue>();
             var file8 = new Dict<StringValue, BufferedReader>();
             var heap8 = new Heap<Integer, IValue>();
             var prg8 = new PrgState(exeStack8, symTable8, out8, file8, heap8, ex8, id);
             var repo8 = new Repo(outputPath);
             repo8.addPrg(prg8);
             var controller8 = new Controller(repo8);
//
//       //  int v; Ref int a; v=10;new(a,22);fork(wH(a,30);v=32;print(v);print(rH(a)));
//       //   print(v);print(rH(a))
//
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new HeapAllocationStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new HeapReadingExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))))))));

       var typeChecker9 = new Dict<String,IType>();
       try{
         ex9.typeCheck(typeChecker9);
       }catch(Exception e){
        e.printStackTrace();
       }
       var exeStack9 = new MyStack<IStmt>();
       var symTable9 = new Dict<String,IValue>();
       var out9 =  new ArrayList<IValue>();
       var file9 = new Dict<StringValue,BufferedReader>();
       var heap9 = new Heap<Integer,IValue>();
       var prg9 = new PrgState(exeStack9,symTable9,out9,file9,heap9,ex9,id);
       var repo9 = new Repo(outputPath);
       repo9.addPrg(prg9);
       var controller9 = new Controller(repo9);

         var textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0","exit"));
        textMenu.addCommand(new RunCommand("1",ex1.toString(),controller1));
        textMenu.addCommand(new RunCommand("2",ex2.toString(),controller2));
        textMenu.addCommand(new RunCommand("3",ex3.toString(),controller3));
        textMenu.addCommand(new RunCommand("4",ex4.toString(),controller4));
        textMenu.addCommand(new RunCommand("5",ex5.toString(),controller5));
        textMenu.addCommand(new RunCommand("6",ex6.toString(),controller6));
        textMenu.addCommand(new RunCommand("7",ex7.toString(),controller7));
        textMenu.addCommand(new RunCommand("8",ex8.toString(),controller8));
        textMenu.addCommand(new RunCommand("9",ex9.toString(),controller9));
        var controllers = new Vector<Controller>();
        controllers.add(controller1);
        controllers.add(controller2);
        controllers.add(controller3);
        controllers.add(controller4);
        controllers.add(controller5);
        controllers.add(controller6);
        controllers.add(controller7);
        controllers.add(controller8);
        controllers.add(controller9);
        textMenu.show();
        return controllers;
    }

public static  class HeapTable {
        SimpleStringProperty address;
        SimpleStringProperty value;
        HeapTable(String address, String value) {
            this.address = new SimpleStringProperty(address);
            this.value = new SimpleStringProperty(value);
        }
        public String getFileName(){
            return address.get();
        }
        public void setFileName(String fname){
            address.set(fname);
        }
        public String getPath(){
            return value.get();
        }
        public void setPath(String fpath){
            value.set(fpath);
        }
    }

public static class SymTable{
        SimpleStringProperty varName;
        SimpleStringProperty value;
        private SymTable(String varName,String value){
            this.varName = new SimpleStringProperty(varName);
            this.value = new SimpleStringProperty(value);
        }
        public String getFileName(){
            return varName.get();
        }
        public void setFileName(String fname){
            varName.set(fname);
        }
        public String getPath(){
            return value.get();
        }
        public void setPath(String fpath){
            value.set(fpath);
        }
}


 public void start(Stage stage) throws Exception {
     stage.setTitle("Statements");
     var listView = new ListView<String>();
     listView.getItems().add("ex 1: int v; v=2 ;Print(v)");
     listView.getItems().add("ex 2: a=2+3*5; b=a+1; Print(b)");
     listView.getItems().add("ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
     listView.getItems().add("ex 4: (open file:input.txt;(read data for variable:v from file:input.txt;print(v);close file:input.txt)))");
     listView.getItems().add("ex 5: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)");
     listView.getItems().add("ex 6: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);");
     listView.getItems().add("ex 7: Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))");
     listView.getItems().add("ex 8: Ref 8:int v; v=4; (while (v>0) print(v);v=v-1);print(v)");
     listView.getItems().add("ex 9: int v;Ref int a;v=10;new(a,22);fork(wH(a,30);v=32;print(v);print(rH(a)))+print(v);print(rH(a))");
     listView.setOrientation(Orientation.VERTICAL);
     var vbox = new VBox(listView);
     var scene = new Scene(vbox,600,270);
     stage.setScene(scene);
     listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
      private TableView<HeapTable> table = new TableView<HeapTable>();
      private ListView<String> Output = new ListView<String>();
      private ListView<String> FileTable = new ListView<String>();
      private ListView<String> Identifier = new ListView<String>();
      private TableView<SymTable> SymTable = new TableView<SymTable>();
      private ListView<String> ExeStack = new ListView<String>();
      private TextField textfield = new TextField();
      private Button button = new Button("RunOneStep");
      int remember = 0;
      int finished = 0;
      ArrayList<ArrayList<IValue>> wholeOutput = new ArrayList<ArrayList<Model.value.IValue>>();

      private final ObservableList<HeapTable> data = FXCollections.observableArrayList();
      private final ObservableList<String> outMap = FXCollections.observableArrayList();
      private final ObservableList<String> fileMap = FXCollections.observableArrayList();
      private final ObservableList<String> Id = FXCollections.observableArrayList();
      private final ObservableList<SymTable> varMap = FXCollections.observableArrayList();
      private final ObservableList<String> exeStack = FXCollections.observableArrayList();
      Vector<Controller> controllers = mainFunction();
      Controller controller ;
        @Override
        public void handle(MouseEvent mouseEvent) {
         table.getItems().clear();
         Output.getItems().clear();
         FileTable.getItems().clear();
         Identifier.getItems().clear();
         SymTable.getItems().clear();
         ExeStack.getItems().clear();
         int index = listView.getSelectionModel().getSelectedIndex();
         controller = controllers.get(index);
         Output = new ListView<String>(outMap);
         FileTable = new ListView<String>(fileMap);
         Identifier = new ListView<String>(Id);
         ExeStack = new ListView<String>(exeStack);
         var newStage = new Stage();
         var label1 = new Label("Heap Table:");
         var font1 = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
         label1.setFont(font1);
         var fileNameCol = new TableColumn("Address");
         fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
         var pathCol = new TableColumn("Value");
         pathCol.setCellValueFactory(new PropertyValueFactory("path"));
         table.setItems(data);
         table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         table.getColumns().addAll(fileNameCol, pathCol);
         table.setMaxSize(350, 200);
         var vbox1 = new VBox();
         vbox1.setSpacing(5);
         vbox1.setPadding(new Insets(10, 50, 50, 60));
         vbox1.getChildren().addAll(label1, table);

         var label2 = new Label("Output");
         var font2 = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
         label2.setFont(font2);
         var vbox2 = new VBox();
         vbox2.setPadding(new Insets(10,50,50,50));
         vbox2.getChildren().addAll(label2,Output);

         var label3 = new Label("FileTable");
         var font3 = Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,12);
         label3.setFont(font3);
         var vbox4 = new VBox();
         vbox4.setPadding(new Insets(10,50,50,60));
         vbox4.getChildren().addAll(label3,FileTable);

         var label4 = new Label("Identifier");
         var font4 = Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,12);
         label4.setFont(font4);
         var vbox5 = new VBox();
         vbox5.setSpacing(5);
         vbox5.setPadding(new Insets(10,50,50,60));
         vbox5.getChildren().addAll(label4,Identifier);

         var label6 = new Label("Thread Pool");
         var font6 = Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,12);
         label6.setFont(font6);
         vbox5.setSpacing(5);
         vbox5.setPadding(new Insets(10,50,50,60));
         vbox5.getChildren().addAll(label6,textfield);

         var label5 = new Label("Sym Table:");
         var font5 = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
         label5.setFont(font5);
         var fileNameCol2 = new TableColumn("Variable");
         fileNameCol2.setCellValueFactory(new PropertyValueFactory<>("fileName"));
         var pathCol2 = new TableColumn("Value");
         pathCol2.setCellValueFactory(new PropertyValueFactory("path"));
         SymTable.setItems(varMap);
         SymTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         SymTable.getColumns().addAll(fileNameCol2, pathCol2);
         SymTable.setMaxSize(350, 200);
         var vbox6 = new VBox();
         vbox6.setSpacing(5);
         vbox6.setPadding(new Insets(10, 50, 50, 60));
         vbox6.getChildren().addAll(label5, SymTable);

         var label7 = new Label("ExeStack");
         var font7 = Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,12);
         label7.setFont(font7);
         var vbox7 = new VBox();
         vbox7.setSpacing(5);
         vbox7.setPadding(new Insets(10,50,50,60));
         vbox7.getChildren().addAll(label7,ExeStack);

         var button = new Button("RunOneStep");
         var vbox3 = new VBox();
         vbox3.getChildren().add(vbox1);
         vbox3.getChildren().add(vbox2);
         var hBox = new HBox();
         var vbox10 = new VBox();
         var vbox11 = new VBox();
         vbox10.getChildren().add(vbox4);
         vbox10.getChildren().add(vbox5);
         vbox10.getChildren().add(textfield);
         vbox11.getChildren().add(vbox6);
         vbox11.getChildren().add(vbox7);
         hBox.getChildren().add(vbox3);
         hBox.getChildren().add(vbox10);
         hBox.getChildren().add(vbox11);
         hBox.getChildren().add(button);
         var scene = new Scene(hBox,1200,700);
         newStage.setScene(scene);
         newStage.show();
         button.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent mouseEvent) {
                 var threadPool = controller.getRepository().getMyPrgStates();
                 for (int i = 0; i < threadPool.size(); ++i) {
                     textfield.setPromptText(String.valueOf(threadPool.size()));
                     var currentPrgState = threadPool.get(i);
                     var stack = currentPrgState.getStack();
                     IStmt current = null;
                     if (stack.getSize() == 0) {
                         var currentOutput = currentPrgState.getOutput();
                         wholeOutput.add(currentOutput);
                         finished++;

                     } else {
                         try {
                             current = stack.pop();
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                         exeStack.add(current.toString());
                         var symTable = currentPrgState.getSymTable().getDictionary();
                         for (var el : symTable.keySet()) {
                             varMap.add(new SymTable(el, String.valueOf(symTable.get(el))));
                         }
                         var currentFileTable = currentPrgState.getFiletable().getDictionary();
                         for (var el : currentFileTable.keySet()) {
                             fileMap.add(String.valueOf(el));
                         }
                         var identifier = currentPrgState.getId();
                         Id.add(String.valueOf(identifier));
                         var heaptable = currentPrgState.getHeap().getHashMap();
                         for (var el : heaptable.keySet()) {
                             data.add(new HeapTable(String.valueOf(el), String.valueOf(heaptable.get(el))));
                         }
                         try {
                             if(current.toString().length() > 4) {
                                 if (current.toString().substring(0, 4).equals("Fork")) {
                                     remember++;
                                 }
                             }
                             if (remember == 2) {
                                 var newThread = current.execute(currentPrgState);
                                 threadPool.add(newThread);
                                 remember++;
                             }
                             current.execute(currentPrgState);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }
                 }
                 if(finished == threadPool.size()+1){
                    outMap.add("Output" + wholeOutput.get(0).toString());
                     var finalStage = new Stage();
                     var finalLabel = new Label("Statement is Done");
                     var finalFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
                     finalLabel.setFont(finalFont);
                     var finalScreen = new Scene(finalLabel, 150, 100);
                     finalStage.setScene(finalScreen);
                     finalStage.show();
                 }
             }
         });
       }
     });
     stage.show();
 }
        public static void main(String args[]){
        launch(args);
    }
}