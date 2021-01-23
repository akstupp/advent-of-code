import java.io.File

typealias Time = Long
typealias BusID = Int

fun parseInput(filename: String): Pair<Time, List<BusID>> {
    var lines = File(filename).readLines()
    val time = lines[0].toLong()
    val busIDs = lines[1].split(',').filter { it[0] != 'x' }.map { it.toInt() }
    return Pair(time, busIDs)
}

fun findEarliestBus(time: Time, buses: List<BusID>): Long {
    var candidates = mutableListOf<Pair<BusID, Time>>()
    for (bus in buses) {
        val min = time / bus
        candidates.add(Pair(bus, bus * (min + 1)))
    }
    candidates.sortBy { it.second }
    val earliest = candidates.first { it.second > time }
    return earliest.first * (earliest.second - time)
}

var (departure, buses) = parseInput("13_input")
var calculation = findEarliestBus(departure, buses)
println("Part 1: $calculation")
