import java.io.File
import java.nio.charset.Charset
import java.util.Comparator
import kotlin.math.absoluteValue

fun parse(filename: String): List<Int> {
    return File(filename).readText(Charset.defaultCharset())
            .split(",")
            .map { it.toInt() }
}

fun findCheapestCrabDistance(crabs: List<Int>, cumulative: Boolean): Int {
    val range = IntRange(0, crabs.max()!!)
    val hammingDistancesMap: Map<Int, List<Int>> = range.associateWith { crab ->
        crabs.map { crab.minus(it).absoluteValue }
    }
    return hammingDistancesMap.mapValues {
        it.value.map { dist ->
            if (cumulative)
                (dist * (dist + 1)) / 2
            else
                dist
        }.sum()
    }.minBy { it.value }!!.value
}

var crabs: List<Int> = parse("test")
check(findCheapestCrabDistance(crabs, cumulative = false) == 37)
check(findCheapestCrabDistance(crabs, cumulative = true) == 168)

crabs = parse("data")
var answer = findCheapestCrabDistance(crabs, cumulative = false)
println("Part 1: $answer")
answer = findCheapestCrabDistance(crabs, cumulative = true)
println("Part 2: $answer")