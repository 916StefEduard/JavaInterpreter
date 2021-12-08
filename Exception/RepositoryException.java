package Exception;

import Repo.Repo;

public class RepositoryException extends Exception{
    public RepositoryException(String error){
        super(error);
    }
}
