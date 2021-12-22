import java.io.File
import java.util.ArrayDeque

typealias Coord = Pair<Int, Int>

data class Heatmap(val points: Map<Coord, Int>) {
    private fun neighbors(coord: Coord): List<Coord> {
        return listOf(
                Coord(coord.first - 1, coord.second),
                Coord(coord.first + 1, coord.second),
                Coord(coord.first, coord.second - 1),
                Coord(coord.first, coord.second + 1))
    }

    private fun isLow(coord: Coord): Boolean {
        return neighbors(coord).all { neighbor ->
            points.get(neighbor)?.let { it > points.get(coord)!! } ?: true
        }
    }

    private fun lows(): Map<Coord, Int> {
        return points.keys.filter { isLow(it) }
                .map { it to points.get(it)!! }.toMap()
    }

    private fun basins(): List<List<Coord>> {
        val basins = mutableListOf<List<Coord>>()
        lows().keys.forEach { low ->
            val basin = mutableListOf<Coord>(low)
            val explorationSet = ArrayDeque<Coord>(listOf(low))
            while (explorationSet.isNotEmpty()) {
                val coord = explorationSet.removeFirst()
                neighbors(coord).forEach { neighbor ->
                    val neighborIncreasing = points[neighbor]?.let {
                        it > points[coord]!!
                    } ?: false
                    if (neighborIncreasing && points[neighbor]!! != 9 && !basin.contains(neighbor)) {
                        basin.add(neighbor)
                        explorationSet.addLast(neighbor)
                    }
                }
            }
            basins.add(basin)
        }
        return basins
    }

    fun sumRiskScores(): Int {
        return lows().values.map { height -> height + 1 }.sum()
    }

    fun produceLargestBasins(): Int {
        return basins().map { it.size }.sorted().takeLast(3)
                .reduce { a, b -> a * b }
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
check(heatmap.produceLargestBasins() == 1134)

heatmap = Heatmap.parse("data")
var answer = heatmap.sumRiskScores()
println("Part 1: $answer")
answer = heatmap.produceLargestBasins()
println("Part 2: $answer")



