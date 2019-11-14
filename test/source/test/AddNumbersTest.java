package test;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import production.StringCalculator;
import production.StringFormatException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AddNumbersTest {


    @Test
    void emptyString() {

        //assertEquals(0, new StringCalculator().add(""));
        //we can do the same with hamcrest

        assertThat(StringCalculator.add(""), is(0));
    }

    @Test
    void oneNumber() {

        assertThat(StringCalculator.add("1"), is(1));
    }

    @Test
    void twoNumbers() {

        assertThat(StringCalculator.add("1,2"), is(3));
    }

    @Test
    void manyNumbers() {

        assertThat(StringCalculator.add("1,2,3,4,5"), is(15));
    }

    @Test
    void separatedByNewLines() {

        assertThat(StringCalculator.add("1\n2,3"), is(6));
    }

    @Test
    void differentDelimiters() {

        assertThat(StringCalculator.add("//;\n1;2"), is(3));
    }

    @Test
    void negativesNotAllowed() {

        Throwable thrown = assertThrows(StringFormatException.class, () -> {StringCalculator.add("1,-3,2,-5,5");});
        assertEquals(thrown.getMessage().equals("Negatives not allowed: -3 -5"), true);
    }

    @Test
    void overOneThousand() {

        assertThat(StringCalculator.add("1001,2"), is(2));
    }

    @Test
    void anyLengthDelimiter() {

        assertThat(StringCalculator.add("//[:::]\n1:::2:::3"), is(6));
    }

    @Test
    void multipleDelimiters() {

        assertThat(StringCalculator.add("//[:][°]\n1:2°3"), is(6));
    }
}
