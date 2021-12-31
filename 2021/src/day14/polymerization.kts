import java.io.File

typealias Rule = Pair<Char, Char>

data class Polymer(val chain: List<Char>, val insertions: Map<Rule, Char>) {

    fun grow(iterations: Int): Long {
        val counts = chain.toSet().associateWith { a ->
            chain.count { b -> b == a }.toLong()
        }.toMutableMap()
        val pairs =
            chain.windowed(2).associateBy<List<Char>, Pair<Char, Char>, Long>(
                keySelector = { it[0] to it[1] },
                valueTransform = { k ->
                    chain.windowed(2).count { it == k }.toLong()
                }
            ).toMutableMap()
        for (i in 1..iterations) {
            pairs.filter { it.value > 0 }.forEach { pair ->
                insertions[pair.key]?.let {
                    pairs.merge(
                        pair.key.first to it,
                        pair.value
                    ) { a, b -> a + b }
                    pairs.merge(
                        it to pair.key.second,
                        pair.value
                    ) { a, b -> a + b }
                    pairs.merge(pair.key, pair.value * -1) { a, b -> a + b }
                    counts.merge(it, pair.value) { a, b -> a + b }
                }
            }
        }
        return counts.maxOf { it.value } - counts.minOf { it.value }
    }

    companion object {
        fun parse(filename: String): Polymer {
            val lines = File(filename).readLines()
            val chain = lines.first().toCharArray()
            val insertions = lines.takeLastWhile {
                it.matches(Regex("\\w+ -> \\w+"))
            }.map { line ->
                line.toCharArray().filter { it.isLetter() }
            }.map { chars ->
                Rule(chars[0], chars[1]) to chars[2]
            }.toMap()
            return Polymer(LinkedList<Char>(chain.toList()), insertions)
        }
    }
}

var polymer = Polymer.parse("test")
check(polymer.grow(10) == 1588L)
check(polymer.grow(40) == 2188189693529)

polymer = Polymer.parse("data")
var answer = polymer.grow(10)
println("Part One: $answer")
answer = polymer.grow(40)
println("Part Two: $answer")



