require 'pact/consumer/rspec'

Pact.service_consumer 'PlanIt' do
  has_pact_with 'Destinations Service' do
    mock_service(:destinations_service) { port 1234 }
  end
end