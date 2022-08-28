package Snake;
import javax.swing.JFrame;

public class frameJuego extends JFrame {

	frameJuego() {

		this.add(new panelJuego());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}

}