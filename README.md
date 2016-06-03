# Consumer-driven contracts

This repository demonstrates the use of consumer-driven contracts to validate that cross-microservice interactions are working as expected.

Consumer-driven contracts enable service consumers to hit fake provider services in their tests that are guaranteed to work -- no worrying about uptime, flakiness, or provider contract violations breaking consumer tests. At the same time, this testing approach enables teams that own providers to validate that they are not violating consumers' expectations before every push and enables them to refactor their API with knowledge of how they are being consumed.

This repository contains one application (PlanIt) and two services (activities and destinations). The PlanIt application and the activities service both depend on parts of the API presented by the destinations service.

## PlanIt application

##### planit/spec/respositories/destination_respository_spec.rb

PlanIt's destination repository test validates that it can deserialize a list of destinations, making an assumption that the destination service behaves as expected.

```ruby
describe DestinationRepository, pact: true do
  before do
    DestinationRepository.base_uri 'localhost:1234'
  end

  describe 'get_all' do
    let(:destination_repository) { DestinationRepository.new }

    before do
      destinations_service.given('two destinations exist').
        upon_receiving('request for all destinations').
        with(method: :get, path: '/destinations', query: '').
        will_respond_with(
          status: 200,
          body: [
            {
              city: 'Miami',
              country: 'United States'
            },
            {
              city: 'Madrid',
              country: 'Spain'
            }
          ]
        )
    end

    it 'returns two cities' do
      all_destinations = destination_repository.get_all
      miami = all_destinations[0]
      madrid = all_destinations[1]

      expect(miami.city).to eq('Miami')
      expect(miami.country).to eq('United States')

      expect(madrid.city).to eq('Madrid')
      expect(madrid.country).to eq('Spain')
    end
  end
end
```

##### planit/spec/pacts/planit-destinations_service.json

The assumption that PlanIt's destination repository test makes about the destinations service's behavior generates this pact.

```json
{
  "consumer": {
    "name": "PlanIt"
  },
  "provider": {
    "name": "Destinations Service"
  },
  "interactions": [
    {
      "description": "request for all destinations",
      "provider_state": "two destinations exist",
      "request": {
        "method": "get",
        "path": "/destinations",
        "query": ""
      },
      "response": {
        "status": 200,
        "headers": {
        },
        "body": [
          {
            "city": "Miami",
            "country": "United States"
          },
          {
            "city": "Madrid",
            "country": "Spain"
          }
        ]
      }
    }
  ]
}
```

## Activities service

##### activities/src/test/java/io/pivotal/activities/repositories/DestinationRepositoryTest.java

Similarly, the activities service's destination repository test validates that it can deserialize a single destination, making an assumption that the destination service behaves as expected.

```java
public class DestinationRepositoryTest extends TestBase {
    private DestinationRepository destinationRepository;

    @Before
    public void setup() {
        destinationRepository = new DestinationRepository();
    }

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("destinations_service", "localhost", 4321, this);

    @Pact(provider = "destinations_service", consumer = "activities_service")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider defineContract) {
        return defineContract
            .given("a destination exists with id 456")
            .uponReceiving("a request for destination with id 456")
                .path("/destinations/456")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body("{ \"city\": \"Paris\" }").toFragment();
    }

    @Test
    @PactVerification("destinations_service")
    public void get_fetchesTheDestinationWithTheGivenId() throws IOException {
        Destination destination = destinationRepository.get(456l);
        assertThat(destination.getCity()).isEqualTo("Paris");
    }
}
```

##### activities/target/pacts/activities_service-destinations_service.json

The assumption that the activities service's destination repository test makes about the destinations service's behavior generates this pact.

```json
{
  "provider" : {
    "name" : "destinations_service"
  },
  "consumer" : {
    "name" : "activities_service"
  },
  "interactions" : [ {
    "providerState" : "a destination exists with id 456",
    "description" : "a request for destination with id 456",
    "request" : {
      "method" : "GET",
      "path" : "/destinations/456"
    },
    "response" : {
      "status" : 200,
      "body" : {
        "city" : "Paris"
      }
    }
  } ]
}
```

## Destinations service

It is the destinations service's responsibility to honor the pacts created by its consumers. Running `rake pact:verify` will spin up the destinations service, hit it with the requests in every pact that it honors, and validate that it gets the expected responses.

##### destinations/spec/service_consumers/pact_helper.rb

```ruby
Pact.service_provider 'Destinations Service' do
  honours_pact_with('PlanIt') { pact_uri '../planit/spec/pacts/planit-destinations_service.json' }
  honours_pact_with('Activities Service') { pact_uri '../activities/target/pacts/activities_service-destinations_service.json' }
end
```

##### destinations/spec/service_consumers/provider_states_for_planit.rb

The GET request for /destinations made by PlanIt assumes that 'two destinations exist', so we create a provider state to match.

```ruby
Pact.provider_states_for 'PlanIt' do
  provider_state 'two destinations exist' do
    set_up do
      Destination.create({city: 'Miami', country: 'United States', rating: 10})
      Destination.create({city: 'Madrid', country: 'Spain', rating: 8})
    end

    tear_down do
      Destination.destroy_all
    end
  end
end
```

##### destinations/spec/service_consumers/provider_states_for_activities_service.rb

The GET request for /destinations/456 made by the activities service assumes that 'a destination exists with id 456', so we create a provider state for that as well.

```ruby
Pact.provider_states_for 'activities_service' do
  provider_state 'a destination exists with id 456' do
    set_up do
      Destination.create({id: 456, city: 'Paris', country: 'United States', rating: 10})
    end

    tear_down do
      Destination.destroy_all
    end
  end
end
```
