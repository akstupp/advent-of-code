import java.io.File

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

fun partA(vals: List<Int>): Int {
    for (one in vals)
        for (two in vals)
            if (one + two == 2020)
                return one * two
    return -1
}

fun partB(vals: List<Int>): Int {
    for (one in vals)
        for (two in vals)
            for (three in vals)
                if (one + two + three == 2020)
                    return one * two * three
    return -1
}

var stringVals = readInputFile("1input")
var vals  = stringVals.map { s -> Integer.valueOf(s) }
val answerA = partA(vals)
val answerB = partB(vals)
println("Part A: " + answerA)
println("Part B: " + answerB)