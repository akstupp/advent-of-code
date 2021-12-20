import java.io.File

data class Direction(val direction: String, val distance: Int) {}

fun parseDirections(filename: String): List<Direction> {
    return File(filename).readLines().map { line ->
        val (dir, dist) = line.split(" ", limit = 2)
        Direction(dir, dist.toInt())
    }
}

fun navigate(directions: List<Direction>): Int {
    var depth = 0
    var distance = 0
    for (dir in directions) {
        when (dir.direction) {
            "forward" -> distance = distance + dir.distance
            "down" -> depth = depth + dir.distance
            "up" -> depth = depth - dir.distance
        }
    }
    return depth * distance
}

fun navigateWithAim(directions: List<Direction>): Int {
    var depth = 0
    var distance = 0
    var aim = 0
    for (dir in directions)
        when (dir.direction) {
            "forward" -> {
                distance = distance + dir.distance
                depth = depth + (aim * dir.distance)
            }
            "up" -> aim = aim - dir.distance
            "down" -> aim = aim + dir.distance
        }
    return distance * depth
}

val directions: List<Direction> = parseDirections("data")
// Part 1
var product = navigate(directions)
println("Part 1: $product")
// Part 2
product = navigateWithAim(directions)
println("Part 2: $product")


