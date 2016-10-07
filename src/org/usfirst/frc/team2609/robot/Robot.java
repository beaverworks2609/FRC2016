
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2609.robot.driveToEnc;
import java.util.Calendar;

public class Robot extends IterativeRobot {

	Command autonomousCommand;
	int state = 0;
	int targetTime = 0;
	DriveState driveState = DriveState.FORWARD;
	Calendar calendar = Calendar.getInstance();
	
	public void robotInit() {
		RobotMap.init();// put this here when imports don't work / robots don't quit
		encReset();
	}

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
	}

	public void autonomousInit() {

		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		encReset();
		driveState = DriveState.FORWARD;
	}
	public enum DriveState{
		FORWARD,BACK,STOP,WAIT,RESET
	}
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
		SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
		SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
		switch (driveState) {
		case FORWARD:
			driveForward();
			break;
		case BACK:
			driveBack();
			break;
		case STOP:
			driveStop();
			break;
		case WAIT:
			driveWait(2);
			break;
		}

	}

	public void testPeriodic() {
		LiveWindow.run();
	}

	public void encReset() {
		RobotMap.driveEncLeft.reset();
		RobotMap.driveEncRight.reset();
	}

	public void driveForward() {
		driveToEnc.drive(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), 2000,
				RobotMap.driveEncLeft.getRate(), RobotMap.driveEncRight.getRate());
		if (driveToEnc.onTarget(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), 2000)) {
			//encReset();
			driveState = DriveState.STOP;
		}
	}

	public void driveBack() {
		driveToEnc.drive(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), 0,
				RobotMap.driveEncLeft.getRate(), RobotMap.driveEncRight.getRate());
		if (driveToEnc.onTarget(RobotMap.driveEncLeft.getDistance(), RobotMap.driveEncRight.getDistance(), 0)) {
			//encReset();
			driveState = DriveState.STOP;
		}
	}

	public void driveStop() {
		driveToEnc.motorStop();
		driveState = DriveState.WAIT;
	}
	public void driveWait(int targetSeconds){
		int currentTime = (int) System.currentTimeMillis();

		System.out.println("currentTime:" + currentTime);
		System.out.println("targetTime:" + targetTime);
		if(targetTime == 0){
			targetTime = currentTime+(targetSeconds*1000);
		}
		else if((int) System.currentTimeMillis() >= targetTime)
		{
			driveState = DriveState.BACK;
			targetTime = 0;
		}
		

	}

}
