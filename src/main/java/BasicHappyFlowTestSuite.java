import client.DummyClient;
import client.IClient;
import client.SocketClient;
import client.TestHttpClient;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import methods.client.*;
import methods.client.CloseAccountMethod;
import methods.client.CloseSavingsAccountMethod;
import methods.client.DepositIntoAccountMethod;
import methods.client.GetAuthTokenMethod;
import methods.client.GetBalanceMethod;
import methods.client.GetBankAccountAccessMethod;
import methods.client.GetOverdraftLimitMethod;
import methods.client.GetTransactionsMethod;
import methods.client.GetUserAccessMethod;
import methods.client.InvalidateCardMethod;
import methods.client.OpenAccountMethod;
import methods.client.OpenAdditionalAccountMethod;
import methods.client.OpenSavingsAccountMethod;
import methods.client.PayFromAccountMethod;
import methods.client.ProvideAccessMethod;
import methods.client.RevokeAccessMethod;
import methods.client.SetOverdraftLimitMethod;
import methods.client.SimulateTimeMethod;
import methods.client.TransferMoneyMethod;
import methods.client.UnblockCardMethod;
import models.AccountCardTuple;
import models.BankAccount;
import models.CustomerAccount;
import models.PinCard;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicHappyFlowTestSuite {

    public static void main(String[] args) {

        // Setup the Client here.
        IClient client = new DummyClient();

//        IClient client = new SocketClient();

        // Create CustomerAccount.
        CustomerAccount customer1 = new CustomerAccount("Duck", "Donald", "D", new Date().toString(),
                "571376046", "1313 Webfoot Walk, Duckburg",  "+316 12345678", "donald@gmail.com",
                "duckd", "kwikkwekkwak");
        CustomerAccount customer2 = new CustomerAccount("Duck", "Daisy", "D", new Date().toString(),
                "571376047", "1313 Webfoot Walk, Duckburg",  "+316 12345679", "daisy@gmail.com",
                "daisyduck", "donald");

        BankAccount bankAccount1 = null;
        BankAccount bankAccount2 = null;
        BankAccount bankAccount3 = null;

        PinCard card1 = null;
        PinCard card2 = null;
        PinCard card3 = null;
        PinCard card4 = null;

        AccountCardTuple tuple = null;

        JSONRPC2Request request;
        JSONRPC2Response response;
        Map<String, Object> parsedResponse;

        // Method 1. OpenAccount.
        System.out.println("-- OpenAccountMethod. Donald opens an account --");

        request = OpenAccountMethod.createRequest(customer1);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            tuple = OpenAccountMethod.parseResponse(parsedResponse, customer1);
            bankAccount1 = tuple.getAccount();
            card1 = tuple.getCard();
        }

        // Method 2. getAccountAuth.
        System.out.println("-- getAccountAuth Method. Donald logs in. --");

        request = GetAuthTokenMethod.createRequest(customer1);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            GetAuthTokenMethod.parseResponse(parsedResponse, customer1);
        }

        // Method 2. OpenAdditonalAccount.
        System.out.println("-- OpenAdditonalAccount Method. Donald wants a holiday savings account --");

        request = OpenAdditionalAccountMethod.createRequest(customer1);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            tuple = OpenAdditionalAccountMethod.parseResponse(parsedResponse, customer1);
            bankAccount2 = tuple.getAccount();
            card2 = tuple.getCard();
        }



        // Access Module
        System.out.println("--Open Account Method for Daisy--");

        request = OpenAccountMethod.createRequest(customer2);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            tuple = OpenAccountMethod.parseResponse(parsedResponse, customer2);
            bankAccount3 = tuple.getAccount();
            card3 = tuple.getCard();
        }

        System.out.println("-- Daisy logs in. --");

        request = GetAuthTokenMethod.createRequest(customer2);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            GetAuthTokenMethod.parseResponse(parsedResponse, customer2);
        }

        // ProvideAccessMethod
        System.out.println("-- ProvideAccessMethod. Donald shares access with Daisy--");

        request = ProvideAccessMethod.createRequest(customer1,bankAccount1,customer2);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            card4 = ProvideAccessMethod.parseResponse(parsedResponse,bankAccount1, customer2);
        }

        // DepositIntoAccount
        System.out.println("-- DepositIntoAccount. Donald deposits his salary--");

        request = DepositIntoAccountMethod.createRequest(bankAccount1, card1, 313);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            DepositIntoAccountMethod.parseResponse(parsedResponse);
        }

        // PayFromAccount
        System.out.println("-- PayFromAccount. Donald buys hot dogs --");

        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            PayFromAccountMethod.parseResponse(parsedResponse);
        }

        // TransferMoney
        System.out.println("-- TransferMoney. Daisy refunds Donald's hotdogs --");

        request = TransferMoneyMethod.createRequest(bankAccount3, bankAccount1, customer2, (12.3), "Moniez");
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            TransferMoneyMethod.parseResponse(parsedResponse);
        }
        
        // Extension 5 - Overdrafting
        System.out.println("-- Extension 5: Overdrafting --");
        System.out.println("-- Daisy wants to set her overdraft limit to 1000. --");

        request = SetOverdraftLimitMethod.createRequest(customer2, bankAccount3, 1000f);
        response = client.processRequest(request);

        // TransferMoney 2
        System.out.println("-- TransferMoney. Daisy transfers to Donald --");

        request = TransferMoneyMethod.createRequest(bankAccount3, bankAccount1, customer2, 200, "Moniez");
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            TransferMoneyMethod.parseResponse(parsedResponse);
        }

        // ObtainBalance
        System.out.println("-- Donald wants to obtain his balance. --");

        request = GetBalanceMethod.createRequest(customer1, bankAccount1);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            GetBalanceMethod.parseResponse(parsedResponse);
        }

        // getTransactionOverview
        System.out.println("-- Donald wants to get transaction overview --");

        request = GetTransactionsMethod.createRequest(customer1, bankAccount1, 2);
        response = client.processRequest(request);

        List<Map<String, Object>> namedArrayResults = null;
        if((namedArrayResults = checkArrayResponse(response)) != null){
            GetTransactionsMethod.parseResponse(namedArrayResults);
        }

        // GetUserAccess
        System.out.println("-- Donald wants to obtain his access. --");

        request = GetUserAccessMethod.createRequest(customer1);
        response = client.processRequest(request);

        if((namedArrayResults = checkArrayResponse(response)) != null){
            GetUserAccessMethod.parseResponse(namedArrayResults);
        }


        // GetBankAccountAccessMethod
        System.out.println("-- Donald wants to get bank account access list --");

        request = GetBankAccountAccessMethod.createRequest(customer1, bankAccount1);
        response = client.processRequest(request);

        if((namedArrayResults = checkArrayResponse(response)) != null){
            GetBankAccountAccessMethod.parseResponse(namedArrayResults);
        }

        // Extension 2. Test blocking and unblocking of PIN card.

        //
        System.out.println("-- 3x wrong pincode. Donald is screwed --");


        String pinCode = card1.getPinCode();
        card1.setPinCode(getInvalidPin(pinCode));

        // Attempt 1
        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        client.processRequest(request);

        // Attempt 2
        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        client.processRequest(request);

        // Attempt 3
        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        client.processRequest(request);


        System.out.println("-- 4th attmpt with correct Pin. Expect failure: --");
        card1.setPinCode(pinCode);

        // Attempt 4 - Should be blocked.
        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            PayFromAccountMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- Unblock Card: --");

        // Unblock card.
        request = UnblockCardMethod.createRequest(customer1, bankAccount1, card1);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            UnblockCardMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- 5th attmpt. Should Work again: --");
        card1.setPinCode(pinCode);

        // Attempt 5 - Should work again.
        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            PayFromAccountMethod.parseResponse(parsedResponse);
        }
        
        // Get Daisy's balance back to 0
        System.out.println("-- TransferMoney. Daisy buys baguettes --");

        request = TransferMoneyMethod.createRequest(bankAccount3, bankAccount1, customer2, (12.3), "Baguettes");
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            TransferMoneyMethod.parseResponse(parsedResponse);
        }


        System.out.println("-- Extension 3 - Invalidate Card --");
        // Extension 3 - Invalidate Card

        String oldPinCard = card1.getPinCardNumber();

        request = InvalidateCardMethod.createRequest(customer1, bankAccount1, card1, true);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            InvalidateCardMethod.parseResponse(parsedResponse, card1);
        }

        String  newPinCard = card1.getPinCardNumber();

        System.out.println("-- Attempt to use old PinCard. Expect Failure --");
        card1.setPinCardNumber(oldPinCard);

        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            PayFromAccountMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- Attempt to use new PinCard. Should Work --");
        card1.setPinCardNumber(newPinCard);

        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            PayFromAccountMethod.parseResponse(parsedResponse);
        }
        
        // Get Daisy's balance back to 0
        System.out.println("-- TransferMoney. Daisy buys milk --");

        request = TransferMoneyMethod.createRequest(bankAccount3, bankAccount1, customer2, (12.3), "Milk");
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            TransferMoneyMethod.parseResponse(parsedResponse);
        }

        // Extension 4 - Simulate Time
        System.out.println("-- Extension 4: SimulateTime. No response expected --");

        request = SimulateTimeMethod.createRequest(25);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            SimulateTimeMethod.parseResponse(parsedResponse);
        }


        // Extension 5 - Overdrafting Limit
        System.out.println("-- Extension 5: Overdrafting --");
        System.out.println("-- SetOverdraftingLimit to 4000. Should work. --");

        request = SetOverdraftLimitMethod.createRequest(customer2, bankAccount3, 4000f);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            SetOverdraftLimitMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- SetOverdraftingLimit to 10.000. Should Fail. Remain 4000 or goto 5000. --");

        request = SetOverdraftLimitMethod.createRequest(customer2, bankAccount3, 10000f);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            SetOverdraftLimitMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- Daisy wants bankruptcy. Lower balance to -1000. --");

        request = TransferMoneyMethod.createRequest(bankAccount3, bankAccount1, customer2, 800, "More Moniez");
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            DepositIntoAccountMethod.parseResponse(parsedResponse);
        }


        System.out.println("-- SimulateTime 366 days. Than check balance--");

        request = SimulateTimeMethod.createRequest(366);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            SimulateTimeMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- Check balance after a year. Should be +/-1100 --");
        // ObtainBalance
        request = GetBalanceMethod.createRequest(customer2, bankAccount3);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            GetBalanceMethod.parseResponse(parsedResponse);
        }

        // Extension 5 - Overdrafting Limit
        System.out.println("-- Extension 6: Savings Account --");
        System.out.println("-- OpenSavingsAccount --");
        request = OpenSavingsAccountMethod.createRequest(customer2, bankAccount3);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            OpenSavingsAccountMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- CloseSavingsAccount --");
        request = CloseSavingsAccountMethod.createRequest(customer2, bankAccount3);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            CloseSavingsAccountMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- Extension 7: Logging --");
        request = GetEventLogsMethod.createRequest("2017-7-17", "2017-8-17");
        response = client.processRequest(request);

        namedArrayResults = null;
        if((namedArrayResults = checkArrayResponse(response)) != null){
            GetTransactionsMethod.parseResponse(namedArrayResults);
        }

        ///------ TEAR DOWN TESTS.

        // First we progress time 2000 days. All cards should be expired.
        System.out.println("-- SimulateTime 2000 days to make sure all cards are expired --");

        request = SimulateTimeMethod.createRequest(2000);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            SimulateTimeMethod.parseResponse(parsedResponse);
        }

        System.out.println("-- Attempt to use pincard after expiration date. Should Fail--");
        card1.setPinCardNumber(newPinCard);

        request = PayFromAccountMethod.createRequest(bankAccount1, bankAccount3, card1, (12.3));
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            PayFromAccountMethod.parseResponse(parsedResponse);
        }





        // TEAR DOWN TESTS PART 2

        // RevokeAccessMethod
        System.out.println("-- RevokeAccessMethod. Donald revokes Daisy's access--");

        request = RevokeAccessMethod.createRequest(customer1,bankAccount1,customer2);
        response = client.processRequest(request);

        if((parsedResponse = checkResponse(response)) != null){
            RevokeAccessMethod.parseResponse(parsedResponse,bankAccount1, customer2);
        }

        // Method 3. Close both accounts.
        System.out.println("-- CloseAccount method. Donald closes all his Bank Accounts --");
        for(BankAccount bankAccount : customer1.getBankAccounts()){

            request = CloseAccountMethod.createRequest(customer1, bankAccount);
            response = client.processRequest(request);

            if((parsedResponse = checkResponse(response)) != null){
                CloseAccountMethod.parseResponse(parsedResponse);
            }
        }





    }

    private static String getInvalidPin(String pinCode) {
		int numPIN = Integer.parseInt(pinCode);
		if (numPIN == 9999) {
			numPIN--;
		} else {
			numPIN++;
		}
		
		String invalidPIN = Integer.toString(numPIN);
		
		if (invalidPIN.length() == 3) {
			invalidPIN = invalidPIN.concat("0");
		}
		
		return invalidPIN;
	}

	public static Map<String, Object> checkResponse(JSONRPC2Response respIn){
        Map<String, Object> namedResults = null;

        // Check for success or error
        if (!respIn.indicatesSuccess()) {

            System.out.println("The request failed :");

            JSONRPC2Error err = respIn.getError();

            System.out.println("\terror.code    : " + err.getCode());
            System.out.println("\terror.message : " + err.getMessage());
            System.out.println("\terror.data    : " + err.getData());

        }
        else {
            System.out.println("The request succeeded :");

            System.out.println("\tresult : " + respIn.getResult());
            System.out.println("\tid     : " + respIn.getID());

            namedResults
                    = (Map<String, Object>) respIn.getResult();


        }

        return namedResults;
    }

    public static List<Map<String, Object>> checkArrayResponse(JSONRPC2Response respIn){
        List<Map<String, Object>> namedArrayResults = null;

        // Check for success or error
        if (!respIn.indicatesSuccess()) {

            System.out.println("The request failed :");

            JSONRPC2Error err = respIn.getError();

            System.out.println("\terror.code    : " + err.getCode());
            System.out.println("\terror.message : " + err.getMessage());
            System.out.println("\terror.data    : " + err.getData());

        }
        else {
            System.out.println("The request succeeded :");

            System.out.println("\tresult : " + respIn.getResult());
            System.out.println("\tid     : " + respIn.getID());

             namedArrayResults
                    = (List<Map<String, Object>>) respIn.getResult();


        }

        return namedArrayResults;
    }
}
