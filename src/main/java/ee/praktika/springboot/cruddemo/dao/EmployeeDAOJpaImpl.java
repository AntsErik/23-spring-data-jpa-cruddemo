package ee.praktika.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ee.praktika.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    private EntityManager entityManager;

    public EmployeeDAOJpaImpl( EntityManager theEntityManager ) {
        entityManager = theEntityManager;
    }

    @Override
    public List<Employee> findAll(){

        //create a query
        Query theQuery = entityManager.createQuery( "from Employee" );

        //excecute the query to get result in
        List<Employee> employees = theQuery.getResultList();

        //return the result
        return employees;
    }

    @Override
    public Employee findById( int theId ){

        //get employee
        Employee theEmployee = entityManager.find( Employee.class, theId );

        //return the employee
        return theEmployee;
    }

    @Override
    public void save( Employee theEmployee ){

        //save/update the employee
        Employee dbEmployee = entityManager.merge( theEmployee );

        //updated with id from the DB..this will return the id for save/insert
        theEmployee.setId( dbEmployee.getId() );
    }

    @Override
    public void deleteById( int theId ){

        //delete employee with primary key
        Query theQuery = entityManager.createQuery( "delete from Employee where id=:employeeId" );

        theQuery.setParameter( "employeeId", theId );

        theQuery.executeUpdate();
    }
}
