package others.clock;

import static java.lang.Math.abs;

public class Clock {

    String digitalInput;
    public Clock(String digitalInput) {
        this.digitalInput = digitalInput;
    }

    public int[] splitHourAndMinute(String digitalInput){
        int[] hourAndMinute = new int[2];
        String[] splitInput = digitalInput.split(":");
        for(int i = 0; i < splitInput.length; i++){
            hourAndMinute[i] = Integer.valueOf(splitInput[i]);
        }
        return hourAndMinute;
    }

    public float findAngle(int hour, int minute){
        int section = 360 / 12;
        float minuteHandMovement = minute / 60f;
        float hourHandMovement = minuteHandMovement * section;
        float minuteHandPosition = minuteHandMovement * 360f;
        float hourHandPosition = (section * hour) + hourHandMovement;

        if(abs(hourHandPosition - minuteHandPosition) > 180){
            return 360 - abs(hourHandPosition - minuteHandPosition);
        }

        return abs(hourHandPosition - minuteHandPosition);
    }
}
