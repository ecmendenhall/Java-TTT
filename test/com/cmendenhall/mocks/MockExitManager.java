package com.cmendenhall.mocks;

import com.cmendenhall.utils.ExitManager;

public class MockExitManager extends ExitManager {
    private boolean exitWasCalled = false;

    public void exit() {
        exitWasCalled = true;
    }

    public boolean exitWasCalled() {
        return exitWasCalled;
    }

}
