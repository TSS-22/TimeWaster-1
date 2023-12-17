package com.tss.timewaster_1.domain.use_case

class CheckIntInput() {

    operator fun invoke(
        input: Any
    ): Result{
        if(input is Int){
            return Result.Success(input)
        } else {
            return Result.Failure("The data in not an Int")
        }
    }

    sealed class Result {
        data class Success(
            val value: Int
        ): Result()

        data class Failure(
            val message: String
        ): Result()
    }
}