class DestinationsController < ActionController::Base
  def index
    render json: Destination.all
  end
end