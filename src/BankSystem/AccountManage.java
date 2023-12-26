package BankSystem;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AccountManage {
    private Connection conn;
    private Scanner sc;

    public AccountManage(Connection conn,Scanner sc){
        this.conn = conn;
        this.sc = sc;
    }

    public void credit_money(int account_number) throws SQLException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sc.nextLine();
        System.out.println("Enter amount-> ");
        int amount = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter security pin -> ");
        String pin = sc.nextLine();

        try{
            conn.setAutoCommit(false);
            if(account_number!=0){
                String sql = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";
                PreparedStatement prt = conn.prepareStatement(sql);
                prt.setInt(1,account_number);
                prt.setString(2,pin);
                ResultSet set = prt.executeQuery();

                if(set.next()){
                    String sq = "UPDATE accounts SET balance =  balance + ? WHERE account_number = ?";
                    PreparedStatement pr = conn.prepareStatement(sq);
                    pr.setInt(1,amount);
                    pr.setInt(2,account_number);
                    int ef = pr.executeUpdate();
                    if(ef>0){
                        System.out.println("Rs "+ amount+" credited successfully..");
                        conn.commit();
                        conn.setAutoCommit(true);
                        return;
                    }
                    else{
                        System.out.println("transaction failed...");
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                }
                else{
                    System.out.println("invalid security pin .");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        conn.setAutoCommit(true);
    }
    public void debit_money(int account_number) throws SQLException{
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sc.nextLine();
        System.out.println("Enter amount-> ");
        int amount = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter security pin -> ");
        String pin = sc.nextLine();

        try{
            conn.setAutoCommit(false);
            if(account_number!=0){
                String sql = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";
                PreparedStatement prt = conn.prepareStatement(sql);
                prt.setInt(1,account_number);
                prt.setString(2,pin);
                ResultSet set = prt.executeQuery();

                if(set.next()){
                    String sq = "UPDATE accounts SET balance =  balance - ? WHERE account_number = ?";
                    PreparedStatement pr = conn.prepareStatement(sq);
                    pr.setInt(1,amount);
                    pr.setInt(2,account_number);
                    int ef = pr.executeUpdate();
                    if(ef>0){
                        System.out.println("Rs "+ amount+" debited successfully..");
                        conn.commit();
                        conn.setAutoCommit(true);
                        return;
                    }
                    else{
                        System.out.println("transaction failed...");
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                }
                else{
                    System.out.println("Insufficient balance .");
                }
            }
        else{
                System.out.println("invalid pin");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        conn.setAutoCommit(true);
    }

    public void getBalance(int account_number){
        System.out.println("Enter pin-> ");
        sc.nextLine();
        String pin = sc.nextLine();
        try{
            String sql = "SELECT balance FROM accounts WHERE account_number = ? AND security_pin = ?";
            PreparedStatement prt = conn.prepareStatement(sql);
            prt.setInt(1,account_number);
            prt.setString(2,pin);
            ResultSet set = prt.executeQuery();
            if(set.next()){
                int ans = set.getInt("balance");
                System.out.println("Balance -> "+ans);
            }
            else{
                System.out.println("Invalid pin !....");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void transfer_money(int sender_account_number) throws SQLException{
        //sc.nextInt();
        System.out.println("Enter Receiver Account Number -> ");
        int receiver_account_number = sc.nextInt();
        System.out.println("Enter amount-> ");
        //sc.nextInt();
        int amount = sc.nextInt();
        System.out.println("security pin -> ");
        sc.nextLine();
        String pin = sc.nextLine();
        try{
            conn.setAutoCommit(false);
            if(receiver_account_number!=0 && sender_account_number!=0){
                String sql = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";
                PreparedStatement prt = conn.prepareStatement(sql);
                prt.setInt(1,sender_account_number);
                prt.setString(2,pin);
                ResultSet set = prt.executeQuery();
                if(set.next()){
                    int current_balance = set.getInt("balance");
                    if(amount<=current_balance){
                        String debit = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                        String credit = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                        PreparedStatement prt1 = conn.prepareStatement(debit);
                        PreparedStatement prt2 = conn.prepareStatement(credit);
                        prt1.setInt(1,amount);
                        prt1.setInt(2,sender_account_number);
                        prt2.setInt(1,amount);
                        prt2.setInt(2,receiver_account_number);
                        int effected1 = prt1.executeUpdate();
                        int effected2 = prt2.executeUpdate();
                        if(effected1>0 && effected2>0){
                            System.out.println("Transaction Successfully...");
                            System.out.println("Rs."+ amount+" transferred  successfully");
                            conn.commit();
                            conn.setAutoCommit(true);
                            return;
                        }
                        else{
                            System.out.println("Transaction failed!.");
                            conn.rollback();
                            conn.setAutoCommit(true);
                        }

                    }
                    else{
                        System.out.println("Insufficient Balance..");
                    }
                }
                else{
                    System.out.println("Invalid security pin.");
                }
            }
            else{
                System.out.println("Invalid Account Number! ");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}


