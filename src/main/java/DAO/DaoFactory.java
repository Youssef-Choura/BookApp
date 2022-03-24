/* Dao initialisation */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private final String url;
    private final String username;
    private final String connectionPassword;

    public DaoFactory(String url, String username, String connectionPassword) {
        this.url = url;
        this.username = username;
        this.connectionPassword = connectionPassword;
    }
    //Setting Connection
    public static DaoFactory getInstance(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new DaoFactory("jdbc:mysql://localhost:3306/database", "root", "Aszdefrgth123");
    }
    //Getting Database Connection
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url,username, connectionPassword);
        connection.setAutoCommit(false);
        return connection;
    }
    //Retrieving DAOs (Tables) so that the user can access to the connected database
    public DaoUser getUtilisateurDao(){
        return new DaoUserImpl(this);
    }
}
