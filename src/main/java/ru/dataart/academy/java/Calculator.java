package ru.dataart.academy.java;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
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
        int wordMaxLength = 0;
        try (FileInputStream fis = new FileInputStream(zipFilePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis);
             InputStreamReader isr = new InputStreamReader(zis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            while (zis.getNextEntry() != null) {
                int entryWordMaxLength = br.lines()
                        .mapToInt(s -> stringsToStreamOfWords(s).mapToInt(String::length).max().orElse(0))
                        .max().orElse(0);
                wordMaxLength = Math.max(entryWordMaxLength, wordMaxLength);
            }
        } catch (IOException e) {
            System.out.println("File not found or read-protected.");
        }
        return wordMaxLength;
    }

    private Stream<String> stringsToStreamOfWords(String stringOfWords) {
        List<String> outputList = new ArrayList<>(Arrays.asList(stringOfWords.split("\\s+")));
        return outputList.stream();
    }

}
