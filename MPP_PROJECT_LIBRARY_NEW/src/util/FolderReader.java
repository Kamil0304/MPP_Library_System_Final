/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.util.Arrays;

/**
 *
 * @author win7
 */
public class FolderReader {

    public static String getLastFileName(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null || listOfFiles.length == 0)return "0";
        String[]names = new String[listOfFiles.length];
        for(int i=0 ; i<listOfFiles.length ; i++){
            names[i] = listOfFiles[i].getName();
        }
        Arrays.sort(names);
        return names[names.length-1];
    }
}
