package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.ArrayList;

//@Autonomous (name = "DuckAuton3")
public class A1 extends Team7593OpMode {


    @Override
    public void init(){
        super.init();
    }

    @Override
    public ArrayList<AutonStep> createAutonSteps() {
        ArrayList<AutonStep> steps = new ArrayList<>();

        steps.add(new DuckAutonStep());
        steps.add(new AngleRotate(25,-.5));
        steps.add(new DriveY(0.8, .6));

        return steps;
    }


}