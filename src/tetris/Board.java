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
    NextPieceDisplay next_display;

    int current_score;
    boolean update_current_score;

    String plus_points_message;
    static Random rand = new Random();

    Board(int square_size, NextPieceDisplay n)
    {
        SQUARE_SIZE = square_size;
        setBackground(board_colour);

        next_display = n;
        plus_points_message = "Good Luck!";

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

        current_score = 0;
        update_current_score = true;

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
            
            //if a block is outside the grid (starting position), then don't print it
            if((row + y) < 0)
                continue;
            
            //print block
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
            
            rows_count[row] = 0;
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
            if(row < 0)
                continue;
            game_board[row][column] = curr_piece.piece_colour;
            rows_count[row]++;
        }

        if(rows_count[0] != 0)
        {
            Game.game_status = Game.Status.NONE;
            return;
        }

        //changing next_piece to curr_piece
        curr_piece = next_piece;
        curr_piece.curr_pos = new int[]{0, 4};
        //exception in starting position for vertical I-shape
        if(curr_piece.piece_shape == Tetromino.Shape.I_shape && curr_piece.curr_orientation == 1)
            curr_piece.curr_pos[0] = 1;

        //initializing a new next_piece
        int r = rand.nextInt(0, Tetromino.Shape.values().length);
        next_piece = new Tetromino(Tetromino.Shape.values()[r]);

        //painting the next piece display
        next_display.piece = next_piece;
        next_display.repaint();

        //check full rows
        checkFullRows();

        //repainting the board
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

    //checks the full rows in the board
    void checkFullRows()
    {
        int full_rows[] = new int[]{-1, -1, -1, -1};
        int count = 0;

        for(int i = 0; i < 20; i++)
        {
            if(rows_count[i] == 10)
            {
                full_rows[count] = i;
                count++;
            }
        }
        
        //if there are full rows, it clears them and updates the score based on the number of rows cleared
        if(count > 0)
        {
            current_score += count*100;
            update_current_score = true;
            plus_points_message = "+" + Integer.toString(count*100) + " points!";
            deleteFullRows(full_rows, count);
        }
    }

    //deleted the full rows
    void deleteFullRows(int full_rows[], int count)
    {
        //clears all full rows
        for(int i = 0; i < count; i++)
        {
            int row = full_rows[i];
            for(int j = 0; j < 10; j++)
                game_board[row][j] = board_colour;
            rows_count[row] = 0;
        } 

        //shifting the upper rows down
        for(int i = full_rows[0] - 1; i >= 0; i--)
        {
            if(rows_count[i] == 0)
                break;
            int new_row = i + count;
            rows_count[new_row] = rows_count[i];
            rows_count[i] = 0;
            for(int j = 0; j < 10; j++)
            {
                game_board[new_row][j] = game_board[i][j];
                game_board[i][j] = board_colour;
            }
        }
    }



    
}
