package com.edonica.alexa.ghost.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class WordsTest {
    Words words;

    @Before
    public void setup() {
        words = new Words();
        words.useRandom=false;
    }
    @Test
    public void testGetPossibleWord() {
        String blankWord = words.getPossibleWord("");
        Assert.assertEquals("a", blankWord);
        String aWord = words.getPossibleWord("ab");
        Assert.assertEquals("abandon", aWord);
        String nonWord = words.getPossibleWord("asd");
        Assert.assertEquals("", nonWord);
    }

    @Test
    public void testIsWord() {
        Assert.assertTrue(words.isWord("beef"));
        Assert.assertTrue(words.isWord("bee"));
        Assert.assertFalse(words.isWord("beeg"));
    }

    @Test
    public void testGetNextPossibleLettter() {
        Assert.assertEquals(new Character('i'), words.getNextPossibleLetter("bel"));
        Assert.assertNull(words.getNextPossibleLetter("beef"));

    }
}
