import java.io.File
import java.util.LinkedList

fun parseFile(filename: String): List<Long> {
    return File(filename).readLines().map { s -> s.toLong() }
}

fun findWeakNumber(numbers: List<Long>, preamble: Int): Long {
    var index = preamble
    var numberPool = LinkedList(numbers.subList(0, preamble).toMutableList())
    while (index < numbers.size) {
        var target = numbers.get(index)
        if (containsTwoElementsSumTo(numberPool, target).not())
            return target
        numberPool.removeFirst()
        numberPool.addLast(target)
        index++
    }
    return -1
}

fun containsTwoElementsSumTo(list: List<Long>, target: Long): Boolean {
    for (a in list)
        for (b in list)
            if (a != b && a + b == target) {
                return true
            }
    return false
}

fun findWeakNumberSum(list: List<Long>, target: Long): Long {
    for (index in 0..list.size) {
        var sum: Long = 0L
        var offset = index
        var sumSet = mutableSetOf<Long>()
        while (sum < target) {
            val value = list.get(offset)
            sumSet.add(value)
            sum = sum + value
            offset++
        }
        if (sum == target) return sumSet.min()!!.plus(sumSet.max()!!)
    }
    return -1

}

var numbers = parseFile("9_input")
var weakest = findWeakNumber(numbers, 25)
println(weakest)
var sum = findWeakNumberSum(numbers, weakest)
println(sum)