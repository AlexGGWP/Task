package com.tryzens.task.rest.api

/**
 * Handle the errors, that might occur
 */
data class APIError(val message: String) {
    constructor() : this("")
}