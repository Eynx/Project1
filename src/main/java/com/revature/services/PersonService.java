package com.revature.services;

import com.revature.Models.Person;
import com.revature.Models.Reimbursement;
import com.revature.daos.PersonDAO;
import com.revature.daos.ReimbursementDAO;
import com.revature.exceptions.PersonNotFoundException;
import com.revature.exceptions.RiembursmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonDAO personDao;
    private final ReimbursementDAO reimbursementDao;

    @Autowired
    public PersonService(PersonDAO personDao, ReimbursementDAO reimbursementDao) {
        this.personDao = personDao;
        this.reimbursementDao = reimbursementDao;
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
    //get all reimbursement request by id
    public List<Reimbursement> getReimbursementsByPersonId(int id){
        // First thing we should do is get the person from the db

        Optional<Person> returnedPerson = personDao.findById(id);

        if (returnedPerson.isPresent()){
            return returnedPerson.get().getReimbursements();
        } else{
            throw new PersonNotFoundException("No Person with id: " + id);
        }
    }
    //submit Reimbursement
    public Person submitReimbursements(int pid, int rid) throws RiembursmentNotFoundException {
        Person p = getPersonById(pid);

        List<Reimbursement> reimbursements = p.getReimbursements();

        // Now we need to search the reimbursements table to find the reimbursements with id = cid
        Optional<Reimbursement> returnedReimbursement = reimbursementDao.findById(rid);

        if (returnedReimbursement.isPresent()){
            if (!reimbursements.contains(returnedReimbursement.get())){
                reimbursements.add(returnedReimbursement.get());
                p.setReimbursements(reimbursements);

                // Save the person to the db which *should*  have the new course made
                personDao.save(p);
            }
        } else{
            throw new RiembursmentNotFoundException("No Course with id: " + rid);
        }

        return p;
    }
}
