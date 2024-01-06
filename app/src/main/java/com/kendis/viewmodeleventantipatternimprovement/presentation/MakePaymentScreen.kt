package com.kendis.viewmodeleventantipatternimprovement.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MakePaymentScreen(
    onPaymentMade: (PaymentModel, Boolean) -> Unit,
    viewModel: MakePaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // This field is generated to prevent navigate to Success Screen after click back from Success Screen
    var isOneTimeProcessRunning by rememberSaveable { mutableStateOf(false) }

    uiState.paymentResult?.let { paymentResult ->
        if (isOneTimeProcessRunning) {
            val currentOnPaymentMade by rememberUpdatedState(onPaymentMade)
            LaunchedEffect(uiState) {
                isOneTimeProcessRunning = false
                currentOnPaymentMade(
                    paymentResult.paymentModel,
                    paymentResult.isPaymentSuccessful
                )
            }
        }
    }
    // Rest of the UI for the login screen.

    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            viewModel.makePayment()
            isOneTimeProcessRunning = true
        }) {
            Text(text = "Payment")
        }
    }
}