package com.springcodes.journalApp.controller;

import com.springcodes.journalApp.entity.JournalEntry;
import com.springcodes.journalApp.entity.User;
import com.springcodes.journalApp.service.JournalEntryService;
import com.springcodes.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    //injecting service in controller
    @Autowired
    private JournalEntryService journalEntryService;

    //injecting userService
    private UserService userService;

    //here all methods are written for single/particular user
    //method to get single user and will store that user info in user variable
    /*@GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName)
    {
        //calling user service method this below (userName) is of path variable string userName
        //with this below line we will get all the journal entries of a particular user
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntries(@PathVariable String userName)
    {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //to store the data from the user
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try
        {
            //if any entry is getting inserted then it will be added in that paticular user
            //myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //calling method FindById
    //declaring http response
    @GetMapping("id'/{myId}")
    public ResponseEntity<JournalEntry>  getJournalEntryById(@PathVariable ObjectId myId)
    {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent())
        {
            //if present return new instance of response entity
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        //if not present the return not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //to delete any entry
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName)
    {
        journalEntryService.deleteById(myId, userName);
        //response entity after successfully deleting
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //here the data which is given we are updating that data only, other existing data should remain the same
    //here we have written the code that if we are updating the title(newEntry) the content should remain as it is(old) and vice versa
    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName)
    {
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            //when found <old(variable on which action is performed and , HttpStatus which you want to give as a response)
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        //when not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
