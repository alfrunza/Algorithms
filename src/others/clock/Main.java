package others.clock;

/*
    Given a digital time input (ex. "3:45"), find the angle between the hour and the minute hands on an analogue clock that shows the given time
 */

public class Main {
    public static void main(String[] args) {
        String digitalInput = "3:15";
        Clock clock = new Clock(digitalInput);

        int hour = clock.splitHourAndMinute(digitalInput)[0], minute = clock.splitHourAndMinute(digitalInput)[1];

        System.out.println(clock.findAngle(hour, minute));
    }
}
