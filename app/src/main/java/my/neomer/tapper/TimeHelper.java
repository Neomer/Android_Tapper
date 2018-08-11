package my.neomer.tapper;

public class TimeHelper {

    static String StringTimeFromMilliseconds(long milliseconds) {
        int min = (int)(milliseconds / 60000);
        int sec = (int)(milliseconds % 60000 * 0.001);
        int msec = (int)(milliseconds % 100);

        return String.format("%02d:%02d.%02d", min, sec, msec);
    }

}
