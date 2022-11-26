package com.abc.restApi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.abc.restApi.entity.Donator;


public interface DonatorDao {
	
	public boolean saveDonator(Donator donator);

	public Donator getDonatorById(int donatorId);

	public List<Donator> getAllDonators();

	public boolean deleteDonator(int donatorid);

	public boolean updateDonator(Donator donator);

	public List<Donator> sortDonatorsById_ASC();

	public List<Donator> sortDonatorsById_DESC();

	public List<Donator> sortDonatorsByName_ASC();

    public List<Donator> sortDonatorsByName_DESC();

   public ArrayList<Entry<String, Long>> getCountOfAvailableBloodGroups();
    
    
    public long getTotalCountOfDonators();
    
    public String deleteAllDonators();
    
   
    public int excelToDatabase(List<Donator>list) ;
    
   public List<Donator> selectDonatorByNameList_HQL(String donatorName);
   public List<Donator> selectDonatorByBloodGroup_HQL(String donatorBloodGroup);
}
