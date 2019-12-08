package lt.em.datamodel

data class StaffAndFacilities(val overall: Int, val staff: Staff, val facilities: Facilities)

data class Staff(val experience: Int,
                 val motivation: Int,
                 val technicalSkill: Int,
                 val stressHandling: Int,
                 val concentration: Int,
                 val efficiency: Int)

data class Facilities(val windTunner: Int,
                      val pitstopTrainingCentre: Int,
                      val rnDWorkshop: Int,
                      val rnDDesignCentre: Int,
                      val engineeringWorkshop: Int,
                      val alloyAndChemicalLab: Int,
                      val commercial: Int)