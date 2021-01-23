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
            'N' -> vertCoord +=  distance
            'S' -> vertCoord -=  distance
            'E' -> horzCoord += distance
            'W' -> horzCoord -= distance
            'L' -> faceIndex = (faceIndex + 3 * (distance / 90)) % 4
            'R' -> faceIndex = (faceIndex + (distance / 90)) % 4
            'F' ->
                when (faces[faceIndex]) {
                    'N' -> vertCoord += distance
                    'S' -> vertCoord -= distance
                    'E' -> horzCoord += distance
                    'W' -> horzCoord -= distance
                }
        }
    }
    return Pair(vertCoord, horzCoord)
}

fun navigateByWaypoint(directions: List<Direction>): Pair<Int, Int> {
    var ship = Pair(0, 0)
    var wayX = 10
    var wayY = 1
    for ((direction, distance) in directions) {
        when (direction) {
            'N' -> wayY += distance
            'S' -> wayY -= distance
            'E' -> wayX += distance
            'W' -> wayX -= distance
            'R' -> {
                val turns = (distance / 4) % 4
                val diff = Pair(wayX - ship.first, wayY - ship.second)
                var newDiff: Pair<Int, Int> = Pair(0, 0)
                when (turns) {
                    0 -> newDiff = diff
                    1 -> newDiff = Pair(diff.second, -1 * diff.first)
                    2 -> newDiff = Pair(-1 * diff.first, -1 * diff.second)
                    3 -> newDiff = Pair(-1 * diff.second, diff.first)
                }
                wayX = ship.first + newDiff.first
                wayY = ship.second + newDiff.second
            }
            'L' -> {
                val turns = (distance / 4) % 4
                val diff = Pair(wayX - ship.first, wayY - ship.second)
                var newDiff: Pair<Int, Int> = Pair(0, 0)
                when (turns) {
                    0 -> newDiff = diff
                    1 -> newDiff = Pair(-1 * diff.second, diff.first)
                    2 -> newDiff = Pair(-1 * diff.first, -1 * diff.second)
                    3 -> newDiff = Pair(diff.second, -1 * diff.first)
                }
                wayX = ship.first + newDiff.first
                wayY = ship.second + newDiff.second
            }
            'F' -> {
                val diff = Pair(distance * (wayX - ship.first), distance * (wayY - ship.second))
                val waypointDiff = Pair(wayX - ship.first, wayY - ship.second)
                ship = Pair(ship.first + diff.first, ship.second + diff.second)
                wayX += waypointDiff.first
                wayY += waypointDiff.second
            }
        }
    }
    return ship

}

var directions = parseDirections("12_input")
var coords = navigate(directions)
var manhattanDistance = abs(coords.first) + abs(coords.second)
println("Coords: $coords")
println("Manhattan Distance: $manhattanDistance")
