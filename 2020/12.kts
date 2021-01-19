import java.io.File
import kotlin.math.abs

typealias Direction = Pair<Char, Int>

fun parseDirections(filename: String): List<Direction> {
    return File(filename).readLines().map { Pair(it[0], it.substring(1).toInt())}
}

fun navigate(directions: List<Direction>): Pair<Int, Int> {
    var vertCoord = 0
    var horzCoord = 0
    var faces = arrayOf('N', 'E', 'S', 'W')
    var faceIndex = 1
    for ((direction, distance) in directions) {
        when (direction) {
            'N' -> { vertCoord = vertCoord + distance }
            'S' -> { vertCoord = vertCoord - distance }
            'E' -> { horzCoord = horzCoord + distance }
            'W' -> { horzCoord = horzCoord - distance }
            'L' -> { faceIndex = (faceIndex + 3 * (distance / 90)) % 4}
            'R' -> { faceIndex = (faceIndex + (distance / 90)) % 4 }
            'F' -> {
                when (faces[faceIndex]) {
                    'N' -> { vertCoord = vertCoord + distance }
                    'S' -> { vertCoord = vertCoord - distance }
                    'E' -> { horzCoord = horzCoord + distance }
                    'W' -> { horzCoord = horzCoord - distance }
                }
            }
        }
    }
    return Pair(vertCoord, horzCoord)
}

var directions = parseDirections("12_input")
var coords = navigate(directions)
var manhattanDistance = abs(coords.first) + abs(coords.second)
println("Coords: $coords")
println("Manhattan Distance: $manhattanDistance")
