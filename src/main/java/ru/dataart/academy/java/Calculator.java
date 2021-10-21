package ru.dataart.academy.java;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipInputStream;

public class Calculator {
    /**
     * @param zipFilePath -  path to zip archive with text files
     * @param character   - character to find
     * @return - how many times character is in files
     */
    public Integer getNumberOfChar(String zipFilePath, char character) {
        int totalCount = 0;
        try (FileInputStream fis = new FileInputStream(zipFilePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis);
             InputStreamReader isr = new InputStreamReader(zis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            while (zis.getNextEntry() != null) {
                totalCount += br.lines().mapToInt(x -> (int) x.chars().filter(c -> c == character).count()).sum();
            }
        } catch (IOException e) {
            System.out.println("File not found or read-protected.");
        }
        return totalCount;
    }

    /**
     * @param zipFilePath - path to zip archive with text files
     * @return - max length
     */

    public Integer getMaxWordLength(String zipFilePath) {
        int maxWordLength = 0;
        try (FileInputStream fis = new FileInputStream(zipFilePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis);
             InputStreamReader isr = new InputStreamReader(zis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            while (zis.getNextEntry() != null) {
                int entryMaxWordLength = br.lines().mapToInt(this::getMaxLengthOfWordInString).max().orElse(0);
                maxWordLength = Math.max(entryMaxWordLength, maxWordLength);
            }
        } catch (IOException e) {
            System.out.println("File not found or read-protected.");
        }
        return maxWordLength;
    }

    private int getMaxLengthOfWordInString(String stringOfWords) {
        List<String> outputList = new ArrayList<>(Arrays.asList(stringOfWords.split("\\s+")));
        return outputList.stream().mapToInt(String::length).max().orElse(0);
    }

}
