package br.com.patrickriibeiro.tasks.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(){
        super("Task not found.");
    }

}
