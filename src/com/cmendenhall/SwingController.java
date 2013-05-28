package com.cmendenhall;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols.X;
public class SwingController extends GameController implements Controller {

    public SwingController(View view) {
        super(view);
    }
}
