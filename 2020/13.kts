import java.io.File

typealias Timestamp = Long
typealias BusID = Int
typealias Index = Int

fun parseLines(filename: String): List<String> {
    return File(filename).readLines()
}

fun parseTimestamp(filename: String): Timestamp {
    return parseLines(filename).get(0).toLong()
}

fun parseBusSchedule(filename: String): List<Pair<Index, BusID>> {
    return parseLines(filename)
            .get(1)
            .split(',')
            .mapIndexed { index, s -> Pair(index, s) }
            .filter {it.second[0] != 'x'}
            .map { Pair(it.first, it.second.toInt()) }
}

fun findEarliestBus(time: Timestamp, buses: List<Pair<Index, BusID>>): Long {
    var candidates = mutableListOf<Pair<BusID, Timestamp>>()
    for (bus in buses.map { it.second }) {
        val next = (time / bus) + 1
        candidates.add(Pair(bus, bus * next))
    }
    candidates.sortBy { it.second }
    val earliest = candidates.first { it.second > time }
    return earliest.first * (earliest.second - time)
}

fun findChainedBusSchedule(buses: List<Pair<Index, BusID>>): Long {
    var d = 1L
    var i = 0L
    for ((offset, bus) in buses) {
        while (true) {
            i += d
            if ((i + offset) % bus == 0L) {
                d = d * bus
                break
            }
        }
    }
    return i
}

var file = "13_input"
var timestamp = parseTimestamp("13_input")
var busSchedule = parseBusSchedule("13_input")
var earliestBus = findEarliestBus(timestamp, busSchedule)
println("Part 1: $earliestBus")
var calculation = findChainedBusSchedule(busSchedule)
println("Part 2: $calculation")
