package game;

public class NumberFormatter {
    private static final String[] abvString = { "", "k", "M", "B", "T", "Q", "Qt", "Sx" }; // units to append
    private static final double base = Math.log(1000); // used to calculate log base 1000
    
    public static String format(double num) {
        // base cases
        if (num == 0) return "0";
        if (num < 0) return "-" + format(-num);
        if (num < 1) return num + "";

        int index = (int) Math.floor(Math.log(num) / base); // log base 1000. get index of abverviation
        if (index > abvString.length) return "" + num;

        if (index == 0 && num % 1 == 0) { // if its less than 1000, don't show decimal place
            return "" + (long) num;
        }

        return Math.floor(
            (
                (
                    num / (
                        Math.pow(1000, index - 1)
                    )
                ) / 1000
            ) * 10
        ) / 10 + abvString[index]; // show one decimal place and add abreviation

    }
}
