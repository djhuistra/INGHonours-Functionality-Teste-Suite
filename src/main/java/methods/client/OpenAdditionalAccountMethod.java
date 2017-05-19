package methods.client;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import models.AccountCardTuple;
import models.BankAccount;
import models.CustomerAccount;
import models.PinCard;

import java.util.HashMap;
import java.util.Map;

public class OpenAdditionalAccountMethod {

    public static JSONRPC2Request createRequest(CustomerAccount customerAccount){
        // The remote method to call
        String method = "openAdditionalAccount";

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

    public static AccountCardTuple parseResponse(Map<String, Object> namedResults, CustomerAccount customerAccount){

        // Assume everything went right.
        BankAccount bankAccount = new BankAccount((String) namedResults.get("iBAN"));
        customerAccount.addBankAccount(bankAccount);

        PinCard pinCard = new PinCard(bankAccount,
                ((Long) namedResults.get("pinCard")).intValue(),
                ((Long) namedResults.get("pinCode")).intValue()
        );
        customerAccount.addPinCard(pinCard);

        return new AccountCardTuple(bankAccount,pinCard);
    }
}
