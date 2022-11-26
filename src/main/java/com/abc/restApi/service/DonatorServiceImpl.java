package com.abc.restApi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.abc.restApi.dao.DonatorDao;
import com.abc.restApi.entity.Donator;

@Service
public class DonatorServiceImpl implements DonatorService {

	@Autowired
	private DonatorDao dao;

	

	int countDonatorsInSheet = 0;

	@Override
	public boolean saveDonator(Donator donator) {
		boolean added = dao.saveDonator(donator);

		return added;
	}

	@Override
	public Donator getDonatorById(int donatorId) {
		Donator donator = dao.getDonatorById(donatorId);

		return donator;
	}

	@Override
	public List<Donator> getAllDonators() {
		List<Donator> list = dao.getAllDonators();
		return list;
	}

	@Override
	public boolean deleteDonator(int donatorid) {
		boolean deleted = dao.deleteDonator(donatorid);
		return deleted;
	}

	@Override
	public boolean updateDonator(Donator donator) {
		boolean updated = dao.updateDonator(donator);
		return updated;
	}

	@Override
	public List<Donator> sortDonatorsById_ASC() {
		List<Donator> list = dao.sortDonatorsById_ASC();
		return list;
	}

	@Override
	public List<Donator> sortDonatorsById_DESC() {
		List<Donator> list = dao.sortDonatorsById_DESC();
		return list;
	}

	@Override
	public List<Donator> sortDonatorsByName_ASC() {
		List<Donator> list = dao.sortDonatorsByName_ASC();
		return list;
	}

	@Override
	public List<Donator> sortDonatorsByName_DESC() {
		List<Donator> list = dao.sortDonatorsByName_DESC();
		return list;
	}

	@Override
	public ArrayList<Entry<String, Long>> getCountOfAvailableBloodGroups() {
		 
		return dao.getCountOfAvailableBloodGroups();
	}

	

	@Override
	public long getTotalCountOfDonators() {
		long toatalDonator = dao.getTotalCountOfDonators();
		return toatalDonator;
	}

	public String deleteAllDonators() {
		String msg = dao.deleteAllDonators();
		return msg;
	}

	// ExcelSheetReading.............. //

	public List<Donator> readExcelSheetData(String path) {
		Donator donator = null;
		List<Donator> list = new ArrayList<>();
		try {
			FileInputStream fileIn = new FileInputStream(new File(path));

			Workbook workbook = new XSSFWorkbook(fileIn);
			Sheet sheet = workbook.getSheetAt(0);
			countDonatorsInSheet = sheet.getLastRowNum();

			Iterator<Row> rows = sheet.rowIterator();

			int count = 0;
			while (rows.hasNext()) {
				Row row = rows.next();
				donator = new Donator();

				if (count == 0) {
					count = count + 1;
					continue;
				}

				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = cells.next();

					int col = cell.getColumnIndex();

					switch (col) {
					case 0: {

						int id = (int) cell.getNumericCellValue();
						donator.setDonatorId(id);

						break;
					}
					case 1: {
						String name = cell.getStringCellValue();
						donator.setDonatorName(name);

						break;
					}
					case 2: {
						String gender = cell.getStringCellValue();
						donator.setDonatorGender(gender);

						break;
					}
					case 3: {
						int age = (int) cell.getNumericCellValue();
						donator.setDonatorAge(age);

						break;
					}
					case 4: {
						String bloodGroup = cell.getStringCellValue();
						donator.setDonatorBloodGroup(bloodGroup);

						break;

					}

					default:
						break;
					}

				}
				list.add(donator);
				workbook.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public String uploadExcelSheet(MultipartFile file, HttpSession session) {

		int addedCount = 0;
		String msg = null;
		String path = session.getServletContext().getRealPath("/uploaded");
		String fileName = file.getOriginalFilename();

		try {
			byte[] data = file.getBytes();
			FileOutputStream fileOut = new FileOutputStream(new File(path + File.separator + fileName));
			fileOut.write(data);

			List<Donator> list = readExcelSheetData(path + File.separator + fileName);

			addedCount = dao.excelToDatabase(list);

			msg = " Total DonatorsInfo in ExcelSheet = " + countDonatorsInSheet
					+ " , Added DonatorsInfo from excelSheet to Database = " + addedCount;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	@Override
	public List<Donator> selectDonatorByNameList_HQL(String donatorName) {
		List<Donator> donator = dao.selectDonatorByNameList_HQL(donatorName);

		return donator;
	}
	@Override
	public List<Donator>  selectDonatorByBloodGroup_HQL(String donatorBloodGroup) {
		List<Donator> donator = dao.selectDonatorByBloodGroup_HQL(donatorBloodGroup);
		
		return donator;
	}
	
	

}
