import java.io.File

fun parseInstructionList(filename: String): List<Pair<String, Int>> {
    var lines = File(filename).readLines()
    return lines.map { s ->
        val (op, value) = s.split(" ")
        Pair(op, value.toInt())
    }
}


fun accumulateForExecution(instructions: List<Pair<String, Int>>): Pair<Boolean, Int> {
    var visitedByIndex: HashSet<Int> = HashSet()
    var index = 0
    var accumulator = 0
    while (visitedByIndex.contains(index).not().and(index.equals(instructions.size).not())) {
        var instruction = instructions.get(index)
        visitedByIndex.add(index)
        when (instruction.first) {
            "acc" -> {
                accumulator = accumulator + instruction.second
                index = index + 1
            }
            "jmp" -> index = index + instruction.second
            "nop" -> index++
        }
    }

    if (index.equals(instructions.size)) {
        return Pair(true, accumulator)
    }

    return Pair(false, accumulator)
}



fun fixBootCode(instructions: List<Pair<String, Int>>): Int {
    var currentInstructions: MutableList<Pair<String, Int>> = instructions.toMutableList()
    for (index in 0..instructions.size - 1) {
        val instruction: Pair<String, Int> = currentInstructions.get(index)
        if (instruction.first.equals("acc").not()) {
            var replacement: String = if (instruction.first.equals("jmp")) "nop" else "jmp"
            currentInstructions.set(index, Pair(replacement, instruction.second))
            var execution: Pair<Boolean, Int> = accumulateForExecution(currentInstructions)
            var terminated: Boolean = execution.first
            if (terminated) {
                return execution.second
            }
            currentInstructions = instructions.toMutableList()
        }
    }
    return -1
}


var instructions: List<Pair<String, Int>> = parseInstructionList("8_input")
var accumulation = accumulateForExecution(instructions).second
println(accumulation)

var terminalAccumulation = fixBootCode(instructions)
println(terminalAccumulation)