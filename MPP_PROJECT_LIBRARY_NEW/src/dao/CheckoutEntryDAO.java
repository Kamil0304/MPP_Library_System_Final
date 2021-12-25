/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.CheckoutEntry;
import util.FolderReader;

/**
 *
 * @author 984894
 */
public class CheckoutEntryDAO {
    static final String OUTPUT_DIR = IStoragePath.OUTPUT_DIR + "checkoutEntry"; 
    
    public void addCheckoutEntry(CheckoutEntry checkoutEntry)throws Exception{
        String lastID = FolderReader.getLastFileName(OUTPUT_DIR);
        checkoutEntry.setId((Integer.parseInt(lastID)+1)+"");
        DataAccessUtil.saveObject(OUTPUT_DIR, checkoutEntry.getId(), checkoutEntry); 
    }
    
    public List<CheckoutEntry> getAllCheckoutEntries(){
        List<CheckoutEntry> checkoutentries = DataAccessUtil.readAllObject(OUTPUT_DIR);
        return checkoutentries;
    }
}
