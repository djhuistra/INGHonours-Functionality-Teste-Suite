package methods.server;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import models.BankAccount;
import models.BankAccount;
import models.CustomerAccount;
import models.PinCard;

import java.util.*;

public class CloseAccountMethod {


    public static String parseRequest(JSONRPC2Request reqIn){
        boolean error = false;

        DummyServerDB db = DummyServerDB.getInstance();

        CustomerAccount customer = null;
        for(CustomerAccount account : db.getCustomers()){
            if(account.getAuthToken().equals((String) reqIn.getNamedParams().get("authToken"))){
                customer = account;
            }
        }

        if(customer==null){
            return new JSONRPC2Response(JSONRPC2Error.INVALID_REQUEST, reqIn.getID()).toString();
        }

        BankAccount bankAccount = null;
        for(BankAccount account : customer.getBankAccounts()){
            if(account.getiBAN().equals((String) reqIn.getNamedParams().get("iBAN"))){
                bankAccount = account;
            }
        }

        if(bankAccount==null){
            return new JSONRPC2Response(JSONRPC2Error.INVALID_REQUEST, reqIn.getID()).toString();
        }

        // Remove PIN CARD
        for (Iterator<PinCard> i = customer.getPinCards().iterator(); i.hasNext();) {
            PinCard element = i.next();
            if (element.getBankAccount().getiBAN().equals(bankAccount.getiBAN())) {
                i.remove();
            }
        }

        // Remove Bank account.
        customer.getBankAccounts().remove(bankAccount);

        // If last bank account. Remove Customer Account.
        if(customer.getBankAccounts().size()<1){
            db.getCustomers().remove(customer);
        }

        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("result", true);

        JSONRPC2Response response= new JSONRPC2Response(params, reqIn.getID());



        return response.toString();
    }
}
