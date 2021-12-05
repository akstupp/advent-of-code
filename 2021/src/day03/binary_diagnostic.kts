import java.io.File

fun calculatePowerConsumption(report: List<CharArray>) : Int {
    val binaryLength = report.get(0).size
    var gamma = CharArray(binaryLength)
    var epsilon = CharArray(binaryLength)
    for (index in 0..binaryLength - 1) {
        val posArray = report.map { binary -> binary[index] }
        val zeroes = posArray.count { it.equals('0') }
        val ones = posArray.count { it.equals('1') }
        if (zeroes > ones) {
            gamma.set(index, '0')
            epsilon.set(index, '1')
        } else {
            gamma.set(index, '1')
            epsilon.set(index, '0')
        }
    }
    return String(gamma).toInt(2) * String(epsilon).toInt(2)
}

val lines: List<String> = File("data").readLines()
val report = lines.map { it.toCharArray() }
val powerConsumption = calculatePowerConsumption(report)
println("$powerConsumption")
