import java.io.File

typealias Coord = Pair<Int, Int>

data class Fold(val direction: Char, val value: Int)
data class Paper(var points: MutableSet<Coord>, val folds: List<Fold>) {

    fun origami() {
        folds.forEach { fold(it) }
        val x = points.map { it.first }.maxOrNull()!!
        val y = points.map { it.second }.maxOrNull()!!
        for (j in 0..y) {
            for (i in 0..x) {
                val char = if (points.contains(i to j)) '.' else ' '
                print(char)
            }
            println()
        }
    }

    fun foldFirst(): Int {
        fold(folds.first())
        return points.count()
    }

    fun fold(fold: Fold) {
        val folded = mutableSetOf<Coord>()
        points.forEach { coord ->
            when (fold.direction) {
                'x' -> {
                    if (coord.first > fold.value) {
                        val mirrored =
                            coord.first - (2 * (coord.first - fold.value))
                        folded.add(mirrored to coord.second)
                    } else
                        folded.add(coord)
                }
                'y' -> {
                    if (coord.second > fold.value) {
                        val mirrored =
                            coord.second - (2 * (coord.second - fold.value))
                        folded.add(coord.first to mirrored)
                    } else
                        folded.add(coord)
                }
            }
        }
        points = folded
    }


    companion object {
        fun parse(filename: String): Paper {
            val lines = File(filename).readLines()
            val coords =
                lines.takeWhile { it.matches(Regex("\\d+,\\d+")) }.map {
                    val (x, y) = it.split(",")
                    Pair(x.toInt(), y.toInt())
                }.toMutableSet()
            val folds =
                lines.takeLastWhile { it.matches(Regex("fold along .+")) }.map {
                    val eq = it.split(" ").get(2)
                    val (dir, value) = eq.split("=")
                    Fold(dir.single(), value.toInt())
                }
            return Paper(coords, folds)
        }
    }
}

var paper = Paper.parse("test")
var count = paper.foldFirst()
check(count == 17)

paper = Paper.parse("data")
count = paper.foldFirst()
println("Part One: $count")
println("Part Two:")
paper.origami()