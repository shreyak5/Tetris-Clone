package tetris;

import java.awt.Graphics;

import javax.swing.*;
import java.awt.*;

class Board extends JPanel
{

    private int SQUARE_SIZE;
    private Color board_colour = Color.LIGHT_GRAY;
    private Color grid_colour = Color.GRAY;

    protected Color game_board[][];



    Tetromino curr_piece;
    Tetromino next_piece;

    Board(int square_size)
    {
        SQUARE_SIZE = square_size;
        setBackground(board_colour);

        //initializing game_board
        game_board = new Color[20][10];
        for(int row = 0; row < 20; row++)
        {
            for(int column = 0; column < 10; column++)
                game_board[row][column] = board_colour;
        }
            
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        paintGrid(g);
        if(Game.game_status != Game.Status.NONE)
            paintCurrentPiece(g);
    }

    void paintGrid(Graphics g)
    {
        g.setColor(grid_colour);
        for(int i = 0; i < 20; i++)
        {
            for(int j = 0; j < 10; j++)
               drawBlock(i, j, g); 
        }
    }

    void paintCurrentPiece(Graphics g)
    {
        int orientations[][] = curr_piece.possible_orientations[curr_piece.curr_orientation];
        int row = curr_piece.currPos[0];
        int column = curr_piece.currPos[1];

        g.setColor(curr_piece.piece_colour);
        //printing all blocks of the current piece
        for(int i = 0; i < orientations.length; i++)
        {
            int x = orientations[i][1];
            int y = orientations[i][0];
            g.setColor(curr_piece.piece_colour);
            fillBlock(row + y, column + x, g);
            g.setColor(Color.BLACK);
            drawBlock(row + y, column + x, g);
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
