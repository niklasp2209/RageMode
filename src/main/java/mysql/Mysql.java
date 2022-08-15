package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mysql {

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    public Mysql(String host, String port, String database, String username, String password){
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

    }

    public void connect(){
        if(!isConnected()){
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&autoReconnect=true",username, password);
                System.out.println("[MySQL] Die Verbindung wurde aufgebaut");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect(){
        if(isConnected()){
            try {
                System.out.println("[MySQL] Die Verbindung wurde getrennt");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){
        return (connection == null ? false: true);
    }

    public Connection getConnection(){
        return connection;
    }

    public ResultSet getResult(String qry) {
        if(isConnected()) {
            try {
                return connection.createStatement().executeQuery(qry);
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void update(String qry) {
        if(isConnected()) {
            try {
                connection.createStatement().executeUpdate(qry);
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
