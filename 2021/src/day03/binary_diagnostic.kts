import java.io.File

data class BitCount(val zeroes: Int, val ones: Int) {}

enum class RatingType { OXYGEN, CO2 }

fun calculatePowerConsumption(report: List<String>): Int {
    val columns = report[0].indices
    val gamma = StringBuilder()
    val epsilon = StringBuilder()
    for (column in columns) {
        val (zeroes, ones) = report.countColumnBits(column)
        val modeBit = if (zeroes > ones) "0" else "1"
        val inverse = if (modeBit == "0") "1" else "0"
        gamma.append(modeBit)
        epsilon.append(inverse)
    }
    return gamma.toString().toInt(2) * epsilon.toString().toInt(2)
}

fun calculateLifeSupport(report: List<String>): Int {
    return report.calculateRating(RatingType.OXYGEN).toInt(2) * report.calculateRating(RatingType.CO2).toInt(2)
}

fun List<String>.calculateRating(type: RatingType): String {
    val columns = this[0].indices
    var eligible = this
    for (column in columns) {
        val (zeroes, ones) = eligible.countColumnBits(column)
        val modeBit = if (zeroes > ones) '0' else '1'
        // filter to bit criteria
        eligible = eligible.filter {
            when (type) {
                RatingType.OXYGEN -> it[column] == modeBit
                RatingType.CO2 -> it[column] != modeBit
            }
        }
        if (eligible.size == 1) break
    }
    return eligible.single()
}

fun List<String>.countColumnBits(column: Int): BitCount {
    var zeroes = 0
    var ones = 0
    this.forEach { line -> if (line[column] == '0') zeroes++ else ones++ }
    return BitCount(zeroes, ones)
}

val test = File("test").readLines()
check(calculatePowerConsumption(test) == 198)
check(calculateLifeSupport(test) == 230)
val report = File("data").readLines()
val powerConsumption = calculatePowerConsumption(report)
val lifeSupport = calculateLifeSupport(report)
println("Part 1: $powerConsumption")
println("Part 2: $lifeSupport")
