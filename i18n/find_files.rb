require 'find'

ALLOW_LIST_PATHS = /.*\.rb/
DENY_LIST_PATHS = /\/spec\/|\/cache\//

def get_all_files(path)
  all_files = []
  Find.find(path) do |path|
    if path =~ ALLOW_LIST_PATHS
      if !(path =~ DENY_LIST_PATHS)
        all_files << path
      end
    end
  end
  all_filet
  s
end

def find_strings_files(path, out)
  string_fo = File.open(out, 'wt+')
  get_all_files(path).each do |filepath|
    full_filepath = filepath.chomp
    search_fo = File.open(full_filepath)
    lines = search_fo.readlines
    if has_string?(lines)
      string_fo.puts(full_filepath)
    end
  end
end

def has_string?(lines)
  lines.each do |line|
    if line.match?(/((?<!Rollbar\.error|info\(|Rails\.logger\.info\s)+"[A-Z][a-z].*\s[a-z]+")/)
      return true
    elsif line.match?(/((?<!Rollbar\.error|info\(|Rails\.logger\.info\s)+"[A-Z][a-z]+"+(?!\s=>))/)
      return true
    end
  end
  false
end

path = "/Users/mukundkumar/carrot/customers/instacart"
out = "checkout_strings.txt"
find_strings_files(path, out)