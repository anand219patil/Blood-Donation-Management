package com.abc.restApi.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abc.restApi.entity.Donator;

@Repository
public class DonatorDaoImpl implements DonatorDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean saveDonator(Donator donator) {

		boolean added = false;
		Session session = sessionFactory.openSession();
		try {

			Donator don = session.get(Donator.class, donator.getDonatorId());

			if (don == null) {
				Transaction transaction = session.beginTransaction();
				session.save(donator);
				transaction.commit();
				added = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return added;
	}

	@Override
	public Donator getDonatorById(int donatorId) {

		Session session = sessionFactory.openSession();
		Donator donator = null;
		try {

			donator = session.get(Donator.class, donatorId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return donator;
	}

	@Override
	public List<Donator> getAllDonators() {
		Session session = sessionFactory.openSession();
		List<Donator> list = null;
		try {
			Criteria criteria = session.createCriteria(Donator.class);
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public boolean deleteDonator(int donatorid) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean deleted = false;
		try {
			Donator donator = session.get(Donator.class, donatorid);
			if (donator != null) {
				session.delete(donator);

				transaction.commit();
				deleted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return deleted;

	}

	@Override
	public boolean updateDonator(Donator donator) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean updated = false;
		try {
			Donator don = session.get(Donator.class, donator.getDonatorId());
			session.evict(don);
			if (don != null) {
				session.update(donator);
				transaction.commit();
				updated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return updated;

	}

	@Override
	public List<Donator> sortDonatorsById_ASC() {
		Session session = sessionFactory.openSession();
		List<Donator> list = null;
		try {
			Criteria criteria = session.createCriteria(Donator.class);
			criteria.addOrder(Order.asc("donatorId"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Donator> sortDonatorsById_DESC() {
		Session session = sessionFactory.openSession();
		List<Donator> list = null;
		try {
			Criteria criteria = session.createCriteria(Donator.class);
			criteria.addOrder(Order.desc("donatorId"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Donator> sortDonatorsByName_ASC() {
		Session session = sessionFactory.openSession();
		List<Donator> list = null;
		try {
			Criteria criteria = session.createCriteria(Donator.class);
			criteria.addOrder(Order.asc("donatorName"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Donator> sortDonatorsByName_DESC() {
		Session session = sessionFactory.openSession();
		List<Donator> list = null;
		try {
			Criteria criteria = session.createCriteria(Donator.class);
			criteria.addOrder(Order.desc("donatorName"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	public ArrayList<Entry<String, Long>> getCountOfAvailableBloodGroups() {
		Session session = sessionFactory.openSession();

		ArrayList<Entry<String, Long>> arrayList = null;

		try {

			Criteria criteria = session.createCriteria(Donator.class);
			criteria.setProjection(Projections.property("donatorBloodGroup"));
			List<String> bloodGroupList = criteria.list();

			Map<String, Long> frequency = bloodGroupList.stream()
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			// frequency.entrySet().stream().sorted((i1, i2) ->
			// i2.getValue().compareTo(i1.getValue()));

			Set<Entry<String, Long>> entrySet = frequency.entrySet();

			arrayList = new ArrayList<>(entrySet);

			Collections.sort(arrayList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return arrayList;

	}

	public long getTotalCountOfDonators() {
		Session session = sessionFactory.openSession();
		long numOfDonators = 0;
		try {
			Criteria criteria = session.createCriteria(Donator.class);
			criteria.setProjection(Projections.rowCount());
			List<Long> list = criteria.list();
			if (!list.isEmpty()) {
				numOfDonators = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return numOfDonators;

	}

	@Override
	public String deleteAllDonators() {
		Session session = sessionFactory.openSession();
		List<Donator> list = null;
		int count = 0;
		String msg = null;
		try {
			Criteria criteria = session.createCriteria(Donator.class);
			list = criteria.list();
			for (Donator donator : list) {
				int id = donator.getDonatorId();
				boolean deleted = deleteDonator(id);
				if (deleted) {
					count++;
				}
				msg = count + " = Donators deleted";

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return msg;
	}

	public int excelToDatabase(List<Donator> list) {

		int count = 0;

		for (Donator donator : list) {

			// System.out.println(product);

			boolean added = saveDonator(donator);

			if (added) {
				count++;

			}
		}
		return count;
	}

	public List<Donator> selectDonatorByNameList_HQL(String donatorName) {
		Session session = sessionFactory.openSession();

		List<Donator> list = null;

		try {
			String hql = "from Donator where donatorName = :xyz  ";
			Query<Donator> query = session.createQuery(hql);
			query.setParameter("xyz", donatorName);
			list = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	public List<Donator> selectDonatorByBloodGroup_HQL(String donatorBloodGroup) {
		Session session = sessionFactory.openSession();

		List<Donator> list = null;

		try {
			String hql = "from Donator where donatorBloodGroup = :xyz  ";
			Query<Donator> query = session.createQuery(hql);
			query.setParameter("xyz", donatorBloodGroup);
			list = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

}
