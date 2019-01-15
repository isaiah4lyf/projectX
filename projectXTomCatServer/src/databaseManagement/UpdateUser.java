package databaseManagement;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UpdateUser {
	public UpdateUser()
	{
	}
	public String do_The_Work(String URL,String UserID,String UserName,String Password, String FirstName, String LastName, String Email, String PhoneNumber, String WeekDaysScore, String WeekendScore, String TotalScore)
	{
		 String address = "";
	        try {
	            final String NAMESPACE = "http://tempuri.org/";
	            final String SOAP_ACTION = "http://tempuri.org/UpdateUser";
	            final String METHOD_NAME = "UpdateUser";
	            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	            request.addProperty("UserID",UserID);
	            request.addProperty("UserName",UserName);
	            request.addProperty("Password",Password);
	            request.addProperty("FirstName",FirstName);
	            request.addProperty("LastName",LastName);
	            request.addProperty("Email",Email);
	            request.addProperty("PhoneNumber",PhoneNumber);
	            request.addProperty("WeekDaysScore",WeekDaysScore);
	            request.addProperty("WeekendScore",WeekendScore);
	            request.addProperty("TotalScore",TotalScore);

	            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            envelope.dotNet = true;
	            envelope.setOutputSoapObject(request);
	            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	            androidHttpTransport.call(SOAP_ACTION, envelope);
	            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
	            address = response.toString();


	        } catch (Exception e) {
	            address = e.getLocalizedMessage();
	        }
	        
	        return address;
	}
}