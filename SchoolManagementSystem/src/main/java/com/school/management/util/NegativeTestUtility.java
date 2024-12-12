//package com.school.management.util;
//
//import com.school.management.exception.BadRequestServiceAlertException;
//
//public class NegativeTestUtility {
//

//    public static void validateCreateException(org.junit.jupiter.api.function.Executable executable) {
//        BadRequestServiceAlertException exception = assertThrows(BadRequestServiceAlertException.class, executable);
//        assertEquals("Error while creating the entity.", exception.getMessage(), "Exception message did not match!");
//    }
//
//
//    public static void validateRetrieveByIdException(org.junit.jupiter.api.function.Executable executable) {
//        BadRequestServiceAlertException exception = assertThrows(BadRequestServiceAlertException.class, executable);
//        assertEquals("Entity not found.", exception.getMessage(), "Exception message did not match!");
//    }
//
//    public static void validateRetrieveAllException(org.junit.jupiter.api.function.Executable executable) {
//        BadRequestServiceAlertException exception = assertThrows(BadRequestServiceAlertException.class, executable);
//        assertEquals("Error while retrieving entities.", exception.getMessage(), "Exception message did not match!");
//    }

//    public static void validateDeleteException(org.junit.jupiter.api.function.Executable executable) {
//        BadRequestServiceAlertException exception = assertThrows(BadRequestServiceAlertException.class, executable);
//        assertEquals("Error while deleting the entity.", exception.getMessage(), "Exception message did not match!");
//    }
//}
