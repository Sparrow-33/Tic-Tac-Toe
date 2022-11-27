import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.exit;

public class Game {
    private static String[] str;
    private static Scanner scanner = new Scanner(System.in);
    private static AtomicInteger Xs = new AtomicInteger();
    private static AtomicInteger Os = new AtomicInteger();
    private static AtomicInteger dash = new AtomicInteger();
    private static int Xcount = 0;
    private static int Ocount = 0;
    private static int col;
    private static int row;
    private static int whoseTurn = 1;

    private static int[][] coordinates = {{0,1,2},{3,4,5},{6,7,8}};


    public static void main(String[] args) throws IOException {

        String str1 = "_________";
        str =str1.split("");
        printTable();
        int position;
        BufferedReader bi;
        String[] strNums;
        int[] num = new int[2];

        while (true){

            while (true) {
                bi = new BufferedReader(new InputStreamReader(System.in));
                try {
                    strNums = bi.readLine().split(" ");
                    for (int i = 0; i < strNums.length; i++) {
                        num[i] = Integer.parseInt(strNums[i]);
                    }
                    position = coordinates[num[0] - 1][num[1] - 1];

                    if (!str[position].equals("_")) {
                        throw new ArrayCaseAlreadyFilledException("Case already filled");
                    }

                    break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } catch (NumberFormatException e) {
                    System.out.println("You should enter numbers!");
                } catch (ArrayCaseAlreadyFilledException e) {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
            Xs.set(0);
            Os.set(0);
            if (whoseTurn % 2 == 0) {
                str[position] = "O";
            } else {
                str[position] = "X";
            }
            whoseTurn++;
            printTable();

//      Count the Xs and Os
            Arrays.stream(str).forEach(e -> {
                switch (e) {
                    case "X" -> Xs.getAndIncrement();
                    case "O" -> Os.getAndIncrement();
                    case "_" -> dash.getAndIncrement();
                }
            });

//        check for a winner
            for (int i = 0; i < str.length; i += 3) {
                row = str[i].charAt(0) + str[i + 1].charAt(0) + str[i + 2].charAt(0);
                if (row == 264) {
                    Xcount++;
                } else if ((row == 237)) {
                    Ocount++;
                }
            }
            for (int i = 0; i < 3; i++) {
                col = str[i].charAt(0) + str[i + 3].charAt(0) + str[i + 6].charAt(0);
                if (col == 264) {
                    System.out.println("here3");
                    Xcount++;
                } else if ((col == 237)) {
                    System.out.println("here4");
                    Ocount++;
                }
            }

            if (Xcount == 1 && Ocount == 1) {
                System.out.println("Impossible");
                exit(0);
            } else if (Xcount == 1) {
                System.out.println("X wins");
                exit(0);
            } else if (Ocount == 1) {
                System.out.println("O wins");
                exit(0);
            }

            int diag1 = str[0].charAt(0) + str[4].charAt(0) + str[8].charAt(0);
            int diag2 = str[2].charAt(0) + str[4].charAt(0) + str[6].charAt(0);
            if (diag1 == 264 || diag2 == 264) {
                System.out.println("X wins");
                exit(0);
            } else if (diag1 == 237 || diag2 == 237) {
                System.out.println("O wins");
                exit(0);
            }
            //        check impossible state
            int difference = Math.abs(Xs.intValue() - Os.intValue());
            if (difference > 1) {
                System.out.println("ImpossibleS");
                exit(0);
            } else if (Xs.intValue() + Os.intValue() == 9) {
                System.out.println("Draw");
                exit(0);
            }
        }
    }

    public static void printTable(){
        System.out.println("--------- ");
        for (int i=0; i<str.length;i+=3){
            System.out.println("| "+str[i]+" "+str[i+1]+" "+str[i+2]+" |");
        }
        System.out.println("--------- ");
    }

    static class  ArrayCaseAlreadyFilledException  extends Exception
    {
        public ArrayCaseAlreadyFilledException (String str)
        {
            // calling the constructor of parent Exception
            super(str);
        }
    }
}
