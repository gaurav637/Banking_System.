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
        System.out.println();
        System.out.println();
        String email;
        int account_number;
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
                Accounts ac = new Accounts(conn,sc);
                AccountManage am = new AccountManage(conn,sc);
                System.out.println("BANK MANAGEMENT SYSTEM");
                System.out.println();
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
                        email = user.login();
                        if(email!=null){
                            System.out.println();
                            System.out.println("Account is login-> ");
                            if(!ac.accounts_exists(email)){
                                System.out.println();
                                System.out.println("1. open new bank account ");
                                System.out.println("2. Exists");
                                int n1 = sc.nextInt();
                                if(n1==1){
                                    int account_no = ac.open_accounts(email);
                                    System.out.println("your account is created..");
                                    System.out.println("your account number is -> "+ account_no);
                                }
                                else{
                                    break;
                                }
                            }
                            account_number = ac.grtAccountsNumber(email);
                            int choice = 0;
                            while(choice!=5){
                                System.out.println();
                                System.out.println(" 1.credit money");
                                System.out.println(" 2.debit money");
                                System.out.println(" 3.transferred money ");
                                System.out.println(" 4.get balance");
                                System.out.println(" 5.exit");
                                System.out.println();
                                System.out.println("Enter your choise -> ");
                                choice = sc.nextInt();
                                switch(choice){
                                    case 1:{
                                        am.credit_money(account_number);
                                        break;
                                    }
                                    case 2:{
                                        am.debit_money(account_number);
                                        break;
                                    }
                                    case 3:{
                                        am.transfer_money(account_number);
                                        break;
                                    }
                                    case 4:{
                                        am.getBalance(account_number);
                                        break;
                                    }
                                    case 5:{
                                        break;
                                    }
                                }
                            }

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
