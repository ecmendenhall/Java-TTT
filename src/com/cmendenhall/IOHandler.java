package com.cmendenhall;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public abstract class IOHandler {

    public abstract IOHandler printf(String fmt, Object... params)
            throws IOException;

    public abstract String readLine() throws IOException;

    public abstract String readLine(String message) throws IOException;

    public abstract char[] readPassword() throws IOException;

    public abstract Reader reader() throws IOException;

    public abstract PrintWriter writer() throws IOException;

    public abstract void setTestInput(String input) throws Exception;

}
