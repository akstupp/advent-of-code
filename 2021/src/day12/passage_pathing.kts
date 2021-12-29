import java.io.File
import java.util.Stack

typealias Node = String
typealias Segment = Pair<Node, Node>
typealias Path = MutableList<Node>

data class CaveSystem(val neighbors: Map<Node, Set<Node>>) {

    fun paths(repeats: Boolean): List<Path> {
        val paths = mutableListOf<Path>()
        val stack = Stack<Pair<Node, Path>>()
        stack.push("start" to mutableListOf<Node>())
        while (stack.isNotEmpty()) {
            val (node, path) = stack.pop()
            if (canVisit(node, path, repeats)) {
                if (node == "end") {
                    val newPath = path.toMutableList()
                    newPath.add(node)
                    paths.add(path)
                } else {
                    neighbors[node]!!.forEach {
                        val newPath = path.toMutableList()
                        newPath.add(node)
                        stack.push(it to newPath)
                    }
                }
            }
        }
        return paths
    }

    fun canVisit(node: String, path: Path, repeats: Boolean): Boolean {
        if (node.lowercase() == node && path.contains(node)) {
            if (!repeats) return false
            else if (node == "start" || node == "end") return false
            else if (path.groupingBy { it }.eachCount()
                    .any { e -> e.key.lowercase() == e.key && e.value > 1 }
            )
                return false
            else return true
        } else return true
    }

    companion object {
        fun parse(filename: String): CaveSystem {
            val segments = File(filename).readLines().map { line ->
                val (start, end) = line.split("-")
                Segment(start, end)
            }
            val nodes =
                segments.map { seg -> seg.toList() }.flatten().distinct()
            val neighbors = nodes.associateWith { _ -> mutableSetOf<Node>() }
                .toMutableMap()
            segments.forEach { segment ->
                neighbors[segment.first]!!.add(segment.second)
                neighbors[segment.second]!!.add(segment.first)
            }
            return CaveSystem(neighbors)
        }
    }
}

var caves = CaveSystem.parse("test")
var pathsCount = caves.paths(false).distinct().count()
check(pathsCount == 226)
pathsCount = caves.paths(true).distinct().count()
check(pathsCount == 3509)

caves = CaveSystem.parse("data")
pathsCount = caves.paths(false).distinct().count()
println("Part One: $pathsCount")
pathsCount = caves.paths(true).distinct().count()
println("Part Two: $pathsCount")