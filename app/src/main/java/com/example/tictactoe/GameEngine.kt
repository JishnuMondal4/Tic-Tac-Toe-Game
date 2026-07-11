package com.example.tictactoe

/**
 * Marker for each cell on the board.
 */
enum class Player {
    X, O;

    fun other(): Player = if (this == X) O else X
}

/**
 * All possible winning lines on a 3x3 board, expressed as cell indices (0..8).
 */
private val WIN_LINES = listOf(
    intArrayOf(0, 1, 2),
    intArrayOf(3, 4, 5),
    intArrayOf(6, 7, 8),
    intArrayOf(0, 3, 6),
    intArrayOf(1, 4, 7),
    intArrayOf(2, 5, 8),
    intArrayOf(0, 4, 8),
    intArrayOf(2, 4, 6)
)

data class GameResult(
    val winner: Player?,
    val winningLine: IntArray?,
    val isDraw: Boolean
)

/**
 * Pure game-logic holder for a 3x3 Tic Tac Toe board.
 * board[i] is null for an empty cell, otherwise the Player occupying it.
 */
class GameEngine {

    val board: Array<Player?> = arrayOfNulls(9)
    var currentPlayer: Player = Player.X
        private set
    var result: GameResult? = null
        private set

    /** Attempts to place the current player's mark at [index]. Returns true if the move was applied. */
    fun play(index: Int): Boolean {
        if (result != null) return false
        if (index !in 0..8) return false
        if (board[index] != null) return false

        board[index] = currentPlayer
        result = evaluate()
        if (result == null) {
            currentPlayer = currentPlayer.other()
        }
        return true
    }

    fun reset(startingPlayer: Player = Player.X) {
        for (i in board.indices) board[i] = null
        currentPlayer = startingPlayer
        result = null
    }

    private fun evaluate(): GameResult? {
        for (line in WIN_LINES) {
            val (a, b, c) = Triple(board[line[0]], board[line[1]], board[line[2]])
            if (a != null && a == b && b == c) {
                return GameResult(winner = a, winningLine = line, isDraw = false)
            }
        }
        val isFull = board.none { it == null }
        return if (isFull) GameResult(winner = null, winningLine = null, isDraw = true) else null
    }
}
