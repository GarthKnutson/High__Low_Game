import java.util.Scanner;
import java.util.Random;

/**
 * This class is for running the game
 * @author Garth Knutson
 */
public class High_Low_Game {
    /**
     * This is the main game function
     * @param args THis is for command line args
     */
    public static void main(String[] args) {
        boolean gameCondition = false;
        final int UPPERBOUND = 101;
        int totalTurnCount = 0;
        int gameCount = 0;

        Scanner scan = new Scanner(System.in);
        Random rand = new Random(System.currentTimeMillis());

        String playerName = startGame();

        System.out.print("Ready to begin "+playerName+ "?\n");
        System.out.print("Type y or yes to begin or anything else to put off starting: \n");
        while(!gameCondition) {
            String readyInput = scan.nextLine();
            readyInput = readyInput.toLowerCase();
            if(readyInput.equals("y") || readyInput.equals("yes")){
                gameCondition = true;
                System.out.print("And awaaaayyy we go\n");
            }else {
                System.out.print("Oh take your time starting\n");
                System.out.print("Enter an y or yes to begin.\n");
            }
        }
        while(gameCondition){
            int guessNum = 0;
            guessNum = rand.nextInt(UPPERBOUND);
            totalTurnCount += gameIteration(guessNum, playerName);
            gameCount += 1;
            System.out.print("So far you have played " + gameCount + " games.\n");
            System.out.print("Total turn count: " + totalTurnCount + "\n");
            System.out.print("Play again y/n?\n");
            String playAgain = scan.nextLine();
            playAgain = playAgain.toLowerCase();
            if(playAgain.equals("y") || playAgain.equals("yes")){
                gameCondition = true;
            } else {
                gameCondition = false;
            }
        }

        endingOutput(totalTurnCount,gameCount,UPPERBOUND,playerName);
    }

    /**
     * This is an entry method that explains the rules of the game, asks for the player's name,
     * and returns it as a String
     * @return String playerName - the name of the player or "Player 1" for no entry
     */
    public static String startGame() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name:");
        String playerName = scan.nextLine();
        if(playerName.isEmpty()) {
            playerName = "Player 1";
            System.out.println("Somehow you failed to type your own name in...");
            System.out.print("I'll just call you Player 1 :)\n\n");
        }
        System.out.print("WELCOME to the High/Low Game " + playerName + "!\n\n");
        System.out.print("The objective of this game is to determine the randomly generated number between 0 & 100\n");
        System.out.println("When prompted for a guess, enter an integer and NOTHING else.");
        System.out.println("Upon your guess, the game will return high if your guess was higher than the number,");
        System.out.println("and low if your guess was lower than the number.");
        System.out.println("The goal is to eventually figure out the exact number... shouldn't be too hard right?");
        System.out.print(".\n.\n.\nGOOD LUCK!\n");
        return playerName;
    }

    /**
     * This runs one full game, starting from guess 0 to the final winning guess.
     * @param gameNum This is the secret number that the player is trying to guess
     * @param playerName This is the name of the player, returned by the startGame method.
     * @return This returns turnCount, the number of turns taken to successfully guess the number.
     */
    public static int gameIteration(int gameNum, String playerName) {
        boolean gameOver = false;
        Scanner scan = new Scanner(System.in);
        int guessNum = 0;
        int turnCount = 0;
        int prevGuess = 0;

        while(!gameOver){
            System.out.printf("Ok %s, enter a guess: \n", playerName);
            if(scan.hasNextInt()){
                guessNum = scan.nextInt();
                if(turnCount == 0) {
                    prevGuess = guessNum;
                }
                gameOver = turnChecker(gameNum, guessNum, prevGuess, turnCount);
                prevGuess = guessNum;
                turnCount += 1;
            } else {
                System.out.print("Enter a NUMBER not whatever you thought would be appropriate.\n");
                scan.nextLine();
            }

        }
        System.out.print("It took you " + turnCount + " guesses to guess the number " + gameNum + "!\n");
        return turnCount;
    }

    /**
     * This method checks the player's guess and outputs a unique string based on the results of the comparison
     * @param gameNum This is the secret number the player is trying to guess
     * @param guessNum This is the player's current guess
     * @param prevGuess This is the player's previous guess
     * @param turnCount This is the current number of turns taken by the player to guess
     * @return This method returns a boolean value based on the results of the comparison,
     *          it returns false for every instance unless the guessNum matches the gameNum
     */
    public static boolean turnChecker(int gameNum, int guessNum, int prevGuess, int turnCount) {
        boolean endChecker = false;
        if (guessNum > 0 && guessNum < 100) {
            if(guessNum < prevGuess && prevGuess < gameNum){
                System.out.println("You need to guess higher NOT lower...");
            } else if(guessNum > prevGuess && prevGuess > gameNum){
                System.out.println("You need to guess lower NOT higher...");
            }
            else if (guessNum > gameNum) {
                System.out.println("Your guess is too high.");
            } else if (guessNum < gameNum) {
                System.out.println("Your guess is too low.");
            } else {
                System.out.print("Wow! You guessed the number right.\n");
                endChecker = true;
            }
        } else {
            System.out.print("Well, you entered a number, which is nice!\n");
            System.out.print("Next time try and enter one between 0 and 100...\n");
            System.out.print("(This still counts as a guess)\n");
        }
        return endChecker;
    }

    /**
     * This method outputs a unique message based on the overall success of the player during the games he played
     * @param totalTurns This is the total sum of the turns taken over all the games played.
     * @param totalGames This is the number of games the player played
     * @param largestNum This is the largest possible secret number, to be used to calculate the optimal guess number
     * @param playerName This is the player's name, to be used for a final goodbye message
     */
    public static void endingOutput(int totalTurns, int totalGames, int largestNum, String playerName) {
        double turnAvg = totalTurns/totalGames;
        double optimalGuesses = Math.log(largestNum - 1) / Math.log(2);
        if(turnAvg < optimalGuesses - 2){
            System.out.println("Wow, you really rocked it!!");
            System.out.printf("An optimal game would have taken %.1f guesses and you averaged\n", optimalGuesses);
            System.out.printf("%.1f guesses over your %d games.\n", turnAvg, totalGames);
        } else if(turnAvg < optimalGuesses){
            System.out.println("Great job! You definitely know what you're doing.");
            System.out.printf("An optimal game would have taken %.1f guesses and you averaged\n", optimalGuesses);
            System.out.printf("%.1f guesses over your %d games.\n", turnAvg, totalGames);
        } else if(turnAvg > optimalGuesses + 2) {
            System.out.println("Jeez, the goal of the game is to find the number in as few guesses as possible.");
            System.out.println("You know that right?");
            System.out.printf("An optimal game would have taken %.1f guesses and you averaged\n", optimalGuesses);
            System.out.printf("%.1f guesses over your %d games.\n", turnAvg, totalGames);
        } else {
            System.out.println("It's clear you understand how to play the game, now work on your luck a bit.");
            System.out.printf("An optimal game would have taken %.1f guesses and you averaged\n", optimalGuesses);
            System.out.printf("%.1f guesses over your %d games.\n", turnAvg, totalGames);
        }
        System.out.printf("Thanks for playing %s, see you next time!\n", playerName);
    }


}

