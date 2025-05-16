package game;

public class NumberFormatter {
    private static final String[] abvString = { "", "k", "M", "B", "T", "Q", "Qt", "Sx" };
    private static final double base = Math.log(1000);
    
    public static String format(double num) {
        if (num == 0) return "0";
        if (num < 0) return "-" + format(-num);
        if (num < 1) return num + "";

        int index = (int) Math.floor(Math.log(num) / base);

        if (index == 0 && num % 1 == 0) {
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
        ) / 10 + abvString[index];

    }
}
