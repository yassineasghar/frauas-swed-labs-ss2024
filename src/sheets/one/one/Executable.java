package sheets.one.one;

public class Executable {
    static void main() {
        int number = 100000000;
        System.out.println("Initial Number is : " + number);
        if (easyFix(number)) {
            ulamFunction(number);
        }
    }

    public static void ulamFunction(int number) {
        while (number != 1) {
            System.out.print(number + " -> ");
            if (number % 2 == 0) {
                number /= 2;
            } else {
                number = 3 * number + 1;
            }
        }
        System.out.println(number);
    }

    public static boolean easyFix(int number) {
        return number <= 1000000;
    }
}