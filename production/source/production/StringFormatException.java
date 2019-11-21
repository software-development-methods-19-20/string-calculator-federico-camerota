package production;

import java.util.List;

public class StringFormatException extends RuntimeException{
    public StringFormatException(String message) {
        super(message);
    }

    public StringFormatException(List<Integer> negatives){
        super(negativesExceptionMessage(negatives));
    }

    private static String negativesExceptionMessage(List<Integer> negatives){

        StringBuilder exceptionText = new StringBuilder("Negatives not allowed: ");
        for ( Integer negativeNumber : negatives) {
            exceptionText.append(negativeNumber).append(" ");
        }

        return exceptionText.toString().trim();
    }

}
