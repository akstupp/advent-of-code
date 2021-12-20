import java.io.File

typealias Coord = Pair<Int, Int>

data class Segment(val sx: Int, val sy: Int, val dx: Int, val dy: Int) {
    fun plotPoints(diagonals: Boolean): List<Coord> {
        var points: MutableList<Coord> = mutableListOf()
        val xRange = UIntRange(minOf(sx, dx), maxOf(sx, dx))
        val yRange = UIntRange(minOf(sy, dy), maxOf(sy, dy))
        if (sx == dx || sy == dy) {
            for (i in xRange)
                for (j in yRange)
                    points.add(Coord(i, j))
        } else {
            if (diagonals) {
                val yslope = if (dy > sy) 1 else -1
                val xslope = if (dx > sx) 1 else -1
                var coord = Coord(sx, sy)
                while (coord != Coord(dx, dy)) {
                    points.add(coord)
                    coord = Coord(coord.first + xslope, coord.second + yslope)
                }
                points.add(coord)
            }
        }
        return points
    }
}

fun parseSegments(filename: String): List<Segment> {
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

fun countOccurences(segments: List<Segment>, diagonals: Boolean): Int {
    return segments.map { segment -> segment.plotPoints(diagonals) }
            .flatten().groupingBy { it }.eachCount().values.count { it > 1 }
}

var segments = parseSegments("test")
check(countOccurences(segments, false) == 5)
check(countOccurences(segments, true) == 12)

segments = parseSegments("data")
println(countOccurences(segments, false))
println(countOccurences(segments, true))


