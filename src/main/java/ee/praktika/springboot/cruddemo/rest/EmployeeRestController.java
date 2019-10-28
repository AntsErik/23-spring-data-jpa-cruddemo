package ee.praktika.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ee.praktika.springboot.cruddemo.entity.Employee;
import ee.praktika.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping( "/api" )
public class EmployeeRestController {

    private EmployeeService employeeService;

    //inject employee DAO directly
    @Autowired
    public EmployeeRestController( EmployeeService theEmployeeService ) {
        employeeService = theEmployeeService;
    }

    //expose "/employees" endpoint and return all the employees
    @GetMapping( "/employees" )
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    //add a mapping to read employee
    @GetMapping( "/employees/{employeeId}" )
    public Employee getEmployee( @PathVariable int employeeId ){

        Employee theEmpoyee = employeeService.findById( employeeId );

        if( theEmpoyee == null ) {
            throw new RuntimeException( "Employee id not found : " + employeeId );
        }
        return theEmpoyee;
    }

    //add mapping for creating employee
    @PostMapping( "/employees" )
    public Employee addEmployee( @RequestBody Employee theEmployee ){

        //need to set default id to 0
        //this is done to force save over update.
        theEmployee.setId( 0 );
        employeeService.save( theEmployee );

        return theEmployee;
    }

    //add mapping to update existing employee
    @PutMapping( "/employees" )
    public Employee updateEmployee( @RequestBody Employee theEmployee ){
        employeeService.save( theEmployee );

        return theEmployee;
    }

    //add mapping to delete employee
    @DeleteMapping( "/employees/{employeeId}" )
    public String deleteEmployee( @PathVariable int employeeId ){

        Employee tempEmployee = employeeService.findById( employeeId );

        //throw exception if null
        if( tempEmployee == null ) {
            throw new RuntimeException( "Employee id not found: " + employeeId );
        }
        employeeService.deleteById( employeeId );

        return "Deleted employee id: " + employeeId;
    }
}
