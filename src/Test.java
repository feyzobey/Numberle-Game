import java.io.IOException;
import java.util.Scanner;

public class Test {
    private static Number number;
    private static Scanner input = new Scanner(System.in);

    public static void clrscr() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    public static int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static void showSecretNumber() {
        var secretNumber = "";
        for (int args : number.getListOfRandom()) {
            secretNumber += "" + args;
        }
        System.out.println("\nThe number was " + secretNumber);
    }

    public static void main(String[] args) {
        var answer = ' ';
        var breakLoop = false;
        var howManyDigits = "";
        var howManyDigitsConvertToInt = 0;
        do {
            clrscr();
            do {
                System.out.println(
                        "Choose one of these(enter 3 or 4 or 5):\n3 digit number\n4 digit number\n5 digit number");
                howManyDigits = input.next();
                howManyDigitsConvertToInt = tryParseInt(howManyDigits, howManyDigitsConvertToInt);

                if (howManyDigits.length() > 1 || howManyDigitsConvertToInt > 5 || howManyDigitsConvertToInt < 3 ) {
                    clrscr();
                    System.out.println("Enter an appropriate answer");
                } else {
                    number = new Number(howManyDigitsConvertToInt);
                    clrscr();
                    System.out.println("** The machine has picked a number which you choosed ** Try to find it **");
                    System.out.println("If you wanna give up and see the secret number write 'giveup'.");
                    System.out.println("******************************************************************************** ");
                    break;
                }

            } while (true);

            Game(howManyDigitsConvertToInt);

            breakLoop = false;
            System.out.print("Do you wanna continue (y/n): ");
            answer = input.next().charAt(0);
            while (answer != 'n' && answer != 'y') {
                System.out.print("Enter an appropriate answer!\nDo you wanna continue (y/n):");
                answer = input.next().charAt(0);
            }
            if (answer == 'y') {
                breakLoop = true;
                clrscr();
            }

        } while (breakLoop);
    }

    public static void Game(int howManyDigitsToInt) {
        // System.out.println(number.getListOfRandom().toString());
        var guessInput = "";
        var remainingAttempt = 10;
        var isDigit = true;
        var breakLoop = false;

        do {
            // adjustment of guessInput
            do {

                System.out.print("Guess it: ");
                guessInput = input.next();

                isDigit = true;
                breakLoop = false;
                if (guessInput.equals("giveup")) {
                    number.getInputOfUser(guessInput);
                    showSecretNumber();
                    return;
                }
                for (int i = 0; i < guessInput.length(); i++) {
                    if (!Character.isDigit(guessInput.charAt(i))) {
                        isDigit = false;
                        break;
                    }
                }
                if (!isDigit) {
                    System.out.println("you must enter integer number");
                    breakLoop = true;
                }
                if (guessInput.length() != howManyDigitsToInt) {
                    System.out.println("You must enter " + howManyDigitsToInt + " digit number");
                    breakLoop = true;
                }

                var guessInputSplitted = guessInput.toCharArray();

                for (var i = 0; i < guessInputSplitted.length; i++) {
                    if (breakLoop) {
                        break;
                    }
                    var count = 0;

                    for (var j = 0; j < guessInputSplitted.length; j++) {
                        if (guessInputSplitted[i] == guessInputSplitted[j]) {
                            count++;
                            if (count > 1) {
                                System.out.println("Don't repeat the digits");
                                breakLoop = true;
                                break;
                            }
                        }
                    }
                }
            } while (breakLoop);

            remainingAttempt--;
            number.getInputOfUser(guessInput);

            if (number.isGameFinished()) {
                System.out.println("Congrats!! you found it :)");
                break;
            }

            if (remainingAttempt == 0) {
                System.out.println("No attempts left!!");
                showSecretNumber();
                break;
            }

            System.out.println(number.toString());
            System.out.println(remainingAttempt + " attempts left\n****************\n");

        } while (true);
    }
}