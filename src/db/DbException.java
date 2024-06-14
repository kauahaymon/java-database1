package db;

public class DbException extends RuntimeException {
    // Custom Exception error message
    public DbException(String msg) {
        super(msg);
    }
}
