package production;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class StringCalculator  {

    private static IntStream parseInputString(String inputString){

            String separator = "[,\\n]";
            if (inputString.startsWith("//")) {

                int firstNewLine = inputString.indexOf('\n');

                separator = inputString.substring(2,firstNewLine);
                if(separator.startsWith("[") && separator.endsWith("]"))
                    separator = separator.substring(1,separator.length() - 1);

                inputString = inputString.substring(firstNewLine + 1);
            }

            Pattern splitPattern = Pattern.compile(separator);

        return splitPattern.splitAsStream(inputString.trim()).mapToInt(Integer::valueOf);
    }

    private static String negativesExceptionMessage(List<Integer> negatives){

        StringBuilder exceptionText = new StringBuilder("Negatives not allowed: ");
        for ( Integer negativeNumber : negatives) {
            exceptionText.append(negativeNumber).append(" ");
        }

        return exceptionText.toString().trim();
    }

    private static IntPredicate loPassFilterPredicate(int threshold){
        return x -> x <= threshold;
    }

    public static int add(String numbers) throws StringFormatException {

        if (numbers.isEmpty())
            return 0;
        else {

            IntStream numStream = parseInputString(numbers).filter(loPassFilterPredicate(1000));

            List<Integer> negatives = new LinkedList<Integer>();

            int sum = numStream.map(x -> {if (x < 0) negatives.add(x); return x;}).sum();

            if (negatives.size() > 0)
                throw new StringFormatException(negativesExceptionMessage(negatives));

            return sum;
        }

    }
}
