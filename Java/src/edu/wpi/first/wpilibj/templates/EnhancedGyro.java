/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author SCHROED
 */
public class EnhancedGyro extends Gyro {
    
    public EnhancedGyro(int channel) {
        super(channel);
    }
    
    public double GetHeading() {
        return getAngle()%360;
    }
}
