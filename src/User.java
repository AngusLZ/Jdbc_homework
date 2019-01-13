public class User {
    // id ,username,password,name,gender,phone
    private String id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String gender; //性别

//    public User(String id , String userName , String password , String name , String gender , String phone){
//        this.id = id;
//        this.userName = userName;
//        this.password = password;
//        this.name = name;
//        this.gender = gender;
//        this.phone = phone;
//    }

    public void setId(String id){
        this.id = id;
    }



    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getId(){
        return this.id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getGender(){
        return this.gender;
    }
}
