package br.com.patrickriibeiro.tasks.controller.dto;

import br.com.patrickriibeiro.tasks.model.TaskState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class TaskUpdateDTO implements Serializable {

    @NotBlank(message = "{blank.id}")
    private String id;

    @NotBlank(message = "{blank.title}")
    @Size(min = 3, max = 20, message = "{size.title}")
    private String title;

    @NotBlank(message = "{blank.description}")
    @Size(min = 10, max = 50, message = "{size.description}")
    private String description;

    @Min(value = 1, message = "{min.priority}")
    private int priority;

    public TaskUpdateDTO() {
    }

    public TaskUpdateDTO(int priority, String description, String title, String id) {
        this.priority = priority;
        this.description = description;
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
