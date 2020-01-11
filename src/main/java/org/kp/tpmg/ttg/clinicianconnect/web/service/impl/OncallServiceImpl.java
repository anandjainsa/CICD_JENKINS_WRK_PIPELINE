package org.kp.tpmg.ttg.clinicianconnect.web.service.impl;

import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.PROBLEM_WHILE_RETRIEVING_THE_DATA_FROM_DATABASE;
import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.SUCCESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kp.tpmg.clinicianconnect.cclib.exception.CCLibInvalidTTGResponseException;
import org.kp.tpmg.clinicianconnect.cclib.exception.CCLibUnauthorizedException;
import org.kp.tpmg.clinicianconnect.cclib.mdoprovider.MdoProviderUtil;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianContact;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianDetailProvider;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianProvider;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookClinicianLocation;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookDepartment;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookFacility;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookSpecialty;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.CCUtilityConstants;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.ClinicianConnectLogger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.ClinicianConnectUtilityMethods;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.StringUtil;
import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.CCUtilityServicesException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.FacilityInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.LogDetails;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OnCallLookup;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OnCallSpecialtySchedule;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchDetailInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SpecialtyInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.util.CommonUtil;
import org.kp.tpmg.clinicianconnect.ccutilityservices.util.LogUtil;
import org.kp.tpmg.ttg.clinicianconnect.web.common.properties.DateAdapter;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.ClinicianPhoneObject;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants;
import org.kp.tpmg.ttg.clinicianconnect.web.dao.OncallDao;
import org.kp.tpmg.ttg.clinicianconnect.web.model.CheckSpecialtyStatusResponse;
import org.kp.tpmg.ttg.clinicianconnect.web.model.CorTextDeptDetails;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Facility;
import org.kp.tpmg.ttg.clinicianconnect.web.model.FacilityEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.FacilityOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallContact;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallData;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallInput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallNotes;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallProvider;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallSchedule;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallSpecialty;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallTelepresence;
import org.kp.tpmg.ttg.clinicianconnect.web.model.PhoneBookSearchDetailEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Specialty;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Status;
import org.kp.tpmg.ttg.clinicianconnect.web.service.OncallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OncallServiceImpl implements OncallService {

	@Autowired
	OncallDao onCallDaoImpl;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	LogUtil logUtil;

	private String specialtyName = null;
	private Long latestRunDate = null;
	private static final String FALSE = "false";
	private static final String GET_FACILITIES = "getFacilities";
	//private static final Logger log = Logger.getLogger(OncallServiceImpl.class);
	private static Logger log = LogManager.getLogger(OncallServiceImpl.class); 
	public static final String CORTEXT_DEPARTMENT_STRING = "CORTEXT";
	
	@Override
	public void prepareResponseEnvelope(FacilityOutput facilityOutput, Status status,
			FacilityEnvelope facilityEnvelope) {
		if (facilityEnvelope != null) {
			status.setCode("200");
			status.setMessage(SUCCESS);
			if (facilityEnvelope.getAppVersion() != null) {
				facilityOutput.setAppVersion(facilityEnvelope.getAppVersion());
			}
			facilityOutput.setiOSVersion(facilityEnvelope.getiOSVersion());
			facilityOutput.setEnvelope(facilityEnvelope);
		} else {
			status.setCode("204");
			status.setMessage(PROBLEM_WHILE_RETRIEVING_THE_DATA_FROM_DATABASE);
		}
	}
	
	@Override
	public FacilityEnvelope getFacilities(FacilityInput facilityInput) {
		List<Facility> facilities;
		FacilityEnvelope facilityEnvelope;
		facilities = onCallDaoImpl.prepareFacilitiesList();
		facilityEnvelope = new FacilityEnvelope();
		try {
			AppVersion appversion = commonUtil.getAppVersion(facilityInput.getAppVersion());
			if (appversion != null) {
				facilityEnvelope.setAppVersion(appversion);
			}
			facilityEnvelope.setiOSVersion(commonUtil.getIOSVersion(facilityInput.getDeviceType(),
					facilityInput.getiOSVersion(), GET_FACILITIES));
		} catch (CCUtilityServicesException e) {
			log.error("ERROR: OncallService: problem in getFacilities", e);
		}
		facilityEnvelope.setFacilities(facilities);
		return facilityEnvelope;
	}

	@Override
	public PhoneBookSearchDetailEnvelope getOncallClinicianDetails(SearchDetailInput searchDetailInput) {
		Calendar startTime = Calendar.getInstance();
		if (log.isDebugEnabled()) {
			logPerformanceMessage("Starting getOncallClinicianDetails() service : ", startTime);
		}
		PhoneBookSearchDetailEnvelope clinicianDetails = new PhoneBookSearchDetailEnvelope();
		setAppVersion(searchDetailInput, clinicianDetails);
		try {
			clinicianDetails.setiOSVersion(commonUtil.getIOSVersion(searchDetailInput.getDeviceType(),
					searchDetailInput.getiOSVersion(), "getOncallClinicianDetails"));
		} catch (CCUtilityServicesException e1) {
			log.error("ERROR: OncallService: problem in getOncallClinicianDetails/getIOSVersion", e1);
		}
		if (StringUtils.isNotBlank(searchDetailInput.getNuid())) {
			List<ClinicianPhoneObject> clnPhoneList = null;
			ClinicianDetailProvider clinicianDetailProvider = null;
			try {
				clinicianDetailProvider = getClinicianDetailsByNUIDDirectRestCall(searchDetailInput.getNuid().trim());
				if (clinicianDetailProvider != null
						&& StringUtils.isNotBlank(clinicianDetailProvider.getResourceId())) {
					clnPhoneList = onCallDaoImpl.getClinicianContacts(clinicianDetailProvider.getResourceId());
				}
			} catch (Exception e) {
				log.error("ERROR: ClinicianConnect: PhoneBookSearchDetail - Unable to get contacts or ttgprovider for "
						+ searchDetailInput.getNuid(), e);
			}
			if (clinicianDetailProvider != null) {
				String nickName = onCallDaoImpl.getNickNameUsingNuid(searchDetailInput.getNuid().trim());
				if (StringUtils.isNotBlank(nickName)) {
					clinicianDetails.setNickName(nickName);
				}
			}
			populatePhoneBookSearchDetailEnvelope(clinicianDetailProvider.getResourceId(), clinicianDetails,
					clnPhoneList, clinicianDetailProvider);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation getOncallClinicianDetails in PhoneBookDAOImpl");
		}
		return clinicianDetails;
	}

	private void setAppVersion(SearchDetailInput searchDetailInput, PhoneBookSearchDetailEnvelope clncnDetail) {

		try {
			AppVersion appversion = commonUtil.getAppVersion(searchDetailInput.getAppVersion());
			if (appversion != null) {
				clncnDetail.setAppVersion(appversion);
			}
		} catch (CCUtilityServicesException e1) {
			log.error("ERROR: OncallService: problem in PhoneBookServiceImpl/getAppVersion", e1);
		}
	}

	public ClinicianDetailProvider getClinicianDetailsByNUIDDirectRestCall(String nuid) {

		log.info("entered getClinicianDetailsByNUIDDirectRestCall");

		ClinicianDetailProvider clinicianDetailProvider = null;
		if (StringUtils.isBlank(nuid)) {
			return null;
		}
		try {
			/*
			 * MdoLiteProviderUtil mdoProviderUtil = new MdoLiteProviderUtil(); Map<String,
			 * String> tokenDetailsMap = commonUtil.getMdoAccessToken();
			 * log.info("Access Toekn : " +
			 * tokenDetailsMap.get(OncallServiceConstants.ACCESS_TOEKN));
			 * clinicianDetailProvider =
			 * mdoProviderUtil.getClinicianDetailsByNUIDDirectRestCall(
			 * tokenDetailsMap.get(OncallServiceConstants.ACCESS_TOEKN), getByNuidUrl,
			 * nuid);
			 * 
			 * log.info("Started Calling MDO Services.");
			 */
			String provider=OncallServiceConstants.PROVIDER_BY_NUID_API_NAME;
			MdoProviderUtil mdoProviderUtil = commonUtil.getMdoProviderUtil(provider);
			clinicianDetailProvider = mdoProviderUtil.getClinicianDetailsByNUIDDirectRestCall(nuid);
		} catch (CCLibUnauthorizedException e) {
			LogDetails details = ClinicianConnectUtilityMethods.prepareLogDetailsObject(
					OncallServiceConstants.CC_SERVICES, "getClinicianDetailByResourceIdsDirectRestCall", "",
					e.getMessage(), "Un Authorized Exception", "", OncallServiceConstants.ERROR_MSG, null);
			log.warn(
					"CCLibUnauthorizedException : Did not get Clinician details while executing getClinicianDetailByResourceIdsDirectRestCall() :"
							+ " error log id : " + logUtil.saveLogDetails(details));
		} catch (CCLibInvalidTTGResponseException e) {
			LogDetails details = ClinicianConnectUtilityMethods.prepareLogDetailsObject(
					OncallServiceConstants.CC_SERVICES, "getClinicianDetailsByNUIDDirectRestCall", "", "",
					e.getMessage(), e.getMessage(), OncallServiceConstants.ERROR_MSG, nuid);
			log.warn("Did not get Clinician details while executing getClinicianDetailsByNUIDDirectRestCall() :"
					+ " error log id : " + logUtil.saveLogDetails(details));
		} catch (Exception e) {
			LogDetails details = ClinicianConnectUtilityMethods.prepareLogDetailsObject(
					OncallServiceConstants.CC_SERVICES, "getClinicianDetailsByNUIDDirectRestCall", "", "",
					e.getMessage(), e.getMessage(), OncallServiceConstants.ERROR_MSG, nuid);
			log.error("Exception occured while executing getClinicianDetailsByNUIDDirectRestCall() :"
					+ " error log id : " + logUtil.saveLogDetails(details), e);
		}
		log.info("completed getClinicianDetailsByNUIDDirectRestCall");
		return clinicianDetailProvider;
	}

	private void populatePhoneBookSearchDetailEnvelope(String resourceId, PhoneBookSearchDetailEnvelope clncnDetail,
			List<ClinicianPhoneObject> clnPhoneList, ClinicianDetailProvider clinicianDetailProvider) {

		setNamesAndTitile(clncnDetail, clinicianDetailProvider);
		setFacilityCodeAndSpecialityCode(clncnDetail, clinicianDetailProvider);
		setPrimaryDepartmentCode(clncnDetail, clinicianDetailProvider);
		setDisplayName(clncnDetail, clinicianDetailProvider);
		setResourceIdAndNuid(clncnDetail, clinicianDetailProvider);
		OnCallTelepresence tele = setOtherDetails(clncnDetail, clinicianDetailProvider);
		if (clinicianDetailProvider.getProviderLocations() != null
				&& !clinicianDetailProvider.getProviderLocations().isEmpty()) {
			List<PhoneBookClinicianLocation> detailClinicianLocations = new ArrayList<>();
			List<String> facCode = new ArrayList<>();
			Calendar cal4 = Calendar.getInstance();
			for (PhoneBookClinicianLocation providerLocations : clinicianDetailProvider.getProviderLocations()) {
				List<ClinicianContact> contacts = null;
				if (facCode.contains(providerLocations.getFacility().getCode())
						&& StringUtils.isNotBlank(providerLocations.getPrimaryIndicator())
						&& "N".equals(providerLocations.getPrimaryIndicator())) {
					continue;
				}
				// set contacts in PhoneBookClinicianDetailEnvelope
				if (clnPhoneList != null && !clnPhoneList.isEmpty()) {
					for (ClinicianPhoneObject phoneObject : clnPhoneList) {
						contacts = new ArrayList<>();
						if (providerLocations.getFacility().getCode().equals(phoneObject.getFacilityCode())) {
							setContacts(clinicianDetailProvider, providerLocations, contacts, phoneObject);
							if (contacts != null && !contacts.isEmpty()) {
								facCode.add(providerLocations.getFacility().getCode());
								break;
							}
						} else if ("Y".equals(providerLocations.getPrimaryIndicator())) {
							if (validatePhoneObjectContatcs(phoneObject)) {
								ClinicianContact contact = new ClinicianContact();
								contact.setType(OncallServiceConstants.MOBILE);
								contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourceCellPhoneNumber()));
								contact.setNumber(phoneObject.getResourceCellPhoneNumber());
								if (log.isDebugEnabled()) {
									log.debug(
											"MobileNumber is avaliable from clinicianphone where facilitycode is null");
								}
								contacts.add(contact);
							}
							prepareAndAddContact(clinicianDetailProvider, providerLocations, contacts);
						}
					}
				} else {
					contacts = new ArrayList<>();
					prepareAndAddContact(clinicianDetailProvider, providerLocations, contacts);
				}
				if (contacts != null && !contacts.isEmpty()) {
					providerLocations.setContacts(contacts);
					detailClinicianLocations.add(providerLocations);
				} else {
					if (StringUtils.isNotBlank(providerLocations.getPrimaryIndicator())
							&& "Y".equals(providerLocations.getPrimaryIndicator())) {
						facCode.add(providerLocations.getFacility().getCode());
						detailClinicianLocations.add(providerLocations);
					}
				}
			}
			Calendar cal5 = Calendar.getInstance();
			if (log.isDebugEnabled()) {
				ClinicianConnectLogger.logPerformanceTime(log, "Time spent in creating/formatting Contact Data : ",
						cal4, cal5);
			}

			if (detailClinicianLocations != null && !detailClinicianLocations.isEmpty())
				clncnDetail.setClinicianLocations(detailClinicianLocations);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("PhoneBookSearchDetail - ProviderLocation is not available for " + resourceId);
			}
		}

		Calendar cal6 = Calendar.getInstance();
		if (StringUtils.isNotBlank(clinicianDetailProvider.getEmailAddress())) {
			clncnDetail.setSecureTextUrl(OncallServiceConstants.coretext_url
					+ OncallServiceConstants.EMAIL_ADDRESS_SMALL + "=" + clinicianDetailProvider.getEmailAddress()
					+ OncallServiceConstants.CORTEXT_START_TYPING_MSG);
		} else {
			clncnDetail.setSecureTextUrl(
					OncallServiceConstants.coretext_url + OncallServiceConstants.EMAIL_ADDRESS_WITH_NULL_STRING
							+ OncallServiceConstants.CORTEXT_START_TYPING_MSG);
		}
		tele.setText("true");
		Calendar cal7 = Calendar.getInstance();
		if (log.isDebugEnabled()) {
			ClinicianConnectLogger.logPerformanceTime(log, "Time spent in Cortext Call : ", cal6, cal7);
		}
	}

	private void setNamesAndTitile(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getFirsName())) {
			clncnDetail.setFirstName(clinicianDetailProvider.getFirsName().trim());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getLastName())) {
			clncnDetail.setLastName(clinicianDetailProvider.getLastName().trim());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getProfTitle())) {
			clncnDetail.setProfTitle(clinicianDetailProvider.getProfTitle().trim());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getMiddleName())) {
			clncnDetail.setMiddleName(clinicianDetailProvider.getMiddleName().trim());
		}
	}

	private void setFacilityCodeAndSpecialityCode(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryFacilityCode())
				&& StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryFacilityName())) {
			PhoneBookFacility fac = new PhoneBookFacility();
			fac.setCode(clinicianDetailProvider.getPrimaryFacilityCode());
			fac.setName(clinicianDetailProvider.getPrimaryFacilityName());
			clncnDetail.setFacility(fac);
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPrimarySpecialtyCode())
				&& StringUtils.isNotBlank(clinicianDetailProvider.getPrimarySpecialtyName())) {
			PhoneBookSpecialty sp = new PhoneBookSpecialty();
			sp.setCode(clinicianDetailProvider.getPrimarySpecialtyCode());
			sp.setName(clinicianDetailProvider.getPrimarySpecialtyName());
			clncnDetail.setSpecialty(sp);
		}
	}

	private void setPrimaryDepartmentCode(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryDepartmentCode())
				&& StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryDepartmentName())) {
			PhoneBookDepartment dept = new PhoneBookDepartment();
			dept.setCode(clinicianDetailProvider.getPrimaryDepartmentCode());
			dept.setName(clinicianDetailProvider.getPrimaryDepartmentName());
			clncnDetail.setDepartment(dept);
		}
	}

	private void setDisplayName(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clncnDetail.getNickName())) {
			clncnDetail.setName(ClinicianConnectUtilityMethods.displayNickName(clinicianDetailProvider.getFirsName(),
					clinicianDetailProvider.getLastName(), clncnDetail.getNickName(),
					clinicianDetailProvider.getProfTitle()));
		} else if (StringUtils.isNotBlank(clinicianDetailProvider.getDisplayName())) {
			clncnDetail.setName(clinicianDetailProvider.getDisplayName());
		}
	}

	private void setResourceIdAndNuid(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getNuid())) {
			clncnDetail.setNuid(clinicianDetailProvider.getNuid());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getResourceId())) {
			clncnDetail.setResourceId(clinicianDetailProvider.getResourceId());
		}
	}

	private OnCallTelepresence setOtherDetails(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPhotoUrl())) {
			clncnDetail.setPhotoUrlString(OncallServiceConstants.photo_url + clinicianDetailProvider.getPhotoUrl()
					.replaceAll(OncallServiceConstants._photoweb, OncallServiceConstants._thumbnail1));
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getHomePageUrl())) {
			clncnDetail.setHomePageURL(clinicianDetailProvider.getHomePageUrl());
		}
		clncnDetail.setProviderType(clinicianDetailProvider.getProviderType());

		OnCallTelepresence tele = new OnCallTelepresence();
		tele.setVideo("YES");
		clncnDetail.setTelepresence(tele);
		return tele;
	}

	private void setContacts(ClinicianDetailProvider clinicianDetailProvider,
			PhoneBookClinicianLocation providerLocations, List<ClinicianContact> contacts,
			ClinicianPhoneObject phoneObject) {
		if (StringUtils.isNotBlank(phoneObject.getResourceCellPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(OncallServiceConstants.MOBILE);
			contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourceCellPhoneNumber()));
			if (log.isDebugEnabled()) {
				log.debug(OncallServiceConstants.MOBILE_NUMBER_IS_AVALIBLE);
			}
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(phoneObject.getResourcePagerPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(OncallServiceConstants.PAGER);
			contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourcePagerPhoneNumber()));
			contact.setNumber(phoneObject.getResourcePagerPhoneNumber());
			if (log.isDebugEnabled()) {
				log.debug(OncallServiceConstants.PAGER_NUMBER_IS_AVALIBLE);
			}
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(phoneObject.getResourcePrivateExternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(OncallServiceConstants.OFFICE);
			contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourcePrivateExternalPhoneNumber()));
			contact.setNumber(phoneObject.getResourcePrivateExternalPhoneNumber());
			if (log.isDebugEnabled()) {
				log.debug(OncallServiceConstants.OFFICE_NUMBER_IS_AVALIBLE);
			}
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(phoneObject.getResourcePrivateInternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(OncallServiceConstants.TIE_LINE);
			contact.setNumber(StringUtil.convertToTieLineNumber(phoneObject.getResourcePrivateInternalPhoneNumber()));
			if (log.isDebugEnabled()) {
				log.debug(OncallServiceConstants.TIE_LINE_IS_AVALIABLE);
			}
			contacts.add(contact);
		}
		prepareAndAddContact(clinicianDetailProvider, providerLocations, contacts);
	}

	private boolean validatePhoneObjectContatcs(ClinicianPhoneObject phoneObject) {
		boolean conditionOne = (phoneObject.getFacilityCode() == null && phoneObject.getClinicianPageNumber() == null
				&& phoneObject.getResourceNurseExternalPhoneNumber() == null) ? true : false;
		boolean conditionTwo = evalCondition(phoneObject);
		boolean conditionThree = (phoneObject.getResourcePagerPhoneNumber() == null
				&& phoneObject.getResourceCellPhoneNumber() != null) ? true : false;
		return (conditionOne && conditionTwo && conditionThree) ? true : false;
	}

	private void prepareAndAddContact(ClinicianDetailProvider clinicianDetailProvider,
			PhoneBookClinicianLocation providerLocations, List<ClinicianContact> contacts) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getEmailAddress())
				&& "Y".equals(providerLocations.getPrimaryIndicator())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(OncallServiceConstants.EMAIL);
			contact.setNumber(clinicianDetailProvider.getEmailAddress());
			if (log.isDebugEnabled()) {
				log.debug(OncallServiceConstants.EMAIL_IS_AVALIABLE);
			}
			contacts.add(contact);
		}
	}

	private boolean evalCondition(ClinicianPhoneObject phoneObject) {
		return (phoneObject.getResourceNurseInternalPhoneNumber() == null
				&& phoneObject.getResourcePrivateExternalPhoneNumber() == null
				&& phoneObject.getResourcePrivateInternalPhoneNumber() == null) ? true : false;
	}

	@Override
	public OnCallEnvelope getOnCallSchedule(OnCallInput onCallInput) {
		Calendar startTime = Calendar.getInstance();
		if (log.isDebugEnabled()) {
			logPerformanceMessage("Starting getOnCallSchedule() service : ", startTime);
		}
		Calendar startTimeDb = Calendar.getInstance();
		Map<String, Object> onCallEnvelopeResponse = onCallDaoImpl.setAppVersionToOnCallEnvelope(onCallInput);
		OnCallEnvelope onCallEnvelope = (OnCallEnvelope) onCallEnvelopeResponse.get("onCallEnvelope");
		CheckSpecialtyStatusResponse checkSpecialtyStatusResponse = (CheckSpecialtyStatusResponse) onCallEnvelopeResponse
				.get("checkSpecialtyStatusResponse");
		if (OncallServiceConstants.NOT_AVALIABLE
				.equalsIgnoreCase(checkSpecialtyStatusResponse.getIsSpecialtyUpdatedTobad())
				|| "true".equalsIgnoreCase(checkSpecialtyStatusResponse.getIsSpecialtyUpdatedTobad())) {
			onCallEnvelope.setMessage(OncallServiceConstants.SPECIALTY_TURNED_OFF_ERROR_MESSAGE);
			return onCallEnvelope;
		}
		List<OnCallData> onCallDataList = onCallDaoImpl.getOnCallDataList(onCallInput);
		List<OnCallSpecialty> onCallSpecialtyList = prepareOncallScheduleTest(onCallDataList);
		OnCallLookup onCallLookup = new OnCallLookup();
		onCallLookup.setPconsultFacilityCode(onCallInput.getFacilityCode());
		onCallLookup.setPconsultSpecialty(onCallInput.getSpecialty());
		setPConsultToEnvelope(onCallEnvelope, onCallLookup);
		
		Calendar endTimeDb = Calendar.getInstance();
		ClinicianConnectLogger.logPerformanceTime(log, "Time taken to execute db interactions(6.4.0) : ", startTimeDb,
				endTimeDb);
		
		setFacilitySpecialtyAlive(onCallEnvelope, onCallInput.getFacilityCode(), onCallInput.getSpecialty());
		setRunDate(onCallEnvelope);
		getTtgProviderInfo(onCallEnvelope, onCallSpecialtyList, onCallInput.getFacilityCode());
		setCortextForDepartments(onCallSpecialtyList);
		setiOSVersion(onCallInput, onCallEnvelope);
		
		return onCallEnvelope;
	}

	private void setiOSVersion(OnCallInput onCallInput, OnCallEnvelope onCallEnvelope) {
		try {
			onCallEnvelope.setiOSVersion(commonUtil.getIOSVersion(onCallInput.getDeviceType(), 
					onCallInput.getiOSVersion(),OncallServiceConstants.GET_ONCALL_SCHEDULE ));
		} catch (CCUtilityServicesException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void setCortextForDepartments(List<OnCallSpecialty> onCallSpecialtyList) {

		// Get Cortext MAp for All Specialties
		Map<String, CorTextDeptDetails> corTextDeptMap = onCallDaoImpl.getCortextDeptEmailDetails();

		if (onCallSpecialtyList != null && !onCallSpecialtyList.isEmpty() && corTextDeptMap.size() != 0) {
			for (OnCallSpecialty onCallSpecialty : onCallSpecialtyList) {
				if (evaluteCorText(onCallSpecialty)) {
					CorTextDeptDetails corTextDeptDetails = corTextDeptMap
							.get(onCallSpecialty.getProvider().getName().trim().toUpperCase());
					setText(onCallSpecialty, corTextDeptDetails);
				}
			}
		}
	}

	private void setText(OnCallSpecialty onCallSpecialty, CorTextDeptDetails corTextDeptDetails) {
		if (corTextDeptDetails == null) {
			return;
		}
		if (StringUtils.isNotBlank(corTextDeptDetails.getEmailAddress())) {
			onCallSpecialty.getProvider()
					.setSecureTextUrl(OncallServiceConstants.coretext_url + OncallServiceConstants.EMAIL_ADDRESS_SMALL
							+ "=" + corTextDeptDetails.getEmailAddress().trim()
							+ OncallServiceConstants.CORTEXT_START_TYPING_MSG);
			onCallSpecialty.getTelepresence().setText(OncallServiceConstants.RETURN_VALUE_TRUE);
		} else if (StringUtils.isNotBlank(corTextDeptDetails.getPhoneNumber())) {
			onCallSpecialty.getProvider().setSecureTextUrl(OncallServiceConstants.coretext_url
					+ OncallServiceConstants.PHONE_NUMBER_SMALL_CASE + "=" + corTextDeptDetails.getPhoneNumber().trim()
					+ OncallServiceConstants.CORTEXT_START_TYPING_MSG);
			onCallSpecialty.getTelepresence().setText(OncallServiceConstants.RETURN_VALUE_TRUE);
		}
	}

	private boolean evaluteCorText(OnCallSpecialty onCallSpecialty) {
		if (onCallSpecialty != null && onCallSpecialty.getProvider() != null) {
			return StringUtils.isNotBlank(onCallSpecialty.getProvider().getName())
					&& onCallSpecialty.getProvider().getName().toUpperCase().indexOf(CORTEXT_DEPARTMENT_STRING) != -1
					&& StringUtils.isBlank(onCallSpecialty.getProvider().getSecureTextUrl());
		}
		return false;
	}

	private void logPerformanceMessage(String string, Calendar startTime) {
		if (log.isDebugEnabled()) {
			log.debug("---->>> " + string + startTime.getTime());
		}
	}

	private void getTtgProviderInfo(OnCallEnvelope onCallEnvelope, List<OnCallSpecialty> onCallSpecialtyList,
			String onCallFacilityCode) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation getTtgProviderInfo in OnCallBOImpl");
		}
		List<OnCallSpecialty> onCallSpecialtyProviderList;
		if (onCallSpecialtyList != null && !onCallSpecialtyList.isEmpty()) {
			onCallSpecialtyProviderList = setTtgProviderInfo(onCallSpecialtyList, onCallFacilityCode);

			if (onCallSpecialtyProviderList != null && !onCallSpecialtyProviderList.isEmpty()) {
				onCallEnvelope.setSpecialists(onCallSpecialtyProviderList);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation getTtgProviderInfo in OnCallBOImpl");
		}
	}

	private List<OnCallSpecialty> setTtgProviderInfo(List<OnCallSpecialty> onCallSpecialtyList,
			String onCallFacilityCode) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation setTtgProviderInfo in OnCallBOImpl");
		}
		try {
			if (onCallSpecialtyList != null && !onCallSpecialtyList.isEmpty()) {
				Map<String, ClinicianProvider> clinicianProviderMap;
				List<String> nuidList = new ArrayList<>();
				getNuidList(onCallSpecialtyList, nuidList);

				// Provider Service
				clinicianProviderMap = callGetProvidersRestCall(nuidList);

				String providerFacilityCode = null;
				if (clinicianProviderMap != null && clinicianProviderMap.size() > 0) {

					ClinicianProvider clinicianProvider;
					for (OnCallSpecialty onCallSpecialty : onCallSpecialtyList) {
						if (onCallSpecialty.getProvider() != null && onCallSpecialty.getProvider().getNuid() != null) {
							providerFacilityCode = onCallSpecialty.getProvider().getFacilityCode();
							clinicianProvider = clinicianProviderMap.get(onCallSpecialty.getProvider().getNuid());
							// if the list dont have on call Location set the
							// primary Facility.
							if (clinicianProvider != null) {
								setFacilityAndSpecialty(onCallFacilityCode, clinicianProvider, onCallSpecialty);

								log.debug("Display facilityCode: " + onCallSpecialty.getProvider().getFacilityCode());
								log.debug("Display facilityName: " + onCallSpecialty.getProvider().getFacilityName());

								setName(clinicianProvider, onCallSpecialty);
								setNuidAndOtherDetails(clinicianProvider, onCallSpecialty);
							}

						}
					}
					// phone book
					if (nuidList != null && !nuidList.isEmpty()) {
						Map<String, ClinicianPhoneObject> clinicianPhoneMap = populatePhoneNumbersForOnCall(nuidList,
								clinicianProviderMap, providerFacilityCode);
						if (clinicianPhoneMap != null && clinicianPhoneMap.size() > 0) {
							ClinicianPhoneObject clinicianPhoneObject;
							for (OnCallSpecialty onCallSpecialty : onCallSpecialtyList) {
								if (onCallSpecialty != null && onCallSpecialty.getProvider() != null
										&& onCallSpecialty.getProvider().getNuid() != null) {

									clinicianPhoneObject = clinicianPhoneMap
											.get(onCallSpecialty.getProvider().getNuid());
									if (clinicianPhoneObject != null
											&& clinicianPhoneObject.getResourceCellPhoneNumber() != null) {
										List<OnCallContact> onCallContactList = onCallSpecialty.getSchedule()
												.getContacts();
										if (onCallContactList != null && !onCallContactList.isEmpty()) {
											for (int i = 0; i < onCallContactList.size(); i++) {
												OnCallContact onCallContact = onCallContactList.get(i);
												if (onCallContact.getType() != null && (OncallServiceConstants.MOBILE)
														.equals(onCallContact.getType())) {
													if (!specialtyName.equalsIgnoreCase(
															OncallServiceConstants.CardiologyInterventional)) {
														onCallContact.setNumber(
																clinicianPhoneObject.getResourceCellPhoneNumber());
													}
													break;
												} else if (i == onCallContactList.size() - 1) {
													List<OnCallContact> onCallContacts = onCallSpecialty.getSchedule()
															.getContacts();
													OnCallContact newOnCallContact = new OnCallContact();
													newOnCallContact.setNumber(
															clinicianPhoneObject.getResourceCellPhoneNumber());
													newOnCallContact.setType(OncallServiceConstants.MOBILE);
													onCallContacts.add(newOnCallContact);
													onCallSpecialty.getSchedule().setContacts(onCallContacts);
													break;
												}
											}
										} else {
											List<OnCallContact> onCallContacts = onCallSpecialty.getSchedule()
													.getContacts();
											if (onCallContacts == null || onCallContacts.isEmpty()) {
												onCallContacts = new ArrayList<>();
											}
											OnCallContact onCallContact = new OnCallContact();
											onCallContact.setNumber(clinicianPhoneObject.getResourceCellPhoneNumber());
											onCallContact.setType(OncallServiceConstants.MOBILE);
											onCallContacts.add(onCallContact);
											onCallSpecialty.getSchedule().setContacts(onCallContacts);
										}
									}
									if (clinicianPhoneObject != null
											&& clinicianPhoneObject.getResourcePagerPhoneNumber() != null) {
										List<OnCallContact> onCallContactList = onCallSpecialty.getSchedule()
												.getContacts();
										if (onCallContactList != null && !onCallContactList.isEmpty()) {
											for (int i = 0; i < onCallContactList.size(); i++) {
												OnCallContact onCallContact = onCallContactList.get(i);
												if (onCallContact.getType() != null && (OncallServiceConstants.PAGER)
														.equals(onCallContact.getType())) {

													if (OncallServiceConstants.SSC
															.equalsIgnoreCase(providerFacilityCode)
															&& !specialtyName.equalsIgnoreCase(
																	OncallServiceConstants.CardiologyInterventional)) {
														onCallContact.setNumber(
																clinicianPhoneObject.getResourcePagerPhoneNumber());
													}

													break;
												} else if (i == onCallContactList.size() - 1) {
													if (clinicianPhoneObject.getResourcePagerPhoneNumber() != null) {
														List<OnCallContact> onCallContacts = onCallSpecialty
																.getSchedule().getContacts();
														OnCallContact newOnCallContact = new OnCallContact();
														newOnCallContact.setNumber(
																clinicianPhoneObject.getResourcePagerPhoneNumber());
														newOnCallContact.setType(OncallServiceConstants.PAGER);
														onCallContacts.add(newOnCallContact);
														onCallSpecialty.getSchedule().setContacts(onCallContacts);
													}
													break;
												}
											}
										} else { // if not found Oncall, found
													// in phone book add that to
													// list

											List<OnCallContact> onCallContacts = onCallSpecialty.getSchedule()
													.getContacts();
											if (onCallContacts == null || onCallContacts.isEmpty()) {
												onCallContacts = new ArrayList<>();
											}
											OnCallContact onCallContact = new OnCallContact();
											onCallContact.setNumber(clinicianPhoneObject.getResourcePagerPhoneNumber());
											onCallContact.setType(OncallServiceConstants.PAGER);
											onCallContacts.add(onCallContact);
											onCallSpecialty.getSchedule().setContacts(onCallContacts);

										}
									}
									if (clinicianPhoneObject != null
											&& clinicianPhoneObject.getResourcePrivateExternalPhoneNumber() != null) {
										List<OnCallContact> onCallContactList = onCallSpecialty.getSchedule()
												.getContacts();
										if (onCallContactList != null && !onCallContactList.isEmpty()) {
											for (int i = 0; i < onCallContactList.size(); i++) {
												OnCallContact onCallContact = onCallContactList.get(i);
												if (onCallContact.getType() != null && (OncallServiceConstants.OFFICE)
														.equals(onCallContact.getType())) {
													if (!specialtyName.equalsIgnoreCase(
															OncallServiceConstants.CardiologyInterventional))
														onCallContact.setNumber(clinicianPhoneObject
																.getResourcePrivateExternalPhoneNumber());
													break;
												} else if (i == onCallContactList.size() - 1) {
													if (clinicianPhoneObject
															.getResourcePrivateExternalPhoneNumber() != null) {
														List<OnCallContact> onCallContacts = onCallSpecialty
																.getSchedule().getContacts();
														OnCallContact newOnCallContact = new OnCallContact();
														newOnCallContact.setNumber(clinicianPhoneObject
																.getResourcePrivateExternalPhoneNumber());
														newOnCallContact.setType(OncallServiceConstants.OFFICE);
														onCallContacts.add(newOnCallContact);
														onCallSpecialty.getSchedule().setContacts(onCallContacts);
													}
													break;
												}
											}
										} else {
											List<OnCallContact> onCallContacts = onCallSpecialty.getSchedule()
													.getContacts();
											if (onCallContacts == null || onCallContacts.isEmpty()) {
												onCallContacts = new ArrayList<>();
											}
											OnCallContact onCallContact = new OnCallContact();
											onCallContact.setNumber(
													clinicianPhoneObject.getResourcePrivateExternalPhoneNumber());
											onCallContact.setType(OncallServiceConstants.OFFICE);
											onCallContacts.add(onCallContact);
											onCallSpecialty.getSchedule().setContacts(onCallContacts);
										}
									}
									if (clinicianPhoneObject != null
											&& clinicianPhoneObject.getResourcePrivateInternalPhoneNumber() != null) {
										List<OnCallContact> onCallContacts = onCallSpecialty.getSchedule()
												.getContacts();
										OnCallContact onCallContact = new OnCallContact();
										onCallContact.setNumber(
												clinicianPhoneObject.getResourcePrivateInternalPhoneNumber());
										onCallContact.setType(OncallServiceConstants.TIE_LINE);
										if (onCallContacts == null) {
											onCallContacts = new ArrayList<>();
										}
										onCallContacts.add(onCallContact);
										onCallSpecialty.getSchedule().setContacts(onCallContacts);
									}
									List<OnCallContact> onCallContacts = onCallSpecialty.getSchedule().getContacts();
									if (onCallContacts != null && !onCallContacts.isEmpty()) {
										List<OnCallContact> orderedOnCallContacts = new ArrayList<>();
										for (OnCallContact onCallContact : onCallContacts) {
											if (onCallContact.getType().equals(OncallServiceConstants.MOBILE)) {
												orderedOnCallContacts.add(onCallContact);
											}
										}
										for (OnCallContact onCallContact : onCallContacts) {
											if (onCallContact.getType().equals(OncallServiceConstants.PAGER)) {
												orderedOnCallContacts.add(onCallContact);
											}
										}
										for (OnCallContact onCallContact : onCallContacts) {
											if (onCallContact.getType().equals(OncallServiceConstants.OFFICE)) {
												orderedOnCallContacts.add(onCallContact);
											}
										}
										for (OnCallContact onCallContact : onCallContacts) {
											if (onCallContact.getType().equals(OncallServiceConstants.TIE_LINE)) {
												orderedOnCallContacts.add(onCallContact);
											}
										}
										onCallSpecialty.getSchedule().setContacts(orderedOnCallContacts);
									}
								}
							}
						}
					}
					setCorTextValue(onCallSpecialtyList);
				}

			}
		} catch (Exception e) {
			log.error("ERROR: OnCallService: problem in setTtgProviderInfo", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation setTtgProviderInfo in OnCallBOImpl");
		}
		return onCallSpecialtyList;
	}

	private Map<String, ClinicianPhoneObject> populatePhoneNumbersForOnCall(List<String> nuidList,
			Map<String, ClinicianProvider> clinicianProviderMap, String providerFacilityCode) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation populatePhoneNumbersForOnCall in OnCallBOImpl");
		}
		Map<String, ClinicianPhoneObject> modifiedClinicianPhoneMap = new TreeMap<>();
		try {
			List<ClinicianPhoneObject> clinicianPhoneObjectList = onCallDaoImpl.getClinicianPhoneBookList(nuidList);
			// set the phone number belongs to primary Facility
			if (clinicianProviderMap != null && clinicianProviderMap.size() > 0) {
				for (String nuid : nuidList) {
					ClinicianProvider clinicianProvider = clinicianProviderMap.get(nuid);
					for (ClinicianPhoneObject clinicianPhoneObject : clinicianPhoneObjectList) {
						if (validateClinicianPhoneObject(nuid, clinicianProvider, clinicianPhoneObject)) {
							ClinicianPhoneObject oldClinicianPhoneObject;
							oldClinicianPhoneObject = modifiedClinicianPhoneMap.get(nuid);
							if (oldClinicianPhoneObject == null) {
								oldClinicianPhoneObject = new ClinicianPhoneObject();
							}
							if (oldClinicianPhoneObject.getFacilityCode() == null) {
								oldClinicianPhoneObject.setFacilityCode(clinicianPhoneObject.getFacilityCode());
							}
							if (oldClinicianPhoneObject.getNuid() == null) {
								oldClinicianPhoneObject.setNuid(clinicianPhoneObject.getNuid());
							}
							if (oldClinicianPhoneObject.getResourceCellPhoneNumber() == null) {
								oldClinicianPhoneObject
										.setResourceCellPhoneNumber(clinicianPhoneObject.getResourceCellPhoneNumber());
							}
							if (oldClinicianPhoneObject.getResourcePrivateExternalPhoneNumber() == null) {
								oldClinicianPhoneObject.setResourcePrivateExternalPhoneNumber(
										clinicianPhoneObject.getResourcePrivateExternalPhoneNumber());
							}
							if (oldClinicianPhoneObject.getResourcePrivateInternalPhoneNumber() == null) {
								oldClinicianPhoneObject.setResourcePrivateInternalPhoneNumber(
										clinicianPhoneObject.getResourcePrivateInternalPhoneNumber());
							}

							if (oldClinicianPhoneObject.getResourcePagerPhoneNumber() == null) {
								oldClinicianPhoneObject.setResourcePagerPhoneNumber(
										clinicianPhoneObject.getResourcePagerPhoneNumber());
							}
							logCPObject(nuid, oldClinicianPhoneObject);

							modifiedClinicianPhoneMap.put(nuid, oldClinicianPhoneObject);
						}
					}
				}
			}
			// set the phone number belongs to logged facility ( the facility
			// that you are looking into it)
			for (String nuid : nuidList) {
				for (ClinicianPhoneObject clinicianPhoneObject : clinicianPhoneObjectList) {
					if (nuid.equals(clinicianPhoneObject.getNuid())
							&& (StringUtils.isNotEmpty(clinicianPhoneObject.getFacilityCode())
									&& StringUtils.isNotEmpty(providerFacilityCode)
									&& providerFacilityCode.equals(clinicianPhoneObject.getFacilityCode()))) {
						ClinicianPhoneObject oldClinicianPhoneObject = modifiedClinicianPhoneMap.get(nuid);
						if (oldClinicianPhoneObject == null) {
							oldClinicianPhoneObject = new ClinicianPhoneObject();
						}

						if (oldClinicianPhoneObject.getNuid() == null) {
							oldClinicianPhoneObject.setNuid(clinicianPhoneObject.getNuid());
						}
						if (clinicianPhoneObject.getResourceCellPhoneNumber() != null) {
							oldClinicianPhoneObject
									.setResourceCellPhoneNumber(clinicianPhoneObject.getResourceCellPhoneNumber());
						}
						if (clinicianPhoneObject.getResourcePrivateExternalPhoneNumber() != null) {
							oldClinicianPhoneObject.setResourcePrivateExternalPhoneNumber(
									clinicianPhoneObject.getResourcePrivateExternalPhoneNumber());
						}
						if (clinicianPhoneObject.getResourcePrivateInternalPhoneNumber() != null) {
							oldClinicianPhoneObject.setResourcePrivateInternalPhoneNumber(
									clinicianPhoneObject.getResourcePrivateInternalPhoneNumber());
						}
						if (clinicianPhoneObject.getResourcePagerPhoneNumber() != null) {
							oldClinicianPhoneObject
									.setResourcePagerPhoneNumber(clinicianPhoneObject.getResourcePagerPhoneNumber());
						}
						logCPObject(nuid, oldClinicianPhoneObject);
						modifiedClinicianPhoneMap.put(nuid, oldClinicianPhoneObject);
					}
				}

			}
			for (String nuid : nuidList) {
				for (ClinicianPhoneObject clinicianPhoneObject : clinicianPhoneObjectList) {
					if (nuid.equals(clinicianPhoneObject.getNuid())
							&& (StringUtils.isEmpty(clinicianPhoneObject.getFacilityCode()))) {
						ClinicianPhoneObject oldClinicianPhoneObject = modifiedClinicianPhoneMap.get(nuid);
						if (oldClinicianPhoneObject == null) {
							oldClinicianPhoneObject = new ClinicianPhoneObject();
						}
						if (oldClinicianPhoneObject.getNuid() == null) {
							oldClinicianPhoneObject.setNuid(clinicianPhoneObject.getNuid());
						}
						if (StringUtils.isEmpty(oldClinicianPhoneObject.getResourceCellPhoneNumber())
								&& clinicianPhoneObject.getResourceCellPhoneNumber() != null) {
							oldClinicianPhoneObject
									.setResourceCellPhoneNumber(clinicianPhoneObject.getResourceCellPhoneNumber());
						}
						modifiedClinicianPhoneMap.put(nuid, oldClinicianPhoneObject);
					}
				}

			}
			if (log.isDebugEnabled()) {
				log.debug("display the rest of the phone numbers which are in primary Facility");
			}
			for (Map.Entry<String, ClinicianPhoneObject> entry : modifiedClinicianPhoneMap.entrySet()) {

				if (log.isDebugEnabled()) {
					log.debug(entry.getKey() + "/" + entry.getValue().toString());
				}
			}
		} catch (Exception e) {
			log.error("ERROR: OnCallService: problem in populatePhoneNumbersForOnCall", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation populatePhoneNumbersForOnCall in OnCallBOImpl");
		}
		return modifiedClinicianPhoneMap;
	}

	private boolean validateClinicianPhoneObject(String nuid, ClinicianProvider clinicianProvider,
			ClinicianPhoneObject clinicianPhoneObject) {
		boolean returnVal = false;
		boolean conditionOne = (clinicianProvider != null && nuid.equals(clinicianPhoneObject.getNuid())) ? true
				: false;
		if (conditionOne) {
			boolean conditionTwo = (clinicianProvider.getPrimaryFacilityCode() != null
					&& (clinicianPhoneObject.getFacilityCode() != null && clinicianPhoneObject.getFacilityCode()
							.equals(clinicianProvider.getPrimaryFacilityCode()))) ? true : false;
			if (conditionTwo) {
				returnVal = true;
			}
		}
		return returnVal;
	}

	private void logCPObject(String nuid, ClinicianPhoneObject oldClinicianPhoneObject) {
		if (log.isDebugEnabled()) {
			log.debug("nuid with getPrimaryFacilityCode " + nuid);
			log.debug("oldClinicianPhoneObject.getResourceCellPhoneNumber(): "
					+ oldClinicianPhoneObject.getResourceCellPhoneNumber());
			log.debug("oldClinicianPhoneObject.getResourcePrivateExternalPhoneNumber(): "
					+ oldClinicianPhoneObject.getResourcePrivateExternalPhoneNumber());
			log.debug("oldClinicianPhoneObject.getResourcePrivateInternalPhoneNumber(): "
					+ oldClinicianPhoneObject.getResourcePrivateInternalPhoneNumber());
			log.debug("oldClinicianPhoneObject.getResourcePagerPhoneNumber(): "
					+ oldClinicianPhoneObject.getResourcePagerPhoneNumber());
		}
	}

	private void setFacilityAndSpecialty(String onCallFacilityCode, ClinicianProvider clinicianProvider,
			OnCallSpecialty onCallSpecialty) {
		if (clinicianProvider.getLocationList() != null && !clinicianProvider.getLocationList().isEmpty()
				&& StringUtils.isNotBlank(onCallFacilityCode)
				&& !onCallFacilityCode.equalsIgnoreCase(clinicianProvider.getPrimaryFacilityCode())) {
			onCallSpecialty.getProvider().setFacilityCode(clinicianProvider.getPrimaryFacilityCode());
			onCallSpecialty.getProvider()
					.setFacilityName(clinicianProvider.getPrimaryFacilityName());
		}

		if (clinicianProvider.getPrimarySpecialtyName() != null) {
			onCallSpecialty.getProvider().setSpecialtyCode(clinicianProvider.getPrimarySpecialtyCode());
			onCallSpecialty.getProvider().setSpecialtyName(clinicianProvider.getPrimarySpecialtyName());
		}
	}

	private void setNuidAndOtherDetails(ClinicianProvider clinicianProvider, OnCallSpecialty onCallSpecialty) {
		if (StringUtils.isNotBlank(clinicianProvider.getProfTitle())) {
			onCallSpecialty.getProvider().setProfTitle(clinicianProvider.getProfTitle());
		}

		onCallSpecialty.getProvider().setNuid(clinicianProvider.getNuid());
		onCallSpecialty.getProvider().setHomepageUrl(clinicianProvider.getHomePageURL());
		if (clinicianProvider.getPhotoUrl() != null && !("").equals(clinicianProvider.getPhotoUrl())
				&& clinicianProvider.getPhotoUrl().contains(OncallServiceConstants._photoweb)) {
			onCallSpecialty.getProvider().setPhotoUrl(OncallServiceConstants.photo_url + clinicianProvider.getPhotoUrl()
					.replaceAll(OncallServiceConstants._photoweb, OncallServiceConstants._thumbnail1));
		}

		if (clinicianProvider.getEmailAddress() != null && !("").equals(clinicianProvider.getEmailAddress())) {
			onCallSpecialty.getProvider().setEmail(clinicianProvider.getEmailAddress());
		}
	}

	private void setName(ClinicianProvider clinicianProvider, OnCallSpecialty onCallSpecialty) {
		if (StringUtils.isNotBlank(onCallSpecialty.getProvider().getNickName())) {
			onCallSpecialty.getProvider()
					.setName(ClinicianConnectUtilityMethods.displayNickName(clinicianProvider.getFirsName(),
							clinicianProvider.getLastName(), onCallSpecialty.getProvider().getNickName(),
							clinicianProvider.getProfTitle()));
		} else {
			onCallSpecialty.getProvider()
					.setName(ClinicianConnectUtilityMethods.getDisplayName(clinicianProvider.getFirsName(),
							clinicianProvider.getLastName(), clinicianProvider.getProfTitle()));
		}

		if (StringUtils.isNotBlank(clinicianProvider.getFirsName())) {
			onCallSpecialty.getProvider().setFirstName(clinicianProvider.getFirsName());
		}
		if (StringUtils.isNotBlank(clinicianProvider.getLastName())) {
			onCallSpecialty.getProvider().setLastName(clinicianProvider.getLastName());
		}
		if (StringUtils.isNotBlank(clinicianProvider.getMiddleName())) {
			onCallSpecialty.getProvider().setMiddleName(clinicianProvider.getMiddleName());
		}
	}

	private Map<String, ClinicianProvider> callGetProvidersRestCall(List<String> nuidList) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation callGetProvidersRestCall in OnCallBOImpl");
		}
		String[] nuids;
		Map<String, ClinicianProvider> clinicianProviderMap = null;
		if (nuidList != null && !nuidList.isEmpty()) {
			nuids = nuidList.toArray(new String[nuidList.size()]);
			try {
				Calendar startTime = Calendar.getInstance();
				clinicianProviderMap = getProvidersByNUIDsDirectRestCall(nuids);

				Calendar endTime = Calendar.getInstance();
				ClinicianConnectLogger.logPerformanceTime(log, "Time Taken To Complete TTG Call : ", startTime,
						endTime);

			} catch (Exception e) {
				log.error("ERROR: OnCallService: problem in setTtgProviderInfo", e);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation callGetProvidersRestCall in OnCallBOImpl");
		}
		return clinicianProviderMap;
	}

	public Map<String, ClinicianProvider> getProvidersByNUIDsDirectRestCall(String[] nuids) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation getProvidersByNUIDsDirectRestCall in ClinicianConnectUtil");
		}
		Map<String, ClinicianProvider> clinicianProviderMap = null;
		if (nuids == null || nuids.length == 0) {
			return null;
		}
		// String getByNuidsUrl =
		// CCUtilityProperties.getProperty(CCUtilityConstants.GET_BY_NUIDS);

		try {
			/*
			 * clinicianProviderMap = mdoProviderUtil.getProvidersByNUIDsDirectRestCall(
			 * tokenDetailsMap.get(CCUtilityConstants.ACCESS_TOEKN), getByNuidsUrl, nuids);
			 */
			String provider=OncallServiceConstants.PROVIDER_BY_NUIDS_API_NAME;
			MdoProviderUtil mdoProviderUtil = commonUtil.getMdoProviderUtil(provider);
			clinicianProviderMap = mdoProviderUtil.getProvidersByNUIDsDirectRestCall(nuids);
		} catch (CCLibInvalidTTGResponseException e) {
			String noOfNuidsSubmittedStr = "No.Of Nuids Submitted : " + nuids.length;
			LogDetails details = ClinicianConnectUtilityMethods.prepareLogDetailsObject("CC_SERVICES",
					"getProvidersByNUIDsDirectRestCall", "Nuids Submitted : " + Arrays.toString(nuids),
					noOfNuidsSubmittedStr, e.getMessage(), e.getMessage(), CCUtilityConstants.ERROR_MSG, null);
			log.warn("Did not get Clinician details while executing getProvidersByNUIDsDirectRestCall() : "
					+ noOfNuidsSubmittedStr + ", LOG_DETAILS.LOG_DETAILS_ID : " + logUtil.saveLogDetails(details));
		} catch (Exception e) {
			String noOfNuidsSubmittedStr = "No.Of Nuids Submitted : " + nuids.length;
			LogDetails details = ClinicianConnectUtilityMethods.prepareLogDetailsObject("CC_SERVICES",
					"getProvidersByNUIDsDirectRestCall", "Nuids Submitted : " + Arrays.toString(nuids),
					noOfNuidsSubmittedStr, e.getMessage(), e.getMessage(), CCUtilityConstants.ERROR_MSG, null);
			log.error("Exception occured while executing getProvidersByNUIDsDirectRestCall() : " + noOfNuidsSubmittedStr
					+ ", LOG_DETAILS.LOG_DETAILS_ID : " + logUtil.saveLogDetails(details), e);
		}
		log.info("completed getProvidersByNUIDsDirectRestCall");
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation getProvidersByNUIDsDirectRestCall in CCUtility");
		}
		return clinicianProviderMap;
	}

	private void getNuidList(List<OnCallSpecialty> onCallSpecialtyList, List<String> nuidList) {
		for (OnCallSpecialty onCallSpecialty : onCallSpecialtyList) {
			if (onCallSpecialty.getProvider() != null && onCallSpecialty.getProvider().getNuid() != null) {
				nuidList.add(onCallSpecialty.getProvider().getNuid());
			}
		}
	}

	private void setCorTextValue(List<OnCallSpecialty> onCallSpecialtyList) {
		for (OnCallSpecialty onCallSpecialty : onCallSpecialtyList) {
			if (onCallSpecialty.getProvider() != null
					&& StringUtils.isNotBlank(onCallSpecialty.getProvider().getNuid())) {
				if (StringUtils.isNotBlank(onCallSpecialty.getProvider().getEmail())) {
					onCallSpecialty.getProvider().setSecureTextUrl(
							OncallServiceConstants.CORTEXT_URL + OncallServiceConstants.EMAIL_ADDRESS_SMALL + "="
									+ onCallSpecialty.getProvider().getEmail()
									+ OncallServiceConstants.CORTEXT_START_TYPING_MSG);
				} else {
					onCallSpecialty.getProvider()
							.setSecureTextUrl(OncallServiceConstants.CORTEXT_URL
									+ OncallServiceConstants.EMAIL_ADDRESS_WITH_NULL_STRING
									+ OncallServiceConstants.CORTEXT_START_TYPING_MSG);
				}
				onCallSpecialty.getTelepresence().setText("true");
			}
		}
	}

	private void setRunDate(OnCallEnvelope onCallEnvelope) {
		if (latestRunDate != null) {
			onCallEnvelope.setAsOfDate(latestRunDate);
		} else {
			onCallEnvelope.setAsOfDate(DateAdapter.getCurrentDateInMilliSeconds());
		}
	}

	private void setFacilitySpecialtyAlive(OnCallEnvelope onCallEnvelope, String onCallFacilityCode,
			String onCallSpecialty) {
		Facility facility = new Facility();
		facility.setCode(onCallFacilityCode);
		facility.setIsLive("Y");

		Specialty speciality = new Specialty();
		speciality.setName(onCallSpecialty);
		speciality.setIsLive("Y");

		onCallEnvelope.setLocation(facility);
		onCallEnvelope.setSpecialty(speciality);
	}

	private List<OnCallSpecialty> prepareOncallScheduleTest(List<OnCallData> onCallDataList) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation prepareOncallScheduleTest in OnCallBOImpl");
		}
		String facilityCode;
		List<Long> runDateList = new ArrayList<>();
		List<OnCallSpecialty> onCallSpecialtyList = new ArrayList<>();

		for (OnCallData onCallData : onCallDataList) {
			OnCallSpecialty onCallSpecialty = new OnCallSpecialty();
			List<OnCallContact> onCallContactList = new ArrayList<>();
			if (StringUtils.isNotBlank(onCallData.getRundate())) {
				runDateList.add(DateAdapter.convertToMilliSeconds(onCallData.getRundate()));
			}
			OnCallSchedule onCallSchedule = new OnCallSchedule();
			facilityCode = onCallData.getFacilityCode();
			onCallSchedule.setRole(onCallData.getRole());
			onCallSchedule.setSpeciality(onCallData.getSpecialty());
			onCallSchedule.setUnavailableTill(onCallData.getUnavailableTill());
			specialtyName = onCallSchedule.getSpeciality();
			String callOrder = onCallData.getCallOrder();
			if (callOrder != null && !"0".equals(callOrder)) {
				onCallSchedule.setCallOrder(callOrder);
			}
			if (StringUtils.isNotBlank(onCallData.getStartDateTime())
					&& StringUtils.isNotBlank(onCallData.getEndDateTime())) {
				Long startDateTime = DateAdapter.convertToMilliSeconds(onCallData.getStartDateTime());
				Long endDateTime = DateAdapter.convertToMilliSeconds(onCallData.getEndDateTime());
				setStartAndEndTime(onCallSchedule, startDateTime, endDateTime);
			}

			if (StringUtils.isNotBlank(onCallData.getMobileNumber())) {
				OnCallContact onCallContact = new OnCallContact();
				onCallContact.setNumber(onCallData.getMobileNumber());
				onCallContact.setType(OncallServiceConstants.MOBILE);
				onCallContactList.add(onCallContact);
			}

			if (StringUtils.isNotBlank(onCallData.getPagerNumber())) {
				OnCallContact onCallContact = new OnCallContact();
				onCallContact.setNumber(onCallData.getPagerNumber());
				onCallContact.setType(OncallServiceConstants.PAGER);
				onCallContactList.add(onCallContact);
			}

			if (StringUtils.isNotBlank(onCallData.getLineNumber())) {
				OnCallContact onCallContact = new OnCallContact();
				onCallContact.setNumber(onCallData.getLineNumber());
				onCallContact.setType(OncallServiceConstants.OFFICE);
				onCallContactList.add(onCallContact);
			}
			List<OnCallNotes> onCallNotesList = null;
			if (StringUtils.isNotBlank(onCallData.getNotes()) && hasInValidNotes(onCallData.getNotes())) {
				onCallNotesList = prepareOnCallNotesList(onCallData.getNotes());
			}

			// works in case of no nuid
			setContactList(onCallContactList, onCallSchedule);

			onCallSchedule.setNotes(onCallNotesList);
			onCallSpecialty.setSchedule(onCallSchedule);
			setOncallProvider(onCallData, facilityCode, onCallSpecialty);

			OnCallTelepresence onCallTelepresence = new OnCallTelepresence();
			onCallTelepresence.setAudio(FALSE);

			if (StringUtils.isNotBlank(onCallData.getHumanType()) && ("Y").equals(onCallData.getHumanType())) {
				onCallTelepresence.setVideo("true");
			} else {
				onCallTelepresence.setVideo(FALSE);
			}

			onCallSpecialty.setTelepresence(onCallTelepresence);
			onCallSpecialtyList.add(onCallSpecialty);
		}

		if (runDateList != null && !runDateList.isEmpty()) {
			latestRunDate = getLatestRunDateForSpecialty(runDateList);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation prepareOncallScheduleTest in OnCallBOImpl");
		}
		return onCallSpecialtyList;
	}

	private Long getLatestRunDateForSpecialty(List<Long> runDateList) {

		try {
			Collections.sort(runDateList);
		} catch (Exception e) {
			log.error("ERROR: OnCallService: problem while sorting rundate in getLatestRunDateForSpecialty" + e);
		}
		return runDateList.get(runDateList.size() - 1);
	}

	private void setStartAndEndTime(OnCallSchedule onCallSchedule, Long startDateTime, Long endDateTime) {
		if (startDateTime != null) {
			onCallSchedule.setStartDateTime(startDateTime.toString());
		}
		if (endDateTime != null) {
			onCallSchedule.setEndDateTime(endDateTime.toString());
		}
	}

	private boolean hasInValidNotes(String notes) {
		int schCount = checkValidNameOfNotes(OncallServiceConstants.SCHEDULED_NOTES, notes);
		int perCount = checkValidNameOfNotes(OncallServiceConstants.PERSONAL_NOTES, notes);
		int depCount = checkValidNameOfNotes(OncallServiceConstants.DEPARTMENT_NOTES, notes);
		return !(schCount > 1 || perCount > 1 || depCount > 1);
	}

	private int checkValidNameOfNotes(String notesName, String notes) {
		int count = 0;
		try {
			int lastIndex = 0;

			while (lastIndex != -1) {
				lastIndex = notes.indexOf(notesName, lastIndex);
				if (lastIndex != -1) {
					count++;
					lastIndex += notesName.length();
				}
			}
		} catch (Exception e) {
			log.error("ERROR: OnCallService: problem with name of notes in checkValidNameOfNotes" + e);
		}
		return count;
	}

	private List<OnCallNotes> prepareOnCallNotesList(String notes) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation prepareOnCallNotesList in OnCallBOImpl");
		}
		List<OnCallNotes> onCallNotesList = new ArrayList<>();
		try {
			if (notes.indexOf(OncallServiceConstants.SCHEDULED_NOTES) >= 0
					&& notes.indexOf(OncallServiceConstants.PERSONAL_NOTES) > 0
					&& notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES) > 0) {
				String[] scheduled = notes.substring(0, notes.indexOf(OncallServiceConstants.PERSONAL_NOTES) - 2).trim()
						.split("\n", 2);
				if (isValidStringArray(scheduled)) {
					OnCallNotes scheduledNotes = new OnCallNotes();
					scheduledNotes.setType(OncallServiceConstants.SCHEDULED);
					scheduledNotes.setNote(scheduled[1]);
					onCallNotesList.add(scheduledNotes);
				}
				String[] personal = notes.substring(notes.indexOf(OncallServiceConstants.PERSONAL_NOTES),
						notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES) - 2).trim().split("\n", 2);
				if (isValidStringArray(personal)) {
					OnCallNotes personalNotes = new OnCallNotes();
					personalNotes.setType(OncallServiceConstants.PERSONAL);
					personalNotes.setNote(personal[1]);
					onCallNotesList.add(personalNotes);
				}
				String[] department = notes
						.substring(notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES), notes.length()).trim()
						.split("\n", 2);
				if (isValidStringArray(department)) {
					OnCallNotes departmentNotes = new OnCallNotes();
					departmentNotes.setType(OncallServiceConstants.DEPARTMENT);
					departmentNotes.setNote(department[1]);
					onCallNotesList.add(departmentNotes);
				}
			} else if (notes.indexOf(OncallServiceConstants.SCHEDULED_NOTES) >= 0
					&& notes.indexOf(OncallServiceConstants.PERSONAL_NOTES) > 0
					&& notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES) == -1) {
				String[] scheduled = notes.substring(0, notes.indexOf(OncallServiceConstants.PERSONAL_NOTES) - 2).trim()
						.split("\n", 2);
				if (isValidStringArray(scheduled)) {
					OnCallNotes scheduledNotes = new OnCallNotes();
					scheduledNotes.setType(OncallServiceConstants.SCHEDULED);
					scheduledNotes.setNote(scheduled[1]);
					onCallNotesList.add(scheduledNotes);
				}
				String[] personal = notes
						.substring(notes.indexOf(OncallServiceConstants.PERSONAL_NOTES), notes.length()).split("\n", 2);
				if (isValidStringArray(personal)) {
					OnCallNotes personalNotes = new OnCallNotes();
					personalNotes.setType(OncallServiceConstants.PERSONAL);
					personalNotes.setNote(personal[1]);
					onCallNotesList.add(personalNotes);
				}
			} else if (notes.indexOf(OncallServiceConstants.SCHEDULED_NOTES) >= 0
					&& notes.indexOf(OncallServiceConstants.PERSONAL_NOTES) == -1
					&& notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES) == -1) {
				String[] scheduled = notes.trim().split("\n", 2);
				if (isValidStringArray(scheduled)) {
					OnCallNotes scheduledNotes = new OnCallNotes();
					scheduledNotes.setType(OncallServiceConstants.SCHEDULED);
					scheduledNotes.setNote(scheduled[1]);
					onCallNotesList.add(scheduledNotes);
				}

				if (log.isDebugEnabled()) {
					log.debug("the value of Scheduled Notes is : " + notes);
				}
			} else if (notes.indexOf(OncallServiceConstants.SCHEDULED_NOTES) >= 0
					&& notes.indexOf(OncallServiceConstants.PERSONAL_NOTES) == -1
					&& notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES) > 0) {
				if (log.isDebugEnabled()) {
					log.debug("The value of Scheduled Notes is : "

							+ notes.substring(0, notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES) - 2));
					log.debug("The value of Department Notes is : "
							+ notes.substring(notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES), notes.length()));
				}
				String[] scheduled = notes.trim().split("\n", 2);
				if (isValidStringArray(scheduled)) {
					OnCallNotes scheduledNotes = new OnCallNotes();
					scheduledNotes.setType(OncallServiceConstants.SCHEDULED);
					scheduledNotes.setNote(scheduled[1]);
					onCallNotesList.add(scheduledNotes);
				}
				String[] department = notes
						.substring(notes.indexOf(OncallServiceConstants.DEPARTMENT_NOTES), notes.length())
						.split("\n", 2);
				if (isValidStringArray(department)) {
					OnCallNotes departmentNotes = new OnCallNotes();
					departmentNotes.setType(OncallServiceConstants.DEPARTMENT);
					departmentNotes.setNote(department[1]);
					onCallNotesList.add(departmentNotes);
				}
			}
		} catch (Exception e) {
			log.error("ERROR: OnCallService: - Problem while reading from Notes ", e);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation prepareOnCallNotesList in OnCallBOImpl");
		}
		return onCallNotesList;

	}

	private boolean isValidStringArray(String[] strArray) {
		boolean isValidCond = false;
		boolean valueOne = (strArray != null && strArray.length >= 2) ? true : false;
		if (valueOne) {
			boolean valueTwo = (strArray[1] != null && !("").equals(strArray[1]) && strArray[1].length() > 0) ? true
					: false;
			if (valueTwo) {
				isValidCond = true;
			}
		}
		return isValidCond;
	}

	private void setContactList(List<OnCallContact> onCallContactList, OnCallSchedule onCallSchedule) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation setContactList in OnCallBOImpl");
		}
		if (onCallContactList != null && !onCallContactList.isEmpty()) {
			List<OnCallContact> orderedOnCallContacts = new ArrayList<>();
			for (OnCallContact onCallContact : onCallContactList) {
				if (onCallContact.getType().equals(OncallServiceConstants.MOBILE)) {
					orderedOnCallContacts.add(onCallContact);
				}
			}
			for (OnCallContact onCallContact : onCallContactList) {
				if (onCallContact.getType().equals(OncallServiceConstants.PAGER)) {
					orderedOnCallContacts.add(onCallContact);
				}
			}
			for (OnCallContact onCallContact : onCallContactList) {
				if (onCallContact.getType().equals(OncallServiceConstants.OFFICE)) {
					orderedOnCallContacts.add(onCallContact);
				}
			}
			onCallSchedule.setContacts(orderedOnCallContacts);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation setContactList in OnCallBOImpl");
		}
	}

