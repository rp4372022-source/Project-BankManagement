import java.awt.*;
import java.util.Scanner;
public class Console {
    Account account = new Account();
    Scanner sc = new Scanner(System.in);

    public void dislpayFunctions(){
        System.out.println(">>Function Window>>>");
        System.out.println("Select and Enter :");
        System.out.println("1.Create Account \n2.Deposite Money\n3.Withdraw Money\n4.Transfer Money\n5.Display Balance\n6.Exit");
    }
    public void callWithdrawMethod(String accountNumber,int amount){
        account.withdraw(accountNumber,amount);
    }
    public void callDepositMethod(String accountNumber,int amount){
        account.deposit(accountNumber,amount);
    }
    public void callTransferMethod(String senderAccountNumber,String receiverAccountNumber,int amount){
        account.transfer(senderAccountNumber,receiverAccountNumber,amount);
    }
    public void callCreateAccount(String name,String accountNumber,int balance){
        account.createAccount(name, accountNumber, balance);
    }
    public void callDisplayBalance(String accountNumber){
        account.displayBalance(accountNumber);
    }

    public void runProgram(){
        System.out.println("=====Program Started=====");
        while(true){
            dislpayFunctions();
            try{
                System.out.print("Choice :");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 ->{
                        System.out.println("Creating new Account");
                        System.out.print("Name : ");
                        String name = sc.nextLine();
                        System.out.println("AccountNumber : ");
                        String account = sc.nextLine();
                        int INITIAL_BALANCE = 0;
                        callCreateAccount(name,account,INITIAL_BALANCE);
                    }
                    case 2 -> {
                        System.out.print("Enter Account Number : ");
                        String accNum = sc.nextLine();
                        System.out.print("Enter amount to deposit : ");
                        int amount = sc.nextInt();

                        callDepositMethod(accNum,amount);
                    }
                    case 3 -> {
                        System.out.print("Enter Account Number : ");
                        String accNum = sc.nextLine();
                        System.out.print("Enter amount to withdraw : ");
                        int amount = sc.nextInt();

                        callWithdrawMethod(accNum,amount);
                    }
                    case 4 -> {
                        System.out.print("Enter Senders Account Number : ");
                        String sAccNum = sc.nextLine();
                        System.out.print("Enter Reciver Account Number : ");
                        String rAccNum = sc.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        int amount = sc.nextInt();

                        callTransferMethod(sAccNum,rAccNum,amount);
                    }
                    case 5 -> {
                        System.out.print("Enter Account Number : ");
                        String accNum = sc.nextLine();

                        callDisplayBalance(accNum);
                    }
                    case 6 -> {
                        System.out.println("System shut down");
                        System.exit(0);
                    }

                    default -> {
                        System.out.println("Invalid choice, please select valid choice\n");

                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println();
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
