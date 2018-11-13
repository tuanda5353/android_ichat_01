package framgia.com.ichat.utils;

public class DateTime {
    public static final int LOCATION = 4;
    public static final String SPACE = " ";

    public static String getTime(String time) {
        String[] times = time.split(SPACE);
        return times[LOCATION];
    }
}
