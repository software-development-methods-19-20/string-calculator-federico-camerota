package production;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class StringCalculator  {

    public static final String defaultDelimiter = "[,\\n]";

    public static final String delimiterEscapeStart = "//";
    public static final String delimiterEscapeEnd = "\n";

    private static boolean hasDelimiterLine(String inputString) {

        return inputString.startsWith(delimiterEscapeStart);
    }

    private static int delimiterEndIndex(String inputString){

        if (hasDelimiterLine(inputString))
            return inputString.indexOf(delimiterEscapeEnd);

       return 0;
    }

    private static String extractDelimiters(String delimitersString){

        Matcher separatorMatcher = Pattern.compile("\\[([^\\]]+)\\]").matcher(delimitersString); //could be improved to remove dependency from format used to specify delimiters

        StringBuilder separatorBuilder = new StringBuilder();
        while (separatorMatcher.find())
            separatorBuilder.append(separatorMatcher.group(1) + "|");

        return separatorBuilder.toString().substring(0, separatorBuilder.length() - 1);

    }

    private static String getDelimiterRegex(String inputString){

        if (hasDelimiterLine(inputString)) {

            String separator = inputString.substring(delimiterEscapeStart.length(),delimiterEndIndex(inputString));

            if(separator.startsWith("[") && separator.endsWith("]")) //could be improved to remove dependency from format used to specify delimiters
                separator = extractDelimiters(separator);

            return separator;
        }

        return defaultDelimiter;
    }

    private static IntStream parseInputString(String inputString){

        String separator = getDelimiterRegex(inputString);
        if (hasDelimiterLine(inputString)) {

            int firstNewLine = inputString.indexOf('\n');
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
