package tetris;

import java.awt.Graphics;

import javax.swing.*;
import java.awt.*;

class Board extends JPanel
{
    private int SQUARE_SIZE;
    private Color board_colour = Color.LIGHT_GRAY;
    private Color grid_colour = Color.GRAY;

    protected Color gameBoard[][];

    Board(int square_size)
    {
        SQUARE_SIZE = square_size;
        setBackground(board_colour);

        //initializing gameboard
        gameBoard = new Color[20][10];
        for(int row = 0; row < 20; row++)
        {
            for(int column = 0; column < 10; column++)
                gameBoard[row][column] = board_colour;
        }
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        paintGrid(g);
    }

    void paintGrid(Graphics g)
    {
        g.setColor(grid_colour);
        for(int i = 0; i < 20; i++)
        {
            for(int j = 0; j < 10; j++)
                g.drawRoundRect(SQUARE_SIZE*j, SQUARE_SIZE*i, SQUARE_SIZE, SQUARE_SIZE, 5, 5);
        }
    }
}
