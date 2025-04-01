

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    private Square fromMoveSquare;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));
        boolean whiter = true;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
     
        for (int j = 0; j <= 7; j++) {
            for (int i = 0; i <= 7; i++) {
                board[i][j] = new Square(this, whiter, i, j);
                this.add(board[i][j]);
                whiter = !whiter;
            }
            whiter = !whiter;

        }
      
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces.
    private void initializePieces() {
    	
        for (int j = 0; j <= 7; j++) {
            for (int i = 0; i <= 7; i++) {
                if (j == 0) {
                    if (i == 0 || i == 7) {
                        board[i][j].put(new Piece(false, RESOURCES_BROOK_PNG));
                    }
                    if (i == 1 || i == 6) {
                        board[i][j].put(new Piece(false, RESOURCES_BKNIGHT_PNG));
                    }
                    if (i == 2 || i == 5) {
                        board[i][j].put(new Piece(false, RESOURCES_BBISHOP_PNG));
                    }
                    if (i == 3) {
                        board[i][j].put(new Piece(false, RESOURCES_BQUEEN_PNG));
                    }
                    if (i == 4) {
                        board[i][j].put(new Piece(false, RESOURCES_BKING_PNG));
                    }
                } else if (j == 7) {
                    if (i == 0 || i == 7) {
                        board[i][j].put(new Piece(true, RESOURCES_WROOK_PNG));
                    }
                    if (i == 1 || i == 6) {
                        board[i][j].put(new Piece(true, RESOURCES_WKNIGHT_PNG));
                    }
                    if (i == 2 || i == 5) {
                        board[i][j].put(new Piece(true, RESOURCES_WBISHOP_PNG));
                    }
                    if (i == 3) {
                        board[i][j].put(new Piece(true, RESOURCES_WQUEEN_PNG));
                    }
                    if (i == 4) {
                        board[i][j].put(new Piece(true, RESOURCES_WKING_PNG));
                    }
                } else if (j == 6) {

                    board[i][j].put(new Piece(true, RESOURCES_WPAWN_PNG));

                } else if (j == 1) {

                    board[i][j].put(new Piece(false, RESOURCES_BPAWN_PNG));
                    
                }
            }
            
        }

    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            System.out.println(whiteTurn);
                System.out.println("collor " + currPiece.getColor());
            if (!currPiece.getColor() == whiteTurn){
                currPiece = null;
                return;
            }
           
            sq.setDisplay(false);
        }
        repaint();
    }

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        System.out.println("o color " + endSquare.getColor());
        
        //using currPiece

        if (currPiece != null) {
            if (currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare) && currPiece.getColor()==whiteTurn){
                endSquare.put(currPiece);
                fromMoveSquare.put(null);
                whiteTurn = !whiteTurn;
            }
            // if (currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare) && currPiece.getColor() && whiteTurn && !endSquare.equals(fromMoveSquare)) {
            //     if (endSquare.isOccupied() && endSquare.getOccupyingPiece().getColor() != currPiece.getColor()) {
            //         endSquare.put(currPiece);
            //         fromMoveSquare.put(null);
            //         whiteTurn = !whiteTurn;
            //         System.out.println(whiteTurn);
            //         System.out.println(currPiece.getColor());

            //     } else if (endSquare.isOccupied() && endSquare.getOccupyingPiece().getColor() == currPiece.getColor()) {

            //     } else {
            //         endSquare.put(currPiece);
            //         fromMoveSquare.put(null);
            //         whiteTurn = !whiteTurn;
            //         System.out.println(whiteTurn);
            //         System.out.println(currPiece.getColor());
            //     }
                
                

            // } else if (currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare) && !currPiece.getColor() && !whiteTurn && !endSquare.equals(fromMoveSquare)) {
            //     if (endSquare.isOccupied() && endSquare.getOccupyingPiece().getColor() != currPiece.getColor()) {
            //         endSquare.put(null);
            //         endSquare.put(currPiece);
            //         fromMoveSquare.put(null);
            //         whiteTurn = !whiteTurn;
            //         System.out.println(whiteTurn);
            //         System.out.println(currPiece.getColor());

            //     } else if (endSquare.isOccupied() && endSquare.getOccupyingPiece().getColor() == currPiece.getColor()) {

            //     } else {
            //         endSquare.put(currPiece);
            //         fromMoveSquare.put(null);
            //         whiteTurn = !whiteTurn;
            //         System.out.println(whiteTurn);
            //         System.out.println(currPiece.getColor());
            //     }

            // } 
        }
        for (Square[] row: board) {
            for(Square s: row) {
                s.setBorder(null);
            }
        }
        
       
        fromMoveSquare.setDisplay(true);
        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        if (currPiece != null) {
            for (Square s: currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }
            for (Square d: currPiece.getControlledSquares(this.getSquareArray(), fromMoveSquare)) {
                d.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}