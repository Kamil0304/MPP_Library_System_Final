/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import model.Address;
import model.Book;
import model.BookCopy;
import model.CheckoutEntry;
import model.CheckoutRecord;
import model.LibraryMember;
import util.FolderReader;

/**
 *
 * @author win7
 */
public class MemberDAO {
    static final String OUTPUT_DIR = IStoragePath.OUTPUT_DIR + "member"; 
    
    
    public void addMember(LibraryMember libraryMember)throws IOException{
        String lastID = FolderReader.getLastFileName(OUTPUT_DIR);
        libraryMember.setId((Integer.parseInt(lastID)+1)+"");
        DataAccessUtil.saveObject(OUTPUT_DIR, libraryMember.getId(), libraryMember);//auto generate id
    }
    public void editMember(LibraryMember libraryMember)throws IOException{
    	
    	 String lastID = FolderReader.getLastFileName(OUTPUT_DIR);
    	 DataAccessUtil.saveObject(OUTPUT_DIR, libraryMember.getId(), libraryMember);
    	 
    }
    public List<LibraryMember> getAllLibraryMembers(){
        return DataAccessUtil.readAllObject(OUTPUT_DIR);
    }
    
    public LibraryMember getLibraryMember(String id){
        return DataAccessUtil.readGenericObject(OUTPUT_DIR, id);
    }
}
