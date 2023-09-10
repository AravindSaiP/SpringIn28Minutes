package com.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class TodoControllerJPA {
    private TodoService todoService;
    private TodoRepository todoRepository;

    public TodoControllerJPA(TodoService todoService,TodoRepository todoRepository) {
        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    @RequestMapping(value = "list-todos")
    public String listAllTodos(ModelMap model){
        String userName = getLoggedInUserName();
        List<Todo> todos = todoRepository.findByName(userName);
        System.out.println("userName = " + userName);
        System.out.println("todos = " + todos);
        model.addAttribute("todos",todos);
        return "listTodos";
    }

    @RequestMapping(value = "add-todo",method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap map){
        String username = getLoggedInUserName();//(String) map.get("name");
        Todo todo = new Todo(0,"name","",LocalDate.now().plusYears(1),false);
        map.put("todo",todo);
        return "todo";
    }

    @RequestMapping(value = "add-todo",method = RequestMethod.POST)
    public String addNewTodo(ModelMap map, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }
        String userName = getLoggedInUserName();
        todo.setName(userName);
        todoRepository.save(todo);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "delete-todo",method = RequestMethod.GET)
    public String deleteTodo(@RequestParam int id){
        todoRepository.deleteById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo")
    public String showUpdateTodoPage(@RequestParam int id, ModelMap map){
        Todo todo = todoRepository.findById(id).get();
        map.put("todo",todo);
        return "todo";
    }

    @RequestMapping(value = "update-todo",method = RequestMethod.POST)
    public String updateTodo(ModelMap map, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }
        String username = getLoggedInUserName();
        todo.setName(username);
        todoRepository.save(todo);
        return "redirect:list-todos";
    }

    private String getLoggedInUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
