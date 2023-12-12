package BankSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
public class Bank_Management {
    private static final String url = "jdbc:mysql://localhost:3306/Bank";
    private static final String user = "root";
    private static final String password = "91491026";
    public static void main(String args[])throws ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver is loaded..");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        while(true){
            try{
                Connection conn = DriverManager.getConnection(url,user,password);
                Scanner sc = new Scanner(System.in);
                User user = new User(conn,sc);
                System.out.println("BANK MANAGEMENT SYSTEM");
                System.out.println("1.register");
                System.out.println("2. login");
                System.out.println("3. exit");
                System.out.println("choice any option -> ");
                int n = sc.nextInt();
                switch(n){
                    case 1:{
                        user.Register();
                        break;
                    }
                    case 2:{
                        String ans = user.login();
                        if(ans!=null){
                            System.out.println("Account is login-> ");
                        }
                        break;
                    }
                    case 3:{
                        try{
                            System.out.println("thankyou for use our bank...");
                            int i = 0;
                            while(i<5){
                                i++;
                                System.out.print(".");
                                Thread.sleep(200);
                            }
                            System.exit(0);
                        }catch(InterruptedException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    case 4:{
                        user.printele();
                        break;
                    }
                    default:
                        System.out.println("invalid option -> ");
                }

            }catch(SQLException e){
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
