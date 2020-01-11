package org.kp.tpmg.ttg.clinicianconnect.web.service;


import org.kp.tpmg.ttg.clinicianconnect.web.model.FacilityEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.FacilityOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.OnCallInput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.PhoneBookSearchDetailEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.web.model.SpecialtyOutput;
import org.kp.tpmg.ttg.clinicianconnect.web.model.Status;
import org.kp.tpmg.ttg.clinicianconnect.web.model.UpdateClinicianUnavailabilityRequest;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.FacilityInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchDetailInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SpecialtyInput;

public interface OncallService {
	

	public PhoneBookSearchDetailEnvelope getOncallClinicianDetails(SearchDetailInput searchDetailInput);
    public OnCallEnvelope getOnCallSchedule(OnCallInput onCallInput);
	public void prepareResponseEnvelope(FacilityOutput facilityOutput, Status status, FacilityEnvelope facilityEnvelope);
    public void setStatusForFacilitySpecailities(Status status, SpecialtyEnvelope specialtyEnvelope);	
    public void setAppVersionForFacilitySpecialty(SpecialtyOutput specialtyOutput, SpecialtyEnvelope specialtyEnvelope);
    public SpecialtyEnvelope getSpecialtiesList(SpecialtyInput specialtyInput);
	public FacilityEnvelope getFacilities(FacilityInput facilityInput);
	//public boolean updateMyUnavailability(UpdateClinicianUnavailabilityRequest request);
}
