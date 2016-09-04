package com.dferreira.numbers_teach.numbers;

import com.dferreira.numbers_teach.commons.GenericStudySet;


/**
 * Provide the necessary methods to access the data of the languages them selves
 */
public class StudySet extends GenericStudySet {

    public StudySet() {

    }

    /**
     * @return List of the numbers in english ready to be teach to the user
     */
    protected String[] getResourcePrefixes() {
        return new String[]{
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten",
                "eleven",
                "twelve",
                "thirteen",
                "fourteen",
                "fifteen",
                "sixteen",
                "seventeen",
                "eighteen",
                "nineteen",
                "twenty"
        };
    }
}
