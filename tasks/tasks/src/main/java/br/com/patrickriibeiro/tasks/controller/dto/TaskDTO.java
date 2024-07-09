package br.com.patrickriibeiro.tasks.controller.dto;

import br.com.patrickriibeiro.tasks.model.TaskState;

public class TaskDTO {

    private String id;
    private String title;
    private String description;
    private int priority;
    private TaskState state;

    public TaskDTO() {
    }

    public TaskDTO(String title, String description, int priority, TaskState state, String id) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.state = state;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

}



