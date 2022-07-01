package tetris;

import javax.swing.*;
import java.awt.*;
import java.lang.Math;

class NextPieceDisplay extends JPanel
{
    private int SQUARE_SIZE; 
    private Color background_Color = Color.LIGHT_GRAY;
    //next piece variable
    //piece colour
    NextPieceDisplay()
    {
        setBackground(background_Color);
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        SQUARE_SIZE = getWidth() / 4;
        super.paintComponent(g);
        g.setColor(Color.PINK);
        paintGrid(g);  
    }

    void paintGrid(Graphics g)
    {
        g.setColor(Color.DARK_GRAY);
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
                g.drawRoundRect(i*SQUARE_SIZE, j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, 5, 5);
        }
    }

    //change current piece method
    //paint piece method
}