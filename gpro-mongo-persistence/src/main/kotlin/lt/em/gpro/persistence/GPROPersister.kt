package lt.em.gpro.persistence

import lt.em.datamodel.CombinedData
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.slf4j.LoggerFactory

private val LOGGER = LoggerFactory.getLogger(GPROPersister::class.java)
private const val LOCALHOST = "localhost"
private const val GPRO_DB = "gpro_db"
private const val DEFAULT_PORT = 27017

class GPROPersister {
    private val mongoClient = KMongo.createClient(LOCALHOST, DEFAULT_PORT)
    private val database = mongoClient.getDatabase(GPRO_DB)
    init {
        LOGGER.info("Connected to MongoDB")
    }
    private val collection = database.getCollection<CombinedData>("Combined Data Records")

    fun persistCombinedData(combinedData: CombinedData) {
        collection.insertOne(combinedData)
    }

    fun closeDatabaseConnection() {
        mongoClient.close()
    }
}