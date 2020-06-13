package com.bep.lingowords.Service;

import com.bep.lingowords.Model.Word;
import com.bep.lingowords.Repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class WordServiceTest {

    @Mock
    private WordRepository mockWordRepository;

    @InjectMocks
    private WordService wordServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetFilteredWords() throws Exception {
        when(mockWordRepository.findAll()).thenReturn(Arrays.asList(new Word(0, "name")));
        when(mockWordRepository.save(new Word(0, "name"))).thenReturn(new Word(0, "name"));

        wordServiceUnderTest.getFilteredWords("basiswoorden-gekeurd.txt");

        // Verify the results
    }

    @Test
    void testGetFilteredWords_ThrowsIOException() {
        // Setup
        when(mockWordRepository.findAll()).thenReturn(Arrays.asList(new Word(0, "name")));
        when(mockWordRepository.save(new Word(0, "name"))).thenReturn(new Word(0, "name"));

        // Run the test
        assertThrows(IOException.class, () -> {
            wordServiceUnderTest.getFilteredWords("basiswoorden-gekeurd.txt");
        });
    }

    @Test
    void testGetAllWords() throws Exception {
        // Setup
        final List<String> expectedResult = Arrays.asList("value");

        // Run the test
        final List<String> result = wordServiceUnderTest.getAllWords("source");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllWords_ThrowsIOException() {
        // Setup

        // Run the test
        assertThrows(IOException.class, () -> {
            wordServiceUnderTest.getAllWords("source");
        });
    }

    @Test
    void testFilterWords() throws Exception {
        // Setup
        final List<String> wordList = Arrays.asList("value");
        final List<Word> expectedResult = Arrays.asList(new Word(0, "name"));
        when(mockWordRepository.findAll()).thenReturn(Arrays.asList(new Word(0, "name")));

        // Run the test
        final List<Word> result = wordServiceUnderTest.filterWords(wordList);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFilterWords_ThrowsIOException() {
        // Setup
        final List<String> wordList = Arrays.asList("value");
        when(mockWordRepository.findAll()).thenReturn(Arrays.asList(new Word(0, "name")));

        // Run the test
        assertThrows(IOException.class, () -> {
            wordServiceUnderTest.filterWords(wordList);
        });
    }

    @Test
    void testSaveFilteredWords() {
        // Setup
        final List<Word> wordList = Arrays.asList(new Word(0, "name"));
        when(mockWordRepository.save(new Word(0, "name"))).thenReturn(new Word(0, "name"));

        // Run the test
        wordServiceUnderTest.saveFilteredWords(wordList);

        // Verify the results
    }

    @Test
    void testCheckIfWordExist() {
        // Setup
        when(mockWordRepository.findAll()).thenReturn(Arrays.asList(new Word(0, "name")));

        // Run the test
        final boolean result = wordServiceUnderTest.checkIfWordExist("word");

        // Verify the results
        assertTrue(result);
    }
}
