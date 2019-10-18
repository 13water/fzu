package Game;

public class NJ{
    static int cnt = 0;
    static int[] t = new int[10];
    public static char suit[] = {'*', '#', '&', '$'};
    static boolean flush(Poker poker){ //num == 5
        for (int i = 0; i < 4; i++){
            if (poker.row[i] >= 5){
                for (int j = 9; j >= 1; j--){
                    if (poker.card[i][j]){
                        boolean flag = true;
                        for (int k = 0; k < 5; k++){
                            if (poker.card[i][j+k])t[k] = i * 14 + j + k;
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
        for (int i = 13; i >= 1; i--){
            if (poker.col[i] == 4) {
                for (int j = 1; j <= 4; j++) {
                    t[j] = (j - 1) * 14 + i;
                }
                for (int j = 13; j >= 1; j--){
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
        for (int i = 13; i >= 1; i--){
            if (poker.col[i] == 3){
                L = i;
                break;
            }
        }
        for (int i = 13; i >= 1; i--){
            if (poker.col[i] == 2){
                R = i;
            }
        }
        if (R == 0){
            for (int i = 13; i >= 1; i--){
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
        int L = 6, R = 6, x = 6;
        for (int i = 0; i < 4; i++){
            if (poker.row[i] >= 5 && L == 6)
                L = i;
            else if (poker.row[i] >= 5 && L != 6)
                R = i;
        }
        for (int i = 13; i >= 1; i--){
            if (poker.card[L][i] && L != 6)
                x = L;
            else if (poker.card[R][i] && R != 6)
                x = R;
            if (x != 6)
                break;
        }
        if (R != 6 && L != 6){
            for (int i = 13; i >= 1; i--){
                if (poker.card[L][i] == true && poker.card[R][i] == false){
                    x = L;
                    break;
                }
                else if (poker.card[L][i] == false && poker.card[R][i] == true){
                    x = R;
                    break;
                }
            }
        }
        for (int i = x; i < 4 && x != 6; i++){
            if (poker.row[i] >= 5){
                int co = 0;
                for (int j = 13; j >= 1; j--){
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
        for (int i = 9; i >= 1; i--){
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
        for (int i = 13; i >= 1; i--){
            if (poker.col[i] == 3){
                int co = 0;
                for (int j = 0; j < 4; j++){
                    for (int k = 13; k >= 1; k--){
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
        for (int i = 12; i >= 1; i--){
            if (poker.col[i] == poker.col[i + 1] && poker.col[i] == 2){
                int co = 0;
                for (int j = 0; j < 4 && co < 1; j++){
                    for (int k = 13; k >= 1 && co < 1; k--){
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
        for (int i = 13; i >= 1; i--){
            if (L == 0 && poker.col[i] == 2)
                L = i;
            else if (poker.col[i] == 2 && R == 0 && L != 0)
                R = i;
        }
        if (L != 0 && R != 0){
            int co = 0;
            for (int j = 0; j < 4 && co < 1; j++){
                for (int k = 13; k >= 1 && co < 1; k--){
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

        for (int i = 13; i >= 1; i--){
            if (poker.col[i] == 2){
                int co = 0;
                for (int j = 0; j < 4; j++){
                    for (int k = 13; k >= 1; k--){
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
        for (int j = 13; j >= 1; j--){
            for (int i = 0; i < 4; i++){
                if (poker.card[i][j] && co < cnt){
                    t[co++] = i * 14 + j;
                }
            }
        }
        return true;
    }
    public static void Nomal_Judge(Poker poker, hand_card ex, int x){
        if (x == 1)cnt = 3;
        else cnt = 5;
        if (x != 1){
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
        if (x != 1){
            if (two_pair(poker)){
                analyse(ex, x);
                System.out.println(7);
                clr(poker);
                return;
            }
        }
        if (pair(poker)){
            analyse(ex, x);
            System.out.println(8);
            clr(poker);
            return;
        }
        if (zapai(poker)){
            analyse(ex, x);
            System.out.println(9);
            clr(poker);
            return;
        }
    }
    public static String Special_Judge(Poker poker){
        int flag = 0;
        String ans;
        int[] num = new int[5];
        for (int i = 0; i < 5; i++)num[i] = 0;
        int big = 0, small = 0;
        for (int i = 1; i <= 13; i++){
            num[poker.col[i]]++;
            if (i <= 7)
                small += poker.col[i];
            else
                big += poker.col[i];
        }
        if (num[1] == 13)   flag = 1;
        if (poker.col[10] + poker.col[11] + poker.col[12] + poker.col[13] >= 12)    flag = 2;
        if (small == 13 || big == 13)   flag = 3;
        if (num[4] == 3)    flag = 4;
        if (poker.row[0] + poker.row[1] == 13 || poker.row[2] + poker.row[3] == 13) flag = 5;
//        if (num[3] == 2 && ((num[2] == 3 || (num[4] == 1 && num[1] != 3))))
        int x = num[2], y = num[3], z = num[4];
        if (y == 3){
            y--;
            x++;
        }
        else if (y == 1 && z > 0){
            y++;
            z--;
        }
        if (z * 2 >= 3 - x && y == 2 && x <= 3) flag = 6;
        if (num[3] + num[4] == 4)   flag = 7;
        if (num[3] == 1 && num[2] + num[4] * 2 == 5)    flag = 8;
        if (num[3] + num[2] + num[4] * 2 == 6)  flag = 9;
        int[] a = new int[4];
        for (int i = 0; i < 4; i++){
            if (poker.row[i] == 3)
                a[0]++;
            else if (poker.row[i] == 5)
                a[1]++;
            else if (poker.row[i] == 8)
                a[2]++;
            else if (poker.row[i] == 10)
                a[3]++;
        }
        if (a[0] == 1 && (a[1] == 2 || a[3] == 1))
            flag = 10;
        else if (a[1] == 1 && a[2] == 1)
            flag = 11;
        a = new int[15];
        for (int i = 0; i <= 13; i++){
            a[i] = poker.col[i];
        }
        int cnt = 0;
        boolean ff = true;
        for (int i = 1; i <= 13; i++){
//            System.out.print(i);
//            System.out.print(" ");
//            System.out.println(a[i]);
            while(a[i] > 0){
                int lim;
                if (cnt++ == 0)lim = 3;
                else lim = 5;
                for (int j = 0; j < lim; j++){
                    if (i + j > 13){
                        ff = false;
                        break;
                    }
                    a[i+j]--;
                }
            }
            if (a[i] < 0)ff = false;
        }
        if (ff && cnt == 3)flag = 12;
        for (int i = 0; i <= 13; i++){
            a[i] = poker.col[i];
        }
        ff = true;
        cnt = 0;
        for (int i = 1; i <= 13; i++){
//            System.out.print(i);
//            System.out.print(" ");
//            System.out.println(a[i]);
            while(a[i] > 0){
                int lim;
                if (cnt++ == 1)lim = 3;
                else lim = 5;
                for (int j = 0; j < lim; j++){
                    if (i + j > 13){
                        ff = false;
                        break;
                    }
                    a[i+j]--;
                }
            }
            if (a[i] < 0)ff = false;
        }
        if (ff && cnt == 3)flag = 13;
        ff = true;
        for (int i = 0; i <= 13; i++){
            a[i] = poker.col[i];
        }
        cnt = 0;
        for (int i = 1; i <= 13; i++){
            while(a[i] > 0){
                int lim;
                if (cnt++ == 2)lim = 3;
                else lim = 5;
                for (int j = 0; j < lim; j++){
                    if (i + j > 13){
                        ff = false;
                        break;
                    }
                    a[i+j]--;
                }
            }
            if (a[i] < 0)ff = false;
        }
        if (ff && cnt == 3)flag = 14;
        if (flag > 0){
            System.out.println(flag);
            ans = "{\"id\":" + String.valueOf(poker.id) + ",\"card\":[\"" + poker.totp + "\"]}";
            return ans;
        }
        return null;
    }
    static void analyse(hand_card ex, int x){
        int s, num;
        String c, test;
        for (int i = 0; i < cnt; i++){
            s = t[i] / 14;
            num = t[i] - s * 14;
            if (i != 0){
                ex.pushString(" ", x);
            }
            c = String.valueOf(suit[s]);
            test = c;
            ex.pushString(c, x);
            c = change(num);
            test = test + c;
            ex.pushString(c, x);
//            System.out.println(test);
        }
//        for (int i = 0; i < cnt; i++){
//            System.out.println(t[i]);
//        }

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
        if (x >= 1 && x <= 8){
            char c = (char)('1' + x);
            s = String.valueOf(c);
        }
        else if (x == 9)s = "10";
        else if (x == 10)s = "J";
        else if (x == 11)s = "Q";
        else if (x == 12)s = "K";
        else if (x == 13)s = "A";
        else s = "!";
        return s;
    }
}