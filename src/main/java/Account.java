import java.sql.*;

public class Account {
    Connection connection = null;
    PreparedStatement ps = null;
    public void createAccount(String name,String accountNumber, String password){
        try{
            connection = JDBCutil.getConnection();

            String query = "INSERT INTO accounts(UserName, AccountNumber, Balance) VALUES (?,?,?);";

            ps = connection.prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,accountNumber);
            ps.setString(3,password);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected == 0) System.out.println("Account Creation Failed");
            else System.out.println("Account Created Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if(ps != null) ps.close();
                if(connection != null) connection.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void withdraw(String accountNumber,int amount){
        try{
            connection = JDBCutil.getConnection();

            String query = "SELECT Balance FROM accounts WHERE AccountNumber = ?;";

            ps = connection.prepareStatement(query);
            ps.setString(1,accountNumber);

            int balance = 0;
            try{
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    balance = rs.getInt("Balance");
                } else System.out.println("Account not found");
            } catch (SQLException e){
                e.printStackTrace();
            }



            //verification step
            if(amount <= balance) {
                balance -= amount;
                System.out.println("Withdraw successful");

                //update balance in db
                String query1 = "UPDATE accounts SET Balance = ? WHERE AccountNumber = ?";
                ps = connection.prepareStatement(query1);
                ps.setInt(1,balance);
                ps.setString(2,accountNumber);

                //for debug purpose
                int rowsAffected = ps.executeUpdate();

                if(rowsAffected == 0) System.out.println("updation failed");
                else System.out.println("update successful");

            } else System.out.println("Insufficient Funds");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if(ps != null) ps.close();
                if(connection != null) connection.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}

