package login.Exceptions;

public class ConnectionErrorException extends  Exception{
    public ConnectionErrorException(String errorMessage) {
        super(errorMessage);
    }
}
