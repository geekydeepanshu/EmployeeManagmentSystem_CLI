import java.sql.*;

public class DB_Connector {
    Connection postgresConnection;
    DB_Connector() throws SQLException
    {
        String username = "postgres";
        String password = "pass@1234";
        String url = "jdbc:postgresql://localhost:5432/employee_records";
        postgresConnection = DriverManager.getConnection(url, username, password);

    }
    public Connection getPostgresConnection(){
        return postgresConnection;
    }
}
