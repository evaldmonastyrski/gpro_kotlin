package lt.em.core.converters

import lt.em.datamodel.Car
import lt.em.datamodel.Driver
import lt.em.datamodel.PractiseConditions
import lt.em.datamodel.Setup
import lt.em.gpro.calculator.datamodel.*

fun convertSetup(cSetup: CSetup): Setup {
    return Setup(frontWing = cSetup.frontWing,
        rearWing = cSetup.rearWing,
        engine = cSetup.engine,
        brakes = cSetup.brakes,
        gear = cSetup.gear,
        suspension = cSetup.suspension)
}

fun convertDriver(driver: Driver): CDriver {
    return CDriver(name = driver.name,
        energy = driver.energy,
        overall = driver.overall,
        concentration = driver.concentration,
        talent = driver.talent,
        aggressiveness = driver.aggressiveness,
        experience = driver.experience,
        technicalInsight = driver.technicalInsight,
        stamina = driver.stamina,
        charisma = driver.charisma,
        motivation = driver.motivation,
        reputation = driver.motivation,
        weight = driver.weight,
        age = driver.age)
}

fun convertCar(car: Car): CCar {
    return CCar(power = car.power,
        handling = car.handling,
        acceleration = car.acceleration,
        chassis = CChassis(level = car.chassis.level, wear = car.chassis.wear),
        engine = CEngine(level = car.engine.level, wear = car.engine.wear),
        frontWing = CFrontWing(level = car.frontWing.level, wear = car.frontWing.wear),
        rearWing = CRearWing(level = car.rearWing.level, wear = car.rearWing.wear),
        underbody = CUnderbody(level = car.underbody.level, wear = car.underbody.wear),
        sidepods = CSidepods(level = car.sidepods.level, wear = car.sidepods.wear),
        cooling = CCooling(level = car.cooling.level, wear = car.cooling.wear),
        gearbox = CGearbox(level = car.gearbox.level, wear = car.gearbox.wear),
        brakes = CBrakes(level = car.brakes.level, wear = car.brakes.wear),
        suspension = CSuspension(level = car.suspension.level, wear = car.suspension.wear),
        electronics = CElectronics(level = car.electronics.level, wear = car.electronics.wear)
    )
}

fun convertPractiseConditions(practiseConditions: PractiseConditions): CPractiseConditions {
    return CPractiseConditions(trackName = practiseConditions.trackName,
        trackId = practiseConditions.trackId,
        trackPower = practiseConditions.trackPower,
        trackHandling = practiseConditions.trackHandling,
        trackAcceleration = practiseConditions.trackAcceleration,
        temperatureQ1 = practiseConditions.temperatureQ1,
        humidityQ1 = practiseConditions.humidityQ1,
        weather = practiseConditions.weather)
}