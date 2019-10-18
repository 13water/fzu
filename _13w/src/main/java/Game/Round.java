package Game;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Scanner;

import static Until.HttpUntil.*;
import Until.*;
import Auth.*;

public class Round {
    public static User user = new User();
    public static Poker poker;
    public static NJ nj = new NJ();
    public static hand_card Hd = new hand_card();
    public static void Open(User user, Poker P) throws IOException {
        Gson json = new Gson();
        JsonElement s1 = json.toJsonTree(user);
        String str = s1.getAsJsonObject().get("token").getAsString();
        String respond = postRequest("https://api.shisanshui.rtxux.xyz/game/open", null, str);
        s1 = new JsonParser().parse(respond);
        JsonObject s2 = s1.getAsJsonObject();
        String s3 = s2.get("data").getAsJsonObject().get("card").getAsString();
        int num = s2.get("data").getAsJsonObject().get("id").getAsInt();
        System.out.println(s3);
        P.totp = s3;
        P.init(s3, num);
    }
    public static void submit(User user, String s) throws IOException {
        Gson json = new Gson();
        JsonElement s1 = json.toJsonTree(user);
        String str = s1.getAsJsonObject().get("token").getAsString();
        String respond = postRequest("https://api.shisanshui.rtxux.xyz/game/submit", s, str);
        System.out.println(respond);
        s1 = new JsonParser().parse(respond);
        JsonObject s2 = s1.getAsJsonObject();
        String s3 = s2.get("data").toString();
        System.out.println(s3);
    }
    public static void main(String args[]) throws IOException {
        Account account = new Account();
        Gson json = new Gson();
        Scanner sc = new Scanner(System.in);
        user.init("kuliangce","txt");
        user = account.Login(user);
        for (int i = 0; i < 30; i++){
            Open(user, poker);
//            String s1 = sc.nextLine();
//            System.out.println(s1);
//            poker.init(s1, i);
            Hd.init(poker);
            String ss = nj.Special_Judge(poker);
            nj.Nomal_Judge(poker, Hd, 3);
            nj.Nomal_Judge(poker, Hd, 2);
            nj.Nomal_Judge(poker, Hd, 1);
            EX ex = new EX();
            ex = ex.init(Hd);
            JsonElement jj = json.toJsonTree(ex);
            if (ss == null)
                System.out.println(jj.toString());
            else
                System.out.println(ss);
//            Hd.getss(0);
//            System.out.println(jj.toString());
                submit(user, jj.toString());
        }
    }
}

