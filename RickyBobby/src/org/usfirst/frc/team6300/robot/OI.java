package org.usfirst.frc.team6300.robot;

import org.usfirst.frc.team6300.robot.commands.PlaceGear;
import org.usfirst.frc.team6300.robot.commands.SwitchFront;
import org.usfirst.frc.team6300.robot.commands.TeleAgitator;
import org.usfirst.frc.team6300.robot.commands.TeleShooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//generic joystick axes
	public static final int leftXAxis = 0;
	public static final int leftYAxis = 1;
	public static final int leftTrigger = 2;
	public static final int rightTrigger = 3;
	public static final int rightXAxis = 4;
	public static final int rightYAxis = 5;
	
	//generic joystick buttons
	public static final int a = 1;
	public static final int b = 2;
	public static final int x = 3;
	public static final int y = 4;
	public static final int lTrigButton = 5;
	public static final int rTrigButton = 6;
	
	//create joysticks
	public static final Joystick gamepadDr = new Joystick(0);
	public static final Button drButtonA = new JoystickButton(gamepadDr, a);
	public static final Button drButtonY = new JoystickButton(gamepadDr, y);
	
	public static final Joystick gamepadSh = new Joystick(1);
	public static final Button shButtonA = new JoystickButton(gamepadSh, a);
	public static final Button shButtonB = new JoystickButton(gamepadSh, b);
	public static final Button shButtonX = new JoystickButton(gamepadSh, x);
	public static final Button shButtonY = new JoystickButton(gamepadSh, y);
	
	public OI(Robot robot) {
		shButtonB.whenReleased(new TeleAgitator(robot.agitator, 'b'));
		shButtonY.whenReleased(new TeleAgitator(robot.agitator, 'y'));
		
		drButtonA.whenReleased(new SwitchFront(robot.drivetrain));
		drButtonY.toggleWhenPressed(new PlaceGear(robot.drivetrain, robot.gearCam));
		
		shButtonA.whenReleased(new TeleShooter(robot.shooter, 'a'));
		shButtonX.whenReleased(new TeleShooter(robot.shooter, 'x'));
	}
}
