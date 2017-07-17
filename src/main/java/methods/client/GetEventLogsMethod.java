package methods.client;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import java.util.HashMap;
import java.util.Map;

public class GetEventLogsMethod {

    public static JSONRPC2Request createRequest(String beginDate, String endDate){
        // The remote method to call
        String method = "getEventLogs";

        // The required named parameters to pass
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);

        // The mandatory request ID
        String id = "req-001";

        // Create a new JSON-RPC 2.0 request
        JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);

        // Serialise the request to a JSON-encoded string
        String jsonString = reqOut.toString();

        return reqOut;
    }

    public static void parseResponse(Map<String, Object> namedResults){


        // Assume everything went right.
        // Do nothing. Because if there are no errors the result is true.
    }
}
