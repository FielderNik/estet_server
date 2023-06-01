package com.estet.utils.funcional

sealed class Failure(open val exception: Throwable? = null) {

    data class Database(override val exception: Throwable?): Failure(exception) {
        data class Error(override val exception: Throwable?): Failure(exception)
    }

    data class Repository(override val exception: Throwable?): Failure(exception)

    sealed class Feature(override var exception: Throwable? = null): Failure(exception) {
        sealed class Birthday(override var exception: Throwable? = null) : Feature(exception) {
            data class Create(override var exception: Throwable?) : Birthday(exception)
            object NotFound : Birthday()
        }
    }

}
