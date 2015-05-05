package PokerProfessor;

public class PlayerLayout {
	int screenX;
	int screenY;
	double perimeter;
	int numPlayers;

	public PlayerLayout(int screenX, int screenY, int numPlayers) {
		this.screenX = screenX;
		this.screenY = screenY;
		this.numPlayers = numPlayers;
	}

	public int getPlayerX(int whichPlayer) {
		double x = (screenX / 2*.90)
				+ (.75 * screenX / 2 * Math.cos(360 / numPlayers * whichPlayer
						* Math.PI / 180.0));
		return (int) x;
	}

	public int getPlayerY(int whichPlayer) {
		double y = (screenY / 2*.70)
				+ (.70 * screenY / 2 * Math.sin(360 / numPlayers * whichPlayer
						* Math.PI / 180.0));

		return (int) y;
	}

}
