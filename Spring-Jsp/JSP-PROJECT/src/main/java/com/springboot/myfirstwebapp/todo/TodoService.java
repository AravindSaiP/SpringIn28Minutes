package com.springboot.myfirstwebapp.todo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();
    private static int todosCount = 0;
    static {
        todos.add(new Todo(++todosCount, "in28minutes","Learn AWS",
                LocalDate.now().plusYears(1), false ));
        todos.add(new Todo(++todosCount, "in28minutes","Learn DevOps",
                LocalDate.now().plusYears(2), false ));
        todos.add(new Todo(++todosCount, "in28minutes","Learn Full Stack Development",
                LocalDate.now().plusYears(3), false ));
    }

    public List<Todo> findByName(String name){
        return todos;
    }

    public void addNewTodo(String username, String description, LocalDate date, boolean done){
        Todo todo = new Todo(++todosCount,username,description,date,done);
        todos.add(todo);
    }

    public void deleteTodoById(int id){
        Predicate<Todo> predicate = todo -> todo.getId() == id;
        todos.removeIf(predicate);
    }

    public Todo findById(int id) {
        Predicate<Todo> predicate = todo -> todo.getId() == id;
        Todo todo = todos.stream().filter(predicate).findFirst().get();
        return todo;
    }

    public void updateTodo(Todo todo){
        deleteTodoById(todo.getId());
        todos.add(todo);
    }
}
