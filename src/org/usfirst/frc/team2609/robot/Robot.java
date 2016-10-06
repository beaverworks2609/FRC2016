
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    Command autonomousCommand;
    driveToEnc driveEnc = new driveToEnc();
    int state;

    public void robotInit() {
    	RobotMap.init();//put this here when imports don't work / robots don't quit
    	encReset();
    	stateReset();
    }

    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

        //SmartDashboard.putNumber("driveEnc1.getDistance()", RobotMap.driveEncLeft.getDistance());
        //SmartDashboard.putNumber("driveEnc2.getDistance()", RobotMap.driveEncRight.getDistance());
	}

    public void autonomousInit() {

        if (autonomousCommand != null) autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        encReset();
        stateReset();
        
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
        SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
        SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
        SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
        switch (state)
        {
        case 0: driveForward();
        		break;
        case 1: driveBack();
        		break;
        case 2: driveStop();
				break;
        }
        
        //time0 = new System.nanoTime();
        
    }
    public void testPeriodic() {
        LiveWindow.run();
    }
    public void encReset(){
    	RobotMap.driveEncLeft.reset();
    	RobotMap.driveEncRight.reset();
    }
    public void stateReset(){
    	state = 0;
    	// TODO Change state int to enum
    }
    public void driveForward()
    {
    	driveEnc.drive(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), 2000, RobotMap.driveEncLeft.getRate(), RobotMap.driveEncRight.getRate());
    	if(driveEnc.onTarget(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), 2000))
    	{
    		encReset();
    		state++;
    	}
    }
    public void driveBack()
    {
    	driveEnc.drive(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), -2000, RobotMap.driveEncLeft.getRate(), RobotMap.driveEncRight.getRate());
    	if(driveEnc.onTarget(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), -2000))
    	{
    		encReset();
    		state++;
    	}
    }
    public void driveStop()
    {
    	driveEnc.motorStop();
    	state++;
    }
    
}
