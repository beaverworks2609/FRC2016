package org.usfirst.frc.team2609.robot;
// TODO possibly rename this to drive
public class driveToEnc {
	public driveToEnc(){
		
	}
	 public void drive(double encLeft, double encRight, int target, double encRateLeft, double encRateRight){
		 double power = 0.4;
		 if (target < 0)
		 {
			 power=-power;
		 }
			 double encError = Math.min(Math.abs(((encRateLeft)-(-encRateRight))*0.001),0.2);
	         if (Math.abs(encRateRight) > Math.abs(encRateLeft)) {
	         RobotMap.driveVictorRight1.set(power-encError);
	         RobotMap.driveVictorRight2.set(power-encError);
	         RobotMap.driveVictorLeft1.set(-power);
	         RobotMap.driveVictorLeft2.set(-power);
	         }
	         else{
	        	 RobotMap.driveVictorRight1.set(power);
	             RobotMap.driveVictorRight2.set(power);
	             RobotMap.driveVictorLeft1.set(-power-encError);
	             RobotMap.driveVictorLeft2.set(-power-encError);
	             }
	        }
	 public boolean onTarget(double encLeft, double encRight, int target)
	 {
		 if(Math.abs(encLeft) < Math.abs(target) || Math.abs(encRight) < Math.abs(target)){
			 return false;
		 }
		 else{
			 motorStop();
			 return true;
		 }
	 }
	 public void motorStop(){
    	 RobotMap.driveVictorRight1.set(0);
         RobotMap.driveVictorRight2.set(0);
         RobotMap.driveVictorLeft1.set(0);
         RobotMap.driveVictorLeft2.set(0);
	 }
}
