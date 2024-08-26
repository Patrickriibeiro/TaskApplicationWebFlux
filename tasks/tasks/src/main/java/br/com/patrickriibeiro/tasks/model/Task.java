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

    private Address address;

    public Task insert(){
        return builderFrom(this)
                .withState(TaskState.INSERT)
                .build();
    };

    public Task update(Task oldTask){
        return builderFrom(this)
                .withState(oldTask.getState())
                .build();
    }

    public Task() {
    }

    public Task(Builder builder) {
        this.id = builder.getId();
       this.title = builder.getTitle();
       this.description = builder.getDescription();
       this.priority = builder.getPriority();
       this.state = builder.getState();
       this.address = builder.getAddress();
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

    public Address getAddress() {
        return address;
    }

    public static Builder builder(){
      return new Builder();
    };

    public Task updateAddress(Address address) {
        return builderFrom(this)
                .withAddress(address)
                .build();
    }

    public Task start() {
        return builderFrom(this)
                .withState(TaskState.DOING)
                .build();
    }

    public static class Builder {

        private String id;
        private String title;
        private String description;
        private int priority;
        private TaskState state;
        private Address address;

        public Builder(Task task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.priority = task.getPriority();
            this.state = task.getState();
            this.address = task.getAddress();
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

        public Address getAddress() {
            return address;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
          this.title = title;
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

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Task build(){
            return new Task(this);
        }
    }
}
