
package hummingbeans;

import edu.cmu.ri.createlab.hummingbird.HummingbirdRobot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Name: KnobControlledOrbServo.java
 * Author: Tom Lauwers
 * Setup: Connect the Knob to sensor port 1, a tri-color LED to tri-color port 1, and a servo to servo port 1.
 * Description: The Knob controls the servo and LED - turn the knob and the servo turns. The LED changes from red to green to blue.
 */

public class KnobControlledBinaryCounter {

    private static final int LEDON = 64;
    private static final int LEDOFF = 0;
    // Create a mask that is all of the LEDs
    private static final boolean[] ALL_LEDS = new boolean[]{true, true, true, true};

    // yeah, yeah...there are more elegant ways of doing this, but for only 16 states this was trivially easy...
    private static final int[][] MASKS = new int[][]{
        {LEDOFF, LEDOFF, LEDOFF, LEDOFF},
        {LEDOFF, LEDOFF, LEDOFF, LEDON},
        {LEDOFF, LEDOFF, LEDON, LEDOFF},
        {LEDOFF, LEDOFF, LEDON, LEDON},
        {LEDOFF, LEDON, LEDOFF, LEDOFF},
        {LEDOFF, LEDON, LEDOFF, LEDON},
        {LEDOFF, LEDON, LEDON, LEDOFF},
        {LEDOFF, LEDON, LEDON, LEDON},
        {LEDON, LEDOFF, LEDOFF, LEDOFF},
        {LEDON, LEDOFF, LEDOFF, LEDON},
        {LEDON, LEDOFF, LEDON, LEDOFF},
        {LEDON, LEDOFF, LEDON, LEDON},
        {LEDON, LEDON, LEDOFF, LEDOFF},
        {LEDON, LEDON, LEDOFF, LEDON},
        {LEDON, LEDON, LEDON, LEDOFF},
        {LEDON, LEDON, LEDON, LEDON},
    };
    
    public static void main(String[] args) throws IOException {
        // Instantiate the Hummingbird object (establishes a connection to the Hummingbird)
        HummingbirdRobot robot = new HummingbirdRobot();
        
        // Print instructions for exiting
        System.out.println("");
        System.out.println("Press ENTER to quit.");
        
        // Declare a variable to store the sensor value
        int knobVal = 0;
        
        // Necessary for exit
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            // check whether the user pressed a key, if so, break out of the loop
            if (in.ready())
                {
                break;
                }
            
            // Read the value on port 1 (range is 0 to 255)
            knobVal = robot.getSensorValue(1);
            
            knobVal /= 16;
           
            // Set the servo with the value directly - conveniently, servo also has a range of 0-255
            //sillyBird.setServoPosition(1, knobVal);
            
            // Color fade from red at one extreme to green in the middle to blue at the other extreme
            robot.setLEDs(ALL_LEDS, MASKS[knobVal]);
            
        }
        // Disconnect - if you miss this call the Hummingbird will continue doing stuff for five more seconds
        // you may also get a java error.
        robot.disconnect();
    }
}
