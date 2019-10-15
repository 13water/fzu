package Game;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import static Until.HttpUntil.*;
import Until.*;
import Auth.*;

public class Round {
    public static User user = new User();
    public static Poker poker;
    public static void Open(User user, Poker P) throws IOException {
        Gson json = new Gson();
        JsonElement s1 = json.toJsonTree(user);
        String str = s1.getAsJsonObject().get("token").getAsString();
        String respond = postRequest("https://api.shisanshui.rtxux.xyz/game/open", null, str);
        System.out.println(respond);
        s1 = new JsonParser().parse(respond);
        JsonObject s2 = s1.getAsJsonObject();
        String s3 = s2.get("data").getAsJsonObject().get("card").getAsString();
        System.out.println(s3);
        P.init(s3);
    }
    public static void main(String args[]) throws IOException {
        Account account = new Account();
        Gson json = new Gson();
        NJ nj = new NJ();
        hand_card Hd = new hand_card();
        user.init("zxcad","zxcda");
        //      System.out.println(register(user));
        user.init("zxcad","zxcda");
        System.out.println(json.toJson(user));
        user = account.Login(user);
        System.out.println(json.toJson(user));
        Open(user, poker);
        EX ex = new EX();
        nj.Nomal_Judge(poker, Hd, 1);
        ex = ex.init(Hd);
        System.out.println(json.toJson(ex));
        nj.Nomal_Judge(poker, Hd, 2);
        ex = ex.init(Hd);
        System.out.println(json.toJson(ex));
        nj.Nomal_Judge(poker, Hd, 3);
        ex = ex.init(Hd);
        System.out.println(json.toJson(ex));
    }
}

