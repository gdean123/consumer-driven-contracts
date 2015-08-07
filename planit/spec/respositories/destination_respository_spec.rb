require 'service_providers/destinations_service'
require 'app/repositories/destination_repository'

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