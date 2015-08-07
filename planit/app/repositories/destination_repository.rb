require 'httparty'
require_relative '../models/destination'

class DestinationRepository
  include HTTParty
  base_uri 'http://destinations-service.com'

  def get_all
    destinations = JSON.parse(self.class.get('/destinations').body)

    destinations.map do |destination|
      Destination.new(destination['city'], destination['country'])
    end
  end
end