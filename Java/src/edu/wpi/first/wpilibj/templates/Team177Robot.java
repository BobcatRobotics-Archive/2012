/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team177Robot extends IterativeRobot {
    
    /** Control Constants **/
    
     /** Right Joystick Buttons **/
    private static final int shiftButton = 3; //Right Joystick button 3 is the shifter
    
    /** Left Joystick Buttons **/
    private static final int omniButton = 3; //Left button 3 is omni
    
    
    /** Operator Control Buttons **/
    private static final int finButton = 7;
    private static final int fingerButton = 2;

    
    /** IO Definitions **/
    
    /* Instansiate Speed Controlers and Drive */
    Victor frontLeftMotor = new Victor(4);
    Victor frontRightMotor = new Victor(3);    
    Victor rearLeftMotor = new Victor(1);
    Victor rearRightMotor = new Victor(2);
    RobotDrive drive = new RobotDrive(frontLeftMotor,rearLeftMotor,frontRightMotor,rearRightMotor);

    /* Instansiate Joysticks */
    Joystick leftStick = new Joystick(1);
    Joystick rightStick = new Joystick(2);
    Joystick operatorStick = new Joystick(3);

    /* Pnumatics
     * Pressure switch = DIO 1
     * Compressor = Relay 1
     * 
     * Shifter = Solinoid 1
     */
    Compressor compressor = new Compressor(1,1);
    Solenoid shifter = new Solenoid(1);
    Solenoid omnis = new Solenoid(2);
    Solenoid fin = new Solenoid(3);
    Solenoid fingers = new Solenoid(4);
    
    /* State Variables */
    boolean finState;
    boolean lastFinButton;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {        
        /* Initalize Drive */
        //drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        //drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);                                

        /* Start Compressor - logic handled by Compressor class */
        compressor.start();
        
        /* Default to low gear */
        shifter.set(false);                
        
        /* Set default fin state */
        finState = false;
        fin.set(false);
        lastFinButton = false;
        
        /* Set omnis up */
        omnis.set(false);
        
        /* Turn on watchdog */
        getWatchdog().setEnabled(true);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {        
        getWatchdog().feed();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
        /* Drive Code */ 
        
        drive.tankDrive(leftStick, rightStick); // drive with the joysticks
        shifter.set(rightStick.getRawButton(shiftButton));
        
        omnis.set(leftStick.getRawButton(omniButton)); 
        
        /* Fin code */        
        boolean currentFinButton = operatorStick.getRawButton(finButton);
        if(currentFinButton == true && lastFinButton == false) {
            finState = !finState; //toggle value
        }
        lastFinButton = currentFinButton;

        
        fin.set(finState);
        
        /* Fingers */
        fingers.set(operatorStick.getRawButton(fingerButton)); 
        
        Timer.delay(0.005);
        getWatchdog().feed();
    }

    public void disabledPeriodic() {
        getWatchdog().feed();
    }
}
