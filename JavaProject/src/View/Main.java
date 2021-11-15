package View;
import Command.ExitCommand;
import Command.RunCommand;
import Model.adt.*;
import Model.exp.ArithExp;
import Exception.*;
import Model.exp.VarExp;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import Controller.Controller;
import Model.exp.ValueExp;
import Model.PrgState;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;


public class Main{
    public static void main(String[] args) throws Exception {

        String outputPath = "C:\\Users\\eddis\\WebstormProjects\\untitled12\\src\\Data\\output.txt";
        String inputPath = "C:\\Users\\eddis\\WebstormProjects\\untitled12\\src\\Data\\input.txt";

        //   ex 1:  int v; v = 2; Print(v)
         IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
         var exeStack1 = new MyStack<IStmt>();
         var symTable1 = new Dict<String, IValue>();
         var out1 = new ArrayList<IValue>();
         var files1 = new Dict<StringValue, BufferedReader>();
         var prg1 = new PrgState(exeStack1,symTable1,out1,files1,ex1);
         var repo1 = new Repo(outputPath);
         repo1.addPrg(prg1);
         var controller1 = new Controller(repo1);

        //   ex 2: a=2+3*5;b=a+1;Print(b)
         IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                      new PrintStmt(new VarExp("b"))))));
         var exeStack2 = new MyStack<IStmt>();
         var symTable2 = new Dict<String,IValue>();
         var out2 = new ArrayList<IValue>();
         var files2 = new Dict<StringValue,BufferedReader>();
         var prg2 = new PrgState(exeStack2,symTable2,out2,files2,ex2);
         var repo2 = new Repo(outputPath);
         repo2.addPrg(prg2);
         var controller2 = new Controller(repo2);

         // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
         IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

         var exeStack3 = new MyStack<IStmt>();
         var symTable3 = new Dict<String,IValue>();
         var out3 = new ArrayList<IValue>();
         var file3 = new Dict<StringValue, BufferedReader>();
         var prg3 = new PrgState(exeStack3,symTable3,out3,file3,ex3);
         var repo3 = new Repo(outputPath);
         repo3.addPrg(prg3);
         var controller3 = new Controller(repo3);

         // ex 4 : (open file:input.txt;(read data for variable:v from file:input.txt;print(v))))
         IStmt ex4 = new CompStmt(new VarDeclStmt("v",new IntType()),
                 new CompStmt(new OpenFileStmt(new VarExp(inputPath)),new CompStmt(new ReadFileStmt(new VarExp(inputPath),"v"),
                         new PrintStmt(new VarExp("v")))));

         var exeStack4=  new MyStack<IStmt>();
         var symTable4 = new Dict<String,IValue>();
         var out4 = new ArrayList<IValue>();
         var file4 = new Dict<StringValue,BufferedReader>();
         var prg4 = new PrgState(exeStack4,symTable4,out4,file4,ex4);
         var repo4 = new Repo(outputPath);
         repo4.addPrg(prg4);
         var controller4 = new Controller(repo4);

         var textMenu = new TextMenu();
         textMenu.addCommand(new ExitCommand("0","exit"));
         textMenu.addCommand(new RunCommand("1",ex1.toString(),controller1));
         textMenu.addCommand(new RunCommand("2",ex2.toString(),controller2));
         textMenu.addCommand(new RunCommand("3",ex3.toString(),controller3));
         textMenu.addCommand(new RunCommand("4",ex4.toString(),controller4));
         textMenu.show();
    }
}
