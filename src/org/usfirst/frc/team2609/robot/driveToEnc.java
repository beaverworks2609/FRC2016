package org.usfirst.frc.team2609.robot;

// TODO possibly rename this to drive
public class driveToEnc {
	public driveToEnc() {

	}

	public static void drive(double encLeft, double encRight, int target, double encRateLeft, double encRateRight) {
		double power = 0;
		double maxPower = 0.6;
		double encAvg = ((encLeft+encRight)*0.5);
		double encRateAvg = ((Math.abs(encRateLeft)+Math.abs(encRateRight))*0.5);
		if (target < encRateAvg) {
			maxPower = -maxPower;
		}
		double distError = target - encAvg;
		power = Math.min(maxPower,distError*0.0001);
		System.out.println("power" + power);
		double encError = Math.min(Math.abs(((encRateLeft) - (encRateRight)) * 0.1), power*.5);
		System.out.println("encError" + encError);
		if (Math.abs(encRateRight) > Math.abs(encRateLeft)) {
			RobotMap.driveVictorRight1.set(-power + encError);
			RobotMap.driveVictorRight2.set(-power + encError);
			RobotMap.driveVictorLeft1.set(power);
			RobotMap.driveVictorLeft2.set(power);
		} else {
			RobotMap.driveVictorRight1.set(-power);
			RobotMap.driveVictorRight2.set(-power);
			RobotMap.driveVictorLeft1.set(power - encError);
			RobotMap.driveVictorLeft2.set(power - encError);
		}
		
	}

	public static boolean onTarget(double encLeft, double encRight, int target) {
		if (Math.abs(encLeft) < Math.abs(target) || Math.abs(encRight) < Math.abs(target)) {
			return false;
		} else {
			motorStop();
			return true;
		}
	}

	public static void motorStop() {
		RobotMap.driveVictorRight1.set(0);
		RobotMap.driveVictorRight2.set(0);
		RobotMap.driveVictorLeft1.set(0);
		RobotMap.driveVictorLeft2.set(0);
	}
}
