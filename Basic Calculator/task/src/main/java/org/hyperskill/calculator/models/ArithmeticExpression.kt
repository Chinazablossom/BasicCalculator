package org.hyperskill.calculator.models

import java.math.BigDecimal

data class ArithmeticExpression(
    var varX: BigDecimal? = null,
    var operation: String? = null,
    var varY: BigDecimal? = null,
)

