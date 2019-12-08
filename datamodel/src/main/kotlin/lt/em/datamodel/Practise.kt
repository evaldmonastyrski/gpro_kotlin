package lt.em.datamodel

data class Practise(val attempt: Int,
                    val netTime: Double,
                    val frontWingSetup: Int,
                    val rearWingSetup: Int,
                    val engineSetup: Int,
                    val brakesSetup: Int,
                    val gearSetup: Int,
                    val suspensionSetup: Int,
                    val tyreType: String)