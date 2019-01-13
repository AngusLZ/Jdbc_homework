import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class Operation {

    public String username = "root";
    public String password = "root";
    public String connectionUrl = "jdbc:mysql://127.0.0.1:3306/wl_users?useUnicode=true&characterEncoding=UTF-8";

    Connection conn;


    //加密算法 ， 修改信息和增加信息的时候使用
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String buqi(String a){
        while(a.length() < 16)
            a = a + " ";
        return a;
    }

    public static String buqi2(String a){
        while(a.length() < 32)
            a = a + " ";
        return a;
    }

//=================================================================================
    //查看administor的信息
    //查看users的信息
    public void showAdministrator() throws Exception {
        conn = DriverManager.getConnection(connectionUrl , username , password);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM administrator";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println(buqi("Name") + "\t" + buqi("Password"));
        while (rs.next()) {
            String id = buqi(rs.getString(1));
            String pw = buqi2(rs.getString(2));
            System.out.println(id + "\t" + pw);
        }
    } //完成
    public void showUsers() throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM users";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println(buqi("id") + "\t" + buqi("Username") + "\t" + buqi2("Password") + "\t" + buqi("Name") + "\t" + buqi("Gender") + "\t" + buqi("Phone"));
        while(rs.next()){
            String id = buqi(rs.getString(1));
            String un = buqi(rs.getString(2));
            String pw = buqi2(rs.getString(3));
            String name = buqi(rs.getString(4));
            String gender = buqi(rs.getString(5));
            String phone = buqi(rs.getString(6));
            System.out.println(id + "\t" + un + "\t" + pw + "\t" + name + "\t" + gender + "\t" + phone);
        }
    } //完成
//=================================================================================

    //========================================================================
    //增加信息
    //单一性的检测  ----》有时间做
    public void insertAdministrator(Administrator administrator) throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);
        String sql = "INSERT INTO administrator (name , password) VALUES (? , ?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);

        String name = administrator.getName();
        String pw = MD5(administrator.getPassword());

        ptmt.setString(1,name);
        ptmt.setString(2,pw);

        ptmt.execute();
    }

    public void insertUsers(User user) throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);
        String sql = "INSERT INTO users(id , username , password , name , gender , phone) VALUES (? , ? , ? , ? , ? , ?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);

        String id = user.getId();
        String un = user.getUsername();
        String pw = MD5(user.getPassword());
        String name = user.getName();
        String gender = user.getGender();
        String phone = user.getPhone();

        ptmt.setString(1,id);
        ptmt.setString(2,un);
        ptmt.setString(3,pw);
        ptmt.setString(4,name);
        ptmt.setString(5,gender);
        ptmt.setString(6,phone);

        ptmt.execute();

    }
    //========================================================================


    //========================================================================
    //删除信息
    //传入要删除的人的姓名
    public void deleteAdministrator(String a) throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);
        String sql = "delete from administrator where name = ?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1,a);
        ptmt.execute();
    }

    //传入要删除的人的id
    public void deleteUsers(String idid) throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);
        String sql = "delete from users where id = ?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1,idid);
        ptmt.execute();
    }
    //========================================================================

    //========================================================================
    //修改信息
    public void changeAdministrator(String name , Administrator administrator) throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);
        String sql = "UPDATE administrator SET name = ? , password = ? WHERE name = ?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1 , administrator.getName());
        ptmt.setString(2 , MD5(administrator.getPassword()));
        ptmt.setString(3 , name);
        ptmt.execute();
    }

    public void changeUsers(String id , User user) throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);
        String sql = "UPDATE users SET id = ? , username = ? , password = ? , name = ? , gender = ? , phone = ? where id = ?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1 , user.getId());
        ptmt.setString(2 , user.getUsername());
        ptmt.setString(3 , MD5(user.getPassword()));
        ptmt.setString(4 , user.getName());
        ptmt.setString(5 , user.getGender());
        ptmt.setString(6 , user.getPhone());
        ptmt.setString(7 , id);
        ptmt.execute();
    }
    //========================================================================

    //将数据库里面的数据导入到相应的pojo类对象里面
    public List executeQuery1(String sql , Class cls) throws Exception{
        conn = DriverManager.getConnection(connectionUrl , username , password);


        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        int numColums = rsmd.getColumnCount();
        String[] labels = new String[numColums];
        int[] types = new int[numColums];
        for(int i = 0 ; i < numColums ; i++){
            int columnIndex = i + 1;
            labels[i] = rsmd.getColumnLabel(columnIndex);
            types[i] = rsmd.getColumnType(columnIndex);
        }

        Method[] setters = Reflect.findMethod(cls , labels);

        List rows = new ArrayList();

        while(rs.next()){
            Object pojo = cls.newInstance();
            rows.add(pojo);

            for(int i = 0 ; i < numColums ; i++){
                int columIndex = i + 1;
                String columnValue = rs.getString(columIndex);

                try{
                    Reflect.map(pojo , setters[i] , types[i] , columnValue);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return rows;
//        ResultSetMetaData
    }
}
