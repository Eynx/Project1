package com.revature.services;

import com.revature.Models.Person;
import com.revature.Models.Reimbursment;
import com.revature.daos.PersonDAO;
import com.revature.daos.ReimbursmentDAO;
import com.revature.exceptions.PersonNotFoundException;
import com.revature.exceptions.RiembursmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonDAO personDao;
    private final ReimbursmentDAO reimbursmentDao;

    @Autowired
    public PersonService(PersonDAO personDao, ReimbursmentDAO reimbursmentDao) {
        this.personDao = personDao;
        this.reimbursmentDao = reimbursmentDao;
    }
    public Person createPerson(Person p){
        // Now let's call the DAO methods to create user
        Person returnedPerson = personDao.save(p);

        // If successful we should have a positive Id
        if (returnedPerson.getId() > 0){
            // TODO create a log for success
        } else{
            // TODO create a log for failure
        }

        return returnedPerson;
    }

    // Read All
    public List<Person> getAllPeople(){
        return personDao.findAll();
    }

    // Read One
    public Person getPersonById(int id){
        // We'll use the optional methods to return the proper thing
        return personDao.findById(id).orElseThrow(() -> new PersonNotFoundException("No person found with id: " + id));
    }


    // Update
    public Person updatePerson(Person p){
        return personDao.save(p);
    }

    // Delete
    public boolean deletePersonById(int id){
        // We need to delete by id then verify nonexistence
        personDao.deleteById(id);

        // We need to check to see if a person is still in the db
        if (!personDao.existsById(id)){
            // Successful message
            return true;
        } else{
            return false;
        }
    }
    //get all reimbursment request by id
    public List<Reimbursment> getReimbursmentsByPersonId(int id){
        // First thing we should do is get the person from the db

        Optional<Person> returnedPerson = personDao.findById(id);

        if (returnedPerson.isPresent()){
            return returnedPerson.get().getReimbursments();
        } else{
            throw new PersonNotFoundException("No Person with id: " + id);
        }
    }
    //submit reimbursment
    public Person submitReimbursment(int pid, int rid) throws RiembursmentNotFoundException {
        Person p = getPersonById(pid);

        // Now we need to extract the courses to add to it as necessary
        List<Reimbursment> reimbursments = p.getReimbursments();

        // Now we need to search the courses table to find the course with id = cid
        Optional<Reimbursment> returnedReimbursment = reimbursmentDao.findById(rid);

        if (returnedReimbursment.isPresent()){
            if (!reimbursments.contains(returnedReimbursment.get())){
                // The course exists and is NOT registered
                reimbursments.add(returnedReimbursment.get());
                // We need to attach this back to the person before we call the methods to save the person
                p.setReimbursments(reimbursments);

                // Save the person to the db which *should*  have the new course made
                personDao.save(p);

            }
        } else{
            throw new RiembursmentNotFoundException("No Course with id: " + rid);
        }

        return p;
    }
}
