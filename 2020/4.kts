import java.io.File

class Passport {
    val attributes: MutableMap<String, String> = mutableMapOf()
    val requiredAttributes = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val hasRequiredAttributes: Boolean
    get() {
        for (key in requiredAttributes) {
            if (attributes.containsKey(key).not()) return false
        }
        return true
    }

    val valid: Boolean
    get() {
        for (key in requiredAttributes) {
            if (attributes.containsKey(key).not()) return false
            when (key) {
                "byr" -> if (isValidDate(attributes[key]!!, 1920, 2002).not()) return false
                "iyr" -> if (isValidDate(attributes[key]!!, 2010, 2020).not()) return false
                "eyr" -> if (isValidDate(attributes[key]!!, 2020, 2030).not()) return false
                "hgt" -> if (isValidHeight(attributes[key]!!).not()) return false
                "hcl" -> if (isValidHairColor(attributes[key]!!).not()) return false
                "ecl" -> if (isValidEyeColor(attributes[key]!!).not()) return false
                "pid" -> if (isValidPID(attributes[key]!!).not()) return false
            }
        }
        return true
    }

    private fun isValidDate(date: String, min: Int, max: Int): Boolean {
        if (date.length != 4) return false
        if (date.toInt() !in min..max) return false
        return true
    }

    private fun isValidHeight(height: String): Boolean {
        if (height.endsWith("cm")) {
            val heightInCm = height.removeSuffix("cm").toInt()
            if (heightInCm in 150..193) return true
        }
        if (height.endsWith("in")) {
            val heightInIn = height.removeSuffix("in").toInt()
            if (heightInIn in 59..76) return true
        }
        return false
    }

    private fun isValidHairColor(color: String): Boolean {
        return color.matches(Regex("#(\\d|[a-f]){6}"))
    }

    private fun isValidEyeColor(color: String): Boolean {
        return color in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    private fun isValidPID(pid: String): Boolean {
        return pid.matches(Regex("\\d{9}"))
    }
}

fun readInputFile(filename: String): List<String> {
    return File(filename).readLines()
}

fun parseFile(file: List<String>): List<Passport> {
    var passportBatch: MutableList<Passport> = mutableListOf()
    var passport: Passport = Passport()
    var index = 0
    for (line in file) {
        if (line.trim().isEmpty()) {
            passportBatch.add(index, passport)
            passport = Passport()
            index++
        } else {
            line.split(" ", "\n").forEach { kv ->
                val l = kv.split(":")
                passport.attributes.put(l.get(0), l.get(1))
            }
        }
    }
    passportBatch.add(index, passport)
    return passportBatch
}

fun validPassports(passports: List<Passport>, strictly: Boolean): Int {
    var counter = 0
    for (passport in passports) {
        when (strictly) {
            true -> if (passport.valid) counter++
            false -> if (passport.hasRequiredAttributes) counter++
        }
    }
    return counter
}

val passportBatch: List<Passport> = parseFile(readInputFile("4_input"))
val validLeniently = validPassports(passportBatch, false)
println(validLeniently)
val validStrictly = validPassports(passportBatch, true)
println(validStrictly)
