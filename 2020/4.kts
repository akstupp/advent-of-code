import java.io.File

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

fun parseFile(file: List<String>): List<Map<String, String>> {
    var passportBatch: MutableList<Map<String, String>> = mutableListOf()
    var data: MutableMap<String, String> = mutableMapOf()
    var index = 0
    for (line in file) {
        if (line.trim().isEmpty()) {
            passportBatch.add(index, data)
            data = mutableMapOf()
            index++
        } else {
            line.split(" ", "\n").forEach { kv ->
                val l = kv.split(":")
                data.put(l.get(0), l.get(1))
            }
        }
    }
    passportBatch.add(index, data)
    return passportBatch
}

fun validPassportCount(passports: List<Map<String, String>>): Int {
    val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    var counter = 0
    passports.forEach { passport ->
        if (passport.keys.containsAll(requiredFields)) {
            counter++
        }
    }
    return counter
}

val passportBatch: List<Map<String, String>> = parseFile(readInputFile("4_input"))
val valid = validPassportCount(passportBatch)
println(valid)
