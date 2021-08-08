require "/Users/mukundkumar/github/galeej/i18n/file_parser.rb"

include FileParser

files = %w[
/Users/mukundkumar/carrot/customers/instacart/dependencies/orders-shared/app/services/orders/creation_service/related_account_validator.rb
]

files.each do |file_name|
  replace_strings_in_file(file_name)
end
