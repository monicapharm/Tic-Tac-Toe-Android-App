package com.liuqingma.connect3;

import java.util.Arrays;

/**
 * Created by liuqingma on 12/30/17.
 */
class Best {
    int move, score;
    public Best(int move, int score) {
        this.move = move;
        this.score = score;
    }
}

public class ComputerGo {
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {2,4,6}, {0,4,8}};

    public int isWin(int[] gameState) {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                return gameState[winningPosition[0]];
            }
        }
        return 2;
    }

    public int getMove (int[] gameState) {

        Best bestMove = chooseMove(gameState, 1, -1, 1);

        return bestMove.move;

    }

    private Best chooseMove(int[] gameState, int activePlayer, int alpha, int beta) {
        int winningState = isWin(gameState);
        if (winningState != 2) {
            int score = (winningState == 0) ? -1 : 1;
            return new Best(-1, score);
        }

        int grid = 0;
        for (int i = 0; i < 9; i++) {
            if (gameState[i] != 2) {
                grid++;
            } else {
                break;
            }
        }
        if (grid == 9) {
            return new Best(-1, 0);
        }

        Best myBest = new Best(-1, activePlayer == 1 ? alpha : beta);
        Best reply;

        int[] grids = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        for (int i : grids) {
            if (gameState[i] == 2 && activePlayer == 1) {
                gameState[i] = 1;
                reply = chooseMove(gameState, 0, alpha, beta);
                if (reply.score >= myBest.score) {
                    myBest.move = i;
                    myBest.score = reply.score;
                    alpha = reply.score;
                }
                gameState[i] = 2;
            }

            if (gameState[i] == 2 && activePlayer == 0) {
                gameState[i] = 0;
                reply = chooseMove(gameState, 1, alpha, beta);
                if (reply.score <= myBest.score) {
                    myBest.move = i;
                    myBest.score = reply.score;
                    beta = reply.score;
                }
                gameState[i] = 2;
            }

            if (alpha >= beta) {
                return myBest;
            }
        }

        printLog(gameState, activePlayer, myBest, alpha, beta);

        return myBest;
    }

    private void printLog(int[] gameState, int activePlayer, Best myBest, int alpha, int beta) {
        System.out.println(new StringBuilder().append(myBest.move)
                .append('\t')
                .append(myBest.score)
                .append('\t')
                .append(activePlayer)
                .append('\t')
                .append(Arrays.toString(gameState).toString())
                .append('\t')
                .append(alpha)
                .append('\t')
                .append(beta)
        );
    }
}