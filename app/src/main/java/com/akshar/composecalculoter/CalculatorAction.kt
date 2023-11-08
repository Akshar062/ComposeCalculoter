package com.akshar.composecalculater

import com.akshar.composecalculoter.CalculationOperation

sealed class CalculatorAction{
    data class Number(val number: Int): CalculatorAction()
    object Clear: CalculatorAction()
    object Delete: CalculatorAction()
    object Decimal: CalculatorAction()
    object Calculate: CalculatorAction()
    data class Operation(val operation: CalculationOperation): CalculatorAction()

}
