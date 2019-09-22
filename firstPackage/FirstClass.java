package firstPackage;
import javax.swing.*;
public class FirstClass {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Object[] options = {"4x4",
                "8x8",
                "12x12"};
		int n = JOptionPane.showOptionDialog(frame,
		"Choose gride size",
		"Chomp Game",
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		options,
		options[2]);
		frame.setVisible(true);
		int x, y;
		if (n == 0) {
			x=4;
			y=4;
		} else if (n == 1) {
			x=8;
			y=8;
		} else {
			x=12;
			y=12;
		}
		
		WindowDemo demo = new WindowDemo( x, y);
	}

}
