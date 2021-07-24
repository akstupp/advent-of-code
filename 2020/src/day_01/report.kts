import java.io.File

fun readInputFile(filename: String): List<Int> {
    return File(filename).readLines().map { it.toInt() }
}


fun List<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
    var complements = associateBy { sum - it }
    return firstNotNullOfOrNull { n ->
        val x = complements[n]
        if (x != null) Pair(x, n) else null
    }
}

fun List<Int>.findTripleOfSum(sum: Int): Triple<Int, Int, Int>? {
    return firstNotNullOfOrNull { x ->
        findPairOfSum(sum - x)?.let { pair ->
            Triple(x, pair.first, pair.second)
        }
    }
}

var vals = readInputFile("1_input")
val pair = vals.findPairOfSum(2020)
val triple = vals.findTripleOfSum(2020)
val doubleProduct = pair?.let { (a, b) -> a * b }
val tripleProduct = triple?.let { (a, b, c) -> a * b * c }
println("product of pair: $doubleProduct")
println("product of triple: $tripleProduct")