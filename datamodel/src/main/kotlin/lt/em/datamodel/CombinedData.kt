package lt.em.datamodel

data class CombinedData(val driver: Driver,
                        val car: Car,
                        val practiseConditions: PractiseConditions,
                        val staffAndFacilities: StaffAndFacilities,
                        val practise: Practise)