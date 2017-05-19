# ING Honours project - Functionality test suite

## About
This project is meant for testing banking systems developed in the ING Honours project. It currently contains a very basic happy flow test suite to determine if all methods have been implemented and return the correct response in the default case. The plan is that this test suite will be extended to test not default cases and edge case scenarios.


## Setup
- This project has Java 7 code base and a JSON library imported through Maven.

- To initialize the project, first run maven to include the JSON library.

- Than the only thing it requires is an implementation of the client interface that tells the test suite how it should communicate with your server.

- Once you have informed the test suite of your client implementation you can simply run the test suite and observe the outputs to see the test results.

## Examples
- The project currently contains two examples. One for testing the banking system through a direct invocation of a server method to process requests and one that communicates with the server through a socket.
--  To run the direct invocation example. Setup the DummyClient as the client in the test suite and simply run the test suites main method.
-- To run the socket example. Setup the SocketClient as the client in the test suite. Than first startup the server by running SocketServer and than startup the TestSuite that will connect and communicate with the SocketServer.


### Warning
One final warning. The current test does not 100% match the current protocol specification because I want to update the protocol specification.
The main difference are as follows:
- All results are now named. This means that the getAuthToken en all methods returning true now return "authToken": "SomeLongAuthToken" and "result": true. The reason for this is that now all responses can be proccessed in the same way.
- Some small fixes. i.e. 'Amount' is capitalized in specification, should not be.

### Todos

 - Write MOAR Tests

License
----

MIT


**Free Software, Hell Yeah!**

