import java.sql.*;
public class JDBCutil {
    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "root";
        System.out.println("connection successful");
        return DriverManager.getConnection(url,user,password);
    }
}
