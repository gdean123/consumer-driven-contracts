require 'pact/provider/rspec'
require_relative 'provider_states_for_planit'
require_relative 'provider_states_for_activities_service'

Pact.service_provider 'Destinations Service' do
  honours_pact_with('PlanIt') { pact_uri '../planit/spec/pacts/planit-destinations_service.json' }
  honours_pact_with('Activities Service') { pact_uri '../activities/target/pacts/activities_service-destinations_service.json' }
end