import java.io.File
import java.nio.charset.Charset

// Returns a map of counts of each fish in the input
fun parseFish(filename: String): Map<Int, Int> {
    return File(filename).readText(Charset.defaultCharset())
            .split(",")
            .map { it.toInt() }
            .groupingBy { it }
            .eachCount()
}

fun spawn(iterations: Int, dayToCount: Map<Int, Int>): Long {
    val spawnCounts: MutableMap<Int, Long> = IntRange(0, 8)
            .associate { it to 0L }
            .toMutableMap()

    dayToCount.forEach { (day, count) ->
        spawnCounts[day] = count.toLong()
    }

    for (i in 1..iterations) {
        val newbornFish = spawnCounts[0]!!
        spawnCounts.keys.forEach { fish ->
            if (fish != 0)
                spawnCounts[fish - 1] = spawnCounts[fish]!!
        }
        // Add the newborns to the countdown
        spawnCounts[8] = newbornFish

        // Reset those that have finished
        spawnCounts.merge(6, newbornFish) { a, b -> a + b }
    }
    return spawnCounts.values.sum()
}

var input = parseFish("test")
check(spawn(80, input) == 5934L)
check(spawn(256, input) == 26984457539L)

input = parseFish("data")
val part1 = spawn(80, input)
println("Part 1: $part1")

val part2 = spawn(256, input)
println("Part 2: $part2")