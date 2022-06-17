import java.util.ArrayList;

public class Number {
    private String input;
    private ArrayList<Integer> listOfRandom;

    public Number(int howManyDigits) {
        creatingListOfRandom(howManyDigits);
    }

    public void getInputOfUser(String input) {
        this.input = input;
    }

    public ArrayList<Integer> getListOfRandom() {
        return listOfRandom;
    }

    public boolean isGameFinished() {
        if (checkDigitCorrect(input)[0] == listOfRandom.size()) {
            return true;
        }
        return false;
    }

    public int randomNumber() {
        return (int) (Math.random() * 10);
    }

    private void checkExistingRandomNumber(int howManyDigits) {
        var rnd = randomNumber();
        listOfRandom.add(rnd);

        for (int i = 0; i < howManyDigits - 1; i++) {
            
            do {
                rnd = randomNumber();
            } while (listOfRandom.contains(rnd));

            listOfRandom.add(rnd);
        }
    }

    public ArrayList<Integer> creatingListOfRandom(int howManyDigits) {
        listOfRandom = new ArrayList<>();

        switch (howManyDigits) {
            case 3:
            case 4:
            case 5:
                checkExistingRandomNumber(howManyDigits);
                break;
            default:
                System.out.println("Invalid input!");
                return null;
        }
        return listOfRandom;
    }

    public int[] checkDigitCorrect(String input) {
        var inputArray = input.split("");
        // 0. index is true location count
        // 1. index is false location count
        var outCodes = new int[] { 0, 0 };
        for (var i = 0; i < input.length(); i++) {
            for (var j = 0; j < listOfRandom.size(); j++) {
                if (listOfRandom.get(j).toString().equals(inputArray[i])) {
                    if (listOfRandom.get(i).toString().equals(inputArray[i])) {
                        outCodes[0]++;
                        break;
                    } 
                    outCodes[1]++;
                    break;
                }
            }
        }
        return outCodes;
    }

    @Override
    public String toString() {
        if (checkDigitCorrect(input)[0] == 0 && checkDigitCorrect(input)[1] == 0) {
            return "0";
        }
        if (checkDigitCorrect(input)[1] == 0) {
            return "+" + checkDigitCorrect(input)[0];
        }
        if (checkDigitCorrect(input)[0] == 0) {
            return "-" + checkDigitCorrect(input)[1];
        }
        return "+" + checkDigitCorrect(input)[0] + " and -" + checkDigitCorrect(input)[1];
    }
}