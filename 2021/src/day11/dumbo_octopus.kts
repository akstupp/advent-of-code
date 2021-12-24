import java.io.File

typealias Coord = Pair<Int, Int>

data class OctopusSimulation(val octopi: Map<Coord, Int>) {
    var simulation = octopi.toMutableMap()

    fun findUniversalFlash(): Int {
        simulation = octopi.toMutableMap()
        var count = 0
        while (simulation.values.all { it == 0 }.not()) {
            count++
            simulate()
        }
        return count
    }

    fun simulate(): Int {
        val flashes = mutableSetOf<Coord>()
        octopi.keys.forEach { coord ->
            simulation[coord] = simulation[coord]!!.plus(1)
            if (simulation[coord]!! > 9) {
                flashes.addAll(flash(coord))
            }
        }
        flashes.forEach { coord -> simulation[coord] = 0 }
        return flashes.size
    }

    // Return count of flashes
    fun simulate(iterations: Int): Int {
        simulation = octopi.toMutableMap()
        var count = 0
        IntRange(1, iterations).forEach { _ ->
            val flashCount = simulate()
            count = count + flashCount
        }
        return count
    }

    fun flash(coord: Coord): Set<Coord> {
        val flashes = mutableSetOf<Coord>()
        var flashSet = mutableSetOf<Coord>(coord)
        while (flashSet.isNotEmpty()) {
            val newFlashSet = mutableSetOf<Coord>()
            flashSet.forEach { flashCoord ->
                if (flashes.add(flashCoord)) {
                    neighbors(flashCoord).forEach {
                        simulation[it] = simulation[it]!! + 1
                        if (simulation[it]!! > 9)
                            newFlashSet.add(it)
                    }
                    simulation[flashCoord] = 0
                }
            }
            flashSet = newFlashSet
        }
        return flashes
    }

    fun neighbors(coord: Coord): List<Coord> {
        return listOf(
                coord.first + 1 to coord.second + 1,
                coord.first + 1 to coord.second,
                coord.first + 1 to coord.second - 1,
                coord.first to coord.second + 1,
                coord.first to coord.second - 1,
                coord.first - 1 to coord.second,
                coord.first - 1 to coord.second - 1,
                coord.first - 1 to coord.second + 1
        ).filter { simulation[it] != null }
    }


    companion object {
        fun parse(filename: String): OctopusSimulation {
            val data = File(filename).readLines().mapIndexed { i, line ->
                line.toCharArray().mapIndexed { j, oct ->
                    Coord(i, j) to Integer.parseInt(oct.toString())
                }
            }.flatten().toMap()
            return OctopusSimulation(data)
        }
    }
}

var simulation = OctopusSimulation.parse("test")
check(simulation.simulate(10) == 204)
check(simulation.simulate(100) == 1656)
check(simulation.findUniversalFlash() == 195)

simulation = OctopusSimulation.parse("data")
var answer = simulation.simulate(100)
println("Part 1: $answer")
answer = simulation.findUniversalFlash()
println("Part 2: $answer")