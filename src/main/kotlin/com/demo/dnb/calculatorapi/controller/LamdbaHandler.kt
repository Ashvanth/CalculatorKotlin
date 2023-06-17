package com.demo.dnb.calculatorapi.controller

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.ashvanth.kotlin.kotlinDemo.model.Arithmetic
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import java.io.InputStream
import java.io.OutputStream

private val log = KotlinLogging.logger {}

class LambdaHandler : RequestStreamHandler {
    private val postController = PostController()

    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        val requestBody = readInputStream(inputStream)

        val arithmetic: Arithmetic
        val objectMapper = ObjectMapper()
        try {
            arithmetic = objectMapper.readValue(requestBody, Arithmetic::class.java)
            log.debug("----------------Value1------------${arithmetic.first_number}")
            log.debug("----------------Value2------------${arithmetic.second_number}")
            log.debug("----------------Operator------------${arithmetic.operation}")
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }

        val result = postController.arthimaticOperation(arithmetic)

        val responseBody = "Value for the Provided arithmeticOperation -> ${arithmetic.operation} is = $result"
        outputStream.write(responseBody.toByteArray())
    }

    private fun readInputStream(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            stringBuilder.append(String(buffer, 0, bytesRead))
        }
        return stringBuilder.toString()
    }
}
