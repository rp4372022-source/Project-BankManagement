import java.sql.*;

public class Account {
    Connection connection = null;
    PreparedStatement ps = null;
    public void createAccount(String name,String accountNumber, int balance){
        try{
            connection = JDBCutil.getConnection();

            String query = "INSERT INTO accounts(UserName, AccountNumber, Balance) VALUES (?,?,?);";

            ps = connection.prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,accountNumber);
            ps.setInt(3,balance);

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

    public void deposit(String accountNumber, int amount){

        if(amount < 0){
            System.out.println("Invalid amount");
            return;
        }

        try{
            connection = JDBCutil.getConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }

        //get balance
        String query = "SELECT Balance FROM accounts WHERE AccountNumber = ?;";

        ResultSet rs = null;
        int balance = 0;
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1,accountNumber);

            rs = ps.executeQuery();
            if( rs.next()){
                balance = rs.getInt("Balance");
            } else {
                System.out.println("Failed to fetch balance");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        //Update Balance
        System.out.println("Balance before :"+balance);
        balance += amount;
        System.out.println("Balance After : "+balance);

        String query1 = "UPDATE accounts SET Balance = ? WHERE AccountNumber = ?;";
        try{
            ps = connection.prepareStatement(query1);
            ps.setInt(1,balance);
            ps.setString(2,accountNumber);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected == 0){
                System.err.println("Failed to update balance");
            } else {
                System.out.println("Balance updated successfully");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void displayBalance(String accountNumber){
        try{
            connection = JDBCutil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e); //replacement of e.printStackTrace
        }

        String query = "Select Balance FROM accounts WHERE AccountNumber = ?;";

        try{
            ps = connection.prepareStatement(query);
            ps.setString(1,accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //fetch and display balance
        try{
            ResultSet rs = ps.executeQuery();

            int currentBalance = 0;
            if(rs.next()){
                currentBalance = rs.getInt("Balance");
            } else {
                System.out.println("Failed to fetch balance");
                return;
            }

            System.out.println("Current Banalce : "+currentBalance);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void transfer(String senderAccountNumber, String receiverAccountNumber, int amount){
        withdraw(senderAccountNumber,amount);
        System.out.println("Withdraw done");
        deposit(receiverAccountNumber,amount);
        System.out.println("Deposit done");
    }
}

