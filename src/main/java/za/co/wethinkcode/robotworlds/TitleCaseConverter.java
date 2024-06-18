package za.co.wethinkcode.robotworlds;

/**
 * A utility class for converting strings to title case.
 */
public class TitleCaseConverter {
    /**
     * Converts the given string to title case.
     * @param input The input string to be converted.
     * @return The input string converted to title case.
     */
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
