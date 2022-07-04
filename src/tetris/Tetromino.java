package tetris;

import java.awt.*;
import javax.swing.*;
import java.util.Hashtable;

class Tetromino 
{
    //This code is executed only once, i.e when the class is loaded into the memory
    static enum Shape {O_shape, I_shape, J_shape, L_shape, S_shape, Z_shape, T_shape};
    static Hashtable<Shape, Color> Colours = new Hashtable<Shape, Color>();
    static
    {
        Colours.put(Shape.O_shape, Color.RED);
        Colours.put(Shape.I_shape, Color.BLUE);
        Colours.put(Shape.J_shape, Color.GREEN);
        Colours.put(Shape.L_shape, Color.YELLOW);
        Colours.put(Shape.S_shape, Color.PINK);
        Colours.put(Shape.Z_shape, Color.ORANGE);
        Colours.put(Shape.T_shape, Color.MAGENTA);
    }

    //Tetromino instance variables
    Shape piece;
    Color piece_colour;
    int curr_orientation;
    int possible_orientations[][][];
    int currPos[];
    int maxLeft, maxRight, maxDown;

    //Constructor
    Tetromino(Shape shape)
    {
        piece = shape;
        piece_colour = Colours.get(shape);
        
        switch(piece)
        {
            case O_shape:
                initOshape();
                break;
            case I_shape:
                initIshape();
                break;
            case J_shape:
                initJshape();
                break;
            case L_shape:
                initLshape();
                break;
            case S_shape:
                initSshape();
                break;
            case Z_shape:
                initZshape();
                break;
            case T_shape:
                initTshape();
                break;
        }

    }

    //Functions used to initialize different tetromino shapes
    void initOshape()
    {

    }

    void initIshape()
    {

    }

    void initJshape()
    {

    }

    void initLshape()
    {

    }

    void initSshape()
    {

    }

    void initZshape()
    {

    }

    void initTshape()
    {

    }
   

}
