package production;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringCalculator  {

    public static final String DEFAULT_DELIMITER_REGEX = "[,\\n]";

    public static final String DELIMITER_ESCAPE_START = "//";
    public static final String DELIMITER_ESCAPE_END = "\n";

    public static int add(String numbers) throws StringFormatException {

        if (numbers.isEmpty())
            return 0;
        else {

            IntStream numStream = loPassFilter(checkNegatives(parse(numbers)), 1000);

            return numStream.sum();

        }

    }

    private static IntStream checkNegatives(IntStream numbers) throws StringFormatException{

       int[] numArray = numbers.toArray();

       List<Integer> negatives = IntStream.of(numArray).filter(x -> x < 0).boxed().collect(Collectors.toList());

       if (negatives.size() > 0)
           throw new StringFormatException(negatives);

       return IntStream.of(numArray);

    }

    private static IntStream parse(String inputString){

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

            String separator = inputString.substring(DELIMITER_ESCAPE_START.length(),delimiterEndIndex(inputString));

            if(separator.startsWith("[") && separator.endsWith("]")) //could be improved to remove dependency from format used to specify delimiters
                separator = buildCustomDelimiterRegEx(separator);

            return separator;
        }

        return DEFAULT_DELIMITER_REGEX;
    }

    private static int delimiterEndIndex(String inputString){

        if (hasDelimiterLine(inputString))
            return inputString.indexOf(DELIMITER_ESCAPE_END);

        return 0;
    }

    private static boolean hasDelimiterLine(String inputString) {

        return inputString.startsWith(DELIMITER_ESCAPE_START);
    }

    private static String buildCustomDelimiterRegEx(String delimitersString){

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
