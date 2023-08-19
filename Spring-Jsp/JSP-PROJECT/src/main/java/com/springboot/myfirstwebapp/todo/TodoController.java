package com.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")

//Should take notes on request scope and session scope
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(value = "list-todos")
    public String listAllTodos(ModelMap model){
        List<Todo> todos = todoService.findByName("in28Minutes");
        System.out.println("todos = " + todos);
        model.addAttribute("todos",todos);
        return "listTodos";
    }

    @RequestMapping(value = "add-todo",method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap map){
        String username = (String) map.get("name");
        Todo todo = new Todo(0,"name","",LocalDate.now().plusYears(1),false);
        map.put("todo",todo);
        return "todo";
    }

    @RequestMapping(value = "add-todo",method = RequestMethod.POST)
    public String addNewTodo(ModelMap map, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }
        String username = (String) map.get("name");
        todoService.addNewTodo(username,todo.getDescription(), todo.getDate(),false);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "delete-todo",method = RequestMethod.GET)
    public String deleteTodo(@RequestParam int id){
        todoService.deleteTodoById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo")
    public String showUpdateTodoPage(@RequestParam int id, ModelMap map){
        Todo todo = todoService.findById(id);
        map.put("todo",todo);
        return "todo";
    }

    @RequestMapping(value = "update-todo",method = RequestMethod.POST)
    public String updateTodo(ModelMap map, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }
        String username = (String) map.get("name");
        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }
}
