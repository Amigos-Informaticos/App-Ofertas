package com.example.consumirappi.modelo

import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern

/**
 * An Email format validator for [android.widget.EditText].
 */
class EmailValidator {
    var isValid = false
        private set
    var EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )



    /**
     * Validates if the given input is a valid email address.
     *
     * @param email        The email to validate.
     * @return `true` if the input is a valid email. `false` otherwise.
     */
    fun isValidEmail(email: String): Boolean {
        return email != null && EMAIL_PATTERN.matcher(email).matches()
    }

}