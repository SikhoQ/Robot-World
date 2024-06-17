package za.co.wethinkcode.robotworlds;

public class StyledOutputExample {
    public static void main(String[] args) {
        System.out.println("\u001B[31mRed Text\u001B[0m");
        System.out.println("\u001B[32mGreen Text\u001B[0m");
        System.out.println("\u001B[1mBold Text\u001B[0m");
        System.out.println("\u001B[3mItalic Text\u001B[0m");
        System.out.println("\u001B[4mUnderlined Text\u001B[0m");

        // Animation example (simple bouncing text)
        for (int i = 0; i < 10; i++) {
            System.out.print("\r");
            System.out.print("Loading: " + (i % 2 == 0 ? "..." : ""));
            try {
                Thread.sleep(500); // Wait for 500 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
