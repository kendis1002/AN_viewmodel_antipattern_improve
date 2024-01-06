package com.kendis.viewmodeleventantipatternimprovement.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kendis.viewmodeleventantipatternimprovement.repository.PaymentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class PaymentModel(
    val amount: Int,
)

data class PaymentResult(
    val paymentModel: PaymentModel,
    val isPaymentSuccessful: Boolean
)

data class MakePaymentUiState(
    val paymentInfo: PaymentModel,
    val isLoading: Boolean = false,
    val paymentResult: PaymentResult? = null
)

@HiltViewModel
class MakePaymentViewModel @Inject constructor(private val paymentsRepository: PaymentsRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(
        MakePaymentUiState(
            paymentInfo = PaymentModel(1000),
        )
    )
    val uiState: StateFlow<MakePaymentUiState> = _uiState.asStateFlow()

    // Protecting makePayment from concurrent callers
    // If a payment is in progress, don't trigger it again
    private var makePaymentJob: Job? = null

    fun makePayment() {
        if (makePaymentJob != null) return

        makePaymentJob = viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val isPaymentSuccessful = paymentsRepository.makePayment(_uiState.value.paymentInfo)

                // The event of what to do when the payment response comes back
                // is immediately handled here. It causes a UI state update.
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        paymentResult = PaymentResult(it.paymentInfo, isPaymentSuccessful)
                    )
                }
            } catch (ioe: IOException) {
                print(ioe)
            } finally {
                makePaymentJob = null
            }
        }
    }
}