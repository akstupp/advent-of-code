import java.io.File

data class BingoEntry(var number: Int, var marked: Boolean)

class BingoBoard(var board: MutableList<List<BingoEntry>>) {
    fun addRow(row: List<BingoEntry>) {
        board.add(row)
    }

    fun hasWon(): Boolean {
        for (row in board) {
            if (row.all { entry -> entry.marked })
                return true
        }
        for (col in board[0].indices) {
            if (board.all { row -> row[col].marked })
                return true
        }
        return false
    }

    fun markValue(number: Number) {
        for (row in board) {
            val found = row.find { entry -> entry.number == number }
            found?.marked = true
        }
    }
}

data class Game(val boards: List<BingoBoard>, val numbers: List<Int>)

fun parseInput(filename: String): Game {
    val lines = File(filename).readLines()
    val numbers = lines.first().split(",").map { it.toInt() }
    // Second line is whitespace
    val boardInput = lines.drop(2)
    val boards: MutableList<BingoBoard> = mutableListOf()
    var currentBoard = BingoBoard(mutableListOf())
    for (line in boardInput) {
        if (line.isEmpty()) {
            boards.add(currentBoard)
            currentBoard = BingoBoard(mutableListOf())
            continue
        }
        val entries: List<BingoEntry> = line.split(Regex("[ /t]+"))
                .filter { it.isNotBlank() }
                .map { str -> BingoEntry(str.toInt(), false) }
        currentBoard.addRow(entries)
    }
    boards.add(currentBoard)
    return Game(boards, numbers)
}

fun playGame(game: Game, greedy: Boolean): Int {
    val candidates = game.boards.toMutableList()
    var iterator = candidates.iterator()
    var boardScore = 0
    for (pick in game.numbers) {
        while (iterator.hasNext()) {
            val board = iterator.next()
            board.markValue(pick)
            if (board.hasWon()) {
                val sum = board.board.flatten().sumBy {
                    if (it.marked) 0 else it.number
                }
                boardScore = sum * pick
                if (greedy)
                    return boardScore
                iterator.remove()
            }
        }
        iterator = candidates.iterator()
    }
    return boardScore
}

var game = parseInput("test")
check(playGame(game, true) == 4512)
check(playGame(game, false) == 1924)

game = parseInput("data")
val one = playGame(game, true)
val two = playGame(game, false)
println("Part 1: $one")
println("Part 2: $two")


