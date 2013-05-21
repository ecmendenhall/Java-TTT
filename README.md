# Java Tic-Tac-Toe

A TDD rewrite of [ClojureScript Tic-Tac-Toe](https://github.com/ecmendenhall/clojurescript-tic-tac-toe/) in Java.

[![Build Status](https://travis-ci.org/ecmendenhall/Java-TTT.png)](https://travis-ci.org/ecmendenhall/Java-TTT)

## Build instructions
This project includes an [ant](https://ant.apache.org/) buildfile and
manages dependencies with [ivy](https://ant.apache.org/ivy/). To build
and run on your own machine, first clone this git repository:

    $ git clone git://github.com/ecmendenhall/Java-TTT.git

Make sure you've got ant installed:

    $ which ant

If ant isn't found, check out the installation instructions [here](https://ant.apache.org/manual/index.html). To build the project with ant:

    $ cd Java-TTT
    $ ant

Then, to run the bundled JUnit tests:

    $ ant run-tests

To run the Tic Tac Toe game, navigate to build/jar, then run:

    $ java -jar TicTacToe

