import java.io.File

class LuggageNode {
    val descriptor: String
    val contents: Set<LuggageNode>
}

fun parseLuggageRules(filename: String): Set<LuggageNode> {
    var lines = File(filename).readLines()
    var nodeSet = mutableSetOf<LuggageNode>()
    for (line in lines) {
        nodeSet = parseLuggageNode(line, nodeSet)
    }
    return nodeSet
}

fun parseLuggageNode(line: String, existingNodes: MutableSet<LuggageNode>): MutableSet<LuggageNode> {
    val regex = Regex("(\\sbags\\scontain\\s)|(\\sbag,\\s)|(\\sbag[.])|(\\sbags,\\s)|(\\sbags[.])")
    val regexArray = line.split(regex).map { s -> s.replace(Regex("\\d\\s"), "") }
    val trimmed = regexArray.map { s -> s.trim() }.filter { s -> s.isNotBlank() }
    var nodes: MutableList<LuggageNode> = trimmed.map { s ->
        val maybeNode = existingNodes.singleOrNull { n -> n.descriptor = s }
        if (maybeNode == null) {
            var node = LuggageNode()
            node.descriptor = s
            existingNodes.add(node)
            node
        } else {
            maybeNode
        }
    }
    var first = nodes.first()
    nodes.removeAt(0)
    first.contents = nodes.toSet()
    return existingNodes
}

fun breadthFirstSearch(start: LuggageNode, target: LuggageNode): Int {

}

fun findShinyGoldBags(nodes: Set<LuggageNode>): Int {
    var shinyGold = nodes.single { n -> n.descriptor = "shiny gold" }
    var count = 0
    for (node in nodes) {
        count = count + breadthFirstSearch(node, shinyGold)
    }
    return count
}

var input = "plaid tan bags contain 4 clear magenta bags, 5 posh brown bags, 5 drab lime bags."

parseLuggageGraph("5_input")


