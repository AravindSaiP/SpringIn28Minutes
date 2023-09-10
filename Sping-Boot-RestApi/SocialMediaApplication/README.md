
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.15.2</version>
</dependency>

This dependency is used to give different responses for same url

EX:- /users gives all the list of users.
If client wants data to be in xml format he can set new header Accept:application/xml
If client wants data to be in json format he can set new header Accept:application/json (default)
This is called content negotiation bcz we are giving response in any format



<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.0.0</version>
</dependency>

This dependency generates API documentation automatically
To access Swagger Ui and test the URLs use:- http://localhost:8080/swagger-ui/index.html
To access open api documentation use:- http://localhost:8080/v3/api-docs or Link is already provided in swagger UI

//Read version which represented in the code directly

HATEOAS:-

When we are giving response to the consumer we are not only giving data we are also giving operations to perform.

EX:- Once we open GitHub page and open master branch there we see all the codes available and also differet oprations such a cloning repo, switching branches etc...
Hence, along with the data we should also need to send links 
Example of such response:-

{
        "name": "Adam",
        "birthDate": "1992-08-19",
        "_links": {
            "all-users": {
            "href": "http://localhost:8080/users"
        }
    }
}
We can achieve this by making changes in the code but it is very complex.

By using Spring.HATEOAS we can do this very easily

Add this dependency

<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>

Add this lines code

EntityModel<User> entityModel = EntityModel.of(user);
WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
entityModel.add(link.withRel("all-users"));

To understand this code please look at User package and UserService class. we can undestand this


STATIC FILTERING:-

It is to ignore some fields for all requests. It can be achieved by putting @JsonIgnore property

DYNMAIC FILTERING:-

It is to ignore or Not ignore some fields according to the requests(url). It can be achieved by using the code 

MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);

SimpleBeanPropertyFilter filter = 
    SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		
FilterProvider filters = 
    new SimpleFilterProvider().addFilter("SomeBeanFilter", filter );
		
mappingJacksonValue.setFilters(filters );

To undestand this code please see version package and codes




