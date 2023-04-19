import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int flag = 0;
        //The game starts at the first time.
        startTheGame();

        //This loop is for restarting the game.
        while (flag != 1){

            Scanner input2 = new Scanner(System.in);
            char [] restart = new char[1];
            restart[0] = ' ';

            System.out.println("Press R to restart the game...");
            System.out.println("Press any key else to exit...");

            restart[0] = input2.next().charAt(0);
            if(restart[0] == 'r'){
                startTheGame();
            }

            if(restart[0] != 'r'){
                flag = 1;
            }
        }
    }

    /**
     * In this method the game is started by the methods has written before.
     */
    private static void startTheGame() {
        // Board has 16 square which is initialized by space
        String[][] page = { {" "," "," "," " },
                            {" "," "," "," " },
                            {" "," "," "," " },
                            {" "," "," "," " }};

        //Choosing your rival (Human or AI)
        System.out.println("Select your player:");
        System.out.println("1. AI player");
        System.out.println("2. Player 2");
        Scanner input2 = new Scanner(System.in);
        int choosingPlayer = input2.nextInt();

        //Choosing 3 squares to lock randomly
        chooseRandomForLock(page);

        //Flags and counters
        int flag = 0;
        int countOfEmptySpaces = 13;
        Scanner input = new Scanner(System.in);

        System.out.print("\033[H\033[2J");
        while (countOfEmptySpaces > 1) {
            if(flag != 0){
                break;
            }

            playerTurn(page, input);
            flag = checkForWinner(page, flag);
            countOfEmptySpaces--;

            if(choosingPlayer == 1){
                computerTurn(page);
                countOfEmptySpaces--;

                //printPage(page);
                flag = checkForWinner(page, flag);
            }

            if (choosingPlayer == 2){
                printPage(page);
                playerTwoTurn(page, input);
                countOfEmptySpaces--;

                //printPage(page);
                flag = checkForWinner(page, flag);
            }
            System.out.print("\033[H\033[2J");
            printPage(page);
        }


        if(flag == 1){
            System.out.println("You Won :)");
        }
        else if (flag == 2){
            System.out.println("You lost :(");
        }
        if((countOfEmptySpaces == 1) && (flag != 1) && (flag != 2)){
            flag = 3;
        }
        if (flag == 3){
            System.out.println("Nobody won :(");
        }
    }


    /**
     * Detecting the winner by initializing flag with numbers(1 or 2)
     * Note: If flag is equal to 1 -> you won
     *      but if flag equal to 2 -> you lost
     * @param page Used to check all the elements of matrix
     * @param flag Flag will be initialized int this method.
     * @return integer flag
     */
    private static int checkForWinner(String[][] page, int flag) {
        for (int i = 0; i <4; i++) {
            for (int j = 0; j < 4; j++) {
                if(page[i][j] != " "){
                    if ((gameFinishedHorizental(page, "X")) || (gameFinishedVertical(page, "X")) || (gameFinishedCornerwise(page, "X"))) {
                        flag = 1;
                        // You Won :)
                    }
                    if ((gameFinishedHorizental(page, "O")) || (gameFinishedVertical(page, "O")) || (gameFinishedCornerwise(page, "O"))) {
                        flag = 2;
                        // Computer Won :)
                    }
                }
            }
        }
        return flag;
    }


    /**
     * Generate 3 random number.
     * There are 3 integers that will be initialized randomly.
     * Each integer is the number of the square that is initialized by #.
     * @param page
     */
    private static void chooseRandomForLock(String[][] page) {
        int lock1;
        int lock2;
        int lock3;
        Random randForLock = new Random();
        lock1 = randForLock.nextInt(15) + 1;
        placeMove(page, Integer.toString(lock1), "#");
        while (true){
            lock2 = randForLock.nextInt(15) + 1;
            if(avaliableSpace(page, Integer.toString(lock2))){
                break;
            }
        }
        placeMove(page, Integer.toString(lock2), "#");

        while (true){
            lock3 = randForLock.nextInt(15) + 1;
            if(avaliableSpace(page, Integer.toString(lock3))){
                break;
            }
        }
        placeMove(page, Integer.toString(lock3), "#");
    }


    /**
     * Check every row to see if the 3 squares is initialized by 'X' or 'O' continuously
     * @param page used to check all the elements in matrix
     * @param symbol can be equal to 'X' or 'O'
     * @return boolean value
     */
    private static boolean gameFinishedHorizental (String[][]page, String symbol){
        for (int i = 0; i < 4; i++) {
            int countHorizental = 0;
            for (int j = 0; j < 4; j++) {
                if (page[i][j] == symbol) {
                    countHorizental++;
                }
            }
                if (((page[i][1] == symbol) && (page[i][2] == symbol)) && countHorizental >= 3) {
                    return true;
                }
        }
        return false;
    }


    /**
     * Check every column to see if the 3 squares is initialized by 'X' or 'O' continuously
     * @param page used to check all the elements in matrix
     * @param symbol can be equal to 'X' or 'O'
     * @return boolean value
     */
        private static boolean gameFinishedVertical (String[][]page, String symbol){
            for (int j = 0; j < 4; j++) {
                int countVertical = 0;
                for (int i = 0; i < 4; i++) {
                    if (page[i][j] == symbol) {
                        countVertical++;
                    }
                }
                    if (((page[1][j] == symbol) && (page[2][j] == symbol)) && countVertical >= 3) {
                        return true;
                    }
            }
            return false;
        }


    /**
     * Check every diameter to see if the 3 squares is initialized by 'X' or 'O' continuously.
     * @param page used to check all the elements in matrix
     * @param symbol symbol can be equal to 'X' or 'O'
     * @return boolean value
     */
        private static boolean gameFinishedCornerwise(String[][]page, String symbol) {

            if ((page[0][1] == symbol) && (page[1][2] == symbol) && (page[2][3] == symbol) ||
                    (page[0][0] == symbol) && (page[1][1] == symbol) && (page[2][2] == symbol) ||
                    (page[1][1] == symbol) && (page[2][2] == symbol) && (page[3][3] == symbol) ||
                    (page[1][0] == symbol) && (page[2][1] == symbol) && (page[3][2] == symbol) ||
                    (page[0][2] == symbol) && (page[1][1] == symbol) && (page[2][0] == symbol) ||
                    (page[0][3] == symbol) && (page[1][2] == symbol) && (page[2][1] == symbol) ||
                    (page[1][2] == symbol) && (page[2][1] == symbol) && (page[3][0] == symbol) ||
                    (page[1][3] == symbol) && (page[2][2] == symbol) && (page[3][1] == symbol)) {
                return true;
            } else {
                return false;
            }
        }


    /**
     * AI player will choose randomly numbers(1 - 16).
     * Every chose will be checked before allocation to its square.
     * @param page used to check all the elements in matrix
     */
    private static void computerTurn(String[][] page) {
        Random rand = new Random();
        int AI_player;
        while (true){
            AI_player = rand.nextInt(15) + 1;
            if(avaliableSpace(page, Integer.toString(AI_player))){
                break;
            }
        }System.out.println("Computer chose " + AI_player);
        placeMove(page, Integer.toString(AI_player), "O");
    }


    /**
     * All the elements are initialized by ' ' .
     * If any of the elements initialize except ' ' , the function wouldn't allow to allocate any values else.
     * @param page used to check all the elements in matrix
     * @param location the number of each square witch has cast to string.
     * @return boolean value
     */
    private static boolean avaliableSpace(String[][] page , String location){
        switch (location) {
            case "1":
                return (page[0][0] == " ");
            case "2":
                return (page[0][1] == " ");
            case "3":
                return (page[0][2] == " ");
            case "4":
                return (page[0][3] == " ");
            case "5":
                return (page[1][0] == " ");
            case "6":
                return (page[1][1] == " ");
            case "7":
                return (page[1][2] == " ");
            case "8":
                return (page[1][3] == " ");
            case "9":
                return (page[2][0] == " ");
            case "10":
                return (page[2][1] == " ");
            case "11":
                return (page[2][2] == " ");
            case "12":
                return (page[2][3] == " ");
            case "13":
                return (page[3][0] == " ");
            case "14":
                return (page[3][1] == " ");
            case "15":
                return (page[3][2] == " ");
            case "16":
                return (page[3][3] == " ");
            default:
                return false;
        }
    }


    /**
     * You as a human should enter the number.
     * Note that if the square you want to select, has been initialized before, you should enter another number.
     * @param page Used to check whether the selected square is empty or not.
     * @param input You as a player should enter a number.
     */

    private static void playerTurn(String[][] page, Scanner input) {
        String userInput;
        while (true) {
            System.out.println("Where would you like to play? (1-16)");
            userInput = input.nextLine();
            if (avaliableSpace(page, userInput)) {
                break;
            }
            else {
                System.out.println(userInput + " is not a valid location.");
            }
        }
        placeMove(page, userInput, "X");
    }

    /**
     * Second player as a human should enter the number.
     * Note that if the square you want to select, has been initialized before, you should enter another number.
     * @param page Used to check whether the selected square is empty or not.
     * @param input Second player as a human should enter a number.
     */
    private static void playerTwoTurn(String[][] page, Scanner input) {
        String userInput;
        while (true) {
            System.out.println("Player2: please choose a number(1-16)");
            userInput = input.nextLine();
            if (avaliableSpace(page, userInput)) {
                break;
            }
            else {
                System.out.println(userInput + " is not a valid location.");
            }
        }
        placeMove(page, userInput, "O");
    }

    /**
     * Squares has been numbered from 1 to 16.
     * The selected square will be initialized by the value of symbol.
     * @param page Used to check all the elements of matrix.
     * @param location the number of each square.
     * @param symbol It can be "X" or "O".
     */
    private static void placeMove(String[][] page, String location, String symbol) {
        switch (location) {

            case "1":
                page[0][0] = symbol;
                break;
            case "2":
                page[0][1] = symbol;
                break;
            case "3":
                page[0][2] = symbol;
                break;
            case "4":
                page[0][3] = symbol;
                break;
            case "5":
                page[1][0] = symbol;
                break;
            case "6":
                page[1][1] = symbol;
                break;
            case "7":
                page[1][2] = symbol;
                break;
            case "8":
                page[1][3] = symbol;
                break;
            case "9":
                page[2][0] = symbol;
                break;
            case "10":
                page[2][1] = symbol;
                break;
            case "11":
                page[2][2] = symbol;
                break;
            case "12":
                page[2][3] = symbol;
                break;
            case "13":
                page[3][0] = symbol;
                break;
            case "14":
                page[3][1] = symbol;
                break;
            case "15":
                page[3][2] = symbol;
                break;
            case "16":
                page[3][3] = symbol;
                break;
            default:
                System.out.println("Wrong input!!!");
                break;
        }
    }

    /**
     * This method print the board of the game.
     * @param page
     */
    private static void printPage(String[][] page) {
        System.out.println(" -----------------");
        System.out.println(" | " + page[0][0] + " | " + page[0][1] + " | " + page[0][2] + " | " + page[0][3] + " | ");
        System.out.println(" |---------------|");

        System.out.println(" | " + page[1][0] + " | " + page[1][1] + " | " + page[1][2] + " | " + page[1][3] + " | ");
        System.out.println(" |---------------|");

        System.out.println(" | " + page[2][0] + " | " + page[2][1] + " | " + page[2][2] + " | " + page[2][3] + " | ");
        System.out.println(" |---------------|");

        System.out.println(" | " + page[3][0] + " | " + page[3][1] + " | " + page[3][2] + " | " + page[3][3] + " | ");
        System.out.println(" -----------------");
    }
}