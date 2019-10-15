package Game;

import java.util.Scanner;

 public class Poker {
    public static boolean[][] card = new boolean[4][14];
    public static int row[] = new int[4];
    public static int col[] = new int[14];
    public static char suit[] = {'*', '#', '&', '$'};
    static void begin(char[] s) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] != ' ') {
                int t = 0, num = 0;
                for (int j = 0; j < 4; j++) {
                    if (s[i] == suit[j]) {
                        t = j;
                        break;
                    }
                }
                num = change(s[i+1]);
                if (num == 9)
                    i++;
                i++;
                card[t][num] = true;
                row[t]++;
                col[num]++;
            }
        }
    }
    static void print() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= 13; j++) {
                System.out.print((card[i][j] == true));
                if (j == 13)
                    System.out.println();
                else
                    System.out.print(' ');
            }
        }
    }
    static int change(char c) {
        if (c >= '2' && c <= '9')
            return (int)(c - '1');
        else if (c == '1')
            return 9;
        else if (c == 'J')
            return 10;
        else if (c == 'Q')
            return 11;
        else if (c == 'K')
            return 12;
        else if (c == 'A')
            return 13;
        else
            return 0;
    }
    static void init(String card_json){
        int len = card_json.length();
        char[] s = new char[len + 2];
        for (int i = 0; i < len; i++)s[i] = card_json.charAt(i);
        System.out.println(s);
        s[len + 1] = s[len] = ' ';
        begin(s);
        print();
    }
}
class NJ{
     static int cnt = 0;
    static int[] t = new int[10];
    public static char suit[] = {'*', '#', '&', '$'};
     static boolean flush(Poker poker){ //num == 5
         for (int i = 0; i < 4; i++){
             if (poker.row[i] >= 5){
                 for (int j = 1; j <= 9; j++){
                     if (poker.card[i][j]){
                         boolean flag = true;
                         for (int k = 0; k < 5; k++){
                             if (poker.card[i][j+k])t[k] = i * 14 + j;
                             else flag = false;
                         }
                         if (flag)
                             return true;
                     }
                 }
             }
         }
         return false;
     }
     static boolean boom(Poker poker){
         for (int i = 1; i <= 13; i++){
             if (poker.col[i] == 4) {
                 for (int j = 1; j <= 4; j++) {
                     t[j] = (j - 1) * 14 + i;
                 }
                 for (int j = 1; j <= 13; j++){
                     for (int k = 0; k < 4; k++){
                         if (j == i)continue;
                         else if (poker.card[k][j]){
                             t[0] = k * 14 + j;
                             return true;
                         }
                     }
                 }
                 return true;
             }
         }
         return false;
     }
     static boolean cucurbit(Poker poker){ //葫芦
         int L = 0, R = 0;
         for (int i = 1; i <= 13; i++){
             if (poker.col[i] == 3){
                 L = i;
                 break;
             }
         }
         for (int i = 1; i <= 13; i++){
             if (poker.col[i] == 2){
                 R = i;
             }
         }
         if (R == 0){
             for (int i = 1; i <= 13; i++){
                 if (poker.col[i] == 3 && i != L){
                     R = i;
                     break;
                 }
             }
         }
         if (R == 0 || L == 0)
             return false;
         else{
             int co = 0;
             for (int i = 0; i < 4; i++){
                 if (poker.card[i][L])
                     t[co++] = i * 14 + L;
             }
             for (int i = 0; i < 4; i++){
                 if (poker.card[i][R] && co < 5)
                     t[co++] = i * 14 + R;
             }
             return true;
         }
     }
     static boolean same_suit(Poker poker){
         for (int i = 0; i < 4; i++){
             if (poker.row[i] >= 5){
                 int co = 0;
                 for (int j = 1; j <= 13; j++){
                     if (poker.card[i][j]){
                         t[co++] = i * 14 + j;
                     }
                     if (co >= 5)
                         return true;
                 }
             }
         }
         return false;
     }
     static boolean straight(Poker poker){
         for (int i = 1; i <= 9; i++){
             boolean flag = true;
             for (int j = 0; j < 5;j++){
                 if (poker.col[i+j] == 0)flag = false;
             }
             if (flag){
                 int co = 0;
                 for (int j = 0; j < 5; j++){
                     for (int k = 0; k < 4; k++){
                         if (poker.card[k][i+j]){
                             t[co++] = k * 14 + i + j;
                             break;
                         }
                     }
                 }
                 return true;
             }
         }
         return false;
     }
     static boolean three(Poker poker){
         for (int i = 1; i <= 13; i++){
             if (poker.col[i] == 3){
                 int co = 0;
                 for (int j = 0; j < 4; j++){
                     for (int k = 1; k <= 13; k++){
                         if (k == i)continue;
                         if (poker.card[j][k] && co < 2){
                             t[co++] = j * 14 + k;
                         }
                     }
                 }
                 for (int j = 0; j < 4; j++){
                     if (poker.card[j][i])
                        t[co++] = j * 14 + i;
                 }
                 return true;
             }
         }
         return false;
     }
     static boolean two_pair(Poker poker){
         for (int i = 1; i <= 12; i++){
             if (poker.col[i] == poker.col[i + 1] && poker.col[i] == 2){
                 int co = 0;
                 for (int j = 0; j < 4 && co < 1; j++){
                     for (int k = 1; k <= 13 && co < 1; k++){
                         if (k == i || k == i + 1)continue;
                         if (poker.card[j][k]){
                             t[co++] = j * 14 + k;
                         }
                     }
                 }
                 for (int j = 0; j < 4; j++)
                    if (poker.card[j][i])t[co++] = j * 14 + i;
                 for (int j = 0; j < 4; j++)
                     if (poker.card[j][i+1])t[co++] = j * 14 + i + 1;
                 return true;
             }
         }
         int L = 0, R = 0;
         for (int i = 1; i <= 13; i++){
             if (L == 0 && poker.col[i] == 2)
                 L = i;
             else if (poker.col[i] == 2 && R == 0 && L != 0)
                 R = i;
         }
         if (L != 0 && R != 0){
             int co = 0;
             for (int j = 0; j < 4 && co < 1; j++){
                 for (int k = 1; k <= 13 && co < 1; k++){
                     if (k == L || k == R)continue;
                     if (poker.card[j][k]){
                         t[co++] = j * 14 + k;
                     }
                 }
             }
             for (int j = 0; j < 4; j++)
                 if (poker.card[j][L])t[co++] = j * 14 + L;
             for (int j = 0; j < 4; j++)
                 if (poker.card[j][R])t[co++] = j * 14 + R;
             return true;
         }
         return false;
     }
     static boolean pair(Poker poker){

         for (int i = 1; i <= 13; i++){
             if (poker.col[i] == 2){
                 int co = 0;
                 for (int j = 0; j < 4; j++){
                     for (int k = 1; k <= 13; k++){
                         if (k == i)continue;
                         if (poker.card[j][k] && co < 3){
                             t[co++] = j * 14 + k;
                         }
                     }
                 }
                 for (int j = 0; j < 4; j++){
                     if (poker.card[j][i]){
                         t[co++] = j * 14 + i;
                     }
                 }
                 return true;
             }
         }
         return false;
     }
     static boolean zapai(Poker poker){
         int co = 0;
         for (int i = 0; i < 4; i++){
             for (int j = 1; j <= 13; j++){
                 if (poker.card[i][j] && co < cnt){
                     t[co++] = i * 14 + j;
                 }
             }
         }
         return true;
     }
     static void Nomal_Judge(Poker poker, hand_card ex, int x){
         if (x == 3)cnt = 3;
         else cnt = 5;
         if (x != 3){
             if (flush(poker)){
                 analyse(ex, x);
                 System.out.println(1);
                 clr(poker);
                 return;
             }
             if (boom(poker)){
                 analyse(ex, x);
                 System.out.println(2);
                 clr(poker);
                 return;
             }
             if (cucurbit(poker)){
                 analyse(ex, x);
                 System.out.println(3);
                 clr(poker);
                 return;
             }
             if (same_suit(poker)){
                 analyse(ex, x);
                 System.out.println(4);
                 clr(poker);
                 return;
             }
             if (straight(poker)){
                 analyse(ex, x);
                 System.out.println(5);
                 clr(poker);
                 return;
             }
         }
         if (three(poker)){
             analyse(ex, x);
             System.out.println(6);
             clr(poker);
             return;
         }
         if (x != 3){
             if (two_pair(poker)){
                 analyse(ex, x);
                 System.out.println(7);
                 clr(poker);
                 return;
             }
         }
         if (pair(poker)){
             analyse(ex, x);
             System.out.println(1);
             clr(poker);
             return;
         }
         if (zapai(poker)){
             analyse(ex, x);
             System.out.println(1);
             clr(poker);
             return;
         }
     }
     static void analyse(hand_card ex, int x){
         int s, num;
         String c;
        for (int i = 0; i < cnt; i++){
            s = t[i] / 14;
            num = t[i] - s * 14;
            if (i != 0){
                ex.pushString(" ", x);
            }
            c = String.valueOf(suit[s]);
            ex.pushString(c, x);
            c = change(num);
            ex.pushString(c, x);
        }
     }
     static void clr(Poker poker){
         int s, num;
         for (int i = 0; i < cnt; i++){
             s = t[i] / 14;
             num = t[i] - s * 14;
             poker.card[s][num] = false;
             poker.col[num]--;
             poker.row[s]--;
         }
     }
    static String change(int x) {
        String s;
        if (x >= 1 && x <= 8)s = String.valueOf('1' + x);
        else if (x == 9)s = "10";
        else if (x == 10)s = "J";
        else if (x == 11)s = "Q";
        else if (x == 12)s = "K";
        else if (x == 13)s = "A";
        else s = "!";
        return s;
    }
}

