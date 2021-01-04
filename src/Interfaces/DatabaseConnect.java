package interfaces;

 
public interface DatabaseConnect extends Runnable
{
    final String login = "root";
    final String passwort = "";
    final String dbURL = "jdbc:mariadb://127.0.0.1/appdevii";
    final String driver = "org.mariadb.jdbc.Driver";
}
