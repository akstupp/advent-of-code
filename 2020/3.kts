import java.io.File

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

fun toTreesMap(lines: List<String>): Array<Array<Int>> {
    var map = [];
    var index = 0;
    for (line in lines) {
        var asArray = line.toCharArray()
        var intArray = asArray.map(c -> {
            if (c.equals(".")) {
                return 0
            } else {
                return 1
            }
        })
        map[index] = intArray
        index++
    }
    return map
}

fun countTrees(map: Array<Array<Int>>, slopeHorizontal: Int, slopeVertical: Int): Int {

}

var lines = readInputFile("3input")
var map = toTreesMap(lines)
var partAResult = countTrees(map, 3, 1)
//var partBResult = partB(map)