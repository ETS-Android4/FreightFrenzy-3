package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;

@TeleOp (name = "TeleOp")
public class Team7593TeleOp extends Team7593OpMode {


    double position1 = robot.HOME;
    double position2 = robot.MAX;
    final double SPEED = 0.02; //sets rate to move servo

    public int cEncoderVal;
    public int oEncoderVal;

    public int currEncoderVal;
    public int oldEncoderVal;

    Orientation angles; //to use the imu (mostly for telemetry)

    //double extensionPosition = robot.EXTENSION_HOME;
    final double EXTENSION_SPEED = 0.08; //sets rate to move servo

    @Override
    //code block to that will run at the VERY beginning of Teleop
    public ArrayList<AutonStep> createAutonSteps() {
        return null;
    }

    @Override
    public void init(){
        super.init();

        //stop the motor(s) and reset the motor encoders to 0
        robot.linearSlide.setPower(0);
        robot.arm.setPower(0);

        //robot.claw.setPosition(0);

        robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //get the starting encoder value of tilt (this is so we don't assume the starting econder value is 0)
        oEncoderVal = robot.arm.getCurrentPosition();
        oldEncoderVal = robot.linearSlide.getCurrentPosition();


        telemetry.addData("Say", "HELLO FROM THE OTHER SIIIIIDE");

    }


    public void loop() {

        //use sarmer's loop so that auton steps can run
        super.loop();

        //get the current encoder value of tilt
        cEncoderVal = robot.arm.getCurrentPosition();
        currEncoderVal = robot.linearSlide.getCurrentPosition();

        double leftX, rightX, leftY, linearSlide, arm, linearSlidePower, armPower; //declaration for the game sticks + power
        boolean slow, slow1, slowDrive, slowDrive2, duckRight, duckLeft, boxRight, boxLeft; //declaration for the buttons/bumpers
        WheelSpeeds speeds; //variable to hold speeds

        leftX = gamepad1.left_stick_x; //must keep
        rightX = gamepad1.right_stick_x; //must keep
        leftY = gamepad1.left_stick_y; //must keep
        slowDrive = gamepad1.left_bumper; //must keep
        slowDrive2 = gamepad1.right_bumper; //must keep
        slow = gamepad2.right_bumper;
        slow1 = gamepad2.left_bumper;
        arm = gamepad2.left_stick_y;
        linearSlide = gamepad2.right_stick_y;
        duckRight = gamepad2.x;
        duckLeft = gamepad2.b;
        boxRight = gamepad2.y;
        boxLeft = gamepad2.a;

        armPower = arm*.75;
        linearSlidePower = linearSlide*.75;

//        if(spin){
//            robot.spin1.setPower(-1);
//            robot.spin2.setPower(1);
//        }

        //get the speeds
        if(slowDrive || slowDrive2){
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, true);
        }else{
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, false);
        }


        //power the motors
        robot.powerTheWheels(speeds);

//        if(gamepad2.a){
//            robot.claw.setPosition(.7);
//        }else if(gamepad2.b){
//            robot.claw.setPosition(0);
//        }


        if(duckRight){
            robot.duck.setPower(1);
        } else if (duckLeft) {
            robot.duck.setPower(-1);
        } else {
            robot.duck.setPower(0);
        }

        if(boxRight){
            robot.box.setPower(1);
        } else if(boxLeft){
            robot.box.setPower(-1);
        } else {
            robot.box.setPower(0);
        }


        if(slow || slow1){
            armPower = arm*.5;
            linearSlidePower = linearSlide*.5;
        }

        //recursive encoder loop to the keep the tilt motor still-ish
        if (arm > 0) {
            if (robot.arm.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.arm.setPower(armPower);
            oEncoderVal = cEncoderVal;
        } else if (arm < 0) {
            if (robot.arm.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.arm.setPower(armPower);
            oEncoderVal = cEncoderVal;
        } else {
            if (robot.arm.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.arm.setTargetPosition(oEncoderVal);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            //robot.arm.setTargetPosition(oEncoderVal);
            robot.arm.setPower(0.05);
        }

        //recursive encoder loop to the keep the tilt motor still-ish
//        if (linearSlide > 0) {
//            telemetry.addData("Say", "linearSlide is greater than 0");
//            if (robot.linearSlide.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
//                robot.linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            }
//            robot.linearSlide.setPower(linearSlidePower);
//
//            telemetry.addData("Say", "linearSlide set power");
//
//            oldEncoderVal = currEncoderVal;
//        } else if (linearSlide < 0) {
//            telemetry.addData("Say", "linearSlide is less than 0");
//            if (robot.linearSlide.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
//                robot.linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            }
//            robot.linearSlide.setPower(linearSlidePower);
//
//            telemetry.addData("Say", "linearSlide set power");
//
//            oldEncoderVal = currEncoderVal;
//        } else {
//            telemetry.addData("Say", "linearSlide should be still");
//            if (robot.linearSlide.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
//                robot.linearSlide.setTargetPosition(oldEncoderVal);
//                robot.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//            //robot.linearSlide.setTargetPosition(oldEncoderVal);
//            robot.linearSlide.setPower(0.05);
//        }


//        if (linearSlide > 0) {
//            robot.linearSlide.setPower(linearSlidePower);
//        } else if (arm < 0) {
//            robot.linearSlide.setPower(linearSlidePower);
//        } else {
//            robot.linearSlide.setPower(0);
//        }


        //use the imu
        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        /*
        WHOA TELEMETRY
        */
        telemetry.addLine().addData("slow mode: ", slowDrive);


        //angles on each of the axes
        telemetry.addLine().addData("IMU angle Y:", angles.secondAngle);
        telemetry.addData("IMU angle Z:", angles.firstAngle);
        telemetry.addData("IMU angle X:", angles.thirdAngle);

        //angles it's at
        telemetry.addLine();
        telemetry.addData("Current Angle: ", robot.getCurrentAngle());
        telemetry.addData("Init Angle: ", robot.initAngle);
    }
}