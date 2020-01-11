package org.kp.tpmg.ttg.clinicianconnect.web.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.StringUtil;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SpecialtyInput;
import org.kp.tpmg.ttg.clinicianconnect.web.common.properties.AppProperties;
import org.kp.tpmg.ttg.clinicianconnect.web.common.properties.DateAdapter;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.ClinicianPhoneObject;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.QueryConstants;
import org.kp.tpmg.ttg.clinicianconnect.web.dao.OncallDao;
import org.kp.tpmg.ttg.clinicianconnect.web.model.CheckSpecialtyStatusResponse;
import org.kp.tpmg.ttg.clinicianconnect.web.model.CorTextDeptDetails;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Facility;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallData;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallInput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Specialty;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OnCallDaoImpl implements OncallDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String GOOD = "G";
	private static Logger log = LogManager.getLogger(OnCallDaoImpl.class); 
	
	//@Value(value="${MDO_DB_NAME}")
	private String mdoDbName="MDO";
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Facility> prepareFacilitiesList() {
		if (log.isDebugEnabled()) {
			log.debug(" call has entered the operation prepareFacilitiesList in  OnCallDaoImpl");
		}
		List<Facility> facilityList = null;
		try {
			//String mdoDbName = AppProperties.getProperty(OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY);
			List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(QueryConstants.FIND_FACILITIES_NOSTARTCHAR
							.replace(OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY, mdoDbName));

			for (Map row : rows) {
				if (facilityList == null) {
					facilityList = new ArrayList<>();
				}
				Facility facility = new Facility();
				if (null != row.get(OncallServiceConstants.FACILITYCODE)) {
					facility.setCode(row.get(OncallServiceConstants.FACILITYCODE).toString());
				}
				if (null != row.get(OncallServiceConstants.FACILITY_NAME_WITHOUT_CAMELCASE)) {
					facility.setName(StringUtil.getNormalFacilityString(
							row.get(OncallServiceConstants.FACILITY_NAME_WITHOUT_CAMELCASE).toString()));
				}
				if (null != row.get(OncallServiceConstants.islive)) {
					facility.setIsLive(row.get(OncallServiceConstants.islive).toString());
				}
				facilityList.add(facility);
			}
		} catch (Exception e) {
			log.error("ERROR: ClinicianConnect: problem in prepareFacilitiesList", e);
		}
		if (log.isDebugEnabled()) {
			log.debug(" call has returned the operation prepareFacilitiesList in  OnCallDaoImpl");
		}
		return facilityList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ClinicianPhoneObject> getClinicianContacts(String resourceId) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation getClinicianContacts in OnCallDAOImpl");
		}
		List<ClinicianPhoneObject> clinicianPhoneObject = new ArrayList<>();
		try {
			clinicianPhoneObject = jdbcTemplate.query(QueryConstants.GET_CONTACTS,
					new Object[] { resourceId }, new BeanPropertyRowMapper(ClinicianPhoneObject.class));
		} catch (Exception e) {
			log.error("ERROR: OnCallDaoImpl: problem in getClinicianContacts", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has returned the operation getClinicianContacts in OnCallDAOImpl");
		}
		return clinicianPhoneObject;
	}

	@Override
	public String getNickNameUsingNuid(String nuid) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation getNickNameUsingNuid in OnCallDAOImpl");
		}
		String nickname = null;
		try {
			nickname = jdbcTemplate.queryForObject(QueryConstants.GET_NICK_NAME_FROM_CCUSERS_BASED_ON_NUID,
					new Object[] { nuid }, String.class);
		} catch (Exception e) {
			log.error("ERROR: OnCallDaoImpl: problem in getNickNameUsingNuid", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation getNickNameUsingNuid in OnCallDAOImpl");
		}
		return nickname;
	}

	@Override
	public Map<String, Object> setAppVersionToOnCallEnvelope(final OnCallInput onCallInput) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation setAppVersionToOnCallEnvelope in OnCallDAOImpl");
		}
		Map<String, Object> response = null;
		try {
			response = jdbcTemplate.execute(new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection connection) throws SQLException {
					CallableStatement callStmt = connection.prepareCall(QueryConstants.CHECK_SPECAILTY_STATUS);
					callStmt.setString(1, onCallInput.getFacilityCode());
					callStmt.setString(2, onCallInput.getSpecialty());
					callStmt.setString(3, onCallInput.getAppVersion());
					callStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
					callStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
					callStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
					return callStmt;
				}
			}, new CallableStatementCallback<Map<String, Object>>() {
				@Override
				public Map<String, Object> doInCallableStatement(CallableStatement statement) throws SQLException {
					statement.execute();
					ResultSet resultSet = statement.getResultSet();
					Map<String, Object> response = new LinkedHashMap<String, Object>();
					OnCallEnvelope onCallEnvelope = new OnCallEnvelope();
					AppVersion appVersion = new AppVersion();
					CheckSpecialtyStatusResponse checkSpecialtyStatusResponse = new CheckSpecialtyStatusResponse();
					if (resultSet != null) {
						while (resultSet.next()) {
							appVersion.setVersion(resultSet.getString(OncallServiceConstants.APP_VERSION));
							appVersion.setInstructions(resultSet.getString(OncallServiceConstants.SPECIAL_INSTRUCTION));
							appVersion.setSpecialInstructionDetail(
									resultSet.getString(OncallServiceConstants.SPECIAL_INSTRUCTION_DETAIL).toString());
							appVersion.setLaunchUrl(resultSet.getString(OncallServiceConstants.LAUNCH_URL).toString());
						}
					}
					String isSpecialtyUpdatedTobad = statement.getString(5);
					String isSpecailtyTurnedOff = statement.getString(4);
					if (OncallServiceConstants.NOT_AVALIABLE.equalsIgnoreCase(isSpecialtyUpdatedTobad)
							|| "true".equalsIgnoreCase(isSpecialtyUpdatedTobad)) {
						checkSpecialtyStatusResponse.setIsSpecailtyTurnedOff(isSpecailtyTurnedOff);
						checkSpecialtyStatusResponse.setIsSpecialtyUpdatedTobad(isSpecialtyUpdatedTobad);
					}
					onCallEnvelope.setAppVersion(appVersion);
					response.put("onCallEnvelope", onCallEnvelope);
					response.put("checkSpecialtyStatusResponse", checkSpecialtyStatusResponse);
					return response;
				}
			});
		} catch (Exception e) {
			log.error("ERROR: OnCallDAOImpl : problem in setAppVersionToOnCallEnvelope", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has returned the operation setAppVersionToOnCallEnvelope in OnCallDAOImpl");
		}
		return response;
	}

	@Override
	public List<OnCallData> getOnCallDataList(final OnCallInput onCallInput) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation getOnCallDataList in OnCallDAOImpl");
		}
		List<OnCallData> onCallDataList = new ArrayList<>();
		try {
		    onCallDataList = jdbcTemplate.execute(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection connection) throws SQLException {
				CallableStatement callStmt = connection.prepareCall(QueryConstants.GET_ONCALL_DATA);
				callStmt.setString(1, onCallInput.getFacilityCode());
				callStmt.setString(2, onCallInput.getSpecialty());
				callStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
				callStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
				return callStmt;
			}
		}, new CallableStatementCallback<List<OnCallData>>() {
			@Override
			public List<OnCallData> doInCallableStatement(CallableStatement statement) throws SQLException {
				statement.execute();
				ResultSet onCallResultSet = statement.getResultSet();
				List<OnCallData> onCallDataList = new ArrayList<>();
				while (onCallResultSet.next()) {
					OnCallData onCallData = new OnCallData();
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.rundate))) {
						onCallData.setRundate(onCallResultSet.getString(OncallServiceConstants.rundate));
					}
					onCallData.setFacilityCode(onCallResultSet.getString(OncallServiceConstants.FACILITYCODE));
					onCallData.setRole(onCallResultSet.getString(OncallServiceConstants.role));
					onCallData.setSpecialty(onCallResultSet.getString(OncallServiceConstants.intelliwebspecialty));
					onCallData.setCallOrder(onCallResultSet.getString(OncallServiceConstants.callorder));
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.STARTDATETIME))
							&& StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.enddatetime))) {
						onCallData.setStartDateTime(onCallResultSet.getString(OncallServiceConstants.STARTDATETIME));
						onCallData.setEndDateTime(onCallResultSet.getString(OncallServiceConstants.enddatetime));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.MOBILENUMBER))) {
						onCallData.setMobileNumber(onCallResultSet.getString(OncallServiceConstants.MOBILENUMBER));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.PAGERNUMBER))) {
						onCallData.setPagerNumber(onCallResultSet.getString(OncallServiceConstants.PAGERNUMBER));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.linenumber))) {
						onCallData.setLineNumber(onCallResultSet.getString(OncallServiceConstants.linenumber));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.NOTES))) {
						onCallData.setNotes(onCallResultSet.getString(OncallServiceConstants.NOTES));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.RESOURCE_NAME))) {
						onCallData.setResourceName(onCallResultSet.getString(OncallServiceConstants.RESOURCE_NAME));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.NUID_CAMELCASE))) {
						onCallData.setNuid(onCallResultSet.getString(OncallServiceConstants.NUID_CAMELCASE));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.NICK_NAME))) {
						onCallData.setNickName(onCallResultSet.getString(OncallServiceConstants.NICK_NAME));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.humantype))) {
						onCallData.setHumanType(onCallResultSet.getString(OncallServiceConstants.humantype));
					}
					if (StringUtils.isNotBlank(onCallResultSet.getString(OncallServiceConstants.UNAVAILABILITY_DATETIME))) {
						Long unavailabilityDateTime = DateAdapter.convertToMilliSeconds(onCallResultSet.getString(OncallServiceConstants.UNAVAILABILITY_DATETIME));
						onCallData.setUnavailableTill((unavailabilityDateTime != null) ? unavailabilityDateTime.toString() : null);
					}			
					onCallDataList.add(onCallData);
				}
				return onCallDataList;
			}
		  });
		}catch (Exception e) {
			log.error("ERROR: OnCallDAOImpl : problem in getOnCallDataList", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has returned the operation getOnCallDataList in OnCallDAOImpl");
		}
		return onCallDataList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ClinicianPhoneObject> getClinicianPhoneBookList(List<String> nuidList) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation getClinicianPhoneBookList in OnCallDAOImpl");
		}
		StringBuilder nuidString = new StringBuilder();
		String inputString = null;
		List<ClinicianPhoneObject> clinicianPhoneObjectList = null;
		try {
		for (int i = 0; i < nuidList.size(); i++) {
			nuidString.append("'").append(nuidList.get(i)).append("'").append(",");
		}
		inputString = nuidString.deleteCharAt(nuidString.length() - 1).toString();
		String getPhoneClnPhoneBook = QueryConstants.GET_PHONE_CLN_PHONEBOOK.concat(inputString + ") ");
		clinicianPhoneObjectList = new ArrayList<>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(getPhoneClnPhoneBook);
		for (Map row : rows) {
			ClinicianPhoneObject clinicianPhoneObject = new ClinicianPhoneObject();
			if (null != row.get(OncallServiceConstants.NUID_CAMELCASE)) {
				clinicianPhoneObject.setNuid(row.get(OncallServiceConstants.NUID_CAMELCASE).toString());
			}
			if (null != row.get(OncallServiceConstants.FACILITYCODE)) {
				clinicianPhoneObject.setFacilityCode(row.get(OncallServiceConstants.FACILITYCODE).toString());
			}
			if (null != row.get(OncallServiceConstants.RESOURCE_CELL_PHONE_NUMBER)) {
				clinicianPhoneObject.setResourceCellPhoneNumber(
						row.get(OncallServiceConstants.RESOURCE_CELL_PHONE_NUMBER).toString());
			}
			if (null != row.get(OncallServiceConstants.RESOURCE_PRIVATE_EXTERNAL_PHONE_NUMBER)) {
				clinicianPhoneObject.setResourcePrivateExternalPhoneNumber(
						row.get(OncallServiceConstants.RESOURCE_PRIVATE_EXTERNAL_PHONE_NUMBER).toString());
			}
			if (null != row.get(OncallServiceConstants.RESOURCE_PRIVATE_INTERNAL_PHONE_NUMBER)) {
				clinicianPhoneObject.setResourcePrivateInternalPhoneNumber(StringUtil.convertToTieLineNumber(
						row.get(OncallServiceConstants.RESOURCE_PRIVATE_INTERNAL_PHONE_NUMBER).toString()));
			}
			if (null != row.get(OncallServiceConstants.RESOURCE_PAGER_PHONE_NUMBER)) {
				clinicianPhoneObject.setResourcePagerPhoneNumber(
						row.get(OncallServiceConstants.RESOURCE_PAGER_PHONE_NUMBER).toString());

			}
			clinicianPhoneObjectList.add(clinicianPhoneObject);
		  }
		}catch (Exception e) {
			log.error("ERROR: OnCallDAOImpl : problem in getClinicianPhoneBookList", e);
		}
		if (log.isDebugEnabled()) {
			log.debug(" call has returned the operation getClinicianPhoneBookList in  OnCallDaoImpl");
		}
		return clinicianPhoneObjectList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public SpecialtyEnvelope prepareSpecialties(SpecialtyInput specialtyInput) {
		if (log.isDebugEnabled()) {
			log.debug(" call has entered the operation prepareSpecialties in  OnCallDaoImpl ");
		}
		SpecialtyEnvelope specEnvelope = new SpecialtyEnvelope();
		List<Specialty> specialityList = null;
		Facility facility = null;
		int count = 0;
		int isTurnedOnCount = 0;
		try {
			//String mdoDbName = AppProperties.getProperty(OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY);
			List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(
							QueryConstants.FIND_SPECIALTIES_LIST
									.replace(OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY, mdoDbName),
							new Object[] { specialtyInput.getFacilityCode() });
			for (Map row : rows) {
				if (facility == null) {
					facility = new Facility();
					if (null != row.get(OncallServiceConstants.FACILITYCODE)) {
						facility.setCode(row.get(OncallServiceConstants.FACILITYCODE).toString());
					}
					if (null != row.get(OncallServiceConstants.FACILITY_NAME_WITHOUT_CAMELCASE)) {
						facility.setName(StringUtil.getNormalFacilityString(
								row.get(OncallServiceConstants.FACILITY_NAME_WITHOUT_CAMELCASE).toString()));
					}
					if (null != row.get(OncallServiceConstants.islive)) {
						facility.setIsLive(row.get(OncallServiceConstants.islive).toString());
					}
				}
				if (specialityList == null) {
					specialityList = new ArrayList<>();
				}
				if (null != row.get(OncallServiceConstants.quality)) {
					if (row.get(OncallServiceConstants.quality).toString().equals(GOOD))
						count++;
				}
				if (null != row.get(OncallServiceConstants.IS_TURNED_OFF)) {
					String turnedOffInd = row.get(OncallServiceConstants.IS_TURNED_OFF).toString();
					if (turnedOffInd == null
							|| (StringUtils.isNotBlank(turnedOffInd) && "N".equalsIgnoreCase(turnedOffInd))) {
						isTurnedOnCount++;
					}
				}
				Specialty speciality = new Specialty();
				if (null != row.get(OncallServiceConstants.SPECIALTY_LOWER_CASE)) {
					speciality.setName(row.get(OncallServiceConstants.SPECIALTY_LOWER_CASE).toString());
				}
				if (null != row.get(OncallServiceConstants.islive)) {
					speciality.setIsLive(row.get(OncallServiceConstants.islive).toString());
				}
				specialityList.add(speciality);
			}
			specEnvelope.setLocation(facility);
		} catch (Exception e) {
			log.error("ERROR: OnCallDaoImpl: problem in prepareSpecialties", e);
		}
		// No specialties good for this location
		if (count == 0) {
			specialityList = null;
		}
		if (isTurnedOnCount == 0) {
			specialityList = null;
		}

		if (specialityList != null && !specialityList.isEmpty()) {
			specEnvelope.setSpecialties(specialityList);
		} else {
			log.warn("No specialties for this facility");
			specEnvelope.setMessage(OncallServiceConstants.SPECIALTY_TURNED_OFF_ERROR_MESSAGE);
		}
		if (log.isDebugEnabled()) {
			log.debug(" call has returned the operation prepareSpecialties in  OnCallDaoImpl ");
		}
		return specEnvelope;
	}

