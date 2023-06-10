package com.estet.database

import com.estet.utils.funcional.Either
import com.estet.utils.funcional.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseService {

    suspend fun <T> handleRequest(fn: suspend () -> T): Either<Failure, T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = fn()
                Either.Right(result)
            } catch (ex: Exception) {
                ex.printStackTrace()
                Either.Left(Failure.Database.Error(ex))
            }
        }

    }
}