package BankSystem;

import java.sql.Connection;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class User {
    private  Connection  conn;
    private Scanner   sc;

    public User(Connection conn,Scanner sc){
        this.conn = conn;
        this.sc = sc;
        //System.out.println(" user connection ");
    }
    public void Register()throws IOException{
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        System.out.print("Enter full name -> ");
        String full_name = br.readLine();
        System.out.print("enter email->  ");
        String u_email = br.readLine();
        System.out.println("Enter password-> ");
        String u_password = br.readLine();
        if(user_exist(u_email)){
            System.out.println("user already exists for this email address..");
            return;
        }
        String rg = "INSERT INTO user (full_name,email,password)VALUES(?,?,?)";
        try{
            PreparedStatement prt = conn.prepareStatement(rg);
            prt.setString(1,full_name);
            prt.setString(2,u_email);
            prt.setString(3,u_password);
            int af = prt.executeUpdate();
            if(af>0){
                System.out.println("data is inserted ");
            }
            else{
                System.out.println("data does not inserted.");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void printele(){
        try{
            String qu = "SELECT * FROM user";
            Statement stt = conn.createStatement();
            ResultSet ans = stt.executeQuery(qu);
            while(ans.next()){
                String name = ans.getString("full_name");
                String pass = ans.getString("password");
                String email = ans.getString("email");
                System.out.println("full name-> "+name +" password-> "+pass+" email -> "+ email);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public String login()throws SQLException {
        try{
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            System.out.println("Enter your email-> ");
            String l_email = br.readLine();
            System.out.println("Enter your password");
            String l_pass = br.readLine();
            String sql = "SELECT email,password FROM user WHERE email = ?,password = ?";
            PreparedStatement prt = conn.prepareStatement(sql);
            prt.setString(1,l_email);
            prt.setString(2,l_pass);
            ResultSet set = prt.executeQuery();
            if(set.next()){
                return l_email;
            }

        } catch(IOException a){
            System.out.println(a.getMessage());
        }
        return null;
    }
    public  boolean user_exist(String u_email){
        try{
            String sql = "SELECT email FROM user WHERE email = ?";
            PreparedStatement prt = conn.prepareStatement(sql);
            prt.setString(1,u_email);
            ResultSet set = prt.executeQuery();
            //System.out.println("true...");
            return set.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
