package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DeliverGearV2 extends CommandGroup {

    public DeliverGearV2(Robot robot) {
    	addSequential(new PositionForDelivery(robot));
    	addSequential(new PlaceGear(robot.drivetrain, robot.gearCam, true));
    }
}
