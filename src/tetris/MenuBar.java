package tetris;

import javax.swing.*;

class MenuBar extends JMenuBar
{   
    JMenu game;
    JMenuItem newGame, pause, resume, exit;

    JMenu help;
    JMenuItem instructions;

    MenuBar()
    {
        //Creating menus
        game = new JMenu("Game");
        help = new JMenu("Help");

        //Adding the menus to the menubar
        add(game);
        add(help);
    }
}
