package br.com.patrickriibeiro.tasks.model;


import br.com.patrickriibeiro.tasks.service.TaskService;
import org.springframework.data.annotation.Id;

public class Task {

    @Id
    private String id;

    private String title;

    private String description;

    private int priority;

    private TaskState state;

    public Task insert(){
        return builderFrom(this)
                .withState(TaskState.INSERT)
                .build();
    };

    public Task() {
    }

    public Task(Builder builder) {
       this.title = builder.getTitle();
       this.description = builder.getDescription();
       this.priority = builder.getPriority();
       this.state = builder.getState();
    }

    public static Builder builderFrom(Task task){
      return new Builder(task);
    };

    public int getPriority() {
        return priority;
    }

    public String getId() {
        return id;
    }

    public TaskState getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public static Builder builder(){
      return new Builder();
    };

    public static class Builder {

        private String id;
        private String title;
        private String description;
        private int priority;
        private TaskState state;

        public Builder(Task task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.priority = task.getPriority();
            this.state = task.getState();
        }

        public Builder() {
        }

        public String getId(){
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getPriority() {
            return priority;
        }

        public TaskState getState() {
            return state;
        }

        public Builder withTitle(String Title) {
          this.title = title;
          return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder withState(TaskState state) {
            this.state = state;
            return this;
        }

        public Task build(){
            return new Task(this);
        }
    }
}
