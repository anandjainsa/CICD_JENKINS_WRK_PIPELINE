package org.kp.tpmg.ttg.clinicianconnect.web.dao;

import java.util.List;
import java.util.Map;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SpecialtyInput;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.ClinicianPhoneObject;
import org.kp.tpmg.ttg.clinicianconnect.web.model.CorTextDeptDetails;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Facility;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallData;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallInput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.UpdateClinicianUnavailabilityRequest;

public interface OncallDao {
	public List<ClinicianPhoneObject> getClinicianContacts(String resourceId);
	public String getNickNameUsingNuid(String trim);
	public Map<String, Object> setAppVersionToOnCallEnvelope(OnCallInput onCallInput);
	public List<OnCallData> getOnCallDataList(OnCallInput onCallInput);
    public List<ClinicianPhoneObject> getClinicianPhoneBookList(List<String> nuidList);
	public SpecialtyEnvelope prepareSpecialties(SpecialtyInput specialtyInput);
   public List<Facility> prepareFacilitiesList();
   public Map<String, CorTextDeptDetails> getCortextDeptEmailDetails();
	//public boolean updateMyUnavailability(UpdateClinicianUnavailabilityRequest request);
}
