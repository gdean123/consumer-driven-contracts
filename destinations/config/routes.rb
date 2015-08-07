Rails.application.routes.draw do
  resources :destinations, only: [:index]
end