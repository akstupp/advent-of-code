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
    var coords: Pair<Int, Int>
    while (i < map.size - 1) {
        coords = Pair(i + slopeVertical, j + slopeHorizontal)
        println("x: " + coords.first + " y: " + coords.second % map[0].size)
        treeCounter.plus(map.get(coords.first).get(coords.second % map.get(0).size))
        i = i + slopeVertical
        j = j + slopeHorizontal
    }
    return treeCounter
}

var lines = readInputFile("3_input")
var map = toTreesMap(lines)
println(map)
var partAResult = countTrees(map, 3, 1)
println(partAResult)
//var partBResult = partB(map)