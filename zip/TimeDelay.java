import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Math;

// This class is a powerup
public class TimeDelay extends PowerUp {
	TimeDelay() {
		this.icon = new ImageIcon("img/clock.png");
	}
	public void modify(Board board, Snake snake) {
		int defaultTimeLimitConstant = board.timeLimitConstant;
		
		// Reduce the time limit constant back to its default value after N seconds
		// The powerup won't last forever
		// The duration will be N seconds, where N is a random number between 20-25
		int duration = (int)Math.floor(Math.random() * 6) + 20;
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.timeLimitConstant = defaultTimeLimitConstant;
			}
		};
		// The timer interprets the duration in milliseconds, which is why we multiply by 1000
		Timer timer = new Timer(duration*1000, taskPerformer);
		
		// Execute the timer only once
		timer.setRepeats(false);
		
		// Start the timer
		timer.start();
		
		// Increase the time delay constant by 250
		board.timeLimitConstant += 250;
	}
}
