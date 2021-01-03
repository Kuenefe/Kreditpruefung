package interfaces;

 
public interface DatabaseConnect extends Runnable
{
    final String login = "appdevii";
    final String passwort = "appdevmachtspass";
    final String dbURL = "jdbc:mariadb://138.201.52.201/appdevii";
    final String driver = "org.mariadb.jdbc.Driver";
}
