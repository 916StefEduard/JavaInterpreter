import controller.Controller;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import model.PrgState;
import model.adt.IList;
import model.adt.MyDict;
import model.adt.MyList;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.values.*;
import repository.Repository;

import java.util.*;
import java.util.concurrent.ExecutorService;

public class Interpreter extends Application {

    public static CompStmt computeStmt(IStmt[] stmts){
        CompStmt newStmt = new CompStmt(stmts[stmts.length - 2],stmts[stmts.length - 1]);
        for(int i=stmts.length-3; i>=0; --i){
            newStmt = new CompStmt(stmts[i],newStmt);
       }
        return newStmt;
    }

    public static IStmt[] getExamples() {


        IStmt[] stmts = {new VarDecStmt("v",new IntType()),new VarDecStmt("x",new IntType()),new VarDecStmt("y",new IntType()),
                new AssignStmt("v",new ValueExp(new IntValue(0))),new RepeatStmt(new CompStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                new AssignStmt("v",new ArithmeticExp(new VarExp("v"),new ValueExp(new IntValue(1)),'-')))),new AssignStmt("v",new ArithmeticExp(new VarExp("v"),new ValueExp(new IntValue(1)),'+'))),
                new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(3)),"=="))};

        IStmt[] stmts3 = {new VarDecStmt("v1",new RefType(new IntType())),new VarDecStmt("v2",new RefType(new IntType())),new VarDecStmt("v3",new RefType(new IntType())),
        new VarDecStmt("cnt",new IntType()),new NewStmt("v1",new ValueExp(new IntValue(2))),new NewStmt("v2",new ValueExp(new IntValue(3))),
        new NewStmt("v3",new ValueExp(new IntValue(4))),new NewLatchStmt("cnt",new ReadHeapExp(new VarExp("v2"))),new ForkStmt(new CompStmt(new WriteHeapStmt("v1",new ArithmeticExp(new ReadHeapExp(new VarExp("v1")),
                new ValueExp(new IntValue(10)),'*')),new PrintStmt(new ReadHeapExp(new VarExp("v1"))))),new CountDown("cnt"),new ForkStmt(new CompStmt(new WriteHeapStmt("v2",new ArithmeticExp(new ReadHeapExp(new VarExp("v2")),
                new ValueExp(new IntValue(10)),'*')),new PrintStmt(new ReadHeapExp(new VarExp("v2"))))),new CountDown("cnt"),new ForkStmt(new CompStmt(new WriteHeapStmt("v3",new ArithmeticExp(new ReadHeapExp(new VarExp("v3")),new ValueExp(new IntValue(10)),'*')),
                new PrintStmt(new ReadHeapExp(new VarExp("v3"))))),new CountDown("cnt"),new AwaitStmt("cnt"),new PrintStmt(new ValueExp(new IntValue(100))),
                new CountDown("cnt"),new PrintStmt(new ValueExp(new IntValue(100)))};

        IStmt[] condStmts = {new VarDecStmt("b",new BoolType()),new VarDecStmt("c",new IntType()),new AssignStmt("b",new ValueExp(new BoolValue(true))),
        new CondStmt("c",new VarExp("b"),new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),new PrintStmt(new VarExp("c")),
        new CondStmt("c",new ValueExp(new BoolValue(false)),new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),new PrintStmt(new VarExp("c"))};

        IStmt[] stmts5 = {new VarDecStmt("v1",new IntType()),new VarDecStmt("v2",new IntType()),new AssignStmt("v1",new ValueExp(new IntValue(2))),new AssignStmt("v2",new ValueExp(new IntValue(3))),
        new IfStmt(new RelationalExp(new VarExp("v1"),new ValueExp(new IntValue(0)),"!="),new PrintStmt(new MulExp(new VarExp("v1"),new VarExp("v2"))),new PrintStmt(new VarExp("v1")))};
        IStmt example12 =  computeStmt(stmts5);

        IStmt[] stmts6 = {new VarDecStmt("v",new IntType()),new AssignStmt("v",new ValueExp(new IntValue(20))),new WaitStmt(new IntValue(10)),new PrintStmt(new ArithmeticExp(new VarExp("v"),new ValueExp(new IntValue(10)),'*'))};
        IStmt example13 = computeStmt(stmts6);

        IStmt[] stmts7 = {new VarDecStmt("a",new RefType(new IntType())),new NewStmt("a",new ValueExp(new IntValue(20))),
        new ForStmt(new ValueExp(new IntValue(0)),new ValueExp(new IntValue(3)),new ArithmeticExp(new VarExp("v"),new ValueExp(new IntValue(1)),'+'),
                new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithmeticExp(new VarExp("v"),new ReadHeapExp(new VarExp("a")),'*'))))),
        new PrintStmt(new ReadHeapExp(new VarExp("a")))};

        IStmt[] stmts8 = {new VarDecStmt("v1",new RefType(new IntType())),new VarDecStmt("v2",new RefType(new IntType())),
        new VarDecStmt("x",new IntType()),new VarDecStmt("q",new IntType()),new NewStmt("v1",new ValueExp(new IntValue(20))),
        new NewStmt("v2",new ValueExp(new IntValue(20))),new NewLockStmt("x"),
                new ForkStmt(
                        new CompStmt(new ForkStmt(
                                new CompStmt(new LockStmt("x"),new CompStmt(new WriteHeapStmt("v1",new ReadHeapExp(new VarExp("v1"))),
                                        new UnlockStmt("x")))
                        ),
                                new CompStmt(new LockStmt("x"),new CompStmt(new WriteHeapStmt("v1",
                                        new ReadHeapExp(new VarExp("v1"))),new UnlockStmt("x")))
                                )),new NewLockStmt("q"),
        new ForkStmt(
                new CompStmt(new ForkStmt(
                        new CompStmt(new LockStmt("q"),new CompStmt(new WriteHeapStmt("v2",new ReadHeapExp(new VarExp("v2"))),
                                new UnlockStmt("q")))
                ),new CompStmt(new LockStmt("q"),new CompStmt(new WriteHeapStmt("v2",new ReadHeapExp(new VarExp("v2"))),
                        new UnlockStmt("q"))))
        ),
                new LockStmt("x"),new PrintStmt(new ReadHeapExp(new VarExp("v1"))),new UnlockStmt("x"),
                new LockStmt("q"),new PrintStmt(new ReadHeapExp(new VarExp("v2"))),new UnlockStmt("q")
        };

        IStmt example15 = computeStmt(stmts8);

        IStmt[] stmt9 = {new VarDecStmt("a",new IntType()),new VarDecStmt("b",new IntType()),new VarDecStmt("c",new IntType()),
        new AssignStmt("a",new ValueExp(new IntValue(1))),new AssignStmt("b",new ValueExp(new IntValue(2))),new AssignStmt("c",new ValueExp(new IntValue(5))),
        new SwitchStmt(new ArithmeticExp(new VarExp("a"),new ValueExp(new IntValue(10)),'*'),
                new ArithmeticExp(new VarExp("b"),new VarExp("c"),'*'),new ValueExp(new IntValue(10)),
                new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b"))),
                new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),new PrintStmt(new ValueExp(new IntValue(200)))),
                new PrintStmt(new ValueExp(new IntValue(300)))),new PrintStmt(new ValueExp(new IntValue(300)))};

        IStmt switchStmt = computeStmt(stmt9);

        IStmt[] stmts10 = {new VarDecStmt("a",new RefType(new IntType())),new VarDecStmt("b",new RefType(new IntType())),
        new VarDecStmt("v",new IntType()),new NewStmt("a",new ValueExp(new IntValue(0))),new NewStmt("b",new ValueExp(new IntValue(0))),
                new WriteHeapStmt("a",new ValueExp(new IntValue(1))),new WriteHeapStmt("b",new ValueExp(new IntValue(2))),
        new CondStmt("v",new RelationalExp(new ReadHeapExp(new VarExp("a")),new ReadHeapExp(new VarExp("b")),"<"),new ValueExp(new IntValue(100)),
                new ValueExp(new IntValue(200))),new PrintStmt(new VarExp("v")),new CondStmt("v",
                new RelationalExp(new ArithmeticExp(new ReadHeapExp(new VarExp("b")),new ValueExp(new IntValue(2)),'-'),
                        new ReadHeapExp(new VarExp("a")),">"),new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),
        new PrintStmt(new VarExp("v"))};

        IStmt example18 = computeStmt(stmts10);
        IStmt lockStmt = new CompStmt(new VarDecStmt("v1", new RefType(new IntType())), new CompStmt(
                new VarDecStmt("v2", new RefType(new IntType())), new CompStmt(
                new VarDecStmt("x", new IntType()), new CompStmt(
                new VarDecStmt("q", new IntType()), new CompStmt(
                new NewStmt("v1", new ValueExp(new IntValue(20))), new CompStmt(
                new NewStmt("v2", new ValueExp(new IntValue(30))), new CompStmt(
                new NewLockStmt("x"), new CompStmt(
                new ForkStmt(new CompStmt(
                        new ForkStmt(new CompStmt(
                                new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), '-')), new UnlockStmt("x")))),
                        new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')), new UnlockStmt("x"))))),
                new CompStmt(new NewLockStmt("q"), new CompStmt(
                        new ForkStmt(new CompStmt(
                                new ForkStmt(new CompStmt(new LockStmt("q"), new CompStmt(new WriteHeapStmt("v2", new ArithmeticExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)), '+')), new UnlockStmt("q")))),
                                new CompStmt(new LockStmt("q"), new CompStmt(new WriteHeapStmt("v2", new ArithmeticExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), '*')), new UnlockStmt("q"))))
                        ), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(
                        new LockStmt("x"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))), new CompStmt(new UnlockStmt("x"),
                        new CompStmt(new LockStmt("q"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))), new UnlockStmt("q"))))))))))))))))))));

        IStmt latchStmt = computeStmt(stmts3);

        IStmt semaphoreStmt = new CompStmt(new VarDecStmt("v1", new RefType(new IntType())), new CompStmt(new VarDecStmt("cnt", new IntType()),
                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(1))),
                new CompStmt(new CreateSemaphore("cnt", new ReadHeapExp(new VarExp("v1"))),
                        new CompStmt(new ForkStmt(new CompStmt(new AcquireStmt("cnt"), new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)),'*')),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))), new ReleaseStmt("cnt"))))),
                new ForkStmt(new CompStmt(new AcquireStmt("cnt"),
                        new CompStmt(new WriteHeapStmt("v1", new ArithmeticExp( new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)),'*')),
                                new CompStmt(
                                        new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(2)),'*')),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))), new CompStmt(new ReleaseStmt("cnt"),
                            new CompStmt(new AcquireStmt("cnt"),new CompStmt(new PrintStmt(new ArithmeticExp( new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)),'-')),
                                                new ReleaseStmt ("cnt"))))))))))))));
        IStmt[] forStmts = {new VarDecStmt("a",new RefType(new IntType())),new NewStmt("a",new ValueExp(new IntValue(20))),
        new ForStmt(new ValueExp(new IntValue(0)),new ValueExp(new IntValue(3)),new ArithmeticExp(new VarExp("v"),new ValueExp(new IntValue(1)),'+'),
                new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithmeticExp(new VarExp("v"),
                        new ReadHeapExp(new VarExp("a")),'*')))),new PrintStmt(new ReadHeapExp(new VarExp("a")))};

        var forStmt = computeStmt(forStmts);

        IStmt[] sleepStmts = {new VarDecStmt("v",new IntType()),new AssignStmt("v",new ValueExp(new IntValue(10))),
        new ForkStmt(new CompStmt(new AssignStmt("v",new ArithmeticExp(new VarExp("v"),new ValueExp(new IntValue(1)),'-')),
                new CompStmt(new AssignStmt("v",new ArithmeticExp(new VarExp("v"),new ValueExp(new IntValue(1)),'-')),
                        new PrintStmt(new VarExp("v"))))),new SleepStmt(10),new PrintStmt(new ArithmeticExp(new VarExp("v"),
                new ValueExp(new IntValue(10)),'*'))};

        IStmt sleepStmt = computeStmt(sleepStmts);
        IStmt condStmt = computeStmt(condStmts);
        return new IStmt[]{forStmt, lockStmt, semaphoreStmt, latchStmt,sleepStmt,switchStmt,condStmt};
    }
    public static class HeapTable{
        SimpleStringProperty address;
        SimpleStringProperty value;
        HeapTable(String address, String value){
            this.address = new SimpleStringProperty(address);
            this.value = new SimpleStringProperty(value);
        }
        public String getFileName() {
            return address.get();
        }
        public void setFileName(String fname) {
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

    public static class LatchTable {
        SimpleStringProperty location;
        SimpleStringProperty value;
        private LatchTable(String location, String value){
            this.location = new SimpleStringProperty(location);
            this.value = new SimpleStringProperty(value);
        }
        public String getFileName(){
            return location.get();
        }
        public void setFileName(String fname){
            location.set(fname);
        }
        public String  getPath(){
            return value.get();
        }
        public void setPath(String fpath){
            value.set(fpath);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Statements");
        var listView = new ListView<String>();
        listView.getItems().add("ex 1: Ref int a; new(a,20);(for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a))) print(rh(a))");
        listView.getItems().add("ex 2: Ref int v1; Ref int v2; int x; int q; new(v1,20);new(v2,30);newLock(x);fork");
        listView.getItems().add("ex 3: Ref int v1; int cnt; new(v1,1);createSemaphore(cnt,rH(v1));");
        listView.getItems().add("ex 4: Ref int v1; Ref int v2; Ref int v3; int cnt;new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));)");
        listView.getItems().add("ex 5: v=10;; (fork(v=v-1;v=v-1;print(v)); sleep(10);print(v*10)");
        listView.getItems().add("ex 6: int a; int b; int c;(switch(a*10)(case (b*c) : print(a);print(b))");
        listView.getItems().add("ex 7: Ref int a; Ref int b; int v;new(a,0); new(b,0);wh(a,1); wh(b,2); v=(rh(a)<rh(b))?100:200;");
        listView.setOrientation(Orientation.VERTICAL);
        var vbox = new VBox(listView);
        var scene = new Scene(vbox,600,270);
        stage.setScene(scene);
        stage.show();
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            private TableView<LatchTable> latchTable = new TableView<LatchTable>();
            private TableView<HeapTable> table = new TableView<HeapTable>();
            private ListView<String> Output = new ListView<String>();
            private ListView<String> FileTable = new ListView<String>();
            private ListView<String> Identifier = new ListView<String>();
            private TableView<SymTable> SymTable = new TableView<SymTable>();
            private ListView<String> ExeStack = new ListView<String>();
            private TextField textfield = new TextField();
            private Button button = new Button("RunOneStep");
            private ExecutorService executorService;
            int remember = 0;
            int finished = 0;
            int currentThread = 0;
            List<PrgState> prgList ;
            List<String> outPuts;
            MyList<IList<String>> wholeOutput = new MyList<IList<String>>();

            private final ObservableList<LatchTable> threadPool = FXCollections.observableArrayList();
            private final ObservableList<HeapTable> data = FXCollections.observableArrayList();
            private final ObservableList<String> outMap = FXCollections.observableArrayList();
            private final ObservableList<String> fileMap = FXCollections.observableArrayList();
            private final ObservableList<String> Id = FXCollections.observableArrayList();
            private final ObservableList<SymTable> varMap = FXCollections.observableArrayList();
            private final ObservableList<String> exeStack = FXCollections.observableArrayList();
            private final ObservableList<LatchTable> latch = FXCollections.observableArrayList();
            IStmt[] iStmts = getExamples();
            IStmt currentStmt;
            @Override
            public void handle(MouseEvent mouseEvent) {
                table.getItems().clear();
                Output.getItems().clear();
                FileTable.getItems().clear();
                Identifier.getItems().clear();
                SymTable.getItems().clear();
                ExeStack.getItems().clear();
                latchTable.getItems().clear();

                int index = listView.getSelectionModel().getSelectedIndex();
                currentStmt = iStmts[index];
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

                var label10 = new Label("Latch Table");
                var font10 = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
                label10.setFont(font10);
                var fileNameCol12 = new TableColumn("Location");
                fileNameCol12.setCellValueFactory(new PropertyValueFactory<>("fileName"));
                var pathCol12 = new TableColumn("Value");
                pathCol12.setCellValueFactory(new PropertyValueFactory("path"));
                latchTable.setItems(latch);
                latchTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                latchTable.getColumns().addAll(fileNameCol12,pathCol12);
                latchTable.setMaxSize(750,500);
                var vbox15 = new VBox();
                vbox15.setSpacing(5);
                vbox15.setPadding(new Insets(10,50,50,60));
                vbox15.getChildren().addAll(label10,latchTable);

                var label7 = new Label("ExeStack");
                var font7 = Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,12);
                label7.setFont(font7);
                var vbox7 = new VBox();
                vbox7.setSpacing(5);
                vbox7.setPadding(new Insets(50,50,50,50));
                ExeStack.setPrefHeight(300);
                ExeStack.setPrefWidth(500);
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
                hBox.getChildren().add(vbox15);
                hBox.getChildren().add(button);
                currentStmt.typeCheck(new MyDict<>());
                PrgState prgState = new PrgState(currentStmt);
                Repository repository = new Repository(String.format("log%s.txt", 1));
                repository.add(prgState);
                Controller controller = new Controller(repository);
                controller.setExecutor();
                var scene = new Scene(hBox,1400,700);
                newStage.setScene(scene);
                newStage.show();
                controller.setExecutor();
                prgList = controller.removeCompletedPrg(repository.getPrgList());
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(!prgList.isEmpty()){
                            controller.oneStepForAllPrg(prgList);
                            var prgStateList = prgList;
                            for(var el:prgStateList){
                                exeStack.add(el.getExeStack().toString());
                                for(var pair:el.getSymTable().getContent().keySet()){
                                    varMap.add(new SymTable(pair,el.getSymTable().lookup(pair).toString()));
                                }
                                outMap.add(el.getOut().toString());
                                for(var pair:el.getHeapTable().getContent().keySet()){
                                    data.add(new HeapTable(pair.toString(),el.getHeapTable().get(pair).toString()));
                                }
                                for(var pair:el.getFileTable().getContent().keySet()){
                                    fileMap.add(pair.toString());
                                }
                                for(var pair:el.getLatchTable().keys()){
                                    latch.add(new LatchTable(String.valueOf(pair),String.valueOf(el.getLatchTable().get(pair))));
                                }
                                Id.add(String.valueOf(el.getId()));
                                textfield.setPromptText(String.valueOf(prgList.size()));
                            }
                            prgList = controller.removeCompletedPrg(repository.getPrgList());
                        }else{
                            controller.shutExecutor();
                            repository.setPrgList(prgList);
                        }
                    }
                  }
                );
            }
        });
    }

    public static void main(String[] args) {
            launch(args);
   }
}
