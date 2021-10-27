package br.com.estoque.estoque.compartilhado.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ErrosHandler() {
    @Autowired
    lateinit var messageSource: MessageSource

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<Any>  {
        val dto: MutableList<ErrorsDto> = mutableListOf()
        val fieldErrors: MutableList<FieldError> = ex.bindingResult.fieldErrors

        fieldErrors.forEach { e ->
            val msg: String = messageSource.getMessage(e, LocaleContextHolder.getLocale())
            val erro = ErrorsDto(e.field, msg)
            dto.run {
                add(erro)
            }
        }

        return ResponseEntity.badRequest().body(dto)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameterException(ex: MissingServletRequestParameterException): ResponseEntity<Any>  {
        val mensagemErro: String? = ex.localizedMessage
        return ResponseEntity.badRequest().body(mensagemErro)
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun responseStatusException(ex: ResponseStatusException): ResponseEntity<Any>  {
        val mensagemErro: String? = ex.reason
        return ResponseEntity.status(ex.status).body(mensagemErro)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(ex: ConstraintViolationException): ResponseEntity<Any>  {
        val dto: MutableList<ErrorsDto> = mutableListOf()
        val erros = ex.constraintViolations

        erros.forEach { e ->
            val erro = ErrorsDto(field = e.propertyPath.toString(), message = e.message)
            dto.run { add(erro) }
        }

        return ResponseEntity.badRequest().body(dto)
    }
}