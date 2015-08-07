class CreateDestinations < ActiveRecord::Migration
  def change
    create_table :destinations do |table|
      table.string :city
      table.string :country
    end
  end
end
