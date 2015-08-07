class Destination
  attr_reader :city, :country

  def initialize(city, country)
    @city = city
    @country = country
  end
end