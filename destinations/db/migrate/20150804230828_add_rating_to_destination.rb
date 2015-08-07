class AddRatingToDestination < ActiveRecord::Migration
  def change
    add_column :destinations, :rating, :integer
  end
end
