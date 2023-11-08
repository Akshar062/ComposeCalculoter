package com.akshar.composecalculoter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.akshar.composecalculater.CalculatorAction
import com.akshar.composecalculoter.CalculatorState

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction){
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDelete()

        }
    }

    private fun performDelete() {
        when{
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        val result = when (state.operation){
            CalculationOperation.Add -> number1?.plus(number2 ?: 0.0)
            CalculationOperation.Subtract -> number1?.minus(number2 ?: 0.0)
            CalculationOperation.Multiply -> number1?.times(number2 ?: 0.0)
            CalculationOperation.Divide -> number1?.div(number2 ?: 0.0)
            null -> return
        }
        state = state.copy(
            number1 = result.toString().take(15),
            number2 = "",
            operation = null
        )
    }

    private fun enterOperation(operation: CalculationOperation) {
        if (state.number1.isNotBlank()){
            state = state.copy(operation = operation)

        }
    }

    private fun enterDecimal() {
        if (state.operation == null
            && !state.number1.contains('.')
            && state.number1.isNotBlank()
            ){
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        if (!state.number2.contains('.')
            && state.number2.isNotBlank()
            ){
            state = state.copy(
                number2 = state.number2 + "."
            )
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null){
            if (state.number1.length >= MAX_NUM_LENGTH){
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if (state.number2.length >= MAX_NUM_LENGTH){
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )

    }
    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}