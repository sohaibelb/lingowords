package com.bep.lingowords.Service;

import com.bep.lingowords.Repository.WordRepository;
import com.bep.lingowords.Model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    //Get the words of the file, filter them and save them in the database
    public void getFilteredWords(String file) throws IOException {
        List<Word> words = filterWords(getAllWords(file));
        saveFilteredWords(words);
    }

    //Get the all words of the file
    public List<String> getAllWords(String file) throws IOException {
        List<String> wordList = new ArrayList<>();
        FileReader fr = new FileReader("src/main/resources/" + file);
        BufferedReader br = new BufferedReader(fr);

        String lineWord = br.readLine();
        while (lineWord != null) {
            wordList.add(lineWord);
            lineWord = br.readLine();
        }
        br.close();
        return wordList;
    }

    //Filter the words of the file
    public List<Word> filterWords(List<String> wordList) {
        List<Word> filteredWords = new ArrayList<>();

        for (String word : wordList) {
            if (word.matches("^[a-z]{5,7}$") && filteredWords.size() < 1000) {
                Word filteredWord = new Word(null, word);
                filteredWords.add(filteredWord);
            }
        }
        return filteredWords;
    }

 //Save the words in database
    public void saveFilteredWords(List<Word> wordList) {
        List<String> wordNamesInDb = new ArrayList<>();
        //get all words in db
        List<Word> allWords = wordRepository.findAll();

        // Check if there are words in db
        if (allWords.size() > 0) {
            for (Word wordInDb : allWords) {
                wordNamesInDb.add(wordInDb.getName());
            }
        }

        // If there are less than 1000 words in db, then save the new words in db. This is because of the heroku limits.
        if (allWords.size() < 1000) {
            for (Word word : wordList) {
                //Check if the given words already exists in the database
                if (!wordNamesInDb.contains(word.getName())) {
                    wordRepository.save(word);
                }
            }
        }
    }
}