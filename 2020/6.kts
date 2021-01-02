import java.io.File

fun parseFileToUnionSets(filename: String): List<Set<Char>> {
    var lines: List<String> = File(filename).readLines()
    var sets: MutableList<Set<Char>> = mutableListOf()
    var currentSet = mutableSetOf<Char>()
    var index: Int = 0
    lines.forEach { line ->
        if (line.isBlank()) {
            sets.add(index, currentSet)
            index++
            currentSet = mutableSetOf()
        } else {
            val asArray = line.toCharArray()
            asArray.forEach { c -> currentSet.add(c) }
        }
    }
    sets.add(index, currentSet)
    return sets
}

fun parseFileToIntersectedSets(filename: String): List<Set<Char>> {
    var lines: List<String> = File(filename).readLines()
    var sets: MutableList<Set<Char>> = mutableListOf()
    var intersectedSet: Set<Char>
    var groupCharSets = mutableListOf<Set<Char>>()
    var index: Int = 0
    for (line in lines) {
        if (line.isBlank()) {
            intersectedSet = groupCharSets.reduce { i, n -> i.intersect(n) }
            sets.add(index, intersectedSet)
            index++
            groupCharSets = mutableListOf()
        } else {
            val charSet = line.toCharArray().toMutableSet()
            groupCharSets.add(charSet)
        }
    }
    intersectedSet = groupCharSets.reduce { acc, set -> acc.intersect(set) }
    sets.add(index, intersectedSet)
    return sets
}


fun countGroupAnswer(group: Set<Char>): Int {
    return group.size
}

fun countGroupAnswers(groups: List<Set<Char>>): Int {
    var count = 0
    for (group in groups) {
        count = count + countGroupAnswer(group)
    }
    return count
}

var unionSets = parseFileToUnionSets("6_input")
var unionSum = countGroupAnswers(unionSets)
println(unionSum)

var intersectedSets = parseFileToIntersectedSets("6_input")
var intersectedSum = countGroupAnswers(intersectedSets)
println(intersectedSum)