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