//	@SuppressWarnings("rawtypes")
//	@Override
//	public List<Facility> prepareFacilitiesList() {
//		if (log.isDebugEnabled()) {
//			log.debug(" call has entered the operation prepareFacilitiesList in  OnCallDaoImpl");
//		}
//		List<Facility> facilityList = null;
//		try {
//			String mdoDbName = AppProperties.getProperty(OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY);
//			List<Map<String, Object>> rows = dbUtil.getJdbcTemplate()
//					.queryForList(QueryConstants.FIND_FACILITIES_NOSTARTCHAR
//							.replace(OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY, mdoDbName));
//
//			for (Map row : rows) {
//				if (facilityList == null) {
//					facilityList = new ArrayList<>();
//				}
//				Facility facility = new Facility();
//				if (null != row.get(OncallServiceConstants.FACILITYCODE)) {
//					facility.setCode(row.get(OncallServiceConstants.FACILITYCODE).toString());
//				}
//				if (null != row.get(OncallServiceConstants.FACILITY_NAME_WITHOUT_CAMELCASE)) {
//					facility.setName(StringUtil.getNormalFacilityString(
//							row.get(OncallServiceConstants.FACILITY_NAME_WITHOUT_CAMELCASE).toString()));
//				}
//				if (null != row.get(OncallServiceConstants.islive)) {
//					facility.setIsLive(row.get(OncallServiceConstants.islive).toString());
//				}
//				facilityList.add(facility);
//			}
//		} catch (Exception e) {
//			log.error("ERROR: ClinicianConnect: problem in prepareFacilitiesList", e);
//		}
//		if (log.isDebugEnabled()) {
//			log.debug(" call has returned the operation prepareFacilitiesList in  OnCallDaoImpl");
//		}
//		return facilityList;
//	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, CorTextDeptDetails> getCortextDeptEmailDetails() {
		if (log.isDebugEnabled()) {
			log.debug(" call has entered the operation getCortextDeptEmailDetails in  OnCallDaoImpl");
		}
		Map<String, CorTextDeptDetails> corTextDeptDetialsMap = new HashMap<>();

		try {
			List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(QueryConstants.GET_ALL_ONCALL_DEPARTMENT_EMAIL_ADDRESSES);

			for (Map row : rows) {
				CorTextDeptDetails corTextDeptDetails = new CorTextDeptDetails();
				if (null != row.get(OncallServiceConstants.DEPARTMENT_NAME)) {
					corTextDeptDetails.setDepartmentName(row.get(OncallServiceConstants.DEPARTMENT_NAME).toString());
				}
				if (null != row.get(OncallServiceConstants.EMAIL_ADDRESS_CAMEL_CASE)) {
					corTextDeptDetails
							.setEmailAddress(row.get(OncallServiceConstants.EMAIL_ADDRESS_CAMEL_CASE).toString());
				}
				if (null != row.get(OncallServiceConstants.PHONE_NUMBER_CAMEL_CASE)) {
					corTextDeptDetails
							.setPhoneNumber(row.get(OncallServiceConstants.PHONE_NUMBER_CAMEL_CASE).toString());
				}
				if (StringUtils.isNotBlank(corTextDeptDetails.getDepartmentName())
						&& StringUtils.isNotBlank(corTextDeptDetails.getDepartmentName().trim())) {
					corTextDeptDetialsMap.put(corTextDeptDetails.getDepartmentName().trim().toUpperCase(),
							corTextDeptDetails);
				}
			}
		} catch (Exception e) {
			log.error("ERROR: ClinicianConnect: problem in getCortextDeptEmailDetails", e);
		}
		if (log.isDebugEnabled()) {
			log.debug(" call has returned the operation getCortextDeptEmailDetails in OnCallDaoImpl");
		}
		return corTextDeptDetialsMap;
	}
	
