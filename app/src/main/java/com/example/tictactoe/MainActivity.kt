package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.DrawGray
import com.example.tictactoe.ui.theme.Navy
import com.example.tictactoe.ui.theme.Teal
import com.example.tictactoe.ui.theme.TicTacToeTheme
import com.example.tictactoe.ui.theme.WinHighlight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeScreen()
                }
            }
        }
    }
}

/**
 * Holds UI-facing game state, backed by [GameEngine], plus running score across rounds.
 */
private class GameUiState {
    private val engine = GameEngine()

    var board by mutableStateOf<List<Player?>>(List(9) { null })
        private set
    var currentPlayer by mutableStateOf(Player.X)
        private set
    var result by mutableStateOf<GameResult?>(null)
        private set

    var scoreX by mutableIntStateOf(0)
        private set
    var scoreO by mutableIntStateOf(0)
        private set
    var scoreDraw by mutableIntStateOf(0)
        private set

    private var nextStartingPlayer = Player.X

    fun play(index: Int) {
        if (!engine.play(index)) return
        board = engine.board.toList()
        currentPlayer = engine.currentPlayer
        result = engine.result
        result?.let { onRoundFinished(it) }
    }

    private fun onRoundFinished(r: GameResult) {
        when {
            r.isDraw -> scoreDraw++
            r.winner == Player.X -> scoreX++
            r.winner == Player.O -> scoreO++
        }
    }

    /** Starts a new round; loser/first-player alternates who goes first for fairness. */
    fun newRound() {
        nextStartingPlayer = nextStartingPlayer.other()
        engine.reset(nextStartingPlayer)
        board = engine.board.toList()
        currentPlayer = engine.currentPlayer
        result = null
    }

    fun resetMatch() {
        nextStartingPlayer = Player.X
        engine.reset(Player.X)
        board = engine.board.toList()
        currentPlayer = engine.currentPlayer
        result = null
        scoreX = 0
        scoreO = 0
        scoreDraw = 0
    }
}

@Composable
private fun TicTacToeScreen() {
    val state = remember { GameUiState() }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Tic Tac Toe",
                style = MaterialTheme.typography.headlineMedium,
                color = Navy
            )

            Spacer(Modifier.height(20.dp))
            ScoreBoard(scoreX = state.scoreX, scoreO = state.scoreO, scoreDraw = state.scoreDraw)

            Spacer(Modifier.height(20.dp))
            StatusBanner(currentPlayer = state.currentPlayer, result = state.result)

            Spacer(Modifier.height(20.dp))
            GameBoard(
                board = state.board,
                winningLine = state.result?.winningLine,
                enabled = state.result == null,
                onCellClick = { index -> state.play(index) }
            )

            Spacer(Modifier.height(28.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { state.newRound() },
                    colors = ButtonDefaults.buttonColors(containerColor = Teal),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("New Round")
                }
                OutlinedButton(
                    onClick = { state.resetMatch() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset Match", color = Navy)
                }
            }
        }
    }
}

@Composable
private fun ScoreBoard(scoreX: Int, scoreO: Int, scoreDraw: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ScorePill(label = "X", value = scoreX, color = Navy)
        ScorePill(label = "Draws", value = scoreDraw, color = DrawGray)
        ScorePill(label = "O", value = scoreO, color = Teal)
    }
}

@Composable
private fun ScorePill(label: String, value: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color.copy(alpha = 0.12f))
                .border(1.5.dp, color, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = color)
    }
}

@Composable
private fun StatusBanner(currentPlayer: Player, result: GameResult?) {
    val text = when {
        result == null -> "Turn: ${currentPlayer.name}"
        result.isDraw -> "It's a draw!"
        else -> "${result.winner!!.name} wins!"
    }
    val bg = when {
        result == null -> MaterialTheme.colorScheme.surface
        result.isDraw -> DrawGray.copy(alpha = 0.15f)
        else -> WinHighlight.copy(alpha = 0.35f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = Navy
        )
    }
}

@Composable
private fun GameBoard(
    board: List<Player?>,
    winningLine: IntArray?,
    enabled: Boolean,
    onCellClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(Navy)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in 0 until 3) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    val isWinningCell = winningLine?.contains(index) == true
                    BoardCell(
                        player = board[index],
                        highlighted = isWinningCell,
                        enabled = enabled && board[index] == null,
                        modifier = Modifier.weight(1f),
                        onClick = { onCellClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    player: Player?,
    highlighted: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val cellColor = if (highlighted) WinHighlight else Color.White

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(cellColor)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = player != null) {
            Text(
                text = player?.name ?: "",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = if (player == Player.X) Navy else Teal,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
