import java.io.File

typealias Coord = Pair<Int, Int>

data class Heatmap(val points: Map<Coord, Int>) {
    private fun isLow(coord: Coord): Boolean {
        val neighbors = listOf(
                Coord(coord.first - 1, coord.second),
                Coord(coord.first + 1, coord.second),
                Coord(coord.first, coord.second - 1),
                Coord(coord.first, coord.second + 1))
        return neighbors.all { neighbor ->
            points.get(neighbor)?.let { it > points.get(coord)!! } ?: true
        }
    }

    private fun findLows(): List<Int> {
        return points.keys.filter { isLow(it) }.map { points.get(it)!! }
    }

    fun sumRiskScores(): Int {
        return findLows().map { height -> height + 1 }.sum()
    }

    companion object {
        fun parse(filename: String): Heatmap {
            val map = File(filename).readLines().mapIndexed { i, line ->
                line.toCharArray().mapIndexed { j, height ->
                    Pair(i, j) to Integer.parseInt(height.toString())
                }
            }.flatten().toMap()
            return Heatmap(map)
        }
    }
}

var heatmap = Heatmap.parse("test")
check(heatmap.sumRiskScores() == 15)

heatmap = Heatmap.parse("data")
var answer = heatmap.sumRiskScores()
println("Part 1: $answer")



