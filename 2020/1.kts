import java.io.File

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

fun partA(vals: List<Integer>) {
    for (one in vals)
        for (two in vals)
            if (one + two == 2020)
                println(one * two)
}

fun partB(vals: List<Integer>) {
    for (one in vals)
        for (two in vals)
            for (three in vals)
                if (one + two + three == 2020)
                    println(one * two * three)
}

var stringVals = readInputFile("1input")
var vals  = stringVals.map { s -> Integer.valueOf(s) }
partA(vals)
partB(vals)