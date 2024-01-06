package com.kendis.viewmodeleventantipatternimprovement.repository

import com.kendis.viewmodeleventantipatternimprovement.presentation.PaymentModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PaymentsRepositoryImpl(private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    PaymentsRepository {
    override suspend fun makePayment(payment: PaymentModel): Boolean =
        withContext(defaultDispatcher) {
            delay(2000)
            true
        }
}