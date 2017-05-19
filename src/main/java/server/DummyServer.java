package server;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import methods.server.*;

public class DummyServer {

    public static String processRequest(String request) {

        System.out.println("Server. ProcessRequest: "+request);
        // Parse request string
        JSONRPC2Request reqIn = null;

        try {
            reqIn = JSONRPC2Request.parse(request);

        } catch (JSONRPC2ParseException e) {
            System.out.println(e.getMessage());
            return new JSONRPC2Response(JSONRPC2Error.PARSE_ERROR).toString();
        }

        String response = null;

        switch(reqIn.getMethod()){
            case "openAccount": {
                response = OpenAccountMethod.parseRequest(reqIn);
                break;
            }
            case "openAdditionalAccount": {
                response = OpenAdditionalAccountMethod.parseRequest(reqIn);
                break;
            }
            case "closeAccount": {
                response = CloseAccountMethod.parseRequest(reqIn);
                break;
            }
            case "provideAccess": {
                response = ProvideAccessMethod.parseRequest(reqIn);
                break;
            }
            case "revokeAccess": {
                response = RevokeAccessMethod.parseRequest(reqIn);
                break;
            }
            case "depositIntoAccount": {
                response = DepositIntoAccountMethod.parseRequest(reqIn);
                break;
            }
            case "payFromAccount": {
                response = PayFromAccountMethod.parseRequest(reqIn);
                break;
            }
            case "transferMoney": {
                response = TransferMoneyMethod.parseRequest(reqIn);
                break;
            }
            case "getAuthToken": {
                response = GetAuthTokenMethod.parseRequest(reqIn);
                break;
            }
            default:{
                return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, reqIn.getID()).toString();
            }
        }


        return response;
    }




}
