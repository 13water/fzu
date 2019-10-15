package Until;

public class User {
    int user_id;
    private String token;
    private String username;
    private String password;
    public void init(String a, String b){
        this.username = a;
        this.password = b;
    }
    public void ID(int ID){
        user_id = ID;
    }
}
