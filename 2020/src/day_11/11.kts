import java.io.File

typealias Plane = Array<CharArray>
val occupied = '#'
val empty = 'L'
val floor = '.'

fun parsePlaneSeats(filename: String): Plane {
    return File(filename).readLines().map {
        line -> line.toCharArray() }.toTypedArray()
}

fun countOccupiedSeats(plane: Plane, useAdjacentNeighbors: Boolean, limit: Int): Int {
    var current: Pair<Int, Plane> = executeSeatShuffle(plane, useAdjacentNeighbors, limit)
    while (current.first != 0)
        current = executeSeatShuffle(current.second, useAdjacentNeighbors, limit)
    return current.second.sumBy { row -> row.count { it.equals(occupied)} }
}

fun executeSeatShuffle(plane: Plane, useAdjacentNeighbors: Boolean, limit: Int): Pair<Int, Plane> {
    var counter = 0
    val rows = plane.indices
    val cols = plane[0].indices
    var newPlane = deepCopyPlane(plane)
    for (r in rows) {
        for (c in cols) {
            var neighbors: List<Char> = if (useAdjacentNeighbors) getNeighbors(r, c, plane) else getClosestSeats(r, c, plane)
            var currentSeat = plane[r][c]
            if (currentSeat.equals(empty).and(neighbors.none { it.equals(occupied) })) {
                counter++
                newPlane[r][c] = occupied
            }
            if (currentSeat.equals(occupied).and(neighbors.count {it.equals(occupied)} >= limit )) {
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

fun getClosestSeats(row: Int, col: Int, plane: Plane): List<Char> {
    var closest = mutableListOf<Char>()
    for (rowSlope in -1..1) {
        for (colSlope in -1..1) {
            if (rowSlope == 0 && colSlope == 0) {
                continue
            }
            var multiplier = 1
            while (true) {
                val newRow = multiplier * rowSlope + row
                val newCol = multiplier * colSlope + col
                if (newRow in plane.indices && newCol in plane[0].indices) {
                    if (plane[newRow][newCol].equals(floor).not()) {
                        closest.add(plane[newRow][newCol])
                        break
                    } else {
                        multiplier++
                    }
                } else {
                    break
                }
            }
        }
    }
    return closest
}

fun deepCopyPlane(plane: Plane): Plane {
    var mutated: MutableList<CharArray> = mutableListOf()
    for (row in plane)
        mutated.add(row.copyOf())
    return mutated.toTypedArray()
}

var plane: Plane = parsePlaneSeats("11_input")
// Part 1
var adjacentHeuristic = countOccupiedSeats(plane, true, 4)
println("Part 1: $adjacentHeuristic")
// Part 2
var closestHeuristic = countOccupiedSeats(plane, false, 5)
println("Part 2: $closestHeuristic")



