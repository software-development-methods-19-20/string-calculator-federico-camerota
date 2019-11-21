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

    public static int add(String numbers) throws StringFormatException {

        if (numbers.isEmpty())
            return 0;
        else {

            IntStream numStream = loPassFilter(checkNegatives(parseInputString(numbers)), 1000);

            return numStream.sum();

        }

    }

    private static IntStream checkNegatives(IntStream numbers) throws StringFormatException{

       int[] numArray = numbers.toArray();
       List<Integer> negatives = new LinkedList<Integer>();

       for (int x : numArray)
           if (x < 0)
               negatives.add(x);

       if (negatives.size() > 0)
           throw new StringFormatException(negatives);

       return IntStream.of(numArray);

    }

    private static IntStream parseInputString(String inputString){

        String separator = getDelimiterRegex(inputString);
        if (hasDelimiterLine(inputString)) {

            int endDelimiterLine = delimiterEndIndex(inputString);
            inputString = inputString.substring(endDelimiterLine + 1);
        }


        Pattern splitPattern = Pattern.compile(separator);

        return splitPattern.splitAsStream(inputString.trim()).mapToInt(Integer::valueOf);
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

    private static int delimiterEndIndex(String inputString){

        if (hasDelimiterLine(inputString))
            return inputString.indexOf(delimiterEscapeEnd);

        return 0;
    }

    private static boolean hasDelimiterLine(String inputString) {

        return inputString.startsWith(delimiterEscapeStart);
    }

    private static String extractDelimiters(String delimitersString){

        Matcher separatorMatcher = Pattern.compile("\\[([^\\]]+)\\]").matcher(delimitersString); //could be improved to remove dependency from format used to specify delimiters

        StringBuilder separatorBuilder = new StringBuilder();
        while (separatorMatcher.find())
            separatorBuilder.append(separatorMatcher.group(1) + "|");

        return separatorBuilder.toString().substring(0, separatorBuilder.length() - 1);

    }

    private static IntStream loPassFilter(IntStream numbers, int threshold){

        return numbers.filter(x -> x <= threshold);
    }


}
