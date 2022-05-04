package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class DuckAutonStep implements AutonStep {

    private ElapsedTime time; //way to see the time

    public DuckAutonStep() {

    }

    @Override
    public String name() {
        return "duck step";
    }

    @Override
    public void start(Team7593OpMode opmode) {
        time = new ElapsedTime();
    }

    @Override
    public void loop(Team7593OpMode opmode) {
        opmode.robot.duck.setPower(-1);
    }

    @Override
    public boolean isDone(Team7593OpMode opmode) {

        if(time.time() > 4 ){
            opmode.robot.duck.setPower(0);
            return true;
        }
        return false;
    }

    @Override
    public void updateTelemetry(Team7593OpMode opmode) {

    }
}
