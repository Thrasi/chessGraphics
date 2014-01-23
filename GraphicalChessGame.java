package chessGraphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import chess.*;

public class GraphicalChessGame extends ChessGame{
	BoardGraphics board;
	
	public GraphicalChessGame() {
		super();
		board = new BoardGraphics(this);
	}
	
	public BoardGraphics boardGraphics() {
		return board;
	}
	@Override
	public int promotePawnInterface() {
		Object[] options = {"Rook",
                			"Knight",
                			"Bishop",
                			"Queen"};
		
		return JOptionPane.showOptionDialog(new JFrame(),
				"To what rank do you want to promote"
				+ " your pawn: ",
				"Pawn Promotion",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[3]);
	}

	public static void main(String[] args) {
		GraphicalChessGame game = new GraphicalChessGame();
	}
}
