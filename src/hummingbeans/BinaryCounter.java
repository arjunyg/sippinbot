package hummingbeans;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import edu.cmu.ri.createlab.hummingbird.HummingbirdRobot;

/**
 * <p>
 * <code>BinaryCounter</code> is a simple demo that counts in binary from 0 to 15 using the 4 LEDs.
 * </p>
 * Setup: Connect an LED to each of the four regular LED ports on the Hummingbird
 *
 * @author Chris Bartley (bartley@cmu.edu)
 */
@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public class BinaryCounter
   {
    private static final int LEDON = 255;
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

   public static void main(final String[] args) throws IOException
      {
          // Instantiate the Hummingbird object (establishes a connection to the Hummingbird)
      HummingbirdRobot hummingbird = new HummingbirdRobot();

      // Print instructions for exiting
      System.out.println("");
      System.out.println("Press ENTER to quit.");

      int i = 0;
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      while (true)
         {
         // check whether the user pressed a key, if so, break out of the loop
         if (in.ready())
            {
            break;
            }
         // Set all LEDs to the mask
         hummingbird.setLEDs(ALL_LEDS, MASKS[i]);
         i++;
         // Make sure we stick to 16 states
         if (i >= MASKS.length)
            {
            i = 0;
            }
         // Pause for 1 second
         sleep();
         }

        // Disconnect - if you miss this call the Hummingbird will continue doing stuff for five more seconds
        // you may also get a java error.
      hummingbird.disconnect();
      }

   // Pause for 1 second
   private static void sleep()
      {
      try
         {
         Thread.sleep(1000);
         }
      catch (InterruptedException e)
         {
         System.err.println("InterruptedException while sleeping!");
         }
      }

   }
