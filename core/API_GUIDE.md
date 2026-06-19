# Core Library API Guide

This guide describes how to use the Logger, Result Wrapper, and Dispatcher Provider APIs in production.

---

## 1. Logger API

### Basic Logging
```kotlin
import com.codewiththiru.platform.core.logger.CustLogger

// Log messages across different severities
CustLogger.v("API", "Verbose log detailing network payloads")
CustLogger.d("UI", "Button clicked successfully")
CustLogger.i("AUTH", "User authenticated: id=123")
CustLogger.w("CACHE", "Cache size threshold exceeded")
CustLogger.e("DATABASE", "Failed to insert record", exception)
```

### Customizing Configuration
Initialize the logger with custom filters:
```kotlin
import com.codewiththiru.platform.core.logger.CustLogger
import com.codewiththiru.platform.core.logger.CustLoggerConfig
import com.codewiththiru.platform.core.logger.CustLogLevel

CustLogger.initialize(
    config = CustLoggerConfig(
        enabled = true,
        minLevel = CustLogLevel.WARN // Only WARN and ERROR logs will print
    )
)
```

### Pluggable Logging Engines
You can plug in custom engines (e.g. Firebase Crashlytics, local files):
```kotlin
import com.codewiththiru.platform.core.logger.CustLogger
import com.codewiththiru.platform.core.logger.CustLogPrinter
import com.codewiththiru.platform.core.logger.CustLogLevel

class FileLogPrinter : CustLogPrinter {
    override fun printLog(level: CustLogLevel, tag: String, message: String, throwable: Throwable?) {
        // Write formatted log to a local file
    }
}

// Add at initialization or dynamically:
CustLogger.addPrinter(FileLogPrinter())
```

---

## 2. Result Wrapper API

### Wrapping code throwing exceptions
```kotlin
import com.codewiththiru.platform.core.result.CustResult
import com.codewiththiru.platform.core.result.custRunCatching

val result: CustResult<User> = custRunCatching {
    networkApi.fetchUser(userId)
}
```

### Processing results
```kotlin
import com.codewiththiru.platform.core.result.fold
import com.codewiththiru.platform.core.result.map
import com.codewiththiru.platform.core.result.flatMap
import com.codewiththiru.platform.core.result.recover
import com.codewiththiru.platform.core.result.getOrElse
import com.codewiththiru.platform.core.result.onSuccess
import com.codewiththiru.platform.core.result.onFailure

// 1. Fold to resolve states
val displayMessage = result.fold(
    onSuccess = { user -> "Welcome, ${user.name}!" },
    onFailure = { error -> "Error: ${error.localizedMessage}" }
)

// 2. Chaining operations
val emailResult = result
    .map { it.email }
    .onSuccess { email -> println("Fetched email: $email") }
    .onFailure { error -> println("Failed to fetch email: ${error.message}") }

// 3. Fallback recovery
val finalUser = result.recover { error ->
    User.DEFAULT // Fallback user on error
}.getOrElse { User.DEFAULT }
```

---

## 3. Dispatcher Provider API

### Constructor Injection in Classes
```kotlin
import com.codewiththiru.platform.core.dispatcher.CustDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(
    private val dispatcherProvider: CustDispatcherProvider
) {
    suspend fun fetchData() = withContext(dispatcherProvider.io) {
        // Perform IO operation on Dispatchers.IO
    }
}
```

### Mocking Dispatchers in Unit Tests
```kotlin
import com.codewiththiru.platform.core.dispatcher.CustDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Test

class UserRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()
    
    // Inject mock dispatchers into repository
    private val mockProvider = object : CustDispatcherProvider {
        override val main: CoroutineDispatcher = testDispatcher
        override val io: CoroutineDispatcher = testDispatcher
        override val default: CoroutineDispatcher = testDispatcher
        override val unconfined: CoroutineDispatcher = testDispatcher
    }

    private val repository = UserRepository(mockProvider)
}

---

## 4. Date Utilities API

### Formatting and Parsing Dates (`CustDateFormatter`)
`CustDateFormatter` handles modern Java time instances (`Instant`, `LocalDate`, `LocalDateTime`, `ZonedDateTime`) with timezone-aware conversions and pattern caching.

```kotlin
import com.codewiththiru.platform.core.date.CustDateFormatter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Locale

