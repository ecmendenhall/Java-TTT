package com.cmendenhall.tests;


import com.cmendenhall.utils.OutputRecorder;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class TicTacToeTest {

    protected OutputRecorder recorder;

    protected void setUpRecorder() throws UnsupportedEncodingException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        recorder = new OutputRecorder(output, true, "UTF-8");
    }

    protected void startRecorder() {
        System.setOut(recorder);
        System.setErr(recorder);
    }

    @Before
    public void recorderSetUp() throws UnsupportedEncodingException {
        setUpRecorder();
    }

}
