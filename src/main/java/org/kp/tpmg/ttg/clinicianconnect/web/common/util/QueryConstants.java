package org.kp.tpmg.ttg.clinicianconnect.web.common.util;

import org.springframework.beans.factory.annotation.Value;

public class QueryConstants {
	
	@Value(value="${MDO_DB_NAME}")
	private static String mdoDbName;
	public static final String GET_CONTACTS = "SELECT ResourceId, FacilityCode, ResourcePagerPhoneNumber, ResourceCellPhoneNumber, ResourcePrivateExternalPhoneNumber, ResourcePrivateInternalPhoneNumber "
			+ "FROM [ClinicianPhone] " + "WHERE ResourceId = ?";
	public static final String GET_IOS_VERSION_UPGRADE_DETAILS = "SELECT VERSION,DEVICE_TYPE,INSTRUCTION,URL,TEXT_MESSAGE FROM IOS_VERSION_UPGRADE_MESSAGE WHERE UPPER(DEVICE_TYPE) = UPPER(?) AND VERSION = ?";
	public static final String GET_NICK_NAME_FROM_CCUSERS_BASED_ON_NUID = "SELECT NICK_NAME FROM "+ OncallServiceConstants.CC_DBO_CC_USERS +" WHERE NUID = ? ";
	public static final String CC_WEBSERVICE_APP_VERSION = "_7_5";
	public static final String GET_LOCATIONS = "select LocationCode,Description from LocationMap";
	public static final String CHECK_SPECAILTY_STATUS = "{CALL "+OncallServiceConstants.SCHEMA_NAME+"CHECK_SPECIALTY_STATUS" + CC_WEBSERVICE_APP_VERSION + "(?,?,?,?,?,?)}";
	public static final String GET_PHONE_CLN_PHONEBOOK = "SELECT provider.NUID as Nuid , clPhone.FacilityCode as FacilityCode,clPhone.ResourceCellPhoneNumber as ResourceCellPhoneNumber ,clPhone.ResourcePrivateExternalPhoneNumber as ResourcePrivateExternalPhoneNumber, "
			+ " clPhone.ResourcePagerPhoneNumber as ResourcePagerPhoneNumber, clPhone.ResourcePrivateInternalPhoneNumber as ResourcePrivateInternalPhoneNumber FROM "
			+ OncallServiceConstants.CC_DBO_CLINICIAN_PHONE + " clPhone Inner join ["
			+ mdoDbName
			+ "].[dbo].[PROVIDER] provider "
			+ "On clPhone.ResourceId = provider.RESOURCE_ID  where provider.NUID in ( ";
	public static final String FIND_FACILITIES = " SELECT substring(fac.facility_name,1,1) as facStartChar, facMap.FacilityCode as FacilityCode,fac.facility_name as facilityName,locMap.OnLineForCC as isLive FROM "
			+OncallServiceConstants.SCHEMA_NAME+ OncallServiceConstants.CC_DBO_LOCATION_FACILITY_MAP
			+ " facMap join "
			+ OncallServiceConstants.SCHEMA_NAME +OncallServiceConstants.CC_DBO_LOCATION_MAP
			+ " locMap on locMap.LocationMapID = facMap.LocationMapID  "
			+ "and locMap.OnLineForCC = 	'T' join "
			+ OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".dbo.Facility fac on fac.facility_code = facMap.FacilityCode and facMap.LiveOnMobile = 'Y'  order by facStartChar,facilityName ";
	public static final String FIND_SPECIALTIES_LIST = " SELECT facMap.FacilityCode as FacilityCode,fac.facility_name as facilityName,splmap.FacilityCode as specialitycode, splmap.IntelliWebSpecialty as specialty, splMap.Quality,splMap.IS_TURNED_OFF,locMap.OnLineForCC as isLive FROM "
			+OncallServiceConstants.SCHEMA_NAME+ OncallServiceConstants.CC_DBO_LOCATION_FACILITY_MAP
			+ " facMap join "
			+ OncallServiceConstants.SCHEMA_NAME + OncallServiceConstants.CC_DBO_LOCATION_MAP
			+ " locMap on locMap.LocationMapID = facMap.LocationMapID "
			+ " and locMap.OnLineForCC = 'T' "
			+ " join "
			+ OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".dbo.Facility fac on fac.facility_code = facMap.FacilityCode and facMap.LiveOnMobile = 'Y' join "
			+ OncallServiceConstants.SCHEMA_NAME + OncallServiceConstants.CC_DBO_FACILITY_SPECIALTY_MAP
			+ " splMap on facMap.LocationFacilityMapID = splMap.LocationFacilityMapID "
			+ " where splMap.ActiveStatus in ('A','O','M') "
			+ " and facMap.FacilityCode = ? order by splmap.IntelliWebSpecialty";
	public static final String FIND_FACILITIES_NOSTARTCHAR = "SELECT facMap.FacilityCode as FacilityCode,fac.facility_name as facilityName,locMap.OnLineForCC as isLive FROM "
			+ OncallServiceConstants.SCHEMA_NAME + OncallServiceConstants.CC_DBO_LOCATION_FACILITY_MAP
			+ " facMap join "
			+OncallServiceConstants.SCHEMA_NAME +  OncallServiceConstants.CC_DBO_LOCATION_MAP
			+ " locMap on locMap.LocationMapID = facMap.LocationMapID  "
			+ "and locMap.OnLineForCC = 'T' join "
			+ OncallServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".dbo.Facility fac on fac.facility_code = facMap.FacilityCode and facMap.LiveOnMobile = 'Y'  order by facilityName ";
	public static final String GET_CLINICIAN_UNAVAILIABILITY_DATETIME = "SELECT Nuid,UnavailabilityDateTime FROM " +OncallServiceConstants.SCHEMA_NAME+"OncallClinicianUnavailability WHERE Nuid = ? ";
	public static final String UPDATE_CLINICIAN_UNAVAILIABILITY_DATETIME = "UPDATE " +OncallServiceConstants.SCHEMA_NAME+ "OncallClinicianUnavailability SET UnavailabilityDateTime = ?, UpdateDateTime = GETDATE() WHERE Nuid = ?";
	public static final String INSERT_CLINICIAN_UNAVAILIABILITY_DATETIME = "INSERT INTO " +OncallServiceConstants.SCHEMA_NAME+ "OncallClinicianUnavailability (UnavailabilityDateTime,Nuid,CreateDateTime) values (?,?,GETDATE())";

	//##Stored Procedures
	public static final String GET_APP_VERSION_UPGRADE_DETAILS = "{CALL "+OncallServiceConstants.SCHEMA_NAME+"GET_APP_VERSION_UPGRADE_DETAILS" + CC_WEBSERVICE_APP_VERSION + "(?,?)}";
	public static final String GET_ONCALL_DATA = "{CALL "+OncallServiceConstants.SCHEMA_NAME+"GET_ONCALL_SCHDULE" + CC_WEBSERVICE_APP_VERSION + "(?,?,?,?)}";
	public static final String GET_ALL_ONCALL_DEPARTMENT_EMAIL_ADDRESSES = "SELECT DISTINCT DepartmentName, EmailAddress, PhoneNumber FROM dbo.OnCallDepartmentEmailAddress ";
 }
