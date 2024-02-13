package com.github.dsmiles.todos;

import lombok.Data;

/**
 * A representation of a todo item.
 * This class encapsulates the data related to a todo item, including its ID, user ID, title, and completion status.
 * Lombok is used to replace the boiler plate code (getters and setters)
 */
@Data
public class Todo {
    private Long userId;
    private Long id;
    private String title;
    private boolean completed;
}
