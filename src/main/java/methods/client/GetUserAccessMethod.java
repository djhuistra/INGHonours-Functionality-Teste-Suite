package methods.client;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import models.BankAccount;
import models.CustomerAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUserAccessMethod {

    public static JSONRPC2Request createRequest(CustomerAccount customerAccount){
        // The remote method to call
        String method = "getUserAccess";

        // The required named parameters to pass
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("authToken", customerAccount.getAuthToken());

        // The mandatory request ID
        String id = "req-001";

        // Create a new JSON-RPC 2.0 request
        JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);

        // Serialise the request to a JSON-encoded string
        String jsonString = reqOut.toString();

        return reqOut;
    }

    public static void parseResponse(List<Map<String, Object>> namedResults){


        // Assume everything went right.
        // Do nothing. Because if there are no errors the result is true.
    }
}
