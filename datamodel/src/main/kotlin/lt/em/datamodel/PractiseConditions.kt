package lt.em.datamodel

data class PractiseConditions(val trackName: String,
           val trackId: Int,
           val trackPower: Int,
           val trackHandling: Int,
           val trackAcceleration: Int,
           val temperatureQ1: Int,
           val humidityQ1: Int,
           val weather: String)