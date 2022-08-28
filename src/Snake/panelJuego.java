package Snake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class panelJuego extends JPanel implements ActionListener {

	static final int ANCHO_PANTALLA = 1300;
	static final int ALTO_PANTALLA = 750;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (ANCHO_PANTALLA * ALTO_PANTALLA) / (UNIT_SIZE * UNIT_SIZE);
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int partesSerpiente = 6;
	int manzanasConsumidas;
	int manzanaX;
	int manzanaY;
	char direction = 'R';
	boolean enMovimiento = false;
	Timer timer;
	Random random;

	panelJuego() {

		random = new Random();
		this.setPreferredSize(new Dimension(ANCHO_PANTALLA, ALTO_PANTALLA));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();

	}

	public void startGame() {

		nuevaManzana();
		enMovimiento = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		dibujar(g);
	}

	public void dibujar(Graphics g) {
		if (enMovimiento) {
			g.setColor(Color.red);
			g.fillOval(manzanaX, manzanaY, UNIT_SIZE, UNIT_SIZE);

			for (int i = 0; i < partesSerpiente; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Comic Sants", Font.BOLD, 45));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Puntuación: " + manzanasConsumidas,
					(ANCHO_PANTALLA - metrics.stringWidth("Puntuación: " + manzanasConsumidas)) / 2,
					g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}

	public void nuevaManzana() {

		manzanaX = random.nextInt((int) (ANCHO_PANTALLA / UNIT_SIZE)) * UNIT_SIZE;
		manzanaY = random.nextInt((int) (ALTO_PANTALLA / UNIT_SIZE)) * UNIT_SIZE;

	}

	public void mover() {

		for (int i = partesSerpiente; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}

	public void checkManzana() {

		if ((x[0] == manzanaX) && (y[0] == manzanaY)) {
			partesSerpiente++;
			manzanasConsumidas++;
			nuevaManzana();
		}
	}

	public void checkColision() {
		// Checkea que no se choque la cabeza con la cola
		for (int i = partesSerpiente; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				enMovimiento = false;
			}
		}

		// Checkea que la cabeza no toque el borde izquierdo
		if (x[0] < 0) {
			enMovimiento = false;

		}
		// Checkea que la cabeza no toque el borde derecho
		if (x[0] > ANCHO_PANTALLA) {
			enMovimiento = false;

		}
		// Checkea que la cabeza no toque el borde superior
		if (y[0] < 0) {
			enMovimiento = false;

		}
		// Checkea que la cabeza no toque el borde inferior
		if (y[0] > ALTO_PANTALLA) {
			enMovimiento = false;

		}

		if (!enMovimiento) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		// Puntuacion
		g.setColor(Color.red);
		g.setFont(new Font("Comic Sants", Font.BOLD, 45));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Puntuación: " + manzanasConsumidas,
				(ANCHO_PANTALLA - metrics1.stringWidth("Puntuación: " + manzanasConsumidas)) / 2,
				g.getFont().getSize());

		// Game Over texto
		g.setColor(Color.red);
		g.setFont(new Font("Comic Sants", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (ANCHO_PANTALLA - metrics2.stringWidth("Game Over")) / 2, ALTO_PANTALLA / 2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (enMovimiento) {
			mover();
			checkManzana();
			checkColision();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;

			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;

			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;

			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
