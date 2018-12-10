package databaseClasses;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UpdateSessionData {
	public UpdateSessionData()
	{
	}
	public String DoTheWork(String URL,String SessionID,String CurrentQuestion,String CurrentAnswer,String CurrentRemainingTime, String CurrentNumberOfUsers)
	{
		 String result = "";
	        try {
	            final String NAMESPACE = "http://tempuri.org/";
	           
	            final String SOAP_ACTION = "http://tempuri.org/UpdateSessionData";
	            final String METHOD_NAME = "UpdateSessionData";
	            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

	            request.addProperty("SessionID",SessionID);
	            request.addProperty("CurrentQuestion",CurrentQuestion);
	            request.addProperty("CurrentAnswer",CurrentAnswer);
	            request.addProperty("CurrentRemainingTime",CurrentRemainingTime);
	            request.addProperty("CurrentNumberOfUsers",CurrentNumberOfUsers);

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
