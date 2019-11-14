package production;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class StringCalculator  {
    public static int add(String numbers) throws StringFormatException {

        if (numbers.isEmpty())
            return 0;
        else {

            String separator = "[,\\n]";
            if (numbers.startsWith("//")) {
                int firstNewLine = numbers.indexOf('\n');
                separator = numbers.substring(2,firstNewLine);
                numbers = numbers.substring(firstNewLine + 1);
            }
            Pattern splitPattern = Pattern.compile(separator);
            IntStream numStream = splitPattern.splitAsStream(numbers.trim()).mapToInt(Integer::valueOf);

            List<Integer> negatives = new LinkedList<Integer>();

            int sum = numStream.map(x -> {if (x < 0) negatives.add(x); return x;}).sum();

            if (negatives.size() > 0){

                StringBuilder exceptionText = new StringBuilder("Negatives not allowed: ");
                for ( Integer negativeNumber : negatives) {
                    exceptionText.append(negativeNumber).append(" ");
                }
                throw new StringFormatException(exceptionText.toString().trim());
            }
            return sum;
        }

    }
}
