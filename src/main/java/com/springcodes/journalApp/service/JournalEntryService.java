package com.springcodes.journalApp.service;

import com.springcodes.journalApp.entity.JournalEntry;
import com.springcodes.journalApp.entity.User;
import com.springcodes.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService
{
    //injecting repository in service
    @Autowired
    //here we are writing interface name JER and its instance(variable) jER
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    //transactional - if anyone operation fails in this method all the other successful operations will also rollback(fail)
    @Transactional
    //method to save all the entries
    public void saveEntry(JournalEntry journalEntry, String userName){
    //try
        {
        //saving entry in users
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        //all the journal entries saved in journalEntry will be saved in local variable "saved"
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        //adding the found user's entry in journal entries from saved and ref is created
        user.getJournalEntries().add(saved);
        user.setUserName(null);
        //user is saved in the db with new journal entry
        userService.saveEntry(user);
    }
        /*catch(Exception e)
    {
       System.out.println(e);
       throw new RuntimeException("An error occured while saving the entry",e);
    }*/
    }
    //saveEntry overloaded method just to save userName from journalEntryRepository
    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntryRepository.save(journalEntry);
    }

    //method to return journal - here we are calling mongorepo in built method
    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }

    //method to find id
    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    //method to delete id
    public void deleteById(ObjectId id, String userName)
    {
        //delete the entry from user
        User user = userService.findByUserName(userName);
        //using lambada expression to delete the same user entry by id, if x.getId = id from journalEntry
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        //saving updated user
        userService.saveEntry(user);
        //deletes journal entry
        journalEntryRepository.deleteById(id);
    }
}
//controller ---> service ---->repository