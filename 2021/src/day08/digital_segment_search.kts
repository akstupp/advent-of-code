import java.io.File

// technically should be ~ordered but i'm lazy and the kotlin implementation
// uses a linked hash set
typealias Signal = Set<Char>

val chars = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
val displayMap = mapOf(
        "abcefg" to 0,
        "cf" to 1,
        "acdeg" to 2,
        "acdfg" to 3,
        "bcdf" to 4,
        "abdfg" to 5,
        "abdefg" to 6,
        "acf" to 7,
        "abcdefg" to 8,
        "abcdfg" to 9
)

data class Display(val unique: List<Signal>, val output: List<Signal>) {
    fun one() = unique.find { it.size == 2 }!!
    fun four() = unique.find { it.size == 4 }!!
    fun seven() = unique.find { it.size == 3 }!!
    fun eight() = unique.find { it.size == 7 }!!
    fun uniqueOutputs() = output.count { signal ->
        signal in listOf(one(), four(), seven(), eight())
    }
}

fun parseDisplays(filename: String): List<Display> {
    return File(filename).readLines().map { line ->
        val split = line.split(" ")
                .map { sig -> sig.toCharArray().toSet() }
        val uniqueDisplays = split.take(10)
        val output = split.takeLast(4)
        Display(uniqueDisplays, output)
    }
}

fun countUniqueSegments(displays: List<Display>): Int {
    return displays.map { it.uniqueOutputs() }.sum()
}

var displays = parseDisplays("test")
check(countUniqueSegments(displays) == 26)
// TODO(part two): check(sumOutputs(displays) == 61229)

displays = parseDisplays("data")
val answer = countUniqueSegments(displays)
println("Part 1: $answer")