//	//@Override
//	public FacilityEnvelope getFacilities(FacilityInput facilityInput) {
//		List<Facility> facilities;
//		FacilityEnvelope facilityEnvelope;
//		facilities = onCallDaoImpl.prepareFacilitiesList();
//		facilityEnvelope = new FacilityEnvelope();
//		try {
//			AppVersion appversion = commonUtil.getAppVersion(facilityInput.getAppVersion());
//			if (appversion != null) {
//				facilityEnvelope.setAppVersion(appversion);
//			}
//			facilityEnvelope.setiOSVersion(commonUtil.getIOSVersion(facilityInput.getDeviceType(),
//					facilityInput.getiOSVersion(), GET_FACILITIES));
//		} catch (CCUtilityServicesException e) {
//			log.error("ERROR: OncallService: problem in getFacilities", e);
//		}
//		facilityEnvelope.setFacilities(facilities);
//		return facilityEnvelope;
//	}

	private void setOncallProvider(OnCallData oncallData, String facilityCode, OnCallSpecialty onCallSpecialty) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation setOncallProvider in OnCallBOImpl");
		}
		if (StringUtils.isNotBlank(oncallData.getResourceName())) {
			OnCallProvider onCallProvider = new OnCallProvider();
			if (StringUtils.isNotBlank(oncallData.getNuid())) {
				onCallProvider.setNuid(oncallData.getNuid());
			}
			onCallProvider.setName(oncallData.getResourceName());
			if (StringUtils.isNotBlank(oncallData.getNickName())) {
				onCallProvider.setNickName(oncallData.getNickName());
			}
			if (StringUtils.isNotBlank(facilityCode)) {
				onCallProvider.setFacilityCode(facilityCode);
			}
			onCallSpecialty.setProvider(onCallProvider);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation setOncallProvider in OnCallBOImpl");
		}
	}

	private void setPConsultToEnvelope(OnCallEnvelope onCallEnvelope, OnCallLookup onCallLookup) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation setPConsultToEnvelope in OnCallBOImpl");
		}
		List<OnCallSpecialtySchedule> onCallSpecialtyScheduleList = null;
		try {
			onCallSpecialtyScheduleList = commonUtil.preparePConsultNumbers(onCallLookup);
		} catch (Exception e) {
			log.error("ERROR: CCUtility/CommonUtilDAOImpl: problem in getPConsultNumber", e);
		}
		if (onCallSpecialtyScheduleList != null && !onCallSpecialtyScheduleList.isEmpty()) {
			onCallEnvelope.setSpecialtySchedule(onCallSpecialtyScheduleList);
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation setPConsultToEnvelope in OnCallBOImpl");
		}
	}

