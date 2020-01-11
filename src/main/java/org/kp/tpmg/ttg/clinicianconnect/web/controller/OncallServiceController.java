package org.kp.tpmg.ttg.clinicianconnect.web.controller;

import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.GET_FACILITY_LIST;
import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.GET_FACILITY_SPECIALTIES;
import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.GET_ONCALL_CLINICIAN_DETAILS;
import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.INCORRECT_INPUT;
import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM;
import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.REQUEST_PARAMTERS;
import static org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants.SUCCESS;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.ClinicianConnectLogger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.Validator;
import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.InvalidDataFormatException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.FacilityInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchDetailInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SpecialtyInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.util.LogUtil;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants;
import org.kp.tpmg.ttg.clinicianconnect.web.model.FacilityEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.FacilityJSON;
import org.kp.tpmg.ttg.clinicianconnect.web.model.FacilityOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallInput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallJSON;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.PhoneBookSearchDetailEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.PhoneBookSearchDetailJSON;
import org.kp.tpmg.ttg.clinicianconnect.web.model.PhoneBookSearchDetailOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyJSON;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Status;
import org.kp.tpmg.ttg.clinicianconnect.web.service.OncallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oncall Controller
 */
@RestController
@RequestMapping("/oncallController")
public class OncallServiceController {

	private static final Logger log = Logger.getLogger(OncallServiceController.class);
	//private static Logger log = LoggerFactory.getLogger(OncallServiceController.class); 

	public static final String GET_ON_CALL_SCHEDULE = "getOnCallSchedule";

	@Autowired
	OncallService onCallService;
	
	@Autowired
	LogUtil logUtil;
	
