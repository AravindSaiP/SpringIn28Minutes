package com.spring_framework.learnspringframework02.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class BusinessApplication{

   // @Autowired
    private Dependency1 dependency1;
   // @Autowired
    private Dependency2 dependency2;

    public BusinessApplication(Dependency1 dependency1, Dependency2 dependency2) {
        //System.out.println(" Dependency created ");
        this.dependency1 = dependency1;
        this.dependency2 = dependency2;
    }

//    @Autowired
//    public void setDependency1(Dependency1 dependency1) {
//        this.dependency1 = dependency1;
//    }
//
//    @Autowired
//    public void setDependency2(Dependency2 dependency2) {
//        this.dependency2 = dependency2;
//    }
}

@Component
class Dependency1 {

}
@Component
class Dependency2{

}

@Configuration
@ComponentScan
public class DependecyInjectionApplication {
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(DependecyInjectionApplication.class)){
            Arrays.stream(context.getBeanDefinitionNames()).forEach(
                    System.out::println
            );
        }
    }
}
