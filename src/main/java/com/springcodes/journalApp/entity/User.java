package com.springcodes.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection="users")
@Data
public class User {

        @Id
        private ObjectId id;                    //unique id will be provided by mongodb
        @Indexed(unique = true)                 //this will sure every username value is unique
        @NonNull                                //this will make user the field is not null or else it will throw
        private String userName;                // null pointer exception and it not save
        @NonNull
        private String password;

        @DBRef                                  //this annotation will create a link between users and journal_entries collection
        //creating a list which will store all the entries for a single user
        //if there is no value inserted in the journal entry there will be an empty string, but it will be not null
        //journalEntry is acting as a foreign key to create link between the two collections users and journal_entries
        private List<JournalEntry> journalEntries = new ArrayList<>();
}
