import java.io.File

fun readInputFile(filename: String): List<Int> {
    return File(filename).readLines().map { it.toInt() }
}

fun countIncrements(depths: List<Int>): Int {
    return depths.filterIndexed { index, elem -> depths.elementAtOrNull(index + 1)?.let { it > elem } ?: false }.count()
}

fun countIncreasingWindows(depths: List<Int>): Int {
    val sumList = depths.windowed(3, 1).map { window -> window.sum() }
    return countIncrements(sumList)
}

val input = readInputFile("data")
// Part 1
val incrementCount = countIncrements(input)
println("Part One: $incrementCount")
// Part 2
val slidingWindowCount = countIncreasingWindows(input)
println("Part Two: $slidingWindowCount")

