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
class EX{
    private int id = 0;
    private String[] card = new String[3];
    EX init(hand_card Hd){
        EX ex = new EX();
        ex.id = Hd.id;
        ex.card[0] = Hd.card[0];
        ex.card[1] = Hd.card[1];
        ex.card[2] = Hd.card[2];
        return ex;
    }
}
