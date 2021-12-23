import java.io.File
import java.util.Stack

fun Char.isOpen(): Boolean = listOf('<', '{', '[', '(').contains(this)
fun Char.isClosed(): Boolean = listOf('>', '}', ']', ')').contains(this)
fun Char.matches(that: Char): Boolean {
    val ok = listOf('<' to '>', '{' to '}', '[' to ']', '(' to ')')
            .map { it.toList().sorted() }
    return ok.contains(listOf(this, that).sorted())
}

// Returns first incorrect character or the rest of the stack if its incomplete
fun CharArray.validate(): Pair<Char?, Stack<Char>?> {
    val iterator = this.iterator()
    val stack = Stack<Char>()
    while (iterator.hasNext()) {
        val symbol = iterator.next()
        if (symbol.isOpen()) {
            stack.push(symbol)
        } else if (symbol.isClosed()) {
            val elem = stack.pop()
            if (elem.matches(symbol).not())
                return symbol to null
        }
    }
    return null to stack
}

fun calculateSyntaxScore(lines: List<CharArray>): Int {
    return lines.map { it.validate().first }
            .filter { it != null }
            .map {
                when (it!!) {
                    ')' -> 3
                    ']' -> 57
                    '}' -> 1197
                    '>' -> 25137
                    else -> 0
                }
            }.sum()
}

fun calculateAutocompleteScore(lines: List<CharArray>): Long {
    val list = lines.map { it.validate().second }
            .filter { it.isNullOrEmpty().not() }
            .map {
                it!!.toList().asReversed().map {
                    when (it!!) {
                        '(' -> 1
                        '[' -> 2
                        '{' -> 3
                        '<' -> 4
                        else -> throw IllegalStateException("very bad")
                    }
                }
            }.map { line ->
                line.fold(0L, { acc, e -> (acc * 5) + e })
            }.sorted()
    return list.get(list.size / 2)
}

fun parseLines(filename: String): List<CharArray> {
    return File(filename).readLines().map { it.toCharArray() }
}

var lines = parseLines("test")
check(calculateSyntaxScore(lines) == 26397)
check(calculateAutocompleteScore(lines) == 288957L)

lines = parseLines("data")
val syntax = calculateSyntaxScore(lines)
println("part 1: $syntax")
val autocomplete = calculateAutocompleteScore(lines)
println("part 2: $autocomplete")



