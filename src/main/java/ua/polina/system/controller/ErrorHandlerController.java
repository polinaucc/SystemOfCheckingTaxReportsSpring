package ua.polina.system.controller;

//
//@RestControllerAdvice
//public class ErrorHandlerController extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(ResourceNotFoundException.class)
//    protected ResponseEntity<ApiResponse> handleResourceNotFoundException() {
//        return new ResponseEntity<>(
//                new ApiResponse(
//                        false,
//                        "There is no such resource"
//                ),
//                HttpStatus.NOT_FOUND
//        );
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//        return new ResponseEntity<>(
//                new ApiResponse(
//                        false,
//                        "Not valid field"
//                ),
//                HttpStatus.BAD_REQUEST
//        );
//    }
//}