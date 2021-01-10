import java.io.File

fun parseFile(filename: String): List<Int> {
    return File(filename).readLines().map { s -> s.toInt() }.sorted()
}

fun countVoltageDifferences(adapters: List<Int>): Int {
    var distribution: MutableMap<Int, Int> = mutableMapOf()
    distribution.put(adapters.first(), 1)
    var last = adapters.first()
    for (index in 1..adapters.size - 1) {
        var current = adapters.get(index)
        var diff = current - last
        if (diff > 3) {
            return -1
        }
        if (distribution.containsKey(diff)) {
            distribution.put(diff, distribution.get(diff)!!.plus(1))
        } else {
            distribution.put(diff, 1)
        }
        last = adapters.get(index)

    }
    distribution.put(3, 1 + distribution.get(3)!!)
    return distribution.get(1)!!.times(distribution.get(3)!!)
}

fun countPossibilities(input: List<Long>): Long {
    var cache: MutableMap<Long, Long> = mutableMapOf()
    cache.put(0, 1)
    var adapters = input.toMutableList()
    var target = adapters.last() + 3
    adapters.add(target)
    for (adapter in adapters) {
        cache.put(adapter, cache.getOrDefault(adapter - 3, 0)
        + cache.getOrDefault(adapter - 2, 0)
        + cache.getOrDefault(adapter - 1, 0))
    }
    return cache.get(target)!!
}


var adapters = parseFile("10_input")
var voltageDiff = countVoltageDifferences(adapters)
println(voltageDiff)
var possibilities = countPossibilities(adapters.map { it.toLong() })
println(possibilities)

