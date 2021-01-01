import java.io.File
import kotlin.math.abs
import kotlin.math.min

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

fun maxBoardingPass(passes: List<String>): Int {
    var max = 0
    for (pass in passes) {
        val result = evaluateBoardingPass(pass)
        if (result > max) {
            max = result
        }
    }
    return max
}

fun findMySeat(passes: List<String>): Int {
    var ids: List<Int> = passes.map { evaluateBoardingPass(it) }.sorted()
    for (index in 0..ids.size) {
        val thisId = ids.get(index)
        val over = ids.get(index + 1)
        if (thisId + 1 != over) {
            return thisId + 1
        }
    }
    return -1
}

fun evaluateBoardingPass(pass: String): Int {
    var rowDirections = pass.substring(0..6)
    var colDirections = pass.substring(7..9)
    var rowPos = evaluate(rowDirections, 0, 127, 'F', 'B')
    var colPos = evaluate(colDirections, 0, 7, 'L', 'R')
    return (rowPos * 8) + colPos
}

fun evaluate(pass: String, min: Int, max: Int, lower: Char, upper: Char): Int {
    if (pass.startsWith(lower)) {
        return evaluate(pass.removePrefix(lower.toString()), min, min + (max - min) / 2, lower, upper)
    }
    if (pass.startsWith(upper)) {
        return evaluate(pass.removePrefix(upper.toString()), min + (max - min) / 2 + 1, max, lower, upper)
    }
    if (abs(min - max) == 1 || min == max) return min(min, max)
    return -1
}

var boardingPasses: List<String> = readInputFile("5_input")
var maxSeatId = maxBoardingPass(boardingPasses)
var mySeat = findMySeat(boardingPasses)
println(maxSeatId)
println(mySeat)

