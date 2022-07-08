package tetris;

import javax.swing.*;
import java.awt.*;

class NextPieceDisplay extends JPanel
{
    private int SQUARE_SIZE; 
    private Color background_Color = Color.LIGHT_GRAY;
    Tetromino piece;
    boolean paint_piece;
    //piece colour
    NextPieceDisplay()
    {
        setBackground(background_Color);
        paint_piece = false;
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        SQUARE_SIZE = getWidth() / 4;
        super.paintComponent(g);
        g.setColor(Color.PINK);
        if(paint_piece)
            paintNextPiece(g);
        paintGrid(g);  
        
    }

    void paintGrid(Graphics g)
    {
        g.setColor(Color.DARK_GRAY);
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
                drawBlock(i, j, g);
        }
    }

    void paintNextPiece(Graphics g)
    {
        g.setColor(piece.piece_colour);
        int r = 1, c = 1;
        if(piece.piece_shape == Tetromino.Shape.I_shape)
        {
            r = 2;
            c = 2;
        }
        int orientation[][] = piece.possible_orientations[piece.curr_orientation];
        for(int i = 0; i < 4; i++)
        {
            fillBlock(r + orientation[i][0], c + orientation[i][1], g);
        }
    }

    void drawBlock(int row, int column, Graphics g)
    {
        g.drawRoundRect(SQUARE_SIZE*column, SQUARE_SIZE*row, SQUARE_SIZE, SQUARE_SIZE, 5, 5);
    }

    void fillBlock(int row, int column, Graphics g)
    {
        g.fillRoundRect(SQUARE_SIZE*column, SQUARE_SIZE*row, SQUARE_SIZE, SQUARE_SIZE, 5, 5);
    }

}