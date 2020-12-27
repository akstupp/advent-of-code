import java.io.File

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

data class PasswordPolicy(val start: Int, val stop: Int, val letter: Char, val password: String) {}

fun buildPolicy(parsable: String): PasswordPolicy {
    var values = parsable.split("-", ": ", " ").map { c -> c.trim() }
    val first = Integer.valueOf(values[0])
    val last = Integer.valueOf(values[1])
    val l = values[2].single()
    val p = values[3]
    return PasswordPolicy(first, last, l, p)
}

fun partA(input: List<String>): Int {
    var counter = 0
    for (line in input) {
        var policy = buildPolicy(line)
        val count = policy.password.count { c -> c.equals(policy.letter) }
        if (count >= policy.start &&  count <= policy.stop) {
            counter++
        }
    }
    return counter
}

fun partB(input: List<String>): Int {
    var counter = 0
    for (line in input) {
        var policy = buildPolicy(line)
        var xor = policy.password.toCharArray()[policy.start-1].equals(policy.letter)
                .xor(policy.password.toCharArray()[policy.stop-1].equals(policy.letter))
        if (xor) {
            counter++
        }
    }
    return counter
}

var input = readInputFile("2input")
println(partA(input))
println(partB(input))