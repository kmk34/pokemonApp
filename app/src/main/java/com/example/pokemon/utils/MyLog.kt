package com.example.pokemon.utils

object MyLog {

    private const val STACK_TRACE_LEVELS_UP = 5
    private const val LOGGING_ENABLED = true

    private fun getLineNumber(): Int {
        return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].lineNumber
    }

    private fun getMethodName(): String {
        return Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].methodName
    }

    private fun getClassName(): String {
        val fileName = Thread.currentThread().stackTrace[STACK_TRACE_LEVELS_UP].fileName
        return fileName.substringBefore(".")
    }

    private fun getClassMethodLine(): String {
        return "[${getClassName()}.${getMethodName()}(): ${getLineNumber()}]"
    }

    fun d(message: String) {
        if (LOGGING_ENABLED) {
            android.util.Log.d(getClassMethodLine(), message)
        }
    }

    fun e(message: String, throwable: Throwable? = null) {
        if (LOGGING_ENABLED) {
            android.util.Log.e(getClassMethodLine(), message, throwable)
        }
    }
}