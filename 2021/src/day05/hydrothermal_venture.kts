import java.io.File

typealias Coord = Pair<Int, Int>

data class Segment(val start: Coord, val end: Coord) {
    fun plotPoints(diagonals: Boolean): List<Coord> {
        var points: MutableList<Coord> = mutableListOf()
        if (start.first == end.first) {
            val min = minOf(start.second, end.second)
            val max = maxOf(start.second, end.second)
            for (index in min..max) {
                points.add(Coord(start.first, index))
            }
        } else if (start.second == end.second) {
            val min = minOf(start.first, end.first)
            val max = maxOf(start.first, end.first)
            for (index in min..max) {
                points.add(Coord(index, start.second))
            }
        } else {
            if (diagonals) {
                val yslope = if (end.second > start.second) 1 else -1
                val xslope = if (end.first > start.first) 1 else -1
                var coord = start
                while (coord != end) {
                    points.add(coord)
                    coord = Coord(coord.first + xslope, coord.second + yslope)
                }
                points.add(coord)
            }
        }
        return points
    }
}

fun parseInput(filename: String): List<Segment> {
    return File(filename).readLines().map { line ->
        Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")
                .matchEntire(line)!!
                .destructured
                .let { (xi, yi, xj, yj) ->
                    Segment(Coord(xi.toInt(), yi.toInt()),
                            Coord(xj.toInt(), yj.toInt()))
                }
    }
}

fun plotOccurences(segments: List<Segment>, diagonals: Boolean): Map<Coord, Int> {
    return segments.map { segment -> segment.plotPoints(diagonals) }
            .flatten().groupingBy { it }.eachCount()
}

fun countOccurences(segments: List<Segment>, diagonals: Boolean): Int {
    return plotOccurences(segments, diagonals).values.count { it > 1 }
}

var segments = parseInput("test")
check(countOccurences(segments, false) == 5)
check(countOccurences(segments, true) == 12)

segments = parseInput("data")
println(countOccurences(segments, false))
println(countOccurences(segments, true))


