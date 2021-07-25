import java.io.File
import java.util.LinkedList

val BAG_COLOR = "shiny gold"

typealias Color = String
typealias Content = Pair<Int, Color>

fun parseRulesToMap(filename: String): Map<Color, List<Content>> {
    return File(filename).readLines()
            .associate { line ->
                val (bag, inside) = line.split(" bags contain ")
                val contents: List<Pair<Int, String>> = inside.split(", ")
                        .mapNotNull { e ->
                            val (n, adj, color) = e.split(' ')
                            n.toIntOrNull()?.let { it to "$adj $color" }
                        }
                bag to contents
            }
}

fun bagCanContainColor(map: Map<Color, List<Content>>, start: Color, target: Color): Int {
    var queue: LinkedList<Color> = LinkedList()
    var visited: MutableSet<Color> = mutableSetOf(start)
    queue.add(start)
    while (queue.isNotEmpty()) {
        val current = queue.remove()
        if (current.equals(target)) return 1
        for (content in map.get(current)!!) {
            if (visited.contains(content.second).not()) {
                visited.add(content.second)
                queue.add(content.second)
            }
        }
    }
    return 0
}

fun findShinyGoldBags(bagMap: Map<Color, List<Content>>): Int {
    var count = 0
    for (color in bagMap.keys) {
        count = count + bagCanContainColor(bagMap, color, BAG_COLOR)
    }
    return count - 1 // exclude shiny gold as outer bag
}

fun getInnerBagCount(bagMap: Map<Color, List<Content>>, color: Color): Int {
    return bagMap.get(color)!!.sumBy { (count, inner) ->
        count + (count * getInnerBagCount(bagMap, inner))
    }
}

var colorsToContentsMap: Map<Color, List<Content>> = parseRulesToMap("7_input")
var count = findShinyGoldBags(colorsToContentsMap)
println(count)
var bagsInShinyGold = getInnerBagCount(colorsToContentsMap, BAG_COLOR)
println(bagsInShinyGold)

