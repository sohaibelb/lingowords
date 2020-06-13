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
    public List<String> getAllWords(String source) throws IOException {
        List<String> wordList = new ArrayList<>();
        FileReader fr = new FileReader("src/main/resources/" + source);
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
    public List<Word> filterWords(List<String> wordList) throws IOException {
        List<Word> filteredWords = new ArrayList<>();
        for (String word : wordList) {
            if (word.matches("^[a-z]{5,7}$") && !checkIfWordExist(word)) {
                Word filteredWord = new Word(null, word);
                filteredWords.add(filteredWord);
            }
        }
        return filteredWords;
    }

    //Save the words in database
    public void saveFilteredWords(List<Word> wordList) {
        for (Word word : wordList) {
            wordRepository.save(word);
        }
    }

    //Check if the given words already exists in the database
    public boolean checkIfWordExist(String word) {
        List<Word> wordsInDb = wordRepository.findAll();
        List<String> wordNamesInDb = new ArrayList<>();
        for (Word wordOfDb : wordsInDb) {
            wordNamesInDb.add(wordOfDb.getName());
        }

        if (wordNamesInDb.contains(word)) {
            return true;
        } else {
            return false;
        }

    }
}
