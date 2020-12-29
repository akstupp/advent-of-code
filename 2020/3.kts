import java.io.File

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

fun toTreesMap(lines: List<String>): ArrayList<ArrayList<Int>> {
    var map: ArrayList<ArrayList<Int>> = ArrayList()
    var index = 0;
    for (line in lines) {
        var charArray: CharArray = line.toCharArray()
        var binaryConversion: ArrayList<Int> = ArrayList(charArray.map { if (it.equals('#')) 1 else 0 })
        map.add(index, binaryConversion)
        index++
    }
    return map
}

fun countTrees(map: ArrayList<ArrayList<Int>>, slopeHorizontal: Int, slopeVertical: Int): Int {
    var treeCounter = 0
    var i = 0;
    var j = 0;
    while (true) {
        i = i + slopeVertical
        if (i >= map.size)
            break
        j = (j + slopeHorizontal) % map.get(0).size
        treeCounter = treeCounter.plus(map.get(i).get(j))
    }
    return treeCounter
}

fun checkSlopes(map: ArrayList<ArrayList<Int>>): Long {
    var product: Long = 1L
    var slopes: Array<Pair<Int, Int>> = arrayOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
    for (pair in slopes) {
        var trees = countTrees(map, pair.first, pair.second)
        product = product * trees
    }
    return product
}

var lines = readInputFile("3_input")
var map = toTreesMap(lines)
var partAResult = countTrees(map, 3, 1)
println(partAResult)
var partBResult = checkSlopes(map)
println(partBResult)