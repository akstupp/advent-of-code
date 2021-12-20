import java.io.File
import java.nio.charset.Charset
import java.util.Comparator
import kotlin.math.absoluteValue

fun parseCrabs(filename: String): List<Int> {
    return File(filename).readText(Charset.defaultCharset())
            .split(",")
            .map { it.toInt() }
}

fun minimalHammingDistance(crabs: List<Int>, cumulative: Boolean): Int {
    return IntRange(0, crabs.max()!!).map { crab ->
        crabs.map {
            val diff = crab.minus(it).absoluteValue
            if (cumulative) (diff * (diff + 1)) / 2 else diff
        }.sum()
    }.min()!!
}

var crabs: List<Int> = parseCrabs("test")
check(minimalHammingDistance(crabs, cumulative = false) == 37)
check(minimalHammingDistance(crabs, cumulative = true) == 168)

crabs = parseCrabs("data")
var answer = minimalHammingDistance(crabs, cumulative = false)
println("Part 1: $answer")
answer = minimalHammingDistance(crabs, cumulative = true)
println("Part 2: $answer")