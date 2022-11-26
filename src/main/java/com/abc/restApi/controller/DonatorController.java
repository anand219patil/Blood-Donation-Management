package com.abc.restApi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abc.restApi.entity.Donator;
import com.abc.restApi.exception.DonatorAlreadyExistsException;
import com.abc.restApi.exception.DonatorNotFoundException;
import com.abc.restApi.service.DonatorService;

@RestController
@RequestMapping(value = "/donator")
public class DonatorController {

	@Autowired
	private DonatorService service;

	@PostMapping(value = "/savedonator")
	public ResponseEntity<Boolean> saveDonator(@Valid @RequestBody Donator donator) {

		boolean added = service.saveDonator(donator);

		if (added) {
			return new ResponseEntity<Boolean>(added, HttpStatus.CREATED);
		} else {
			throw new DonatorAlreadyExistsException("Donator already exists with ID = " + donator.getDonatorId());
		}

	}

	@GetMapping(value = "/getdonatorbyid/{donatorId}")
	public ResponseEntity<Donator> getDonatorById(@PathVariable int donatorId) {

		Donator donator = service.getDonatorById(donatorId);

		if (donator != null) {
			return new ResponseEntity<Donator>(donator, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donator Not Found for ID = " + donatorId);
		}

	}

	@GetMapping(value = "/getalldonators")
	public ResponseEntity<List<Donator>> getAllDonators() {

		List<Donator> list = service.getAllDonators();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Donator>>(list, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donator Not Found");
		}

	}

	@DeleteMapping(value = "/deletedonatorbyid")
	public ResponseEntity<Boolean> deleteDonator(@RequestParam int donatorid) {
		boolean deleted = service.deleteDonator(donatorid);
		if (deleted) {
			return new ResponseEntity<Boolean>(deleted, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donator with Id =" + donatorid + ", not found");
		}
	}

	@PutMapping(value = "/updatedonator")
	public ResponseEntity<Boolean> updateDonator(@Valid @RequestBody Donator donator) {
		boolean updated = service.updateDonator(donator);
		if (updated) {
			return new ResponseEntity<Boolean>(updated, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donator with Id =" + donator.getDonatorId() + ", not found");
		}
	}

	@GetMapping(value = "/sortdonatorsbyidasc")
	public ResponseEntity<List<Donator>> sortDonatorsById_ASC() {

		List<Donator> list = service.sortDonatorsById_ASC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Donator>>(list, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donators Not Found");
		}

	}

	@GetMapping(value = "/sortdonatorsbyiddesc")
	public ResponseEntity<List<Donator>> sortDonatorsById_DESC() {

		List<Donator> list = service.sortDonatorsById_DESC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Donator>>(list, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donators Not Found");
		}

	}

	@GetMapping(value = "/sortdonatorsbynameasc")
	public ResponseEntity<List<Donator>> sortDonatortsByName_ASC() {

		List<Donator> list = service.sortDonatorsByName_ASC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Donator>>(list, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donators Not Found");
		}

	}

	@GetMapping(value = "/sortdonatorsbynamedesc")
	public ResponseEntity<List<Donator>> sortDonatorsByName_DESC() {

		List<Donator> list = service.sortDonatorsByName_DESC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Donator>>(list, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donators Not Found");
		}

	}

	@GetMapping(value = "/getcountofavailablebloodgroups")
	public ResponseEntity <ArrayList<Entry<String, Long>>> getCountOfAvailableBloodGroups() {

		ArrayList<Entry<String, Long>> list = service.getCountOfAvailableBloodGroups();

		if (!list.isEmpty()) {
			return new ResponseEntity <ArrayList<Entry<String, Long>>>(list, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donators Not Found");
		}

	}

	

	@GetMapping(value = "/gettotalcountofdonators")
	public ResponseEntity<Double> getTotalCountOfDonators() {

		double totalDonators = service.getTotalCountOfDonators();

		if (totalDonators != 0) {
			return new ResponseEntity<Double>(totalDonators, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donators Not Found");
		}

	}

	@DeleteMapping(value = "/deletealldonators")
	public ResponseEntity<String> deleteAllDonators() {

		String msg = service.deleteAllDonators();

		if (msg != null) {
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donators Not Found");
		}

	}

	///////////// import-export sheet //////////////

	@PostMapping(value = "/uploadsheet")
	public ResponseEntity<String> uploadExcelSheet(@RequestParam MultipartFile file, HttpSession session) {

		String message = service.uploadExcelSheet(file, session);

		if (message != null) {
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	}
	
	///////////////
	
	@GetMapping(value = "/getdonatorbyname/{donatorName}")
	public ResponseEntity<List<Donator>> selectDonatorByNameList_HQL(@PathVariable String donatorName) {

		List<Donator> donator = service.selectDonatorByNameList_HQL(donatorName);

		if (donator != null) {
			return new ResponseEntity<List<Donator>>(donator, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donator Not Found for Name = " + donatorName);
		}

	}
	@GetMapping(value = "/getdonatorsbybloodgroup/{donatorBloodGroup}")
	public ResponseEntity<List<Donator>> selectDonatorByBloodGroup_HQL(@PathVariable String donatorBloodGroup) {
		
		List<Donator> donator = service.selectDonatorByBloodGroup_HQL(donatorBloodGroup);
		
		if (donator != null) {
			return new ResponseEntity<List<Donator>>(donator, HttpStatus.OK);
		} else {
			throw new DonatorNotFoundException("Donator Not Found for blood group = " + donatorBloodGroup);
		}
		
	}

}
