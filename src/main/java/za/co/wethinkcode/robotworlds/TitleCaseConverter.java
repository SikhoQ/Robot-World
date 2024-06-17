package za.co.wethinkcode.robotworlds;

public class TitleCaseConverter {
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

    public static void main(String[] args) {
        String text = "this is a sample string";
        String titleCased = toTitleCase(text);
        System.out.println(titleCased); // Output: This Is A Sample String
    }
}
