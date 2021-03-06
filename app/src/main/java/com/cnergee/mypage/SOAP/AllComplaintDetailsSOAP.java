package com.cnergee.mypage.SOAP;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

import com.cnergee.mypage.obj.AuthenticationMobile;
import com.cnergee.mypage.utils.Utils;

public class AllComplaintDetailsSOAP {

	
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private String response;
	
	
	public AllComplaintDetailsSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}

	public String getComplaintDetails(ArrayList<String> alNames,ArrayList<String>alValue) throws SocketException,SocketTimeoutException,Exception {

	

		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

		PropertyInfo pi = new PropertyInfo();
		if(alNames!=null&&alValue!=null){
		for(int i=0;i<alNames.size();i++){
			
			pi = new PropertyInfo();
			pi.setName(alNames.get(i));
			pi.setValue(alValue.get(i));
			pi.setType(String.class);
			request.addProperty(pi);
						
		}
		}
		else{
			Utils.log(this.getClass().getSimpleName()+":"," Name Value pair is null");
		}
		pi = new PropertyInfo();
		pi.setName(AuthenticationMobile.CliectAccessName);
		pi.setValue(AuthenticationMobile.CliectAccessId);
		pi.setType(String.class);
		request.addProperty(pi);
		
		Utils.log(this.getClass().getSimpleName()+":","METHOD:"+METHOD_NAME);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;

		envelope.implicitTypes = true;
		envelope.addMapping(WSDL_TARGET_NAMESPACE, "",
				this.getClass());
		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
		androidHttpTransport.debug = true;

		try {
			
			
			androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
			
		Utils.log(this.getClass().getSimpleName()+":","request:"+androidHttpTransport.requestDump);
		Utils.log(this.getClass().getSimpleName()+":","response:"+androidHttpTransport.responseDump);
		
		 response = envelope.getResponse().toString();
				
			
			return "ok";
		} catch (Exception e) {
			Utils.log(this.getClass().getSimpleName()+":","METHOD:"+METHOD_NAME);
			e.printStackTrace();
			return e.toString();
		}

	}

	public String getResponse(){
		return response;
	}
	

}
