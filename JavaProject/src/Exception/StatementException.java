package Exception;

import com.sun.source.tree.StatementTree;

import javax.swing.plaf.nimbus.State;

public class StatementException extends Exception{
    public StatementException(String error){
        super(error);
    }
}
