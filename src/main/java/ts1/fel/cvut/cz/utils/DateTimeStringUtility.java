package ts1.fel.cvut.cz.utils;

public class DateTimeStringUtility {
    public static boolean timeIsGreaterOrEquals(String t1, String t2) {
        String[] times1 = t1.split(":");
        String[] times2 = t2.split(":");

        int h1 = Integer.parseInt(times1[0]);
        int h2 = Integer.parseInt(times2[0]);
        if (h1 == h2) {
            int m1 = Integer.parseInt(times1[1]);
            int m2 = Integer.parseInt(times2[1]);
            return m1 >= m2;
        }
        return h1 >= h2;
    }

    public static String normalizeDate(String date) {
        StringBuilder normDate = new StringBuilder();
        for (String d: date.split("\\.")) {
            if (d.length() == 1) {
                d = "0" + d;
            }
            normDate.append(d).append(".");
        }
        return normDate.toString();
    }

    public static String[] splitTimeAndDate(String data) {
        data = data.split(" ")[0];

        StringBuilder time = new StringBuilder();
        StringBuilder date = new StringBuilder();

        String [] tmp = data.split(":");
        time.append(tmp[0]).append(':').append(tmp[1], 0, 2);
        date.append(tmp[1].substring(2));

        return new String[]{time.toString(), date.toString()};
    }
}
