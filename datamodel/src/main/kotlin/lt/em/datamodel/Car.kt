package lt.em.datamodel

data class Car(val chassis: Chassis,
               val engine: Engine,
               val frontWing: FrontWing,
               val rearWing: RearWing,
               val underbody: Underbody,
               val sidepods: Sidepods,
               val cooling: Cooling,
               val gearbox: Gearbox,
               val brakes: Brakes,
               val suspension: Suspension,
               val electronics: Electronics,
               val power: Int,
               val handling: Int,
               val acceleration: Int)

data class Chassis(val level: Int, val wear: Int)
data class Engine(val level: Int, val wear: Int)
data class FrontWing(val level: Int, val wear: Int)
data class RearWing(val level: Int, val wear: Int)
data class Underbody(val level: Int, val wear: Int)
data class Sidepods(val level: Int, val wear: Int)
data class Cooling(val level: Int, val wear: Int)
data class Gearbox(val level: Int, val wear: Int)
data class Brakes(val level: Int, val wear: Int)
data class Suspension(val level: Int, val wear: Int)
data class Electronics(val level: Int, val wear: Int)