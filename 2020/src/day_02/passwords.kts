import java.io.File

fun readInputFile(filename: String): List<PasswordPolicy> {
    return File(filename).readLines().map { PasswordPolicy.parse(it) }
}

data class PasswordPolicy(val range: IntRange, val letter: Char, val password: String) {
    fun isValidForPartOne(): Boolean = password.count { it == letter } in range
    fun isValidForPartTwo(): Boolean = (password[range.first - 1] == letter) xor (password[range.last - 1] == letter)

    companion object {
        fun parse(line: String): PasswordPolicy {
            return Regex("""(\d+)-(\d+) ([a-z]): ([a-z]+)""")
                    .matchEntire(line)!!
                    .destructured
                    .let { (start, end, letter, password) ->
                        PasswordPolicy(IntRange(start.toInt(), end.toInt()), letter.single(), password) }
        }
    }
}

var passwords = readInputFile("2_input")
val partOne = passwords.count { it.isValidForPartOne() }
val partTwo = passwords.count { it.isValidForPartTwo()}
println("Valid passwords for part 1: $partOne")
println("Valid passwords for part 2: $partTwo")
