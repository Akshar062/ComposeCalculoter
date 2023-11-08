package com.akshar.composecalculoter

import com.akshar.composecalculoter.CalculationOperation

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculationOperation? = null,
)
