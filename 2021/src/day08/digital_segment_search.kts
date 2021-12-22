import java.io.File

typealias Signal = Set<Char>

data class Display(val unique: List<Signal>, val output: List<Signal>) {
    private fun one() = unique.find { it.size == 2 }!!
    private fun four() = unique.find { it.size == 4 }!!
    private fun seven() = unique.find { it.size == 3 }!!
    private fun eight() = unique.find { it.size == 7 }!!
    fun uniqueOutputs() = output.count { signal ->
        signal in listOf(one(), four(), seven(), eight())
    }

    // Decode the unique signals by deduction - deduced by hand
    private fun decode(): Map<Signal, Int> {
        val index = mutableMapOf<Signal, Int>()
        index.put(one(), 1)
        index.put(four(), 4)
        index.put(seven(), 7)
        index.put(eight(), 8)

        // keep a map from the original signal to the mutated signal to place
        // in the index
        val fives = unique.filter { it.size == 5 }
                .associateWith { sig -> sig.toMutableSet() }
                .toMutableMap()
        var common = fives.values.reduce { prev, set ->
            prev.intersect(set).toMutableSet()
        }
        fives.values.forEach { candidate -> candidate.removeAll(common) }
        val two = fives.entries.single { !four().containsAll(it.value) }
        fives.remove(two.key)
        index.put(two.key, 2)
        val three = fives.entries.single { one().containsAll(it.value) }
        fives.remove(three.key)
        index.put(three.key, 3)
        index.put(fives.entries.single().key, 5)

        val sixes = unique.filter { it.size == 6 }
                .associateWith { sig -> sig.toMutableSet() }
                .toMutableMap()
        common = sixes.values.reduce { prev, set ->
            prev.intersect(set).toMutableSet()
        }
        sixes.values.forEach { it.removeAll(common) }
        val nine = sixes.entries.single { four().containsAll(it.value) }
        sixes.remove(nine.key)
        index.put(nine.key, 9)
        val zero = sixes.entries.single { one().intersect(it.value).isNotEmpty() }
        sixes.remove(zero.key)
        index.put(zero.key, 0)
        index.put(sixes.entries.single().key, 6)
        return index
    }

    fun decodeOutput(): Int {
        val mapping = decode()
        val builder = StringBuilder()
        for (signal in output) {
            val target = mapping[signal]!!
            builder.append(target)
        }
        return builder.toString().toInt()
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

fun sumOutputs(displays: List<Display>): Int {
    return displays.map { it.decodeOutput() }.sum()
}

var displays = parseDisplays("test")
check(countUniqueSegments(displays) == 26)
check(sumOutputs(displays) == 61229)

displays = parseDisplays("data")
var answer = countUniqueSegments(displays)
println("Part 1: $answer")
answer = sumOutputs(displays)
println("Part 2: $answer")
