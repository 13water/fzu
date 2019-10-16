package Game;

public class hand_card {
    static int id = 0;
    static String[] card = new String[3];
    static void pushString(String s, int t){
        card[t - 1] += s;
    }
    static void init(Poker poker){
        id = poker.id;
        card[0] = "";
        card[1] = "";
        card[2] = "";
    }
    static String getss(){
        return card[0];
    }
}

