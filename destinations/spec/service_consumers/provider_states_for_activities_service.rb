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