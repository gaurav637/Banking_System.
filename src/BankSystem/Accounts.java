package BankSystem;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.SQLException;
import java.io.IOException;

import static java.text.ChoiceFormat.nextDouble;

public class Accounts {
    private Connection conn;
    private Scanner sc;
    public Accounts(Connection conn,Scanner sc){
        this.conn = conn;
        this.sc = sc;
    }

    public int open_accounts(String str){
        Double bal = 0.0;
        String st = "INSERT INTO accounts(accounts_number,full_name,email,balance,security_pin)VALUES(?,?,?,?,?)";
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter full name -> ");
            String name = br.readLine();
            System.out.println("Enter initial Ammount-> ");
            bal = Double.parseDouble(br.readLine());
            System.out.println("Enter security pin -> ");
            String pin = br.readLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        try{
            int account_no = generateAccountsNumber();
            PreparedStatement prt = conn.prepareStatement(st);
            prt.setInt(1,account_no);
            prt.setString(2,"name");
            prt.setString(3,str);
            prt.setDouble(4,bal);
            prt.setString(5,"pin");
            int effected = prt.executeUpdate(st);
            if(effected>0){
                return account_no;
            }
            throw new RuntimeException("Account creation failed..!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("Account already exist..!");
    }
    public int grtAccountsNumber(String email_id){
        String sql = "SELECT accounts_number FROM accounts WHERE email = ?";
        try{
            PreparedStatement prt = conn.prepareStatement(sql);
            prt.setString(1,email_id);
            ResultSet set = prt.executeQuery();
            if(set.next()){
                int ac = set.getInt("accounts_number");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("accounts number does not exist!");
    }

    public int generateAccountsNumber() throws SQLException{
        try{
            Statement st = conn.createStatement();
            String sql = "SELECT accounts_number FROM accounts ORDER BY accounts_number DESC LIMIT 1";
            ResultSet set = st.executeQuery(sql);
            if(set.next()){
                int ac = set.getInt("accounts_number");
                return ac+1;
            }
            else{
                return 10000100;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return 10000100;
    }
    public boolean accounts_exists(String email_id){
        String sql = "SELECT account_number FROM accounts WHERE email = ?";
        try{
            PreparedStatement prt = conn.prepareStatement(sql);
            prt.setString(1,email_id);
            ResultSet set = prt.executeQuery();
            if(set.next()){
                return true;
            }
            else{
                return false;
            }
            // return set.next();

        }catch(SQLException e){
            System.out.println(e.getMessage()+" accounts_exists function by Accounts class");
        }
        return false;
    }
}
