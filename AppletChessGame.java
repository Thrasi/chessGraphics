package chessGraphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import chess.*;

public class AppletChessGame extends ChessGame{
	//BoardGraphics board;
	
	public AppletChessGame() {
		super();
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
}
