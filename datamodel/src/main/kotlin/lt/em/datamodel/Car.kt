package lt.em.datamodel

import lt.em.datamodel.carparts.*

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