//	//@Override
//	public void prepareResponseEnvelope(FacilityOutput facilityOutput, Status status,
//			FacilityEnvelope facilityEnvelope) {
//		if (facilityEnvelope != null) {
//			status.setCode("200");
//			status.setMessage(SUCCESS);
//			if (facilityEnvelope.getAppVersion() != null) {
//				facilityOutput.setAppVersion(facilityEnvelope.getAppVersion());
//			}
//			facilityOutput.setiOSVersion(facilityEnvelope.getiOSVersion());
//			facilityOutput.setEnvelope(facilityEnvelope);
//		} else {
//			status.setCode("204");
//			status.setMessage(PROBLEM_WHILE_RETRIEVING_THE_DATA_FROM_DATABASE);
//		}
//	}

	//@Override
	public void setStatusForFacilitySpecailities(Status status, SpecialtyEnvelope specialtyEnvelope) {
		if (StringUtil.validCollectionObject(specialtyEnvelope.getSpecialties())) {
			status.setCode("200");
			status.setMessage(SUCCESS);
		} else {
			status.setCode("503");
			status.setMessage(specialtyEnvelope.getMessage());
		}
	}

	//@Override
	public void setAppVersionForFacilitySpecialty(SpecialtyOutput specialtyOutput,
			SpecialtyEnvelope specialtyEnvelope) {
		if (specialtyEnvelope.getAppVersion() != null) {
			specialtyOutput.setAppVersion(specialtyEnvelope.getAppVersion());
		}
	}

	//@Override
	public SpecialtyEnvelope getSpecialtiesList(SpecialtyInput specialtyInput) {
		if (log.isDebugEnabled()) {
			log.debug("call has entered the operation  SpecialtyEnvelope getSpecialtiesList in SpecialtyDAOImpl");
		}
		SpecialtyEnvelope specialityEnvelope = null;
		if (specialtyInput != null && StringUtils.isNotBlank(specialtyInput.getFacilityCode())) {
			specialityEnvelope = onCallDaoImpl.prepareSpecialties(specialtyInput);
			try {
				specialityEnvelope.setiOSVersion(commonUtil.getIOSVersion(specialtyInput.getDeviceType(),
						specialtyInput.getiOSVersion(), "getSpecialitiesList"));
				specialityEnvelope.setAppVersion(commonUtil.getAppVersion(specialtyInput.getAppVersion()));
			} catch (CCUtilityServicesException e) {
				if (log.isDebugEnabled()) {
					log.debug(
							"ERROR: CCUtility: problem while fetching IOSVersion in getSpecialtiesList - CCUtilityServicesException",
							e);
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("call has exited the operation  SpecialtyEnvelope getSpecialtiesList in FacilityDAOImpl");
		}
		return specialityEnvelope;
	}
	
//	@Override
//	public boolean updateMyUnavailability(UpdateClinicianUnavailabilityRequest request) {
//		return onCallDaoImpl.updateMyUnavailability(request);
//	}
}
