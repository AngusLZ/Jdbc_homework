import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Jdbc {

    public static void main(String[] args) throws Exception {


        Scanner scanner = new Scanner(System.in);
        while(true) {
            String sql1 = "SELECT * FROM administrator";
            String sql2 = "SELECT * FROM users";

            new Menu().SortMenu();
            int judge;
            judge = scanner.nextInt();
            while (judge != 1 && judge != 2 && judge != 3) {
                System.out.println("Input error! Please input again.");
                judge = scanner.nextInt();
            }
            if (judge == 3)
                return;
            int signinAdmministrator = 0;
            int signinUsers = 0;
            while (true) {
                if (judge == 1) {
                    List<Administrator> administrators = new Operation().executeQuery1(sql1 , Administrator.class);
                   if (signinAdmministrator == 0) {
                       System.out.println("Please input name.");
                       String adminPassword = " ";
                       while (true) {
                           int yu = 0;
                           String adminName = scanner.next();
                           for (Administrator a : administrators) {
                               if (a.getName().equals(adminName)) {
                                   yu = 1;
                                   adminPassword = a.getPassword();
                               }
                           }
                           if (yu == 1)
                               break;
                           else
                               System.out.println("No administrator. Please input again.");
                       }
                       String ppp;
                       System.out.println("Please input password.");
                       while (true) {
                           ppp = new Operation().MD5(scanner.next());
                           if (ppp.equals(adminPassword)) {
                               signinAdmministrator = 1;
                               break;
                           }
                           else
                               System.out.println("Wrong password. Please input agagin.");
                       }
                   }

                    List<User> users = new Operation().executeQuery1(sql2 , User.class);

                    new Menu().AdministratorMenu1();
                    int judge1 = scanner.nextInt();
                    while (judge1 != 1 && judge1 != 2 && judge1 != 3 && judge1 != 4 && judge1 != 5 && judge1 != 6 && judge1 != 7 && judge1 != 8 && judge1 != 9 && judge1 != 10) {
                        System.out.println("Input error! Please input again.");
                        judge1 = scanner.nextInt();
                    }

                    int judgee = 2;

                    if (judge1 == 1)
                        new Operation().showAdministrator();

                    if(judge1 == 2){
                        if (administrators.isEmpty()) {
                            System.out.println("No information!");
                            break;
                        }
                        System.out.println("Input name:");
                        String judgeName = " ";
                        while (judgee == 2) {

                            judgeName = scanner.next();

                            for (Administrator a : administrators){
                                if (a.getName().equals(judgeName)){
                                    judgee = 0;}}

                            if (judgee == 2 || judgeName.length() > 16){
                                System.out.println("Input error! Please input again.");
                            }
                        }
                        System.out.println("Please input name.");
                        String name = scanner.next();
                        System.out.println("Please input password.");
                        String password = scanner.next();
                        Administrator aaa = new Administrator();
                        aaa.setName(name);
                        aaa.setPassword(password);
                        new Operation().changeAdministrator(judgeName , aaa);
                    }

                    if (judge1 == 3){
                        judgee = 2;
                        System.out.println("Inpue name.");
                        String judgeName = " ";
                        while(judgee == 2){
                            judgee = 0;

                            judgeName = scanner.next();

                            if (administrators.isEmpty())
                                break;;

                            for(Administrator a : administrators){
                                if (a.getName().equals(judgeName) || judgeName.length() > 16) {
                                    judgee = 2;
                                    System.out.println("Input error! Please input again.");
                                    break;
                                }
                            }
                            if (judgee == 0)
                                break;
                        }

                        System.out.println("Please input password.");
                        String password = scanner.next();
                        Administrator aaa = new Administrator();
                        aaa.setName(judgeName);
                        aaa.setPassword(password);
//                        Administrator aaa = new Administrator(name , password);
                        new Operation().insertAdministrator(aaa);
                    }

                    if (judge1 == 4){
                        if (administrators.isEmpty()){
                            System.out.println("No information!");
                            break;
                        }

                        judgee = 2;
                        System.out.println("Input name.");
                        String judgeName = " ";
                        while(judgee == 2){

                            judgeName = scanner.next();

                            for(Administrator a : administrators){
                                if (a.getName().equals(judgeName))
                                    judgee = 0;}

                            if (judgee == 2)
                                System.out.println("Input error! Please input again.");
                        }
                        new Operation().deleteAdministrator(judgeName);
                    }

                    if (judge1 == 5)
                        new Operation().showUsers();

                    if (judge1 == 6) {
                        if (users.isEmpty()){
                            System.out.println("No information!");
                            break;
                        }

                        System.out.println("Please input id.");
                        String judgeId = " ";
                        while (judgee == 2) {
                            judgeId = scanner.next();

                            for (User a : users) {
                                if (a.getId().equals(judgeId))
                                    judgee = 0;
                            }
                            if (judgee == 2) {
                                System.out.println("Input error! Please input again.");
                            }
                        }
                        System.out.println("Please input id.");
                        String id = " ";
                        judgee = 2;
                        while (judgee == 2){
                            judgee = 0;
                            id = scanner.next();
                            for (User a : users){
                                if (a.getId().equals(id)) {
                                    judgee = 2;
                                    System.out.println("Input error! Please input again.");
                                    break;
                                }
                            }
                            if (judgee == 0)
                                break;
                        }

                        String userName = " ";
                        int judge3 = 1;
                        judgee = 2;
                        System.out.println("Please input username.");
                        while (judgee == 2){
                            judgee = 0;
                            userName = scanner.next();

                            for (User a : users)
                                if (a.getUsername().equals(userName)){
                                    judgee = 2;}

                            if (judgee == 0)
                                System.out.println("Input error! Please input again.");
                        }

                        System.out.println("Please input password.");
                        String password = scanner.next();

                        System.out.println("Please input your name.");
                        String name = scanner.next();

                        System.out.println("Please input your gender.");
                        String gender = scanner.next();

                        System.out.println("Please input your phone.");
                        String phone = scanner.next();
                        while (phone.length() != 11){
                            System.out.println("Input error! Please input again.");
                            phone = scanner.next();
                        }

                        User user = new User();
                        user.setId(judgeId);
                        user.setUsername(userName);
                        user.setPassword(password);
                        user.setName(name);
                        user.setGender(gender);
                        user.setPhone(phone);
                        new Operation().changeUsers(judgeId , user);
                    }

                    if (judge1 == 7) {
                        judgee = 2;
                        System.out.println("Please input id.");
                        String judgeId = " ";
                        while (judgee == 2) {

                            judgee = 0;
                            judgeId = scanner.next();

                            if (users.isEmpty())
                                break;

                            for (int i = 0 ; i < users.size() ; i++) {
                                if (users.get(i).getId().equals(judgeId) || judgeId.length() > 9) {
                                    judgee = 2;
                                    System.out.println("Input error! Please input again.");
                                    break;
                                }
                            }

                            if (judgee == 0) {
                                break;
                            }
                        }

                        String userName = " ";
                        int judge3 = 1;
                        System.out.println("Please input username.");
                        while (judge3 == 1){

                            judge3 = 0;
                            userName = scanner.next();

                            if (users.isEmpty())
                                break;


                            for (int i = 0 ; i < users.size() ; i++)
                                if (users.get(i).getUsername().equals(userName)){
                                    judge3 = 1;
                                    System.out.println("Input error! Please input again.");
                                    break;
                                }

                            if (judge3 == 0)
                                break;
                        }

                        System.out.println("Please input password.");
                        String password = scanner.next();

                        System.out.println("Please input your name.");
                        String name = scanner.next();

                        System.out.println("Please input your gender.");
                        String gender = scanner.next();

                        System.out.println("Please input your phone.");
                        String phone = scanner.next();
                        while (phone.length() != 11){
                            System.out.println("Input error! Please input again.");
                            phone = scanner.next();
                        }

                        User user = new User();
                        user.setId(judgeId);
                        user.setUsername(userName);
                        user.setPassword(password);
                        user.setName(name);
                        user.setGender(gender);
                        user.setPhone(phone);
                        new Operation().insertUsers(user);
                    }

                    if (judge1 == 8) {
                        if (users.isEmpty()) {
                            System.out.println("No information!");
                            break;
                        }
                        judgee = 2;
                        System.out.println("Input id.");
                        String judgeId = " ";
                        while(judgee == 2){

                            judgeId = scanner.next();

                            for(User a : users){
                                if (a.getId().equals(judgeId))
                                    judgee = 0;}

                            if (judgee == 2)
                                System.out.println("Input error! Please input again.");
                        }
                        new Operation().deleteUsers(judgeId);
                    }

                    if(judge1 == 9)
                        break;
                    if (judge1 == 10)
                        return;
                }

                if (judge == 2){
                    int judgee = 2;
                    List<User> users = new Operation().executeQuery1(sql2 , User.class);

                    if (signinUsers == 0) {
                        System.out.println("Please input username.");
                        String userPassword = " ";
                        while (true) {
                            int yu = 0;
                            String userName = scanner.next();
                            for (User a : users) {
                                if (a.getUsername().equals(userName)) {
                                    yu = 1;
                                    userPassword = a.getPassword();
                                }
                            }
                            if (yu == 1)
                                break;
                            else
                                System.out.println("No administrator. Please input again.");
                        }
                        String ppp;
                        System.out.println("Please input password.");
                        while (true) {
                            ppp = new Operation().MD5(scanner.next());
                            if (ppp.equals(userPassword)) {
                                signinUsers = 1;
                                break;
                            }
                            else
                                System.out.println("Wrong password. Please input agagin.");
                        }
                    }


                    new Menu().userMenu1();
                    int judge2 = scanner.nextInt();
                    while (judge2 != 1 && judge2 != 2 && judge2 != 3 && judge2 != 4 && judge2 != 5 && judge2 != 6) {
                        System.out.println("Input error! Please input again.");
                        judge2 = scanner.nextInt();
                    }

                    if (judge2 == 1)
                        new Operation().showUsers();

                    if (judge2 == 2) {
                        if (users.isEmpty()){
                            System.out.println("No information!");
                            break;
                        }

                        System.out.println("Please input id.");
                        String judgeId = " ";
                        while (judgee == 2) {
                            judgeId = scanner.next();

                            for (User a : users) {
                                if (a.getId().equals(judgeId))
                                    judgee = 0;
                            }
                            if (judgee == 2) {
                                System.out.println("Input error! Please input again.");
                            }
                        }
                        System.out.println("Please input id.");
                        String id = " ";
                        judgee = 2;
                        while (judgee == 2){
                            judgee = 0;
                            id = scanner.next();
                            for (User a : users){
                                if (a.getId().equals(id)) {
                                    judgee = 2;
                                    System.out.println("Input error! Please input again.");
                                    break;
                                }
                            }
                            if (judgee == 0)
                                break;
                        }

                        String userName = " ";
                        int judge3 = 1;
                        judgee = 2;
                        System.out.println("Please input username.");
                        while (judgee == 2){
                            judgee = 0;
                            userName = scanner.next();

                            for (User a : users)
                                if (a.getUsername().equals(userName)){
                                    judgee = 2;}

                            if (judgee == 0)
                                System.out.println("Input error! Please input again.");
                        }

                        System.out.println("Please input password.");
                        String password = scanner.next();

                        System.out.println("Please input your name.");
                        String name = scanner.next();

                        System.out.println("Please input your gender.");
                        String gender = scanner.next();

                        System.out.println("Please input your phone.");
                        String phone = scanner.next();
                        while (phone.length() != 11){
                            System.out.println("Input error! Please input again.");
                            phone = scanner.next();
                        }

                        User user = new User();
                        user.setId(judgeId);
                        user.setUsername(userName);
                        user.setPassword(password);
                        user.setName(name);
                        user.setGender(gender);
                        user.setPhone(phone);
                        new Operation().changeUsers(judgeId , user);
                    }

                    if (judge2 == 3)
                        break;

                    if (judge2 == 4)
                        return;
                }
            }
        }
    }
}
