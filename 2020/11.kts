import java.io.File

typealias Plane = Array<CharArray>
val occupied = '#'
val empty = 'L'
val floor = '.'

fun parsePlaneSeats(filename: String): Plane {
    return File(filename).readLines().map {
        line -> line.toCharArray() }.toTypedArray()
}

fun countOccupiedSeats(plane: Plane): Int {
    var current: Pair<Int, Plane> = executeSeatShuffle(plane)
    while (current.first != 0)
        current = executeSeatShuffle(current.second)
    return current.second.sumBy { row -> row.count { it.equals(occupied)} }
}

fun executeSeatShuffle(plane: Plane): Pair<Int, Plane> {
    var counter = 0
    val rows = plane.indices
    val cols = plane[0].indices
    var newPlane = deepCopyPlane(plane)
    for (r in rows) {
        for (c in cols) {
            var neighbors: List<Char> = getNeighbors(r, c, plane)
            var currentSeat = plane[r][c]
            if (currentSeat.equals(empty).and(neighbors.none { it.equals(occupied) })) {
                counter++
                newPlane[r][c] = occupied
            }
            if (currentSeat.equals(occupied).and(neighbors.count {it.equals(occupied)} >= 4 )) {
                counter++
                newPlane[r][c] = empty
            }
        }
    }
    return Pair(counter, newPlane)
}

fun getNeighbors(row: Int, col: Int, plane: Plane): List<Char> {
    var neighbors = mutableListOf<Char>()
    for (i in -1..1) {
        for (j in -1..1) {
            if (i.equals(0).and(j.equals(0)).not()) {
                val newRow = i + row
                val newCol = j + col
                if (newRow in plane.indices && newCol in plane[0].indices) {
                    neighbors.add(plane[i + row][j + col])
                }
            }
        }
    }
    return neighbors
}

fun printPlane(plane: Plane) {
    plane.forEach { r -> println(r) }
    println("---")
}

fun deepCopyPlane(plane: Plane): Plane {
    var mutated: MutableList<CharArray> = mutableListOf()
    for (row in plane)
        mutated.add(row.copyOf())
    return mutated.toTypedArray()
}

var plane: Plane = parsePlaneSeats("11_input")
var finalOccupiedCount = countOccupiedSeats(plane)
println(finalOccupiedCount)