	private void setBadRequestStatusAndLogError(Status status, Exception e) {
		status.setCode(OncallServiceConstants.BAD_REQUEST);
		status.setMessage(e.getMessage());
		log.error("Exception in OncallServiceController::"+e);
	}
	@RequestMapping(value = "/getFacilities", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<FacilityJSON> getFacilityList(@RequestBody FacilityInput facilityInput) {
		if (log.isDebugEnabled()) {
			ClinicianConnectLogger.callEnteredLogAtService(log, GET_FACILITY_LIST,
					OncallServiceConstants.CLINICIAN_CONNECT_API_CLASS_NAME);
			log.debug(REQUEST_PARAMTERS + logUtil.logRequestParam(facilityInput));
		}
		FacilityOutput facilityOutput = new FacilityOutput();
		Status status = new Status();
		Validator validator = new Validator();
		try {
			validator.validateFacilityInput(facilityInput);
			FacilityEnvelope facilityEnvelope = onCallService.getFacilities(facilityInput);
			onCallService.prepareResponseEnvelope(facilityOutput, status, facilityEnvelope);
		} catch (InvalidDataFormatException e) {
			setBadRequestStatusAndLogError(status, e);
		}
		facilityOutput.setName(GET_FACILITY_LIST);
		facilityOutput.setStatus(status);
		FacilityJSON facilityJSON = new FacilityJSON();
		facilityJSON.setService(facilityOutput);
		if (log.isDebugEnabled()) {
			ClinicianConnectLogger.callExitedLogAtService(log, GET_FACILITY_LIST,
					OncallServiceConstants.CLINICIAN_CONNECT_API_CLASS_NAME);
		}
		return new ResponseEntity<>(facilityJSON, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getOncallClinicianDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<PhoneBookSearchDetailJSON> getOncallClinicianDetails(
			@RequestBody SearchDetailInput searchDetailInput) {
		Status status = new Status();
		PhoneBookSearchDetailOutput phoneBookSearchDetailOutput = new PhoneBookSearchDetailOutput();
		PhoneBookSearchDetailJSON phoneBookSearchDetailJSON = new PhoneBookSearchDetailJSON();
		Validator validator = new Validator();
		if (searchDetailInput != null && StringUtils.isNotBlank(searchDetailInput.getNuid())) {
			try {
				validator.validateSearchDetailInput(searchDetailInput);
				PhoneBookSearchDetailEnvelope phoneBookSearchDetailEnvelope = onCallService
						.getOncallClinicianDetails(searchDetailInput);
				if (phoneBookSearchDetailEnvelope != null) {
					setAppVersionAndStatusCode(phoneBookSearchDetailEnvelope, phoneBookSearchDetailOutput, status);
					phoneBookSearchDetailOutput.setEnvelope(phoneBookSearchDetailEnvelope);
				} else {
					status.setCode("204");
					status.setMessage(NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
				}
			} catch (Exception e) {
				setBadRequestStatusAndLogError(status, e);
			}
			phoneBookSearchDetailOutput.setName(GET_ONCALL_CLINICIAN_DETAILS);
			phoneBookSearchDetailOutput.setStatus(status);
			phoneBookSearchDetailJSON.setService(phoneBookSearchDetailOutput);
			if (log.isDebugEnabled()) {
				ClinicianConnectLogger.callExitedLogAtService(log, GET_ONCALL_CLINICIAN_DETAILS,
						OncallServiceConstants.CLINICIAN_CONNECT_API_CLASS_NAME);
			}
			return new ResponseEntity<PhoneBookSearchDetailJSON>(phoneBookSearchDetailJSON, HttpStatus.OK);
		} else {
			status.setCode("204");
			status.setMessage(INCORRECT_INPUT);
			log.warn(INCORRECT_INPUT);
			phoneBookSearchDetailOutput.setName(GET_ONCALL_CLINICIAN_DETAILS);
			phoneBookSearchDetailOutput.setStatus(status);
			phoneBookSearchDetailJSON.setService(phoneBookSearchDetailOutput);
			if (log.isDebugEnabled()) {
				ClinicianConnectLogger.callExitedLogAtService(log, GET_ONCALL_CLINICIAN_DETAILS,
						OncallServiceConstants.CLINICIAN_CONNECT_API_CLASS_NAME);
			}
			return new ResponseEntity<PhoneBookSearchDetailJSON>(phoneBookSearchDetailJSON, HttpStatus.OK);
		}
	}
	
	private void setAppVersionAndStatusCode(PhoneBookSearchDetailEnvelope phoneBookSearchDetailEnvelope,
			PhoneBookSearchDetailOutput phoneBookSearchDetailOutput, Status status) {
		if (phoneBookSearchDetailEnvelope.getAppVersion() != null) {
			status.setCode("200");
			status.setMessage(SUCCESS);
			phoneBookSearchDetailOutput.setAppVersion(phoneBookSearchDetailEnvelope.getAppVersion());
		} else {
			status.setCode("204");
			status.setMessage(NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
		}
	}
	
	@RequestMapping(value = "/getOnCallSchedule", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<OnCallJSON> getOnCallSchedule(@RequestBody OnCallInput onCallInput) {
		OnCallOutput onCallOutput = new OnCallOutput();
		Status status = new Status();
		try {
			if (onCallInput != null && StringUtils.isNotBlank(onCallInput.getFacilityCode())) {
				OnCallEnvelope onCallEnvelope = onCallService.getOnCallSchedule(onCallInput);

				if (onCallEnvelope != null) {
					setStatusCode(status, onCallEnvelope);
					onCallOutput.setEnvelope(onCallEnvelope);
					setAppVersionForOncall(onCallOutput, onCallEnvelope);
					onCallOutput.setiOSVersion(onCallEnvelope.getiOSVersion());
				} else {
					status.setCode("204");
					status.setMessage(NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
				}
			}
		} catch (Exception e) {
			setBadRequestStatusAndLogError(status, e);
		}
		onCallOutput.setName(GET_ON_CALL_SCHEDULE);
		onCallOutput.setStatus(status);
		OnCallJSON onCallJSON = new OnCallJSON();
		onCallJSON.setService(onCallOutput);
		return new ResponseEntity<OnCallJSON>(onCallJSON, HttpStatus.OK);
	}

	private void setStatusCode(Status status, OnCallEnvelope onCallEnvelope) {
		if (onCallEnvelope.getMessage() != null) {
			status.setCode("503");
			status.setMessage(onCallEnvelope.getMessage());
		} else {
			status.setCode("200");
			status.setMessage(SUCCESS);
		}
	}
	
	private void setAppVersionForOncall(OnCallOutput onCallOutput, OnCallEnvelope onCallEnvelope) {
		if (onCallEnvelope.getAppVersion() != null) {
			onCallOutput.setAppVersion(onCallEnvelope.getAppVersion());
		}
	}
	
	@RequestMapping(value = "/getFacilitySpecialties", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<SpecialtyJSON> getFacilitySpecialties(@RequestBody SpecialtyInput specialtyInput) {
		if (log.isDebugEnabled()) {
			ClinicianConnectLogger.callEnteredLogAtService(log, GET_FACILITY_SPECIALTIES,
					OncallServiceConstants.CLINICIAN_CONNECT_API_CLASS_NAME);
			log.debug(REQUEST_PARAMTERS + logUtil.logRequestParam(specialtyInput));
		}
		SpecialtyJSON specialtyJSON = new SpecialtyJSON();
		SpecialtyOutput specialtyOutput = new SpecialtyOutput();
		Status status = new Status();
		Validator validator = new Validator();
		try {
			if (specialtyInput != null && StringUtils.isNotBlank(specialtyInput.getFacilityCode())) {
				validator.validateSpecialtyInput(specialtyInput);
				SpecialtyEnvelope specialtyEnvelope = onCallService.getSpecialtiesList(specialtyInput);
				if (specialtyEnvelope != null) {
					onCallService.setStatusForFacilitySpecailities(status, specialtyEnvelope);
					specialtyOutput.setEnvelope(specialtyEnvelope);
					onCallService.setAppVersionForFacilitySpecialty(specialtyOutput, specialtyEnvelope);
				} else {
					status.setCode("204");
					status.setMessage(NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
				}
			} else {
				status.setCode("204");
				status.setMessage("Please pass the facility code");
			}
		} catch (InvalidDataFormatException e) {
			setBadRequestStatusAndLogError(status, e);
		}
		specialtyOutput.setName(GET_FACILITY_SPECIALTIES);
		specialtyOutput.setStatus(status);
		specialtyJSON.setService(specialtyOutput);
		if (log.isDebugEnabled()) {
			ClinicianConnectLogger.callExitedLogAtService(log, GET_FACILITY_SPECIALTIES,
					OncallServiceConstants.CLINICIAN_CONNECT_API_CLASS_NAME);
		}
		return new ResponseEntity<SpecialtyJSON>(specialtyJSON, HttpStatus.OK);
	}
}
