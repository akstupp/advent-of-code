import java.io.File
import java.util.*
import kotlin.Comparator

typealias Coord = Pair<Int, Int>

data class Risk(
    val coord: Coord,
    val weight: Int,
    var distance: Int = Int.MAX_VALUE - 10,
    var isProcessed: Boolean = false
) {
    fun recalculateMin(neighbors: List<Risk>) {
        val min = neighbors.minOf { it.distance } + this.weight
        if (this.distance > min) this.distance = min
    }

    fun updateNeighbors(neighbors: List<Risk>) {
        neighbors.forEach { it.recalculateMin(listOf(this)) }
    }
}

data class Cavern(val cave: List<List<Risk>>) {

    fun cheapestPath(): Int {
        val start = cave.first().first()
        start.distance = 0
        val end = cave.last().last()
        val distComparator: Comparator<Risk> = compareBy { it.distance }
        val queue = PriorityQueue(distComparator)
        queue.add(start)
        while (queue.isNotEmpty() && end.isProcessed.not()) {
            val risk = queue.remove()
            if (risk.isProcessed.not()) {
                val neighbors = getNeighbors(risk)
                risk.recalculateMin(neighbors)
                neighbors.filterNot { it.isProcessed }.let {
                    risk.updateNeighbors(it)
                    queue.addAll(it)
                }
                risk.isProcessed = true
            }
        }
        return end.distance
    }

    fun getNeighbors(risk: Risk): List<Risk> {
        val coords = listOf<Coord>(
            risk.coord.first + 1 to risk.coord.second,
            risk.coord.first - 1 to risk.coord.second,
            risk.coord.first to risk.coord.second + 1,
            risk.coord.first to risk.coord.second - 1
        )
        return coords.map { cave.getOrNull(it.first)?.getOrNull(it.second) }
            .filterNotNull()
    }

    companion object {
        fun parse(filename: String, expand: Boolean = false): Cavern {
            var risks = File(filename).readLines()
                .map { it.toList().map { it.digitToInt() } }
            if (expand) {
                risks = expandCave(risks)
            }
            var matrix = risks.mapIndexed { i, row ->
                row.toList()
                    .mapIndexed { j, d -> Risk(Coord(i, j), d) }
            }
            return Cavern(matrix)
        }

        private fun expandCave(cave: List<List<Int>>): List<List<Int>> {
            val full = cave.map { expandLine(it) }.toMutableList()
            val batchSize = full.size
            repeat(4) {
                val last =
                    if (full.size > batchSize) full.takeLast(batchSize) else full.toList()
                last.forEach { full.add(it.map { num -> transform(num) }) }
            }
            return full
        }

        private fun expandLine(line: List<Int>): List<Int> {
            var mutable = mutableListOf(line)
            repeat(4) {
                mutable.add(mutable.last().map { transform(it) })
            }
            return mutable.flatten()
        }

        fun transform(risk: Int): Int {
            return (risk + 1).let { if (it == 10) 1 else it }
        }
    }
}

var cavern = Cavern.parse("test")
var answer = cavern.cheapestPath()
check(answer == 40)
cavern = Cavern.parse("test", true)
answer = cavern.cheapestPath()
check(answer == 315)

cavern = Cavern.parse("data")
answer = cavern.cheapestPath()
println("Part One: $answer")
cavern = Cavern.parse("data", true)
answer = cavern.cheapestPath()
println("Part Two: $answer")