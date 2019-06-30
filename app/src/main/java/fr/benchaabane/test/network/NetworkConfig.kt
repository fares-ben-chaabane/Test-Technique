package fr.benchaabane.test.network


class NetworkConfig private constructor(val baseUrl: String,
                                        val connectionTimeOut: Long = 10 * SECOND,
                                        val socketTimeOut: Long = connectionTimeOut) {

    companion object {

        val PROD = NetworkConfig(baseUrl = "https://parseapi.back4app.com/")

        val DEV = NetworkConfig(baseUrl = "https://parseapi.back4app.com/",
                                connectionTimeOut = 15 * SECOND)

        const val CACHE_SIZE = 10 * MEGA

    }
}

typealias ENV = String

fun networkConfig(env: ENV) = when (env) {
    "PROD" -> NetworkConfig.PROD
    "DEV" -> NetworkConfig.DEV
    else -> NetworkConfig.DEV
}

private const val SECOND = 1000L
private const val MEGA = 1024 * 1024L
