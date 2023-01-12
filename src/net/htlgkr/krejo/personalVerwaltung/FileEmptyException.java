package net.htlgkr.krejo.personalVerwaltung;

public class FileEmptyException extends Exception{
    @Override
    public String getMessage() {
        return "File was found but is empty";
    }
}
