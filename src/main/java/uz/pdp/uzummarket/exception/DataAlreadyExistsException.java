package uz.pdp.uzummarket.exception;

public class DataAlreadyExistsException extends RuntimeException{
    public DataAlreadyExistsException(String msg){
        super(msg);
    }
}
