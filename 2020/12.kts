import java.io.File
import kotlin.math.abs

typealias Direction = Pair<Char, Int>

fun parseDirections(filename: String): List<Direction> {
    return File(filename).readLines().map { Pair(it[0], it.substring(1).toInt())}
}

fun navigateByGuessing(directions: List<Direction>): Int {
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
    return abs(vertCoord) + abs(horzCoord)
}

fun navigateByWaypoint(directions: List<Direction>): Int {
    var ship = Pair(0, 0)
    var wayX = 10
    var wayY = 1
    for ((direction, distance) in directions) {
        when (direction) {
            'N' -> wayY += distance
            'S' -> wayY -= distance
            'E' -> wayX += distance
            'W' -> wayX -= distance
            'R', 'L' -> {
                val turns = (distance / 90) % 4
                val diff = Pair(wayX - ship.first, wayY - ship.second)
                var newDiff: Pair<Int, Int> = Pair(0, 0)
                when (turns) {
                    0 -> newDiff = diff
                    1 -> newDiff = if (direction == 'R') Pair(diff.second, -1 * diff.first) else Pair(-1 * diff.second, diff.first)
                    2 -> newDiff = Pair(-1 * diff.first, -1 * diff.second)
                    3 -> newDiff = if (direction == 'R') Pair(-1 * diff.second, diff.first) else Pair(diff.second, -1 * diff.first)
                }
                wayX = ship.first + newDiff.first
                wayY = ship.second + newDiff.second
            }
            'F' -> {
                val diff = Pair(distance * (wayX - ship.first), distance * (wayY - ship.second))
                val waypointDiff = Pair(wayX - ship.first, wayY - ship.second)
                ship = Pair(ship.first + diff.first, ship.second + diff.second)
                wayX = ship.first + waypointDiff.first
                wayY = ship.second + waypointDiff.second
            }
        }
    }
    return abs(ship.first) + abs(ship.second)
}

var directions = parseDirections("12_input")
var guessedDistance = navigateByGuessing(directions)
var correctDistance = navigateByWaypoint(directions)
println("Guessed Manhattan Distance: $guessedDistance")
println("Correct Manhattan Distance: $correctDistance")


