# String Calculator assignment

This assignment is a variant of the TDD Kata by Roy Osherove ([original exercise description](http://osherove.com/tdd-kata-1/)).

## Before you start

* Try not to read ahead.
* Do one task at a time. the trick is to learn to work incrementally.
* Make sure you only test for correct inputs. There is no need to test for invalid inputs for this kata.

## Kata

1. Create a simple String calculator with a method `int add(String numbers)`.
 * The method can take 0, 1 or 2 numbers, and will return their sum (for an empty string it will return 0) for example `“”` or `“1”` or `“1,2”`.
 * Start with the simplest test case of an empty string and move to 1 and two numbers.
 * Remember to solve things as simply as possible so that you force yourself to write tests you did not think about.
 * Remember to refactor after each passing test.
2. Allow the `add` method to handle an unknown amount of numbers.
3. Allow the `add` method to handle new lines between numbers (instead of commas).
 * The following input is **ok**: `“1\n2,3”` (will equal 6).
 * The following input is **not ok**: `“1,\n”` (not need to prove it - just clarifying).
4. Support different delimiters.
 * To change a delimiter, the beginning of the string will contain a separate line that looks like this: `“//[delimiter]\n[numbers…]”`. For example, `“//;\n1;2”` should return 3 where the default delimiter is `;`.
 * The first line is optional. All existing scenarios should still be supported.
5. Calling `add` with a negative number will throw an exception `“Negatives not allowed”` - and the negative that was passed. If there are multiple negatives, show all of them in the exception message.
6. Numbers bigger than 1000 should be ignored, so that 2 + 1001 = 2.
7. Delimiters can be of any length with the following format: `“//[delimiter]\n”`. For example, `“//[:::]\n1:::2:::3”` should return 6.
8. Allow multiple delimiters like this: `“//[delim1][delim2]\n”`. For example, `“//[:][°]\n1:2°3”` should return 6.
9. Make sure you can also handle multiple delimiters with length longer than one char.
