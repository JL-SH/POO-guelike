class Validator<T>(private val validationRule: (T) -> Boolean) {
    // Function that checks if the provided input is valid according to the validation rule
    fun isValid(input: T): Boolean = validationRule(input)

    companion object {
        // A regular expression to validate names (3 to 15 alphanumeric characters or spaces)
        val VALID_NAME = Regex("^[a-zA-Z0-9 ]{3,15}$")
        // A specific validator for validating names (String) using the VALID_NAME regex
        val NAME_VALIDATOR = Validator<String> { it.matches(VALID_NAME) }
    }
}
