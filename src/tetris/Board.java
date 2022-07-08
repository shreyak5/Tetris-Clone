package tetris;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.*;
import java.awt.*;

class Board extends JPanel
{

    private int SQUARE_SIZE;
    private Color board_colour = Color.LIGHT_GRAY;
    private Color grid_colour = Color.GRAY;

    protected Color game_board[][];
    protected int rows_count[];
    Tetromino curr_piece;
    Tetromino next_piece;

    static Random rand = new Random();

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

        //initlizaing number of blocks in each row to 0
        rows_count = new int[20];
        for(int i = 0; i < 20; i++)
            rows_count[i] = 0;

    }

    /* BOARD PAINTING FUNCTIONS */

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        paintGrid(g);
        paintFixed(g);
        if(Game.game_status != Game.Status.NONE)
            paintCurrentPiece(g);
    }

    //paints the tetris grid
    void paintGrid(Graphics g)
    {
        g.setColor(grid_colour);
        for(int i = 0; i < 20; i++)
        {
            for(int j = 0; j < 10; j++)
               drawBlock(i, j, g); 
        }
    }

    //paints the fixed pieces on the grid
    void paintFixed(Graphics g)
    {
        for(int i = 0; i < 20; i++)
        {
            if(rows_count[i] == 0)
                continue;
            for(int j = 0; j < 10; j++)
            {
                if(game_board[i][j] != board_colour)
                {
                    g.setColor(game_board[i][j]);
                    fillBlock(i, j, g);
                    g.setColor(Color.BLACK);
                    drawBlock(i, j, g);
                }
            }
        }
    }

    void paintCurrentPiece(Graphics g)
    {
        int orientations[][] = curr_piece.possible_orientations[curr_piece.curr_orientation];
        int row = curr_piece.curr_pos[0];
        int column = curr_piece.curr_pos[1];

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


    /* GAMEPLAY-RELATED FUNCTIONS */

    //clears the tetris board for a new game
    void clearBoard()
    {
        for(int row = 0; row < 20; row++)
        {
            for(int column = 0; column < 10; column++)
                game_board[row][column] = board_colour;
        }
    }

    //called when the current piece is set in the grid and stops moving
    void changePiece()
    {
        //adding final position of curr_piece to the board
        int orientation[][] = curr_piece.possible_orientations[curr_piece.curr_orientation];
        for(int i = 0; i < 4; i++)
        {
            int row = curr_piece.curr_pos[0] + orientation[i][0];
            int column = curr_piece.curr_pos[1] + orientation[i][1];
            game_board[row][column] = curr_piece.piece_colour;
            rows_count[row]++;
        }

        //changing next_piece to curr_piece
        curr_piece = next_piece;
        curr_piece.curr_pos = new int[]{1, 4};
        if(curr_piece.piece_shape == Tetromino.Shape.I_shape && curr_piece.curr_orientation == 1)
            curr_piece.curr_pos[0] = 2;

        //initializing a new next_piece
        int r = rand.nextInt(0, Tetromino.Shape.values().length);
        next_piece = new Tetromino(Tetromino.Shape.values()[r]);
        repaint();
    }

    // returns true if the next position if a valid position, else false
    boolean checkPosition()
    {
        int orientation[][] = curr_piece.possible_orientations[curr_piece.curr_orientation];
        int row = curr_piece.curr_pos[0];
        int column = curr_piece.curr_pos[1];
        for(int i = 0; i < 4; i++)
        {
            int r = row + orientation[i][0];
            int c = column + orientation[i][1];
            
            //checking for the left, right edges of the board
            if(c < 0 || c > 9)
                return false;
            
            //checking for the bottom and top edges of the board
            if(r > 19 || r < 0)
            return false;

            //checking the presence of fixed pieces in this position
            if(game_board[r][c] != board_colour)
                return false;
              
        }
        return true;
    }
    
}
