package databaseClasses;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class InsertSessionQuestion {
	public InsertSessionQuestion()
	{
	}
	public String DoTheWork(String URL,String sessionID,String QuestionID)
	{
		 String result = "";
	        try {
	            final String NAMESPACE = "http://tempuri.org/";
	           
	            final String SOAP_ACTION = "http://tempuri.org/InsertSessionQuestion";
	            final String METHOD_NAME = "InsertSessionQuestion";
	            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

	            request.addProperty("sessionID",sessionID);
	            request.addProperty("QuestionID",QuestionID);
	            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            envelope.dotNet = true;
	            envelope.setOutputSoapObject(request);
	            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	            androidHttpTransport.call(SOAP_ACTION, envelope);
	            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
	            result = response.toString();


	        } catch (Exception e) {
	        	result = e.toString();
	        }
	        
	        return result;
	}
}