//	@SuppressWarnings("rawtypes")
//	@Override
//	public boolean updateMyUnavailability(UpdateClinicianUnavailabilityRequest request) {
//		log.info("call has entered the operation updateMyUnavailability in OnCallDaoImpl");
//
//		String nuid = null;
//		boolean response = false;
//		try {
//			List<Map<String, Object>> rows = jdbcTemplate.queryForList(
//					QueryConstants.GET_CLINICIAN_UNAVAILIABILITY_DATETIME, new Object[] { request.getNuid() });
//
//			for (Map row : rows) {
//				nuid = (String) row.get(OncallServiceConstants.NUID_CAMELCASE);
//			}
//
//			Timestamp timeStamp = ("null".equalsIgnoreCase(request.getStatusExpiresAtDateTime())
//					|| StringUtils.isBlank(request.getStatusExpiresAtDateTime())) ? null
//							: new Timestamp(Long.valueOf(request.getStatusExpiresAtDateTime()));
//
//			String query = StringUtils.isNotBlank(nuid) ? QueryConstants.UPDATE_CLINICIAN_UNAVAILIABILITY_DATETIME
//					: QueryConstants.INSERT_CLINICIAN_UNAVAILIABILITY_DATETIME;
//			int result = jdbcTemplate.update(query, new Object[] { timeStamp, request.getNuid() });
//			response = result > 0 ? true : false;
//			log.info("updateMyUnavailability Response From DB - " + response);
//		} catch (Exception e) {
//			log.error(
//					"ERROR: ClinicianConnect: updateMyUnavailability - Unable to execute the insert/update statements ",
//					e);
//		}
//		log.info("call has exited the operation updateMyUnavailability in OnCallDAOImpl");
//		return response;
//	}
}
