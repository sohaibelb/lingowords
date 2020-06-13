package com.bep.lingowords.CommandLineRunner;

import com.bep.lingowords.Service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private WordService wordService;

    @Override
    public void run(String... args) throws Exception {
        //Put the file in src/main/resources/ and enter the name of the file below:
        String file = "basiswoorden-gekeurd.txt";

        //Filter the words of the file and put them in the database
		wordService.getFilteredWords(file);
    }
}
