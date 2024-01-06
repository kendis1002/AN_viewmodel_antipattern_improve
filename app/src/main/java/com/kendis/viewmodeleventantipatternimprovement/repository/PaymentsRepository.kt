package com.kendis.viewmodeleventantipatternimprovement.repository

import com.kendis.viewmodeleventantipatternimprovement.presentation.PaymentModel

interface PaymentsRepository {
    suspend fun makePayment(payment: PaymentModel): Boolean
}