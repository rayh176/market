# marketprice

## JDK
Expected to use JDK 11 or please change the pom xml maven properties to reflect the version you use

## How it works
	1. Data received by PricefeedListener
	2. String data is sent to CsvTransformer to transform to price feed [ Market Data]
    3. Every PriceFeed is sent to inMoemory data base for storage, ideally I would preferred to use Chronical queue or similar techs
    4. Data is retrieved from the database by the service modified and dispatched to the controller
    5. I added a swagger support so request for data is simplified and viewed via UI

    

## How to run it
   1. Start the LiquidityMarketApplication either from your preferred IDE 
      Or from command line make sure you have compiled the project with the right JDK version
      goto web-app/ target folder and run java -jar web-app-1.0-SNAPSHOT-spring-boot.jar
   2. Open http://localhost:8081/swagger-ui.html and navigate to [findAll] 
   3. Select the [try out] option
   4. Similar data to the below is received
      [
      {
      "sequenceId": 106,
      "ccyPair": {
      "ccypair": "EUR/USD"
      },
      "bid": 1.1,
      "ask": 1.2,
      "timeStamp": "2020-06-01T12:01:01.001"
      }
      ]

## Suitable test
      In order to view a test that print latest price, i.e. a client that gets, then prints out to console.
      Please run LiquidityMarketApplicationTest

## Note
   1. Multi module structure is preferred choice
	