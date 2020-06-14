package com.bep.lingowords.Service;

import com.bep.lingowords.Model.Word;
import com.bep.lingowords.Repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    }

    @Test
    void testGetFilteredWords_ThrowsIOException() {
        // Setup
        when(mockWordRepository.findAll()).thenReturn(Arrays.asList(new Word(0, "name")));
        when(mockWordRepository.save(new Word(0, "name"))).thenReturn(new Word(0, "name"));

        // Run the test
        assertThrows(IOException.class, () -> {
            wordServiceUnderTest.getFilteredWords("file");
        });
    }

    @Test
    void testGetAllWords() throws Exception {
        final List<String> expectedResult = Arrays.asList("06");

        final List<String> result = wordServiceUnderTest.getAllWords("basiswoorden-gekeurd.txt");

        assertEquals(expectedResult.get(0), result.get(0));
    }

    //Setting up for the filter function below
    public static Stream<Arguments> validArgs() {
        return Stream.of(
                //A list and none of the words match the regex
                Arguments.of(new ArrayList<>(Arrays.asList("tËM-r", "bek", "BEDDEN", "eensuperlangewoord", "!HeelVEeel.")),
                        new ArrayList<>(Arrays.asList())),   //should return an empty list

                //A list and all words match the regex
                Arguments.of(new ArrayList<>(Arrays.asList("baard", "schrift")),
                        new ArrayList<>(Arrays.asList(      //should return a full word list
                                new Word(null, "baard"),
                                new Word(null, "schrift")))),

                //A list with words that doesnt match the regex
                Arguments.of(new ArrayList<>(Arrays.asList("tËM-r", "baard", "schrift")),
                        new ArrayList<>(Arrays.asList(  //should return a list with only the correct words
                                new Word(null, "baard"),
                                new Word(null, "schrift"))))
        );
    }

    //Testing if the filter function return a word list with the words that doest match the regex
    @ParameterizedTest
    @MethodSource({"validArgs"})
    public void testFilterWords(List<String> testStringList, List<Word> expectedWordList) {
        final List<Word> actualResult = wordServiceUnderTest.filterWords(testStringList);

        assertEquals(actualResult, expectedWordList);
    }

    @Test
    void testSaveFilteredWords() {
        final List<Word> wordList = Arrays.asList(new Word(0, "name"));
        when(mockWordRepository.findAll()).thenReturn(Arrays.asList(new Word(0, "name")));
        when(mockWordRepository.save(new Word(0, "name"))).thenReturn(new Word(0, "name"));

        wordServiceUnderTest.saveFilteredWords(wordList);
    }

}