// 1. Formatting Instant to a custom timezone
val instant = Instant.now()
val formattedKolkata = CustDateFormatter.formatInstant(
    instant = instant,
    pattern = "yyyy-MM-dd HH:mm:ss",
    zoneId = ZoneId.of("Asia/Kolkata")
)

// 2. Localized Month Formatting
val localDate = LocalDate.now()
val monthNameUS = CustDateFormatter.formatLocalDate(localDate, "MMMM", Locale.US) // "June"
val monthNameFR = CustDateFormatter.formatLocalDate(localDate, "MMMM", Locale.FRENCH) // "juin"

// 3. Parsing Date strings back to ZonedDateTime
val parsedZonedDateTime = CustDateFormatter.parseToZonedDateTime(
    text = "2026-06-19 12:00:00",
    pattern = "yyyy-MM-dd HH:mm:ss",
    zoneId = ZoneOffset.UTC
)
```

### Localized Relative Time Formatting (`RelativeTimeFormatter`)
`RelativeTimeFormatter` represents intervals of time in human-readable terms. It is built to support internationalization and custom locales.

```kotlin
import com.codewiththiru.platform.core.date.RelativeTimeFormatter
import com.codewiththiru.platform.core.date.RelativeTimeStrings
import java.time.Instant
import java.time.ZoneOffset

val formatter = RelativeTimeFormatter()
val now = Instant.now()

// Format relative difference
val relativeText = formatter.formatRelative(
    from = now.minusSeconds(120), // 2 minutes ago
    to = now,
    zoneId = ZoneOffset.UTC
) // Outputs: "2 minutes ago"

// Formatter with custom strings (e.g. Spanish)
val spanishStrings = object : RelativeTimeStrings {
    override fun justNow() = "Ahora mismo"
    override fun minutesAgo(count: Long) = "Hace $count minutos"
    override fun hoursAgo(count: Long) = "Hace $count horas"
    override fun yesterday() = "Ayer"
    override fun daysAgo(count: Long) = "Hace $count días"
    override fun weeksAgo(count: Long) = "Hace $count semanas"
    override fun monthsAgo(count: Long) = "Hace $count meses"
    override fun yearsAgo(count: Long) = "Hace $count años"
}

val spanishFormatter = RelativeTimeFormatter(spanishStrings)
val relativeSpanish = spanishFormatter.formatRelative(now.minusSeconds(120), now) // Outputs: "Hace 2 minutos"
```

---

## 5. String Utilities API

We enforce the Single Responsibility Principle by using focused, lightweight validator and utility objects instead of a massive `StringUtils` god object.

### Email Validation (`EmailValidator`)
Validates structural correctness of email addresses against RFC 5322 specs:

```kotlin
import com.codewiththiru.platform.core.string.EmailValidator

val isEmailValid = EmailValidator.isValid("user.name+tag@domain.co.uk") // true
val isInvalid = EmailValidator.isValid("plainaddress") // false
```

### Phone Validation (`PhoneValidator`)
Validates subscriber phone numbers conforming to the E.164 international standard:

```kotlin
import com.codewiththiru.platform.core.string.PhoneValidator

// Strict E.164 Check
val isE164 = PhoneValidator.isValidE164("+14155552671") // true
val isInvalidE164 = PhoneValidator.isValidE164("+1 (415) 555-2671") // false (contains spacing symbols)

// International Norm Clean Check
val isIntPhone = PhoneValidator.isValidInternational("+1 (415) 555-2671") // true (strips visual spacing)
```

### URL Encoding/Decoding (`UrlEncoder`)
Provides encoding/decoding parameter wrappers utilizing standard UTF-8 charset:

```kotlin
import com.codewiththiru.platform.core.string.UrlEncoder

val encoded = UrlEncoder.encode("hello world & welcome") // "hello+world+%26+welcome"
val decoded = UrlEncoder.decode(encoded) // "hello world & welcome"
```

### HTML Utilities (`HtmlUtils`)
Secures and strips markup tags from raw text outputs:

```kotlin
import com.codewiththiru.platform.core.string.HtmlUtils

// 1. Tag Stripping
val cleanText = HtmlUtils.stripHtml("<p>Hello <b>World</b></p>") // "Hello World"

// 2. Character Entity Escaping
val safeHtml = HtmlUtils.escapeHtml("A & B < C") // "A &amp; B &lt; C"

// 3. Character Entity Unescaping
val normalText = HtmlUtils.unescapeHtml("A &amp; B &lt; C") // "A & B < C"
```

```
