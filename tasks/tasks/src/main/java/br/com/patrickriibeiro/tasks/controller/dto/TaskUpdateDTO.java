package br.com.patrickriibeiro.tasks.controller.dto;

import br.com.patrickriibeiro.tasks.model.TaskState;

import java.io.Serializable;

public class TaskUpdateDTO implements Serializable {

    private String id;
    private String title;
    private String description;
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
