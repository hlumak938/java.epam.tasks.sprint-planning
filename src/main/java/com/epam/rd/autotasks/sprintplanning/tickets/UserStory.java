package com.epam.rd.autotasks.sprintplanning.tickets;

public class UserStory extends Ticket {

    private final UserStory[] dependencies;

    public UserStory(int id, String name, int estimate, UserStory... dependsOn) {
        super(id, name, estimate);
        dependencies = dependsOn;
    }

    @Override
    public void complete() {
        for(UserStory dependency : dependencies) if(!dependency.isCompleted()) return;
        super.complete();
    }

    public UserStory[] getDependencies() {
        UserStory[] defensiveDependencies = new UserStory[dependencies.length];
        System.arraycopy(dependencies, 0, defensiveDependencies, 0, dependencies.length);
        return defensiveDependencies;
    }

    @Override
    public String toString() {
        return String.format("[US %d] %s", getId(), getName());
    }
}
