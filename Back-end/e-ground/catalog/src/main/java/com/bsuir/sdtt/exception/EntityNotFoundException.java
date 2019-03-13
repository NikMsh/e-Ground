package com.bsuir.sdtt.exception;

/**
 * Class of Entity Not Found Exception.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
