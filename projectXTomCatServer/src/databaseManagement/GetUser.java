package databaseManagement;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


public class GetUser {
	
	public GetUser()
	{
	}
	
	public String[] DoTheWork(String URL,String UserID)
	{
        final String NAMESPACE = "http://tempuri.org/";
        final String SOAP_ACTION = "http://tempuri.org/GetUser";
        final String METHOD_NAME = "GetUser";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("UserID",UserID);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
        	androidHttpTransport.call(SOAP_ACTION, envelope);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SoapObject response = null;
		try {
			response = (SoapObject)envelope.getResponse();
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int size = response.getPropertyCount();
		String[] data = null;
         if(size > 0)
         {
        	 data = new String[size];
             for(int i = 0; i<size; i++)
             {
            	 data[i] = response.getProperty(i).toString();
             }
         }
         return data;
	}
}