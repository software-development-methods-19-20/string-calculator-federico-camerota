package production;

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

            return numStream.sum();
        }

    }
}
