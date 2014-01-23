package chessGraphics;

import javax.imageio.ImageIO;
import javax.swing.*;

import chess.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardGraphics extends JFrame {
	private SquareButton[] boardDisplay = new SquareButton[64];
	// I might want it to be Image for scaleability!
//	private Image[] pieceImages = new Image[12];
	private ImageIcon[] pieceImages = new ImageIcon[12];
	private Image darkSquareImg, lightSquareImg;
	private Board board;
	private ChessGame game;
	private Piece movingPiece;
	
	public SquareButton getButton(int i) {
		return (SquareButton)this.getComponent(i);
	}
	
	
	public BoardGraphics(ChessGame game) {
		this.game = game;
		this.board = game.board();
		setLayout(new GridLayout(8,8));
		
		String[] images = {"whitePawn.png","blackPawn.png",//
						"whiteRook.png","blackRook.png",//
						"whiteKnight.png","blackKnight.png",//
						"whiteBishop.png","blackBishop.png",//
						"whiteQueen.png","blackQueen.png",//
						"whiteKing.png","blackKing.png"};
		
		try {
			// scaling the squares makes the graphics much faster
			darkSquareImg = ImageIO.read(new File("darkSquare.png"));
			darkSquareImg = getScaledImage(darkSquareImg,60,60);
			lightSquareImg = ImageIO.read(new File("lightSquare.png"));
			lightSquareImg = getScaledImage(lightSquareImg,60,60);
			int index = 0;
			for (String img : images) {
				Image pieceImg = ImageIO.read(new File(img));
				pieceImg = getScaledImage(pieceImg,60,60);
//				pieceImages[index++] = pieceImg;
				ImageIcon icon = new ImageIcon(pieceImg);
				pieceImages[index++] = icon;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("images failed to load");
		}
		
		for(int i=0; i<64; i++){
			int row = i/8;
			int column = i%8;
			
			SquareButton squareBtn = new SquareButton(i);
			squareBtn.setPreferredSize(new Dimension(60,60));	
			squareBtn.addActionListener(new SquareListener(row,column));
			add(squareBtn);
			boardDisplay[i] = squareBtn;
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private class SquareListener implements ActionListener {
		int row, column;
		//Image background;
		
		public SquareListener(int row, int column) {
			this.row = row;
			this.column = column;
			boolean lightSquare = (row%2==0 && (8*row+column)%2==0);
			lightSquare = lightSquare || (row%2!=0 && (8*row+column)%2!=0);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			System.out.println("("+row+","+column+")");
			Square square = board.square(row, column);
			
			if (square.isOccupied() && square.piece().inTeamOf(game.king(game.player()))) {
				movingPiece = board.square(row, column).piece();
			} else if (movingPiece != null) {

				try {
					// game.play(movingPiece, square);
					game.play(movingPiece.row(),movingPiece.column(),square.row(),square.column());
					
				} catch (chess.MateException e) {
					String winner = (game.player() == 0) ? "Black" : "White";
					
					JOptionPane.showMessageDialog(new JFrame(),
						winner + " wins!",
						"Check Mate!",
						JOptionPane.PLAIN_MESSAGE);
				} catch (chess.StaleMateException e) {
					JOptionPane.showMessageDialog(new JFrame(),
						"It's a tie!",
						"Stale Mate!",
						JOptionPane.PLAIN_MESSAGE);
				} finally {
					repaint();
					movingPiece = null;
					System.out.println(board);
				}
			}
		}
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
		//srcImg.getScaledInstance(arg0, arg1, arg2)  USE THIS INSTEAD OF THIS METHOD
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);//ARGB for the transparency
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	class SquareButton extends JButton {
		int number, row, column;
		boolean lightSquare;
		
		public SquareButton(int number) {
			super();
			this.number = number;
			this.row = number/8;
			this.column = number%8;
			lightSquare = (row%2==0 && number%2==0) || (row%2!=0 && number%2!=0);
			
			setOpaque(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			setFocusPainted(false);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			update(g);
		}
		
		@Override
		public void update(Graphics g) {
			// FOR SCALING THINK ABOUT BUFFEREDIMAGE SINCE HEIGHT AND WIDTH
			// ARE MORE ACCESSIBLE
			
			if (board.square(row,column).isOccupied()) { 
				int index = board.square(row,column).piece().imageIndex();
//				Image pieceImg = pieceImages[index];
//				ImageIcon icon = new ImageIcon(pieceImg);
				ImageIcon icon = pieceImages[index];
				setIcon(icon);
				}
			else if (lightSquare)
				{ setIcon(new ImageIcon(lightSquareImg)); }
			else
				{ setIcon(new ImageIcon(darkSquareImg)); }
			super.paintComponent(g);
			
		}
	}
	
	
}





