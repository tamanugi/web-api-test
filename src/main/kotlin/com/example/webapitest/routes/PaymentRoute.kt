package com.example.webapitest.routes

import com.example.webapitest.model.User
import com.example.webapitest.service.PaymentService
import com.example.webapitest.service.UserService
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate


// -------
// request
// -------
data class RegisterPayment(val amount: Int)


// -------
// response
// -------
data class AmountResponse(
    val amount: Int
)

// -------
// resource
// -------

@Resource("/payments")
class PaymentResource() {

    @Resource("amount")
    class Amount(val parent: PaymentResource = PaymentResource(), val from: String, val to: String)

    @Resource("")
    class New(val parent: PaymentResource = PaymentResource())
}

// -------
// routing
// -------
fun Route.paymentRoute(
    // TODO: DI by koin
    paymentService: PaymentService
) {

    get<PaymentResource.Amount> {
        val amount =  paymentService.amount(it.from, it.to)
        call.respond(AmountResponse(amount = amount))
    }

    post<PaymentResource.New> {
        val registerPayment = call.receive<RegisterPayment>()
        val payment = paymentService.register(amount = registerPayment.amount)
        call.response.status(HttpStatusCode.Created)
        call.respond(mapOf("payment_id" to payment.id))
    }
}
