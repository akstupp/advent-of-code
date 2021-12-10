import java.io.File

fun countIncrements(depths: List<Int>): Int {
    return depths.windowed(2).count { a, b -> a < b }
}

fun countIncreasingWindows(depths: List<Int>): Int {
    val sumList = depths.windowed(3, 1)
            .map { window -> window.sum() }
    return countIncrements(sumList)
}

val depths = File("data").readLines().map { it.toInt() }
// Part 1
val incrementCount = countIncrements(depths)
println("Part One: $incrementCount")
// Part 2
val slidingWindowCount = countIncreasingWindows(depths)
println("Part Two: $slidingWindowCount")

