package com.demo.dnb.calculatorapi.controller
import com.ashvanth.kotlin.kotlinDemo.model.Arithmetic
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class `PostController` {

    @GetMapping("/posts")
    fun getPosts(): String {
        // Logic to retrieve posts
        return "List of posts"
    }

    @PostMapping("/arthimaticOperation")
    fun arthimaticOperation(@RequestBody arithmetic: Arithmetic): String {
        val firstNumber = arithmetic.first_number
        val secondNumber = arithmetic.second_number

        return when (arithmetic.operation) {
            "ADD" -> (firstNumber + secondNumber).toString()
            "SUBTRACT" -> (firstNumber - secondNumber).toString()
            "MULTIPLY" -> (firstNumber * secondNumber).toString()
            "DIVIDE" -> (firstNumber / secondNumber).toString()
            else -> "Given Operator is INVALID"
        }
    }

}
