package bugetriplanner;

import java.io.Serializable;

// class that carries all the parameters required
// for server to communicate with client
// contains only getters and setters
// for serialization purpose
public class ServerResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String response;
	private String username;
	private int wins;
	private int losses;
	private int waitingCount;
	private String gameString;
	private int incorrectsLeft;
	private String guessed;
	private String winner;
	
	public ServerResponse(String response) {
		this.setResponse(response);
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getWaitingCount() {
		return waitingCount;
	}

	public void setWaitingCount(int waitingCount) {
		this.waitingCount = waitingCount;
	}

	public String getGameString() {
		return gameString;
	}

	public void setGameString(String gameString) {
		this.gameString = gameString;
	}

	public int getIncorrectsLeft() {
		return incorrectsLeft;
	}

	public void setIncorrectsLeft(int incorrectsLeft) {
		this.incorrectsLeft = incorrectsLeft;
	}

	public String getGuessed() {
		return guessed;
	}

	public void setGuessed(String guessed) {
		this.guessed = guessed;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

}
