package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.ArrayList;

//@Autonomous (name = "DuckAuton2")
public class A2 extends Team7593OpMode {


    @Override
    public void init(){
        super.init();
    }

    @Override
    public ArrayList<AutonStep> createAutonSteps() {
        ArrayList<AutonStep> steps = new ArrayList<>();

        steps.add(new DuckAutonStep());
        steps.add(new AngleRotate(20,-.5));
        steps.add(new DriveY(1.2, .6));


        return steps;
    }


}