require 'pact/provider/rspec'
require_relative 'provider_states_for_planit'

Pact.service_provider 'Destinations Service' do
  honours_pact_with 'PlanIt' do
    pact_uri '../planit/spec/pacts/planit-destinations_service.json'
  end
end